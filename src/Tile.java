import java.util.ArrayList;

interface Tile {
    /**
     * Getter
     * @return the cost of moving to the tile.
     */
    int getCost();

    /**
     * Getter.
     * @return the representation of the tile.
     */
    char getRepresentation();
    /**
     * Getter.
     * @return cordinate of the tile , in a pair of x,y cordinate
     */
    Pair<Integer, Integer> getCordinate();
    /**
     * The function calculates his neighrbors in the matrix.
     * @return a list of pairs of it's neighbors
     */
    ArrayList<AbstractTile> getNeighbors(int xAxisSize, char[][] matrix);

    /**
     * if crossable or not.
     * @return
     */
    boolean isCrossable();
}
