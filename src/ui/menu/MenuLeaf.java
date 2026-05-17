package ui.menu;

import command.Command;

public class MenuLeaf implements MenuComponent {
    private String label;
    private Command command;

    public MenuLeaf(String label, Command command) {
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
