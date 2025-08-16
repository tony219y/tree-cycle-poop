import javax.swing.*;

import Controllers.AnimationController;
import Scenes.SceneManager;
import Scenes.skyScene;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My CGI project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // Set frame size to match AnimationController's preferred size

        SceneManager sceneManager = new SceneManager();

        AnimationController animationScene = new AnimationController();
        skyScene skyScene = new skyScene();
        
        sceneManager.addScene("animationScene", animationScene);
        sceneManager.addScene("skyScene", skyScene);
        frame.add(sceneManager);

        frame.setVisible(true);

        sceneManager.showScene("animationScene");
        animationScene.startAnimation();

        // * For go next Scene
        new Timer(2000, e -> sceneManager.showScene("skyScene")).start();

    }
}
