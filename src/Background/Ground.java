package Background;

import Colors.Ground.GroundColor;
import Components.Tree;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;

public class Ground extends JPanel {

    private Tree tree;


    private Runnable repaintCallback;

    private Timer timer;

    // layout constants
    private static final int TOP_GROUND_HEIGHT = 100;
    private static final int UNDER_LAYER_START_OFFSET = 90;
    private static final int UNDER_LAYER_HEIGHT = 100;

    private final Random random = new Random();
    private final List<Rock> rocks1 = new ArrayList<>();
    private final List<Rock> rocks2 = new ArrayList<>();

    private final List<Grass> grass1 = new ArrayList<>();
    private final List<Grass> grass2 = new ArrayList<>();

    private double windTime = 0;

    public Ground() {
        generateRocks(600, 600);
        generateGrass(650, 600);

        tree = new Tree();
        tick();
    }

    public void draw(Graphics g2d, int width, int height) {

        // Main Background
        drawSky(g2d, width, height);
        // top ground
        g2d.setColor(GroundColor.BROWN_PRIMARY);
        g2d.fillRect(0, height - TOP_GROUND_HEIGHT, width, TOP_GROUND_HEIGHT);

        tree.draw(g2d, width, height);
        drawGrass(g2d, width, height);

        // under layer ground
        g2d.setColor(GroundColor.BROWN_SECONDARY);
        g2d.fillRect(0, height - UNDER_LAYER_START_OFFSET, width, UNDER_LAYER_HEIGHT);

        drawRocks(g2d);

    }

    private void tick() {
        windTime += 0.12;
    }

    private void drawSky(Graphics g, int width, int height) {
        g.setColor(GroundColor.SKY_SECONDARY);
        g.fillRect(0, 0, width, height);
    }

    // * ROCKS
    private void drawRocks(Graphics g2d) {

        // วาดก้อนหินที่สร้างไว้แล้ว
        for (Rock rock : rocks1) {
            g2d.setColor(GroundColor.GRAVEL_SECONDARY);
            g2d.fillRect(rock.getX(), rock.getY(), rock.getSize(), rock.getSize());
            g2d.setColor(GroundColor.GRAVEL_TERTIARY);
            g2d.fillRect(rock.getX() + 5, rock.getY() + 5, rock.getSize() - 5, rock.getSize() - 5);
            g2d.setColor(GroundColor.GRAVEL_PRIMARY);
            g2d.fillRect(rock.getX() + 10, rock.getY() + 10, rock.getSize() - 10, rock.getSize() - 10);
        }
    }

    private void generateRocks(int width, int height) {
        rocks1.clear();
        rocks2.clear();
        int numRocks = random.nextInt(11) + 20; // 10-20 ก้อน

        for (int i = 0; i < numRocks; i++) {
            // ตำแหน่ง x แบบ Random
            int x1 = random.nextInt(width - 30);

            // ตำแหน่ง y ใน under layer ground (height-90 ถึง height)
            int y1 = height - 90 + random.nextInt(50);

            // ขนาดก้อนหินแบบ Random
            int rockSize = random.nextInt(20) + 10; // 10-30 พิกเซล

            rocks1.add(new Rock(x1, y1, rockSize));
        }
    }

    // * GRASS */
    private void drawGrass(Graphics g2d, int w, int h) {
        Graphics2D g = (Graphics2D) g2d;

        Stroke old = g.getStroke();

        g.setColor(GroundColor.GRASS_SECONDARY);
        g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (Grass grass : grass1) {
            int size = Math.max(grass.getSize() - 4, 6);
            double phase = grass.getX() * 0.07 + 0.5;
            double sway = Math.sin(windTime + phase) * Math.max(2, size / 5);
            drawGrassCluster(g, grass.getX(), h - TOP_GROUND_HEIGHT, size, sway);
        }

        g.setColor(GroundColor.GRASS_PRIMARY);
        g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (Grass grass : grass2) {
            int size = Math.max(grass.getSize() - 4, 6);
            double phase = grass.getX() * 0.07 + 0.5;
            double sway = Math.sin(windTime + phase) * Math.max(2, size / 5);
            drawGrassCluster(g, grass.getX(), h - TOP_GROUND_HEIGHT, size, sway);
        }

        g.setStroke(old);
    }

    private void drawGrassCluster(Graphics2D g, int baseX, int baseY, int size, double sway) {
        int h = size;
        int tipX = baseX + (int) Math.round(sway);
        g.drawLine(baseX, baseY, tipX, baseY - h);
        g.drawLine(baseX, baseY, tipX - 3, baseY - (int) (h * 0.85));
        g.drawLine(baseX, baseY, tipX + 3, baseY - (int) (h * 0.85));
        g.drawLine(baseX, baseY - (int) (h * 0.4), tipX - 2, baseY - h);
        g.drawLine(baseX, baseY - (int) (h * 0.4), tipX + 2, baseY - h);
    }

    private void generateGrass(int width, int height) {
        grass1.clear();
        grass2.clear();
        int numGrass = random.nextInt(11) + 500;

        for (int i = 0; i < numGrass; i++) {
            int x1 = random.nextInt(width - 30);

            int grassSize = random.nextInt(21) + 10; // 10-30 px

            grass1.add(new Grass(x1, height - TOP_GROUND_HEIGHT, grassSize));
            grass2.add(new Grass(x1, height - TOP_GROUND_HEIGHT, Math.max(grassSize - 10, 6)));
        }
    }

    public void startAnimation(Runnable repaintCallback) {
        this.repaintCallback = repaintCallback;
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick();
                if (Ground.this.repaintCallback != null) {
                    Ground.this.repaintCallback.run();
                }
                repaint();
            }
        });
        timer.start();
    }

}