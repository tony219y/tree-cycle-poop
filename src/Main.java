import java.awt.*;
import javax.swing.*;

public class Main extends JPanel {

    private Ground ground;

    // ! Main Constructors
    public Main() {
        setPreferredSize(new Dimension(600, 600));
        initializeComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.RED);
        ground.drawGround(g);

        // * Make a Rectangle
        // drawRect(g, 0, getHeight() - 100, 600, 100);

        // * Make a Circle
        // g.setColor(Color.GREEN);
        // drawCircle(g, getWidth() - 100, 100, 50);

        // * Make a Ellipse
        // g.setColor(Color.BLUE);
        // drawEllipse(g, 100, 100, 50, 100);

        // * Make a Line
        // g.setColor(Color.GRAY);
        // drawLine(g, 100, 400, 200, 400, 3);

        // * Make a Triangle
        // g.setColor(Color.PINK);
        // drawTriangle(g,
        // 100, 100,
        // 300, 300,
        // 500, 100,
        // 3);

        // * Make a Curve Line
        // g.setColor(Color.CYAN);
        // วาด Bezier Curve หนา 4
        // int[] cx = { 50, 150, 250 }; // control points X
        // int[] cy = { 400, 10, 400 }; // control points Y
        // drawBezier(g, cx, cy, 2);

    }

    private void initializeComponents() {
        ground = new Ground();
    }

    // * ================================================
    // * GLOBAL FUNCTION
    // * ================================================

    // ? ================================================
    // ? Draw a Rectangle
    // ? ================================================
    public void drawRect(Graphics g, int x, int y, int width, int height) {

        int[] xPoints = { x, x + width, x + width, x }; // จุด X: ซ้ายบน, ขวาบน, ขวาล่าง, ซ้ายล่าง

        // * จุด Y: ซ้ายบน, ขวาบน, ขวาล่าง, ซ้ายล่าง
        int[] yPoints = { y, y, y + height, y + height };

        int nPoints = 4; // จำนวนจุดทั้งหมด = 4 (สี่เหลี่ยม)

        // เติมสีข้างใน
        g.fillPolygon(xPoints, yPoints, nPoints);
    }

    // ? ================================================
    // ? Draw a Circle
    // ? ================================================

    private void putPixel(Graphics g, int x, int y, int thickness) { // ! Put a Pixel
        int offset = thickness / 2;
        for (int i = -offset; i <= offset; i++) {
            for (int j = -offset; j <= offset; j++) {
                g.fillRect(x + i, y + j, 1, 1);
            }
        }
    }

    private void drawHLine(Graphics g, int x1, int x2, int y, int thickness) { // ! Draw a Horizontal Line
        for (int x = x1; x <= x2; x++) {
            putPixel(g, x, y, thickness);
        }
    }

    public void drawCircle(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int d = 1 - r;

        // วาดเส้นแนวนอนตรงกลาง (scanline)
        drawHLine(g, xc - r, xc + r, yc, 1);

        while (x <= y) {
            // เติมเส้นในแต่ละแถว (สมมาตร 4 ด้าน)
            drawHLine(g, xc - x, xc + x, yc + y, 1);
            drawHLine(g, xc - x, xc + x, yc - y, 1);
            drawHLine(g, xc - y, xc + y, yc + x, 1);
            drawHLine(g, xc - y, xc + y, yc - x, 1);

            x++;
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                y--;
                d += 2 * (x - y) + 1;
            }
        }
    }

    // ? ================================================
    // ? Draw a Ellipse
    // ? ================================================
    public void drawEllipse(Graphics g, int xc, int yc, int rx, int ry) {
        // Draw Ellipse (Midpoint Ellipse Algorithm)
        int x = 0, y = ry;
        int rx2 = rx * rx, ry2 = ry * ry;
        int tworx2 = 2 * rx2, twory2 = 2 * ry2;
        int px = 0, py = tworx2 * y;

        putPixel(g, xc + x, yc + y, 1);
        putPixel(g, xc - x, yc + y, 1);
        putPixel(g, xc + x, yc - y, 1);
        putPixel(g, xc - x, yc - y, 1);

        // Region 1
        int p = (int) (ry2 - (rx2 * ry) + (0.25 * rx2));
        while (px < py) {
            x++;
            px += twory2;
            if (p < 0) {
                p += ry2 + px;
            } else {
                y--;
                py -= tworx2;
                p += ry2 + px - py;
            }

            // ! FILL A COLOR
            drawHLine(g, xc - x, xc + x, yc + y, 1);
            drawHLine(g, xc - x, xc + x, yc - y, 1);

            // ! JUST DRAW A SINGLE PIXEL
            // putPixel(g, xc + x, yc + y);
            // putPixel(g, xc - x, yc + y);
            // putPixel(g, xc + x, yc - y);
            // putPixel(g, xc - x, yc - y);
        }

        // Region 2
        p = (int) (ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 * ry2);
        while (y > 0) {
            y--;
            py -= tworx2;
            if (p > 0) {
                p += rx2 - py;
            } else {
                x++;
                px += twory2;
                p += rx2 - py + px;
            }

            // ! JUST DRAW A SINGLE PIXEL
            // putPixel(g, xc + x, yc + y);
            // putPixel(g, xc - x, yc + y);
            // putPixel(g, xc + x, yc - y);
            // putPixel(g, xc - x, yc - y);

            // ! FILL A COLOR
            drawHLine(g, xc - x, xc + x, yc + y, 1);
            drawHLine(g, xc - x, xc + x, yc - y, 1);
        }

    }

    // ? ================================================
    // ? Draw a Line
    // ? ================================================
    public void drawLine(Graphics g, int x1, int y1, int x2, int y2, int thickness) {
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(g, x1, y1, thickness);
            if (x1 == x2 && y1 == y2)
                break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    // ? ================================================
    // ? Draw a Triangle
    // ? ================================================

    public void drawTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int thickness) {
        drawLine(g, x1, y1, x2, y2, thickness);
        drawLine(g, x2, y2, x3, y3, thickness);
        drawLine(g, x3, y3, x1, y1, thickness);
    }

    // ? ================================================
    // ? Draw a Curve Line
    // ? ================================================
    public void drawBezier(Graphics g, int[] xPoints, int[] yPoints, int thickness) {
        int n = xPoints.length - 1; // degree
        for (double t = 0; t <= 1; t += 0.001) { // step เล็กๆ เพื่อ smooth curve
            double x = 0, y = 0;
            for (int i = 0; i <= n; i++) {
                double coeff = binomial(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i);
                x += coeff * xPoints[i];
                y += coeff * yPoints[i];
            }
            putPixel(g, (int) Math.round(x), (int) Math.round(y), thickness);
        }
    }

    private int binomial(int n, int k) {
        int res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * (n - i + 1) / i;
        }
        return res;
    }

    // ! ================================================
    // ! GROUND
    // ! ================================================
    class Ground {
        //TODO : Wait for Implement
        public Ground() {

        }

        public void drawGround(Graphics g) {
            drawRect(g, 0, getHeight() - 100, getWidth(), 100);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polygon Rectangle Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new Main());
        frame.setVisible(true);
    }
}