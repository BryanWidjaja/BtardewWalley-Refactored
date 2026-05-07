package ui.menus;

import java.util.ArrayList;
import java.util.List;

public class MenuComposite implements MenuComponent {
    private String label;
    private List<MenuComponent> children;

    public MenuComposite(String label) {
        this.label = label;
        this.children = new ArrayList<>();
    }

    public List<MenuComponent> getChildren() { 
        return children; 
    }

    @Override
    public String getLabel() {
        return label; 
    }

    public void add(MenuComponent component) { 
        children.add(component); 
    }

    public void remove(MenuComponent component) { 
        children.remove(component); 
    }

    @Override
    public void render() {
        System.out.println(label);

        for (int i = 0; i < children.size(); i++) {
            System.out.println((i + 1) + ". " + children.get(i).getLabel());
        }

        System.out.print(">> ");
    }

    @Override
    public void handleInput(String input) {
        try {
            int choice = Integer.parseInt(input.trim());

            if (choice >= 1 && choice <= children.size()) {
                children.get(choice - 1).handleInput(input);
            }
        } catch (NumberFormatException exception) {
        }
    }
}
