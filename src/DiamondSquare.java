import java.util.Random;

/**
 * Class for generating the values used for the terrain.
 * <p/>
 * Credit to M. Jessup on StackOverflow for providing the base code used in this implementation.
 */
public class DiamondSquare {
    public static final double RANGE = 500;

    /**
     * Returns a Terrain object with randomly generated height values.
     * <p/>
     *
     * @param size The size of the output. The resulting output will have a side length of 2^(size)+1 pixels.
     * @param seed The seed to use in the random number generator.
     * @return A Terrain object
     */
    public static Terrain generateTerrain(int size, int seed) {
        //Use the seed to generate random numbers
        Random random = new Random(seed);

        //Represents the current range of alteration values
        double range = RANGE;

        //Create the Terrain object
        Terrain terrain = new Terrain();

        //Calculate the size of the matrix from the input
        int rows = (int) (Math.pow(2, size) + 1);

        //Create the matrix to store the height
        double[][] data = new double[rows][rows];

        //Initialize the 4 corners
        data[0][0] = random.nextDouble();
        data[0][data.length - 1] = random.nextDouble();
        data[data.length - 1][0] = random.nextDouble();
        data[data.length - 1][data.length - 1] = random.nextDouble();

        double heighestHeight = Double.NEGATIVE_INFINITY;
        double lowestHeight = Double.POSITIVE_INFINITY;

        //Fill the matrix
        for (int sideLength = rows - 1; sideLength >= 2; sideLength /= 2, range /= 2) {
            int halfSideLength = sideLength / 2;

            //Generate heights in the squares
            for (int x = 0; x < rows - 1; x += sideLength) {
                for (int y = 0; y < rows - 1; y += sideLength) {
                    //Calculate average of existing corners
                    double avg = data[x][y] + //top left
                            data[x + sideLength][y] +//top right
                            data[x][y + sideLength] + //lower left
                            data[x + sideLength][y + sideLength];//lower right
                    avg /= 4.0;

                    //Add random offset
                    data[x + halfSideLength][y + halfSideLength] = avg + (random.nextDouble() * 2 * range) - range;

                    //Update the lowest and highest heights if necessary
                    double height = data[x + halfSideLength][y + halfSideLength];
                    if(height > heighestHeight)
                        heighestHeight = height;
                    if(height < lowestHeight)
                        lowestHeight = height;
                }
            }

            //Generate heights in the diamonds
            for (int x = 0; x < rows - 1; x += halfSideLength) {
                for (int y = (x + halfSideLength) % sideLength; y < rows - 1; y += sideLength) {
                    //Calculate the average of existing corners (may need to wrap around the array)
                    double avg =
                            data[(x - halfSideLength + rows - 1) % (rows - 1)][y] + //left of center
                                    data[(x + halfSideLength) % (rows - 1)][y] + //right of center
                                    data[x][(y + halfSideLength) % (rows - 1)] + //below center
                                    data[x][(y - halfSideLength + rows - 1) % (rows - 1)]; //above center
                    avg /= 4.0;

                    //Add random offset
                    avg = avg + (random.nextDouble() * 2 * range) - range;
                    //Update value for center of diamond
                    data[x][y] = avg;

                    //Update the lowest and highest heights if necessary
                    double height = data[x][y];
                    if(height > heighestHeight)
                        heighestHeight = height;
                    if(height < lowestHeight)
                        lowestHeight = height;

                    //Wrap the edge values
                    if (x == 0) data[rows - 1][y] = avg;
                    if (y == 0) data[x][rows - 1] = avg;
                }
            }
        }

        //Set the values for the terrain
        terrain.setHeights(data);
        terrain.setHighestHeight(heighestHeight);
        terrain.setLowestHeight(lowestHeight);

        //Proportionalize the values to make it easier to interpret the heights
        terrain.proportionalizeValues();

        return terrain;
    }
}
