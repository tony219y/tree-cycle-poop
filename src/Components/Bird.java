package Components;

import java.awt.Graphics;
import java.awt.Color;

public class Bird {

    public Bird(){
        
    }

    public void draw(Graphics g, int xc, int yc, int a, int b) {
        g.setColor( Color.BLACK); // Brown color for the bird
        midpointEllipse(g, xc, yc, a, b);
    }

    private void midpointEllipse(Graphics g, int xc, int yc, int a, int b) {
        int x, y;
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;

        // ---- REGION 1 ----
        x = 0;
        y = b;
        int dx = 0;
        int dy = twoA2 * y;
        int d1 = Math.round(b2 - (a2 * b) + (0.25f * a2));

        while (dx <= dy) {
            plot4Quadrants(g, xc, yc, x, y); // Draw symmetric points
            x++;
            dx += twoB2;
            d1 += dx + b2;

            if (d1 >= 0) {
                y--;
                dy -= twoA2;
                d1 -= dy;
            }
        }

        // ---- REGION 2 ----
        x = a;
        y = 0;
        dx = twoB2 * x;
        dy = 0;
        int d2 = Math.round(a2 - (b2 * a) + (0.25f * b2));

        while (dx >= dy) {
            plot4Quadrants(g, xc, yc, x, y); // Draw symmetric points
            y++;
            dy += twoA2;
            d2 += dy + a2;

            if (d2 >= 0) {
                x--;
                dx -= twoB2;
                d2 -= dx;
            }
            if (x < 0 || y < 0) break; // Avoid infinite loop or negative coordinates
        }
    }

    private void plot4Quadrants(Graphics g, int xc, int yc, int x, int y) {
        g.fillRect(xc + x, yc + y, 1, 1); // 1st quadrant
        g.fillRect(xc - x, yc + y, 1, 1); // 2nd quadrant
        g.fillRect(xc - x, yc - y, 1, 1); // 3rd quadrant
        g.fillRect(xc + x, yc - y, 1, 1); // 4th quadrant
    }
}
