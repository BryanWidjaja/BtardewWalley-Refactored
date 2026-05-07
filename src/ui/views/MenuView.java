package ui.views;

import ui.menus.MenuComponent;

public class MenuView {
    private MenuComponent rootMenu;

    public MenuView(MenuComponent rootMenu) {
        this.rootMenu = rootMenu;
    }

    public void render() {
        rootMenu.render();
    }

    public void handleInput(String input) {
        rootMenu.handleInput(input);
    }
}
