package Components;

import java.awt.Color;
import java.awt.Graphics;

public class Plane {
    private static final int PLANE_WIDTH = 150;
    private static final int PLANE_HEIGHT = 60;

    private int x = 20; // fixed left margin
    private int y = 20; // fixed top margin
    private int frame = 0;
    private boolean animating = false;

    public void draw(Graphics g) {
        // Floating effect (vertical only)
        int floatEffect = (int) (Math.sin(frame * 0.1) * 10);
        int planeY = y + floatEffect;

        // Body (white)
        g.setColor(Color.WHITE);
        g.fillRect(x, planeY, PLANE_WIDTH, PLANE_HEIGHT);

        // Cockpit/front on right side (blue)
        g.setColor(new Color(0, 0, 200));
        g.fillRect(x + PLANE_WIDTH - 70, planeY + 10, 50, 30);

        // Wings
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x - 30, planeY + 10, 30, 10); // left wing
        g.fillRect(x + PLANE_WIDTH, planeY + 10, 30, 10); // right wing

        // Tail
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x + PLANE_WIDTH - 20, planeY - 20, 20, 20);

        // Outline
        g.setColor(Color.BLACK);
        g.drawRect(x, planeY, PLANE_WIDTH, PLANE_HEIGHT);
        g.drawRect(x + PLANE_WIDTH - 70, planeY + 10, 50, 30);
        g.drawRect(x - 30, planeY + 10, 30, 10);
        g.drawRect(x + PLANE_WIDTH, planeY + 10, 30, 10);
        g.drawRect(x + PLANE_WIDTH - 20, planeY - 20, 20, 20);
    }

    public void startAnimation() {
        if (animating) return;
        animating = true;
        new Thread(() -> {
            while (animating) {
                frame++; // increment frame for vertical float
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
