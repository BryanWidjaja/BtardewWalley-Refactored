package ui.menus;

import commands.Command;

public class MenuItem implements MenuComponent {
    private String label;
    private Command command;

    public MenuItem(String label, Command command) {
        this.label = label;
        this.command = command;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void render() { 
    }

    @Override
    public void handleInput(String input) {
        if (command != null && command.canExecute()) {
            command.execute();
        }
    }
}
