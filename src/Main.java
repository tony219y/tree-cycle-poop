import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My CGI project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JLabel label = new JLabel("Hi, CGI project!", SwingConstants.CENTER);
        frame.add(label);

        frame.setVisible(true);
    }
}
