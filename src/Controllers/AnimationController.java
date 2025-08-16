package Controllers;
import Components.Slime;
import Components.Plane;
import Background.Ground;
import javax.swing.*;
import java.awt.*;

public class AnimationController extends JPanel {
	// พาเนลหลักที่ใช้วาดฉาก
	private Ground ground;


	//create slime
	 private Slime slime;

	 private Plane plane;

	 // create plane
	
	public AnimationController() {
		setPreferredSize(new Dimension(800, 600)); // ขนาดเริ่มต้นของแคนวาส
		initializeComponents();
	}

	// เตรียมออบเจ็กต์ที่ใช้ในฉาก
	private void initializeComponents() {
		ground = new Ground();
		// สร้าง Slime
		slime = new Slime();
		
		plane = new Plane();
		  
	}

	// เริ่มแอนิเมชัน: ให้ Ground อัปเดตสถานะและเรียก repaint ของเราเป็นระยะ
	public void startAnimation() {
		ground.startAnimation(this::repaint);

		//slime แอนิเมชัน
		slime.startAnimation();
		plane.startAnimation();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		

		
		ground.draw(g2d, getWidth(), getHeight());

		slime.draw(g2d, getWidth(), 120);

		plane.draw(g2d);
	}
}