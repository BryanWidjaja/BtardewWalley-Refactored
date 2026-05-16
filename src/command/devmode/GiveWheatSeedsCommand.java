package command.devmode;

import command.Command;
import model.item.PlantSeed;
import viewmodel.PlayerViewModel;

public class GiveWheatSeedsCommand implements Command {
    private static final String SEED_NAME = "Wheat";
    private static final double SEED_PRICE = 50;
    private static final char SEED_SYMBOL = 'w';
    private static final int SEED_GROWTH_TIME = 3;
    private static final int SEED_QUANTITY = 10;

    private final PlayerViewModel playerViewModel;

    public GiveWheatSeedsCommand(PlayerViewModel playerViewModel) {
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        playerViewModel.addItem(
                new PlantSeed(SEED_NAME, SEED_PRICE, SEED_SYMBOL, SEED_GROWTH_TIME),
                SEED_QUANTITY);
    }
}
