package viewmodel;

import java.util.List;
import java.util.stream.Collectors;

import model.PlayerItem;
import model.animal.Animal;
import model.item.AnimalProduct;
import model.item.FarmProduct;
import model.item.Item;
import model.item.PlantSeed;
import model.item.Tool;
import services.StoreService;
import ui.view.MapBoard;
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
        return storeService.getTools().stream()
                .filter(tool -> !playerViewModel.hasItem(tool.getName()))
                .collect(Collectors.toList());
    }

    public Tool getTool(int choice) {
        List<Tool> tools = getAvailableTools();
        if (choice < 1 || choice > tools.size()) {
            return null;
        }
        return tools.get(choice - 1);
    }

    public boolean buyTool(int choice) {
        Tool selectedTool = getTool(choice);
        if (selectedTool == null) {
            return false;
        }

        int price = (int) selectedTool.getPrice();
        if (playerViewModel.spendMoney(price)) {
            playerViewModel.addItem(new Tool(selectedTool.getName(), price), 1);
            return true;
        }
        return false;
    }

    public List<PlantSeed> getAvailableSeeds() {
        return storeService.getSeeds();
    }

    public PlantSeed getSeed(int choice) {
        List<PlantSeed> seeds = getAvailableSeeds();
        if (choice < 1 || choice > seeds.size()) {
            return null;
        }
        return seeds.get(choice - 1);
    }

    public boolean buySeed(int choice, int quantity) {
        PlantSeed selectedSeed = getSeed(choice);
        if (selectedSeed == null) {
            return false;
        }

        double totalPrice = selectedSeed.getPrice() * quantity;
        if (playerViewModel.spendMoney(totalPrice)) {
            playerViewModel.addItem(selectedSeed, quantity);
            return true;
        }
        return false;
    }

    public List<Animal> getAvailableAnimals() {
        return storeService.getAnimals();
    }

    public Animal getAnimal(int choice) {
        List<Animal> animals = getAvailableAnimals();
        if (choice < 1 || choice > animals.size()) {
            return null;
        }
        return animals.get(choice - 1);
    }

    public boolean buyAnimal(int choice, String name) {
        Animal selectedAnimal = getAnimal(choice);
        if (selectedAnimal == null) {
            return false;
        }

        if (playerViewModel.spendMoney(selectedAnimal.getPrice())) {
            mapViewModel.insertAnimal(selectedAnimal.getType(), name);
            return true;
        }
        return false;
    }

    public boolean sellAnimal(int choice) {
        List<Animal> animals = playerViewModel.getAnimals();
        if (choice < 1 || choice > animals.size()) {
            return false;
        }

        Animal selectedAnimal = animals.get(choice - 1);
        playerViewModel.addMoney(selectedAnimal.getPrice());
        MapBoard.clearAnimalAt(selectedAnimal.getPosition().getX(), selectedAnimal.getPosition().getY());
        animals.remove(selectedAnimal);
        return true;
    }

    public boolean sellAnimalProduct(int displayChoice, int quantity) {
        return sellProduct(displayChoice, quantity, AnimalProduct.class);
    }

    public boolean sellFarmProduct(int displayChoice, int quantity) {
        return sellProduct(displayChoice, quantity, FarmProduct.class);
    }

    private <T extends Item> boolean sellProduct(int displayChoice, int quantity, Class<T> type) {
        int selectedIndex = playerViewModel.findItemIndex(type, displayChoice);
        if (selectedIndex == -1) {
            return false;
        }

        PlayerItem selectedItem = playerViewModel.getInventory().get(selectedIndex);
        playerViewModel.addMoney(sellingPriceOf(selectedItem.getItem()) * quantity);
        playerViewModel.removeItemQuantity(selectedIndex, quantity);
        return true;
    }

    private double sellingPriceOf(Item item) {
        if (item instanceof AnimalProduct) {
            return ((AnimalProduct) item).getSellingPrice();
        }
        if (item instanceof FarmProduct) {
            return ((FarmProduct) item).getSellingPrice();
        }
        return item.getPrice();
    }
}
