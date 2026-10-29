import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int boardWidth = 360;
            int boardHeight = 640;

            JFrame frame = new JFrame("Flappy Bird");
            frame.setSize(boardWidth, boardHeight);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            FlappyBird flappyBird = new FlappyBird();
            frame.add(flappyBird);
            frame.pack();
            frame.setVisible(true);

            // Request focus only after the window is visible so Space works immediately.
            SwingUtilities.invokeLater(flappyBird::requestFocusInWindow);
        });
    }
}
