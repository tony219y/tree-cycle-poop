package Components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import Colors.Ground.GroundColor;

public class Tree extends JPanel {

    private int x;
    private static final int TOP_GROUND_HEIGHT = 200;
    private int baseY = 600 - TOP_GROUND_HEIGHT;

    ArrayList<Tree> trees = new ArrayList<>();

    Random random = new Random();

    public Tree(int x) {
        this.x = x;
    }

    public Tree() {
        generateTrees(600, 600, 5);
    }

    public void draw(Graphics g, int w, int h) {
        drawTree(g, w, h);
        // for (Tree tree : trees) {
        //     drawLeaves(g, tree.x, baseY, 20);
        // }
    }

    public void drawTree(Graphics g, int w, int h) {

        for (Tree tree : trees) {
            g.setColor(GroundColor.GRASS_SECONDARY);
            g.fillRect(tree.x-20, baseY-80, 30, 30);
            g.fillRect(tree.x-20, baseY-50, 50, 50);
            g.setColor(GroundColor.BROWN_PRIMARY);
            g.fillRect(tree.x, baseY, 10, 100);
        }
    }

    public void generateTrees(int w, int h, int count) {
        trees.clear();

        for (int i = 0; i < count; i++) {
            // int position = random.nextInt(w-40);
            trees.add(new Tree(100+(i*100)));
        }
    }

    // * Leaves
    public void drawLeaves(Graphics g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int Dx = 2 * x;
        int Dy = 2 * y;
        int D = 1 - r;

        //? Midpoint
        while (x <= y) {
            plot8(g, xc, yc, x, y);

            x = x + 1;
            Dx = Dx + 2;
            D = D + Dx + 1;
            if (D >= 0) {
                y = y - 1;
                Dy = Dy - 2;
                D = D - Dy;
            }
        }

    }

    private void plot8(Graphics g, int xc, int yc, int x, int y) {
        g.fillRect(xc + x, yc + y, 1, 1);
        g.fillRect(xc - x, yc + y, 1, 1);
        g.fillRect(xc + x, yc - y, 1, 1);
        g.fillRect(xc - x, yc - y, 1, 1);
        g.fillRect(xc + y, yc + x, 1, 1);
        g.fillRect(xc - y, yc + x, 1, 1);
        g.fillRect(xc + y, yc - x, 1, 1);
        g.fillRect(xc - y, yc - x, 1, 1);
    }
}
