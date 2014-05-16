import javax.swing.*;
import java.awt.*;

public class Viewer extends JPanel {
    public static JFrame frame;
    public static double[][] terrain;

    public Viewer() {
        frame = new JFrame("TerraGen");
        frame.setSize(1025, 1025);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        terrain = DiamondSquare.generateTerrain(10, (int) (Math.random()*10000));
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
        double val = terrain[y][x] + DiamondSquare.RANGE;
        //Represents the percentage of the maximum height
        double percentage = val / (DiamondSquare.RANGE * 2);

        if (percentage <= 0.6)
            return Color.blue;
        if (percentage >= 0.85)
            return Color.white;
        if (percentage >= 0.80)
            return new Color(139, 125, 107);
        return Color.green;
    }
}
