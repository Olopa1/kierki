package com.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Main class of client.
 */
public class RunClient extends Application {

    /**
     * Derived method to set a first stage
     *
     * @param stage Default stage.
     */
    @Override
    public void start(Stage stage) {
        SceneManager manager = new SceneManager(stage);

        stage.setOnCloseRequest((WindowEvent event) -> {
            try {
                manager.getNetworkHandler().sendMessage("disconnect");
            } catch (Exception e) {
            }
            Platform.exit();
        });

        manager.displayScene("login");
    }

    /**
     * Main client's method.
     *
     * @param args Console arguments.
     */
    public static void main(String[] args) {
        launch();
    }

}
