// package Components;

// import javax.swing.JPanel;

// public class Slime extends JPanel{
//     private static final int TOP_GROUND_HEIGHT = 200;
//     private int baseY = 600 - TOP_GROUND_HEIGHT;

//     //TODO: REAL IMPLEMENT SLIME ON HERE!!!!
//     public Slime(){

//     }
    
// }

package Components;

import java.awt.Color;
import java.awt.Graphics;

public class Slime {
    private static final int SLIME_SIZE = 100;
    private int baseY; // computed from ground

    private int frame = 0;
    private boolean animating = false;

    // Let baseY be set dynamically based on canvas height & ground height
    public void draw(Graphics g, int panelHeight, int groundHeight) {
        baseY = panelHeight - groundHeight; // top of ground

        // bouncing effect
        int bounce = (int) (Math.abs(Math.sin(frame * 0.1)) * 10);

        int slimeX = 350;
        int slimeY = baseY - SLIME_SIZE - bounce;

        // Body
        g.setColor(new Color(100, 255, 100, 200));
        g.fillRect(slimeX, slimeY, SLIME_SIZE, SLIME_SIZE);

        // Face
        g.setColor(new Color(0, 50, 0));
        g.fillRect(slimeX + 20, slimeY + 30, 15, 15); // left eye
        g.fillRect(slimeX + 65, slimeY + 30, 15, 15); // right eye
        g.fillRect(slimeX + 35, slimeY + 65, 30, 10); // mouth

        // Outline
        g.setColor(new Color(0, 100, 0));
        g.drawRect(slimeX, slimeY, SLIME_SIZE, SLIME_SIZE);
    }

    public void startAnimation() {
        if (animating) return;
        animating = true;
        new Thread(() -> {
            while (animating) {
                frame++;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


