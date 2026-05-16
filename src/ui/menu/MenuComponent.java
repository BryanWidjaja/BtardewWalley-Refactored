package ui.menu;

public interface MenuComponent {
    String getLabel();
    void render();
    void handleInput(String input);
}
