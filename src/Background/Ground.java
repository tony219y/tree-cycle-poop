package Background;

import java.awt.Color;
import java.awt.Graphics;

public class Ground {

    private int cellSize = 30;

    public Ground() {
    }

    public void draw(Graphics g2d, int width, int height) {
        int cols = width / cellSize;
        int rows = height / cellSize;

        g2d.setColor(Color.RED);
        drawRows(g2d, cols, rows, 0, 1);

        g2d.setColor(Color.BLUE);
        drawRows(g2d, cols, rows, 3, 5);

        g2d.setColor(Color.RED);
        drawRows(g2d, cols, rows, 7, 8);
    }

    private void drawRows(Graphics g2d, int cols, int rows, int startRow, int endRow) {
        startRow = Math.max(0, startRow);
        endRow = Math.min(rows - 1, endRow);

        for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
            int y = (rows - 1 - rowIndex) * cellSize;
            for (int col = 0; col < cols; col++) {
                int x = col * cellSize;
                g2d.fillRect(x - 2, y - 2, cellSize + 2, cellSize + 10);
            }
        }
    }
}