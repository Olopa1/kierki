package com.example.client;

import java.io.IOException;
import java.util.HashMap;

import com.example.client.scenes.ChooseRoomScene;
import com.example.client.scenes.LoginScene;
import com.example.client.scenes.TableScene;

import javafx.stage.Stage;

/**
 * Class that manages switching between scenes.
 */
public class SceneManager {
    private HashMap<String, ScenesHanlder> sceneManger;
    private Stage stage;
    private ClientNetworkHandler handler = null;

    public String login;

    /**
     * Initializes stage and scenes.
     *
     * @param stage Stage.
     */
    public SceneManager(Stage stage) {
        sceneManger = new HashMap<String, ScenesHanlder>();
        this.stage = stage;
        this.initScenes();
    }

    /**
     * Initializes scenes.
     */
    private void initScenes() {
        this.stage.setOnCloseRequest(event -> {
            try {
                if (this.handler != null) {
                    this.handler.endConnection();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.sceneManger.put("login", new LoginScene(this));
        this.sceneManger.put("show_tables", new ChooseRoomScene(this));
        this.sceneManger.put("game", new TableScene(this));
    }

    /**
     * Switches to the given scene.
     *
     * @param sceneName Name of scene to switch.
     */
    public void displayScene(String sceneName) {
        System.out.println(sceneName);
        this.stage.setScene(sceneManger.get(sceneName).getScene());
        this.stage.setTitle(sceneManger.get(sceneName).getSceneName());
        this.stage.show();
    }

    public ClientNetworkHandler getNetworkHandler() {
        return this.handler;
    }

    public void setNetworkHandler(ClientNetworkHandler networkHandler) {
        this.handler = networkHandler;
    }

}
