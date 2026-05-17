package command.devmode;

import command.Command;
import model.User;

public class DevModeGuardCommand implements Command {
    private User user;
    private Runnable action;

    public DevModeGuardCommand(User user, Runnable action) {
        this.user = user;
        this.action = action;
    }

    @Override
    public void execute() {
        action.run();
    }

    @Override
    public boolean canExecute() {
        return user != null && user.isDevMode();
    }
}
