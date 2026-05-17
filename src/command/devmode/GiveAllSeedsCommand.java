package command.devmode;

import command.Command;
import model.item.plantseed.PlantSeed;
import store.GameCatalog;
import viewmodel.PlayerViewModel;

public class GiveAllSeedsCommand implements Command {
    private static final int SEED_QUANTITY = 10;

    private final PlayerViewModel playerViewModel;

    public GiveAllSeedsCommand(PlayerViewModel playerViewModel) {
        this.playerViewModel = playerViewModel;
    }

    @Override
    public void execute() {
        for (PlantSeed seed : GameCatalog.getSeeds()) {
            playerViewModel.addItem(
                new PlantSeed(seed.getName(), seed.getPrice(), seed.getSymbol(), seed.getGrowthTime()),
                SEED_QUANTITY
            );
        }
    }
}
