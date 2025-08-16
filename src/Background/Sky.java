package Background;

import java.awt.Graphics;

import javax.swing.JPanel;

import Colors.Ground.GroundColor;
import Components.Bird;

public class Sky extends JPanel {
    
    private Bird bird;

    public Sky() {
        bird = new Bird();


    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(GroundColor.SKY_SECONDARY);
        g.fillRect(0, 0, getWidth(), getHeight());
        bird.draw(g, getWidth() / 2, getHeight() / 2, 50, 30); // Draw the bird after the sky
    }
}
