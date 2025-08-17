import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class Main extends JPanel {

    private Ground ground;
    private Bird bird;
    private Slime slime;
    private Plane plane;
    private AnimationController animController; // เพิ่ม Animation Controller

    // ! Main Constructors
    public Main() {
        setPreferredSize(new Dimension(600, 600));
        initializeComponents();

        new Timer(17, _ -> {
            // Update animation controller
            animController.update();
            
            // Bird animation - controlled by AnimationController
            if (animController.isBirdAnimationEnabled() && bird.offsetX < 350) {
                bird.offsetX += 20; // move speed
                if (bird.offsetX % 3 == 0) { 
                    bird.offsetY += 1;
                }
            }
            
            // Plane animation - controlled by AnimationController
            if (animController.isPlaneAnimationEnabled()) {
                plane.setFrame(1);// index 1 = Frame 2
            }
            
            // Slime animation - controlled by AnimationController  
            if (animController.isSlimeAnimationEnabled()) {
                slime.visible = true;
            }

            // Cloud animation - controlled by AnimationController
            if (animController.isCloudAnimationEnabled()) {
                for (Cloud cloud : ground.sky.clouds) {
                    cloud.updatePosition();
                }
            }

            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ground.drawGround(g);
        bird.drawBird(g);
        slime.drawSlime(g);
        plane.drawPlane(g);

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
        bird = new Bird();
        slime = new Slime();
        plane = new Plane();
        animController = new AnimationController(); // สร้าง Animation Controller
        
        // กำหนด timing ของ animation แต่ละอัน
        animController.setBirdPhase(0, 28);     // Bird เล่นจาก frame 0-300
        animController.setPlanePhaseStart(28);   // Plane เริ่มเล่นที่ frame 300
        animController.setSlimePhaseStart(30);   // Slime เริ่มเล่นที่ frame 300
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
        p = (int) (ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 *
                ry2);
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
    // ? Draw a bird
    // ? ================================================
    class Bird {

        int frame = 0;
        int pixelSize = 8; // ขยาย pixel ให้ชัด (ลองปรับได้)
        int offsetX = -200, offsetY = 50; // ตำแหน่งวาง sprite

        Color[] colorMap = {
                new Color(0, 0, 0, 0), // 0 background
                new Color(153, 204, 255), // 1 wing
                new Color(102, 178, 255), // 2 body
                new Color(255, 255, 153), // 3 beak
                new Color(0, 0, 0) // 4 eye/outline
        };

        // ! Changed to 8x8 bird sprite
        int[][][] bird = {
                { // Frame 1
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 1, 1, 0, 2, 2, 2, 0 },
                        { 0, 0, 1, 1, 2, 4, 2, 0 },
                        { 2, 2, 2, 1, 1, 2, 2, 3 },
                        { 0, 0, 2, 2, 2, 2, 2, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                },
                { // Frame 2
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 2, 2, 2, 0 },
                        { 0, 1, 1, 1, 2, 4, 2, 0 },
                        { 2, 2, 1, 1, 1, 2, 2, 3 },
                        { 0, 0, 2, 2, 2, 2, 2, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                },
                { // Frame 3
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 2, 2, 2, 0 },
                        { 0, 0, 1, 1, 2, 4, 2, 0 },
                        { 2, 1, 1, 1, 1, 2, 2, 3 },
                        { 0, 0, 2, 2, 2, 2, 2, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                },
                { // Frame 4
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 2, 2, 2, 0 },
                        { 0, 0, 2, 2, 2, 4, 2, 0 },
                        { 2, 2, 2, 1, 1, 2, 2, 3 },
                        { 0, 0, 1, 1, 1, 2, 2, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                },
                { // Frame 5
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 2, 2, 2, 0 },
                        { 0, 0, 2, 2, 2, 4, 2, 0 },
                        { 2, 2, 2, 2, 1, 2, 2, 3 },
                        { 0, 0, 2, 1, 1, 2, 2, 0 },
                        { 0, 0, 1, 1, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                }

        };

        int direction = 1; // 1 = ไปข้างหน้า, -1 = ย้อนกลับ

        public Bird() {
            startAnimation();
        }

        private void startAnimation() {
            new Timer(10, _ -> {
                frame += direction;

                // ถ้าถึงเฟรมสุดท้าย -> สลับเป็นถอยหลัง
                if (frame == bird.length - 1) {
                    direction = -1;
                }
                // ถ้าถึงเฟรมแรก -> สลับเป็นเดินหน้า
                else if (frame == 0) {
                    direction = 1;
                }

                repaint();
            }).start();
        }

        public void drawBird(Graphics g) {
            // ! Draw a pixel per pixel
            if (offsetX >= 350)
                return;

            for (int i = 0; i < bird[frame].length; i++) { // loop row ของ frame ปัจจุบัน
                for (int j = 0; j < bird[frame][i].length; j++) { // loop column
                    int colorIndex = bird[frame][i][j];
                    if (colorIndex == 0)
                        continue; // 0 = background
                    g.setColor(colorMap[colorIndex]);
                    putPixel(g, offsetX + j * pixelSize, offsetY + i * pixelSize, pixelSize);
                }
            }

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

    public void drawTriangle(Graphics g, int x1, int y1, int x2, int y2, int x3,
            int y3, int thickness) {
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
    // * */

    // * ================================================
    // * Palette
    // * ================================================
    class Palette {

        // * Dirt
        public static final Color DIRT_1 = Color.decode("#ead0a8");
        public static final Color DIRT_2 = Color.decode("#b69f66");
        public static final Color DIRT_3 = Color.decode("#6b5428");
        public static final Color DIRT_4 = Color.decode("#76552b");
        public static final Color DIRT_5 = Color.decode("#402905");

        // * Grass
        public static final Color GRASS_1 = Color.decode("#136d15");
        public static final Color GRASS_2 = Color.decode("#117c13");

        // * Sky
        public static final Color SKY_1 = Color.decode("#9ee0f9");

        // * Bird
        public static final Color BIRD_1 = Color.decode("#99ccff");
        public static final Color BIRD_2 = Color.decode("#66b2ff");
        public static final Color BIRD_3 = Color.decode("#ffff99");
        public static final Color BIRD_4 = Color.decode("#000000");
        // * Slime
        public static final Color SLIME_1 = Color.decode("#009900");
        public static final Color SLIME_2 = Color.decode("#66ff66");
        public static final Color SLIME_3 = Color.decode("#8cff9d");
        public static final Color SLIME_4 = Color.decode("#000000");
        // * Plane
        public static final Color PLANE_1 = Color.decode("#ffffff");
        public static final Color PLANE_2 = Color.decode("#99ccff");
        public static final Color PLANE_3 = Color.decode("#585858");
        public static final Color PLANE_4 = Color.decode("#ff3b3b");
        public static final Color PLANE_5 = Color.decode("#66b2ff");
    }

    // * ================================================
    // * Component
    // * ================================================

    // ! ================================================
    // ! Cloud
    // ! ================================================
    class Cloud {
        private int posX;
        private int posY;
        private int speed;

        public Cloud(int startX, int startY, int cloudSpeed) {
            this.posX = startX;
            this.posY = startY;
            this.speed = cloudSpeed;
        }

        public void drawCloud(Graphics g) {
            g.setColor(Color.WHITE);

            // Create a fluffy cloud with multiple overlapping circles
            // Main cloud body (larger circles)
            drawEllipse(g, 50 + posX, posY, 60, 30); // Left main part
            drawEllipse(g, 90 + posX, posY - 10, 70, 35); // Center main part
            drawEllipse(g, 140 + posX, posY - 5, 65, 32); // Right main part

            // Additional puffs to make it look more natural
            drawEllipse(g, 30 + posX, posY + 15, 45, 25); // Left small puff
            drawEllipse(g, 75 + posX, posY + 15, 50, 28); // Center bottom puff
            drawEllipse(g, 125 + posX, posY + 18, 48, 25); // Right bottom puff
            drawEllipse(g, 160 + posX, posY + 10, 40, 22); // Far right puff

            // Top detail puffs
            drawEllipse(g, 65 + posX, posY - 25, 35, 20); // Top left detail
            drawEllipse(g, 110 + posX, posY - 30, 40, 22); // Top center detail
        }

        public void updatePosition() {
            posX += speed;
            if (posX > 800) { // Give more space before reset
                posX = -300; // Reset further to the left
            }
        }
    }

    // ! ================================================
    // ! SKY
    // ! ================================================
    class Sky {
        public List<Cloud> clouds; // Changed to list of clouds

        public Sky() {
            clouds = new ArrayList<>();
            // Create multiple clouds at different positions and speeds
            clouds.add(new Cloud(-200, 70, 8)); // First cloud - higher, slower
            clouds.add(new Cloud(-500, 120, 5)); // Second cloud - lower, faster
            clouds.add(new Cloud(-800, 40, 6)); // Third cloud - highest, slowest
            clouds.add(new Cloud(-350, 90, 4)); // Fourth cloud - medium height
        }

        public void drawSky(Graphics g) {
            g.setColor(Palette.SKY_1);
            drawRect(g, 0, 0, getWidth(), getHeight());

            // Draw all clouds
            for (Cloud cloud : clouds) {
                cloud.drawCloud(g);
            }
        }
    }

    // ! ================================================
    // ! GROUND
    // ! ================================================
    class Ground {
        private Tree tree;
        public Sky sky; // Changed to public

        Random random = new Random();
        List<Rock> rock = new ArrayList<>();

        public Ground() {
            tree = new Tree();
            sky = new Sky();
            detailDirt();
        }

        public void drawGround(Graphics g) {
            // Sky
            sky.drawSky(g);

            // Dirt
            g.setColor(Palette.DIRT_5);
            drawRect(g, 0, getHeight() - 100, getWidth(), 100);
            drawRect(g, 0, getHeight() - 100, getWidth(), 100);

            tree.drawTree(g, 100);
            tree.drawTree(g, 300);

            // Grass
            g.setColor(Palette.GRASS_1);
            drawRect(g, 0, getHeight() - 105, getWidth(), 20);

            for (Rock grass : rock) {
                g.setColor(Palette.GRASS_1);
                drawCircle(g, grass.x, getHeight() - 110, grass.size);
                g.setColor(Palette.GRASS_2);
                drawCircle(g, grass.x, getHeight() - 100, grass.size);
            }

            for (Rock rocks : rock) {
                g.setColor(rocks.color);
                drawCircle(g, rocks.x, rocks.y, rocks.size);
                g.setColor(Color.gray);
                drawCircle(g, rocks.x, rocks.y - 5, rocks.size - 2);
            }

        }

        private void detailDirt() {
            rock.clear();
            for (int i = 0; i < 100; i++) {
                int x = random.nextInt(600);
                int y = 500 + random.nextInt(80); // Within ground area
                int size = random.nextInt(8) + 3;

                // Randomly choose a dirt color for variation
                Color[] dirtColors = { Palette.DIRT_3, Palette.DIRT_4 };

                rock.add(new Rock(x, y, size, dirtColors[random.nextInt(dirtColors.length)]));
            }
        }

        // ! ================================================
        // ! ROCK
        // ! ================================================
        class Rock {
            int x, y, size;
            Color color;

            Rock(int x, int y, int size, Color color) {
                this.x = x;
                this.y = y;
                this.size = size;
                this.color = color;
            }
        }
        // ! ================================================
        // ! TREE
        // ! ================================================

        class Tree {

            Tree() {

            }

            public void drawTree(Graphics g, int positionX) {
                // Tree trunk (ลำต้น)
                g.setColor(Palette.DIRT_4);
                drawRect(g, positionX, getHeight() - 220, 12, 120); // body

                // Tree branches (กิ่งไม้)
                drawLine(g, positionX, 420, positionX - 26, 380, 4); // left branch
                drawLine(g, positionX + 10, 400, positionX + 58, 350, 4); // right branch

                // Tree leaves (ใบไม้)
                g.setColor(Palette.GRASS_1);
                drawCircle(g, positionX - 26, 380, 25); // left leaf cluster
                g.setColor(Palette.GRASS_2);
                drawCircle(g, positionX - 26, 380, 15);

                g.setColor(Palette.GRASS_1);
                drawCircle(g, positionX + 58, 350, 25); // right leaf cluster
                g.setColor(Palette.GRASS_2);
                drawCircle(g, positionX + 58, 350, 15);

                g.setColor(Palette.GRASS_1);
                drawCircle(g, positionX + 6, getHeight() - 225, 40); // main crown
                g.setColor(Palette.GRASS_2);
                drawCircle(g, positionX + 6, getHeight() - 225, 30);
            }
        }
    }

    // ? ================================================
    // ? Draw a Slime
    // ? ================================================
    class Slime {

        public boolean visible = false;
        int frame = 0;
        int pixelSize = 8; // ขยาย pixel ให้ชัด (ลองปรับได้)
        int offsetX = 400, offsetY = 20; // ตำแหน่งวาง sprite

        Color[] colorMap = {
                null,
                Palette.SLIME_1,
                Palette.SLIME_2,
                Palette.SLIME_3,
                Palette.SLIME_4,
        };

        // ! Changed to 8x8 slime sprite
        int[][][] slime = {
                { // Frame 1
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 1, 1, 1, 1, 1, 1, 1 },
                        { 0, 1, 3, 2, 2, 2, 2, 1 },
                        { 0, 1, 2, 4, 2, 3, 4, 1 },
                        { 0, 1, 2, 2, 2, 2, 2, 1 },
                        { 0, 1, 2, 3, 4, 4, 2, 1 },
                        { 0, 1, 3, 2, 2, 2, 3, 1 },
                        { 0, 1, 1, 1, 1, 1, 1, 1 },
                },
                { // Frame 2
                        { 0, 1, 1, 1, 1, 1, 1, 1 },
                        { 0, 1, 3, 2, 2, 2, 3, 1 },
                        { 0, 1, 2, 4, 2, 2, 4, 1 },
                        { 0, 1, 2, 2, 2, 2, 2, 1 },
                        { 0, 1, 2, 3, 4, 4, 2, 1 },
                        { 0, 1, 3, 2, 2, 2, 3, 1 },
                        { 0, 1, 1, 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                }

        };

        int direction = 1; // 1 = ไปข้างหน้า, -1 = ย้อนกลับ

        public Slime() {
            startAnimation();
        }

        private void startAnimation() {
            new Timer(300, _ -> {
                frame += direction;
                if(offsetY < 400){
                    offsetY+=10;
                }

                // ถ้าถึงเฟรมสุดท้าย -> สลับเป็นถอยหลัง
                if (frame == slime.length - 1) {
                    direction = -1;
                }
                // ถ้าถึงเฟรมแรก -> สลับเป็นเดินหน้า
                else if (frame == 0) {
                    direction = 1;
                }

                repaint();
            }).start();
        }

        public void drawSlime(Graphics g) {
            if (!visible)
                return;// don't draw until visible
            // ! Draw a pixel per pixel
            for (int i = 0; i < slime[frame].length; i++) { // loop row ของ frame ปัจจุบัน
                for (int j = 0; j < slime[frame][i].length; j++) { // loop column
                    int colorIndex = slime[frame][i][j];
                    if (colorIndex == 0)
                        continue; // 0 = background
                    g.setColor(colorMap[colorIndex]);
                    putPixel(g, offsetX + j * pixelSize, offsetY + i * pixelSize, pixelSize);
                }
            }

        }

    }

    // ? ================================================
    // ? Draw a Plane
    // ? ================================================
    class Plane {

        int frame = 0;
        int pixelSize = 20; // ขยาย pixel ให้ชัด (ลองปรับได้)
        int offsetX = 450, offsetY = 0; // ตำแหน่งวาง sprite

        Color[] colorMap = {
                null,
                Palette.PLANE_1,
                Palette.PLANE_2,
                Palette.PLANE_3,
                Palette.PLANE_4,
                Palette.PLANE_5,
        };

        // ! Changed to 8x8 plane sprite
        int[][][] plane = {
                { // Frame 1
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 1, 1, 1, 1, 1, 1 },
                        { 0, 1, 2, 2, 1, 1, 1, 1 },
                        { 1, 2, 2, 2, 1, 3, 3, 3 },
                        { 1, 1, 1, 1, 1, 1, 1, 1 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                },
                { // Frame 2
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 0, 0, 0, 0, 0, 0 },
                        { 0, 0, 1, 1, 1, 1, 1, 1 },
                        { 0, 4, 2, 2, 1, 1, 1, 1 },
                        { 4, 2, 2, 2, 1, 3, 3, 3 },
                        { 4, 1, 1, 1, 1, 1, 1, 1 },
                        { 4, 0, 0, 0, 0, 0, 0, 0 },
                        { 4, 0, 0, 0, 0, 0, 0, 0 },
                }

        };

        int direction = 1; // 1 = ไปข้างหน้า, -1 = ย้อนกลับ

        public Plane() {
            // startAnimation();
        }

        public void setFrame(int frame) {
            if (frame >= 0 && frame < plane.length) {
                this.frame = frame;
            }
        }

        public void startAnimation() {
            new Timer(300, _ -> {
                frame += direction;

                // ถ้าถึงเฟรมสุดท้าย -> สลับเป็นถอยหลัง
                if (frame == plane.length - 1) {
                    direction = -1;
                }
                // ถ้าถึงเฟรมแรก -> สลับเป็นเดินหน้า
                else if (frame == 0) {
                    direction = 1;
                }

                repaint();
            }).start();
        }

        public void drawPlane(Graphics g) {
            // ! Draw a pixel per pixel
            for (int i = 0; i < plane[frame].length; i++) { // loop row ของ frame ปัจจุบัน
                for (int j = 0; j < plane[frame][i].length; j++) { // loop column
                    int colorIndex = plane[frame][i][j];
                    if (colorIndex == 0)
                        continue; // 0 = background
                    g.setColor(colorMap[colorIndex]);
                    putPixel(g, offsetX + j * pixelSize, offsetY + i * pixelSize, pixelSize);
                }
            }

        }

    }

    // Animation Controller Class
    class AnimationController {
        private int currentTime = 0;
        private boolean cloudAnimationEnabled = false;
        private boolean birdAnimationEnabled = false;
        private boolean planeAnimationEnabled = false;
        private boolean slimeAnimationEnabled = false;
        
        // Animation phases
        private int birdPhaseStartTime = 0;
        private int birdPhaseEndTime = 300; // Bird flies for 5 seconds at 60fps
        private int planePhaseStartTime = 300;
        private int slimePhaseStartTime = 300;
        
        public void update() {
            currentTime++;
            
            // Control bird animation
            birdAnimationEnabled = (currentTime >= birdPhaseStartTime && currentTime <= birdPhaseEndTime);
            
            // Control plane animation  
            planeAnimationEnabled = (currentTime >= planePhaseStartTime);
            
            // Control slime animation
            slimeAnimationEnabled = (currentTime >= slimePhaseStartTime);
            
            // Clouds are always enabled (can be changed)
            cloudAnimationEnabled = true;
        }
        
        public boolean isBirdAnimationEnabled() { return birdAnimationEnabled; }
        public boolean isPlaneAnimationEnabled() { return planeAnimationEnabled; }
        public boolean isSlimeAnimationEnabled() { return slimeAnimationEnabled; }
        public boolean isCloudAnimationEnabled() { return cloudAnimationEnabled; }
        
        // Methods to control timing
        public void setBirdPhase(int startTime, int endTime) {
            this.birdPhaseStartTime = startTime;
            this.birdPhaseEndTime = endTime;
        }
        
        public void setPlanePhaseStart(int startTime) {
            this.planePhaseStartTime = startTime;
        }
        
        public void setSlimePhaseStart(int startTime) {
            this.slimePhaseStartTime = startTime;
        }
        
        public void enableCloudAnimation(boolean enabled) {
            this.cloudAnimationEnabled = enabled;
        }
        
        public int getCurrentTime() { return currentTime; }
        
        public void reset() {
            currentTime = 0;
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