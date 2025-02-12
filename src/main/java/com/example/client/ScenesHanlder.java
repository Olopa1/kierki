package com.example.client;

import javafx.scene.Scene;

/**
 * Interface for scene that will be in SceneManager
 */
public interface ScenesHanlder {
    /**
     * Gives Scene class.
     *
     * @return Scene handled by this handler.
     */
    public Scene getScene();

    /**
     * Gives scene name.
     *
     * @return Name of scene handled by this handler.
     */
    public String getSceneName();

    /**
     * Initializes scene.
     *
     * @param sceneManager Manager that will contain this scene.
     */
    public void initScene(SceneManager sceneManager);
}
