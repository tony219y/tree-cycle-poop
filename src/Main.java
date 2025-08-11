import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Bird - Fly from Right to Left");
        PixelPanel panel = new PixelPanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

class PixelPanel extends JPanel {
    private BufferedImage canvas;
    private int frameCounter = 0;
    private int birdX = 800;  // start offscreen at the right
    private int birdY = 200;
    private int birdFrameIndex = 0;

    // Define pixel colors
    private final Color[] palette = {
        new Color(0,0,0,0),        // 0 transparent
        new Color(255, 206, 84),   // 1 light yellow/orange (beak/feet)
        new Color(255, 165, 0),    // 2 orange (beak tip)
        new Color(0, 0, 0),        // 3 black (outline/eye)
        new Color(70, 130, 180),   // 4 steel blue (body)
        new Color(100, 149, 237),  // 5 cornflower blue (lighter body)
        new Color(30, 70, 120),    // 6 dark blue (wing details)
        new Color(135, 206, 235)   // 7 light blue (highlights)
    };

    // Bird frames
    private final int[][][] birdFrames = {
        { // Frame 1
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,3,3,3,3,0,0,0,0,0,0,0,0,0},
            {0,0,3,4,4,4,5,3,3,0,0,0,3,3,3,0},
            {0,3,2,1,3,4,7,4,4,3,6,6,4,4,4,3},
            {3,2,1,1,1,4,4,4,4,4,3,4,4,4,3,0},
            {0,3,3,3,6,6,3,5,7,4,4,3,3,3,0,0},
            {0,0,0,3,4,4,6,3,7,7,4,5,3,0,0,0},
            {0,0,0,0,3,3,4,6,3,7,5,6,3,0,0,0},
            {0,0,0,0,0,0,3,3,3,7,7,6,3,0,0,0},
            {0,0,0,0,0,0,0,0,3,7,7,6,3,0,0,0},
            {0,0,0,0,0,0,0,0,3,7,7,3,0,0,0,0},
            {0,0,0,0,0,0,0,0,3,7,7,3,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0},
        },
        { // Frame 2
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,3,3,3,3,0,0,0,0,0,0,0,0,0},
            {0,0,3,4,4,4,7,3,3,3,0,0,3,3,3,0},
            {0,3,2,1,3,4,4,4,4,4,3,4,7,7,7,3},
            {3,2,1,1,1,4,4,5,7,4,4,3,3,7,3,0},
            {0,3,3,3,6,6,3,5,7,4,4,4,4,3,0,0},
            {0,0,0,3,4,4,6,3,3,7,4,4,4,4,3,0},
            {0,0,0,0,3,3,4,6,6,3,7,4,4,4,3,0},
            {0,0,0,0,0,0,3,3,3,3,3,7,4,4,3,0},
            {0,0,0,0,0,0,0,0,0,0,3,7,7,4,3,0},
            {0,0,0,0,0,0,0,0,0,0,0,3,7,7,3,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,3,3,0,0},
        },
        { // Frame 3
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,3,3,3,3,0,0,0,0,0,0,0,0,0},
            {0,0,3,4,4,4,7,3,3,3,3,0,3,3,3,0},
            {0,3,2,1,3,4,4,4,4,4,4,3,7,7,7,3},
            {3,2,1,1,1,4,4,5,7,4,4,4,3,3,3,0},
            {0,3,3,3,6,6,3,5,7,4,4,4,4,4,4,3},
            {0,0,0,3,4,4,6,3,7,7,4,4,4,4,4,3},
            {0,0,0,0,3,3,4,6,3,7,7,7,7,3,3,0},
            {0,0,0,0,0,0,3,3,3,3,3,3,3,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        }
    };

    public PixelPanel() {
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);

        Timer timer = new Timer(1000 / 30, e -> { // 30 fps
            updateAnimation();
            repaint();
        });
        timer.start();
    }

    private void updateAnimation() {
        frameCounter++;

        // Wing flapping
        if (frameCounter % 8 == 0) {
            birdFrameIndex = (birdFrameIndex + 1) % birdFrames.length;
        }

        // Move bird from right to left
        birdX -= 4;
        if (birdX < -50) { // reset to right
            birdX = 800;
        }

        drawScene();
    }

    private void drawScene() {
        // Background sky & grass
        for (int y = 0; y < canvas.getHeight(); y++) {
            for (int x = 0; x < canvas.getWidth(); x++) {
                if (y < 500) canvas.setRGB(x, y, new Color(135, 206, 250).getRGB()); // sky
                else canvas.setRGB(x, y, new Color(34, 139, 34).getRGB()); // grass
            }
        }

        // Bird
        drawPixelArt(birdFrames[birdFrameIndex], birdX, birdY, 3);
    }

    private void drawPixelArt(int[][] pixels, int startX, int startY, int scale) {
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[0].length; x++) {
                int colorIndex = pixels[y][x];
                if (colorIndex != 0) {
                    drawPixelRect(startX + x * scale, startY + y * scale, scale, scale, palette[colorIndex]);
                }
            }
        }
    }

    private void drawPixelRect(int startX, int startY, int width, int height, Color c) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                if (x >= 0 && y >= 0 && x < canvas.getWidth() && y < canvas.getHeight()) {
                    canvas.setRGB(x, y, c.getRGB());
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }
}