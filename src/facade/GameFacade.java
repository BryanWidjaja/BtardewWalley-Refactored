package facade;

import services.game.GameInitializationService;
import services.game.GameInteractionService;
import services.game.GameRenderingService;

public class GameFacade {
    private GameRenderingService renderer;
    private GameInteractionService interactionService;
    private boolean running;

    public void start() {
        initialize();
        running = true;
        loop();
    }

    private void initialize() {
        GameInitializationService initializer = new GameInitializationService();
        initializer.initialize();

        renderer = new GameRenderingService(
            initializer.getMapViewModel(),
            initializer.getPlayerViewModel(),
            initializer.getIO()
        );

        interactionService = new GameInteractionService(
            initializer.getIO().getScanner(),
            initializer.getCurrentUser(),
            initializer.getPlayerViewModel(),
            initializer.getMapViewModel(),
            initializer.getEventCommands(),
            initializer.getDevModeCommands()
        );
    }

    private void loop() {
        while (running) {
            renderer.render();
            interactionService.update();
        }
    }

    public void stop() {
        running = false;
    }
}
