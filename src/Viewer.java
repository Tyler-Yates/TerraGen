import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Viewer extends JPanel implements KeyListener {
    public static JFrame frame;
    public static Terrain terrain;

    private static final int size = (int) (Math.pow(2, 10) + 1);

    public Viewer() {
        frame = new JFrame("TerraGen");
        frame.setSize(size, size);
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
        } else if(e.getKeyCode() == KeyEvent.VK_P) {
            final BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            paintComponent(image.createGraphics());

            try {
                ImageIO.write(image, "png", new File("sample.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
