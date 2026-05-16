package command.devmode;

import command.Command;
import model.item.AnimalProduct;
import model.item.AnimalProductGrade;
import util.GradeUtils;
import viewmodel.PlayerViewModel;

public class GiveAnimalProductsCommand implements Command {
    private static final int EGG_PRICE = 100;
    private static final int MILK_PRICE = 300;
    private static final int WOOL_PRICE = 800;

    private final PlayerViewModel playerViewModel;
    private final GradeUtils gradeUtils;

    public GiveAnimalProductsCommand(PlayerViewModel playerViewModel, GradeUtils gradeUtils) {
        this.playerViewModel = playerViewModel;
        this.gradeUtils = gradeUtils;
    }

    @Override
    public void execute() {
        AnimalProductGrade grade = gradeUtils.getGrade(playerViewModel.getDay());
        playerViewModel.addItem(new AnimalProduct("Egg", EGG_PRICE, grade), 1);
        playerViewModel.addItem(new AnimalProduct("Milk", MILK_PRICE, grade), 1);
        playerViewModel.addItem(new AnimalProduct("Wool", WOOL_PRICE, grade), 1);
    }
}
