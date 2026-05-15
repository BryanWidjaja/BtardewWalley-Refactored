package app;

import facade.GameFacade;

public class Main {
    private GameFacade gameFacade;

    public static void main(String[] args) {
        new Main().start();
    }

    private void start() {
        gameFacade = new GameFacade();
        gameFacade.initialize();
        
        while (gameFacade.isRunning()) {
            gameFacade.render();
            gameFacade.update();
        }
    }
}
