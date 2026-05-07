package viewmodel;

import java.util.List;

import models.Animal;
import models.PlayerItem;
import models.items.AnimalProduct;
import models.items.FarmProduct;
import models.items.PlantSeed;
import models.items.Tool;
import services.StoreService;
import ui.views.GameMapView;
import util.FreshnessUtils;
import util.GradeUtils;

public class StoreViewModel {
    private StoreService storeService;
    private PlayerViewModel playerViewModel;
    private MapViewModel mapViewModel;
    private GradeUtils gradeUtils;

    public StoreViewModel(StoreService storeService, PlayerViewModel playerViewModel, MapViewModel mapViewModel, GradeUtils gradeUtils) {
        this.storeService = storeService;
        this.playerViewModel = playerViewModel;
        this.mapViewModel = mapViewModel;
        this.gradeUtils = gradeUtils;
    }

    public GradeUtils getGradeUtils() {
        return gradeUtils;
    }

    public List<Tool> getAvailableTools() {
        return storeService.getTools();
    }

    public boolean buyTool(int choice) {
        List<Tool> tools = getAvailableTools();
        if (choice < 1 || choice > tools.size()) return false;

        Tool selectedTool = tools.get(choice - 1);
        int price = (int) selectedTool.getPrice();

        if (playerViewModel.spendMoney(price)) {
            playerViewModel.addItem(new Tool(selectedTool.getName(), price), 1);
            tools.remove(selectedTool);
            return true;
        }
        return false;
    }

    public boolean buyTool(String name) {
        Tool tool = storeService.findTool(name);
        if (tool == null) return false;
        if (!playerViewModel.spendMoney(tool.getPrice())) return false;
        playerViewModel.addItem(new Tool(tool.getName(), (int) tool.getPrice()), 1);
        storeService.removeTool(tool);
        return true;
    }

    public List<PlantSeed> getAvailableSeeds() {
        return storeService.getSeeds();
    }

    public boolean buySeed(int choice, int quantity) {
        List<PlantSeed> seeds = getAvailableSeeds();
        if (choice < 1 || choice > seeds.size()) return false;

        PlantSeed selectedSeed = seeds.get(choice - 1);
        double totalPrice = selectedSeed.getPrice() * quantity;

        if (playerViewModel.spendMoney(totalPrice)) {
            playerViewModel.addItem(selectedSeed, quantity);
            return true;
        }
        return false;
    }

    public boolean buySeed(String name, int quantity) {
        PlantSeed seed = storeService.findSeed(name);
        if (seed == null) return false;
        double totalPrice = seed.getPrice() * quantity;
        if (!playerViewModel.spendMoney(totalPrice)) return false;
        playerViewModel.addItem(seed, quantity);
        return true;
    }

    public List<Animal> getAvailableAnimals() {
        return storeService.getAnimals();
    }

    public boolean buyAnimal(int choice, String name) {
        List<Animal> animals = getAvailableAnimals();
        if (choice < 1 || choice > animals.size()) return false;

        Animal selectedAnimal = animals.get(choice - 1);

        if (playerViewModel.spendMoney(selectedAnimal.getPrice())) {
            mapViewModel.insertAnimal(selectedAnimal.getType(), name);
            return true;
        }
        return false;
    }

    public boolean buyAnimal(String type, String name) {
        Animal template = storeService.findAnimalTemplate(type);
        if (template == null) return false;
        if (!playerViewModel.spendMoney(template.getPrice())) return false;
        mapViewModel.insertAnimal(type, name);
        return true;
    }

    public boolean sellAnimal(int choice) {
        List<Animal> animals = playerViewModel.getAnimals();
        if (choice < 1 || choice > animals.size()) return false;

        Animal selectedAnimal = animals.get(choice - 1);
        playerViewModel.addMoney(selectedAnimal.getPrice());
        GameMapView.ANIMAL_FARM_MAP[selectedAnimal.getPosition().getX()]
                                   [selectedAnimal.getPosition().getY()] = ' ';
        animals.remove(selectedAnimal);
        return true;
    }

    public boolean sellAnimalProduct(int displayChoice, int quantity) {
        int selectedIndex = playerViewModel.findAnimalProductIndex(displayChoice);
        if (selectedIndex == -1) return false;

        PlayerItem selectedItem = playerViewModel.getInventory().get(selectedIndex);
        AnimalProduct product = (AnimalProduct) selectedItem.getItem();

        playerViewModel.addMoney(
                product.getPrice() * gradeUtils.getGradeMultiplier(product.getGrade()) * quantity
        );
        playerViewModel.removeItemQuantity(selectedIndex, quantity);
        return true;
    }

    public boolean sellFarmProduct(int displayChoice, int quantity) {
        int selectedIndex = playerViewModel.findFarmProductIndex(displayChoice);
        if (selectedIndex == -1) return false;

        PlayerItem selectedItem = playerViewModel.getInventory().get(selectedIndex);
        FarmProduct product = (FarmProduct) selectedItem.getItem();

        playerViewModel.addMoney(
                product.getPrice() * FreshnessUtils.getFreshnessMultiplier(product.getFreshness()) * 2 * quantity
        );
        playerViewModel.removeItemQuantity(selectedIndex, quantity);
        return true;
    }
}
