package command;

import model.User;

public class DevModeCommand implements Command {
    private User user;
    private Runnable action;

    public DevModeCommand(User user, Runnable action) {
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
