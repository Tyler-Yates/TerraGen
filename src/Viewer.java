import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Viewer extends JPanel implements KeyListener {
    public static JFrame frame;
    public static Terrain terrain;

    public Viewer() {
        frame = new JFrame("TerraGen");
        frame.setSize(1025, 1025);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        terrain = DiamondSquare.generateTerrain(10, (int) (Math.random() * 10000));
        new Viewer();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < frame.getWidth(); x++) {
            for (int y = 0; y < frame.getHeight(); y++) {
                g.setColor(getColor(x, y));
                g.drawLine(x, y, x, y);
            }
        }
    }

    private static Color getColor(int x, int y) {
        double val = terrain.getHeight(y, x);

        if (val <= 0.6)
            return new Color(30,144,255);
        if (val <= 0.61)
            return new Color(238, 232, 170);

        if (val >= 0.95)
            return Color.white;
        if (val >= 0.90)
            return new Color(139, 125, 107);
        return new Color(50, 205, 50);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            terrain = DiamondSquare.generateTerrain(10, (int) (Math.random() * 10000));
            repaint();
        }
    }
}
