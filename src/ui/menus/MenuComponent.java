package ui.menus;

public interface MenuComponent {
    String getLabel();
    void render();
    void handleInput(String s);
}
