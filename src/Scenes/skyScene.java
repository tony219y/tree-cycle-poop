package Scenes;

import javax.swing.*;
import Background.Sky;
import java.awt.*;

public class skyScene extends JPanel {

    private Sky sky;

    public skyScene() {
        setPreferredSize(new Dimension(600, 600)); 
        setLayout(new BorderLayout());
        sky = new Sky();
        add(sky);
        // add(bird);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        sky.paintComponent(g);
    }
}
