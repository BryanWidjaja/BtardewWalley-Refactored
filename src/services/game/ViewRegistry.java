package services.game;

import java.util.Scanner;

import ui.view.AnimalHarvestView;
import ui.view.AnimalStoreView;
import ui.view.FarmStoreView;
import ui.view.InventoryView;
import ui.view.PlantView;
import ui.view.SleepView;
import ui.view.ToolStoreView;
import util.ConsoleUtils;

public class ViewRegistry {
    private final ToolStoreView toolStore;
    private final AnimalStoreView animalStore;
    private final FarmStoreView farmStore;
    private final SleepView sleep;
    private final PlantView plant;
    private final AnimalHarvestView harvest;
    private final InventoryView inventory;

    public ViewRegistry(Scanner scanner, ConsoleUtils consoleUtils) {
        this.toolStore = new ToolStoreView(scanner, consoleUtils);
        this.animalStore = new AnimalStoreView(scanner, consoleUtils);
        this.farmStore = new FarmStoreView(scanner, consoleUtils);
        this.sleep = new SleepView(scanner, consoleUtils);
        this.plant = new PlantView(scanner, consoleUtils);
        this.harvest = new AnimalHarvestView(scanner, consoleUtils);
        this.inventory = new InventoryView(scanner, consoleUtils);
    }

    public ToolStoreView getToolStore() {
        return toolStore;
    }

    public AnimalStoreView getAnimalStore() {
        return animalStore;
    }

    public FarmStoreView getFarmStore() {
        return farmStore;
    }

    public SleepView getSleep() {
        return sleep;
    }

    public PlantView getPlant() {
        return plant;
    }

    public AnimalHarvestView getHarvest() {
        return harvest;
    }

    public InventoryView getInventory() {
        return inventory;
    }
}
