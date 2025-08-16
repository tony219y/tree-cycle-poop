package Controllers;

import Background.Ground;
import javax.swing.*;
import java.awt.*;

public class AnimationController extends JPanel {
	// พาเนลหลักที่ใช้วาดฉาก
	private Ground ground;

	public AnimationController() {
		setPreferredSize(new Dimension(600, 600)); // ขนาดเริ่มต้นของแคนวาส
		initializeComponents();
	}

	private void initializeComponents() {
		ground = new Ground();
	}

	public void startAnimation() {
		ground.startAnimation(this::repaint);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// วาดพื้นดินและองค์ประกอบทั้งหมด
		ground.draw(g2d, getWidth(), getHeight());
	}
}