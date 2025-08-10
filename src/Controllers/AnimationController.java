package Controllers;

import Background.Ground;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationController extends JPanel {
    private Timer timer;
    private int animationStep = 0;
    private int maxAnimationSteps = 300;

    // Components
    private Ground ground;

    public AnimationController() {
        setPreferredSize(new Dimension(800, 600));
        initializeComponents();
    }

    // * Add the Object On Here
    private void initializeComponents() {
        ground = new Ground();
    }

    public void startAnimation() {
        timer = new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAnimation();
                repaint();
            }
        });
        timer.start();
    }

    private void updateAnimation() {
        animationStep++;

        // Only update ground - no animation needed for ground

        if (animationStep >= maxAnimationSteps) {
            timer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        //? Draw only ground
        ground.draw(g2d, getWidth(), getHeight());
        DrawGrid(g2d);
    }

    void DrawGrid(Graphics g){
        int cellSize = 30;

        int width = getWidth();
        int height = getHeight();

        int cols = width / cellSize;
        int rows = height / cellSize;

        g.setColor(Color.BLACK);
        // วาดเส้นแนวตั้ง
        for (int i = 0; i <= cols; i++) {
            int x = i * cellSize;
            g.drawLine(x, 0, x, rows * cellSize);
        }

        // วาดเส้นแนวนอน
        for (int i = 0; i <= rows; i++) {
            int y = i * cellSize;
            g.drawLine(0, y, cols * cellSize, y);
        }
    }
}