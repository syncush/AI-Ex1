import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;

abstract public  class AbstractTile implements  Tile {
    protected int cost;
    protected Pair<Integer, Integer> cordinate;
    protected char represenation;
    public AbstractTile cameFrom;
    public int timeDiscovered = 0;
    public PLACE cameFromDirection;
    public AbstractTile(int cost, int x, int y, char represent) {
        this.cost = cost;
        this.represenation = represent;
        this.cordinate = new Pair<>(x, y);
    }
    /**
     * Getter
     * @return the cost of moving to the tile.
     */
    @Override
    public  int getCost() {
        return this.cost;
    }

    /**
     * Getter.
     *
     * @return the representation of the tile.
     */
    @Override
    public  char getRepresentation() {
        return this.represenation;
    }

    /**
     * Getter.
     *
     * @return cordinate of the tile , in a pair of x,y cordinate
     */
    @Override
    public abstract Pair<Integer, Integer> getCordinate();

    /**
     * The function calculates his neighrbors in the matrix.
     *
     * @return a list of pairs of it's neighbors
     */
    @Override
    public final ArrayList<Pair<Integer, Integer>> getNeighbors(int xAxisSize, ArrayList<AbstractTile> list) {
        ArrayList<Pair<Integer, Integer>> neighbors = new ArrayList<>();
        int tempX = this.cordinate.getKey();
        int tempY = this.cordinate.getValue();

        int index = (tempX - 1) * xAxisSize + tempY;
        if(tempX - 1 >= 0 && list.get(index).isCrossable()) {
            neighbors.add(new Pair<>(tempX - 1, tempY));
        }

        index = tempY - 1;
        index += tempX * xAxisSize;
        if(tempY - 1 >= 0 && list.get(index).isCrossable()) {
            neighbors.add(new Pair<>(tempX, tempY - 1));
        }

        index = (((tempX + 1) *xAxisSize) + tempY);
        if(tempX + 1 < xAxisSize && list.get(index).isCrossable()) {
            neighbors.add(new Pair<>(tempX + 1, tempY));
        }

        index = (((tempX) *xAxisSize) + (tempY + 1));
        if(tempY + 1 < xAxisSize && list.get(index).isCrossable()) {
            neighbors.add(new Pair<>(tempX, tempY + 1));
        }

        index = ((tempX + 1) * xAxisSize + (tempY + 1));
        if(tempX + 1 < xAxisSize && tempY + 1 < xAxisSize && list.get(index).isCrossable()) {
            index = ((tempX + 1) * xAxisSize + (tempY));
            if(list.get(index).isCrossable()) {
                index = ((tempX) * xAxisSize + (tempY + 1));
                if(list.get(index).isCrossable()) {
                    neighbors.add(new Pair<>(tempX + 1, tempY + 1));
                }
            }

        }

        index = ((tempX - 1) * xAxisSize + (tempY + 1));
        if(tempX - 1 >= 0 && tempY + 1 < xAxisSize && list.get(index).isCrossable()) {
            index = ((tempX) * xAxisSize + (tempY + 1));
            if(list.get(index).isCrossable()) {
                index = ((tempX - 1) * xAxisSize + tempY);
                if (list.get(index).isCrossable()) {
                    neighbors.add(new Pair<>(tempX - 1, tempY + 1));
                }
            }
        }

        index = ((tempX + 1) * xAxisSize + (tempY - 1));
        if(tempX + 1 < xAxisSize && tempY - 1 >= 0 && list.get(index).isCrossable()) {
            index = ((tempX + 1) * xAxisSize + (tempY));
            if(list.get(index).isCrossable()) {
                index = ((tempX) * xAxisSize + (tempY - 1));
                if(list.get(index).isCrossable()) {
                    neighbors.add(new Pair<>(tempX + 1, tempY - 1));
                }
            }
        }

        index = ((tempX - 1) * xAxisSize) + (tempY - 1);
        if(tempX - 1 >= 0 && tempY - 1 >= 0 && list.get(index).isCrossable()) {
            index = ((tempX - 1) * xAxisSize) + (tempY);
            if(list.get(index).isCrossable()) {
                index = ((tempX) * xAxisSize) + (tempY - 1);
                if(list.get(index).isCrossable()) {
                    neighbors.add(new Pair<>(tempX - 1, tempY - 1));
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean isCrossable() {
        return true;
    }
}
