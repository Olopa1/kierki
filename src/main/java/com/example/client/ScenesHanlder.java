package com.example.client;

import javafx.scene.Scene;

public interface ScenesHanlder {
  public Scene getScene();
  public String getSceneName();
  public void initScene(SceneManager sceneManager);
}
