package Scenes;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SceneManager extends JPanel {
    private CardLayout cardLayout;
    private Map<String, JPanel> scenes;

    public SceneManager() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        scenes = new HashMap<>();
    }

    public void addScene(String name, JPanel scenePanel) {
        scenes.put(name, scenePanel);
        this.add(scenePanel, name);
    }

    public void showScene(String name) {
        cardLayout.show(this, name);
    }
}
