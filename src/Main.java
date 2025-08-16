import javax.swing.*;

import Controllers.AnimationController;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My CGI project"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        AnimationController controller = new AnimationController();
        frame.add(controller);

        frame.setVisible(true);

        controller.startAnimation();
    }
}
