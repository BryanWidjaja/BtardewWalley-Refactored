package facades;

import services.game.GameInitializationService;
import services.game.GameInteractionService;
import services.game.GameRenderingService;

public class GameFacade {
    private GameRenderingService renderer;
    private GameInteractionService interactionService;
    private boolean isRunning = true;

    public void initialize() {
        GameInitializationService initializer = new GameInitializationService();
        initializer.initialize();

        this.renderer = new GameRenderingService(
            initializer.getMapViewModel(), 
            initializer.getPlayerViewModel(), 
            initializer.getConsoleUtils()
        );

        this.interactionService = new GameInteractionService(
            initializer.getScanner(),
            initializer.getCurrentUser(),
            initializer.getPlayerViewModel(),
            initializer.getMapViewModel(),
            initializer.getEventCommands(),
            initializer.getDevModeCommands()
        );
    }

    public void render() {
        if (renderer != null) renderer.render();
    }

    public void update() {
        if (interactionService != null) interactionService.update();
    }

    public boolean isRunning() {
        return isRunning;
    }
}
