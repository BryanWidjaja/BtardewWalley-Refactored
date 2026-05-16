package ui.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuComposite implements MenuComponent {
    private final String label;
    private final List<MenuComponent> children;

    public MenuComposite(String label) {
        this.label = label;
        this.children = new ArrayList<>();
    }

    @Override
    public String getLabel() {
        return label;
    }

    public List<MenuComponent> getChildren() {
        return children;
    }

    public int size() {
        return children.size();
    }

    public int exitOption() {
        return children.size();
    }

    public void add(MenuComponent component) {
        children.add(component);
    }

    public void remove(MenuComponent component) {
        children.remove(component);
    }

    @Override
    public void render() {
        render(null);
    }

    public void render(Runnable afterLabel) {
        System.out.println(label);
        if (afterLabel != null) {
            afterLabel.run();
        }
        for (int i = 0; i < children.size(); i++) {
            System.out.println((i + 1) + ". " + children.get(i).getLabel());
        }
        System.out.print(">> ");
    }

    @Override
    public void handleInput(String input) {
        int choice;
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a number.");
            return;
        }
        if (choice < 1 || choice > children.size()) {
            System.out.println("Choice out of range.");
            return;
        }
        children.get(choice - 1).handleInput(input);
    }
}
