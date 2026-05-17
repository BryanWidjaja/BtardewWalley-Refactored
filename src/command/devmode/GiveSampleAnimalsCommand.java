package command.devmode;

import command.Command;
import viewmodel.MapViewModel;

public class GiveSampleAnimalsCommand implements Command {
    private static final String[] SAMPLE_TYPES = {
            "Chicken", "Chicken", "Chicken",
            "Sheep", "Sheep", "Sheep",
            "Cow", "Cow", "Cow"
    };
    private static final int NAME_SUFFIX_MODULO = 3;

    private final MapViewModel mapViewModel;

    public GiveSampleAnimalsCommand(MapViewModel mapViewModel) {
        this.mapViewModel = mapViewModel;
    }

    @Override
    public void execute() {
        for (int i = 0; i < SAMPLE_TYPES.length; i++) {
            String type = SAMPLE_TYPES[i];
            mapViewModel.insertAnimal(type, type + (i % NAME_SUFFIX_MODULO + 1));
        }
    }
}
