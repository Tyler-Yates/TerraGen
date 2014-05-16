public class Terrain {
    private double[][] heights;
    private double highestHeight, lowestHeight;

    public Terrain() {
    }

    public void setHeights(double[][] heights) {
        this.heights = heights;
    }

    public void setHighestHeight(double height) {
        highestHeight = height;
    }

    public void setLowestHeight(double height) {
        lowestHeight = height;
    }

    public void proportionalizeValues() {
        for (int r = 0; r < heights.length; r++) {
            for (int c = 0; c < heights[r].length; c++) {
                double newHeight = (heights[r][c] - lowestHeight) / (highestHeight - lowestHeight);
                heights[r][c] = newHeight;
            }
        }
    }

    public double getHeight(int r, int c) {
        return heights[r][c];
    }
}
