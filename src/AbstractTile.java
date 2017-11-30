import javafx.util.Pair;
import java.util.ArrayList;

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
        this.cordinate = new Pair<Integer, Integer>(x, y);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        AbstractTile that = (AbstractTile) o;
        if(cordinate.getKey() == that.getCordinate().getKey() && cordinate.getValue() == that.getCordinate().getValue()) {
            return true;
        } else {
            return false;
        }
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
    public final ArrayList<AbstractTile> getNeighbors(int xAxisSize, char[][] matrix) {
        ArrayList<AbstractTile> neighbors = new ArrayList<>();
        int tempX = this.cordinate.getKey();
        int tempY = this.cordinate.getValue();

        //RIGHT
        if(tempY + 1 < xAxisSize && matrix[tempX][tempY + 1] != 'W') {
            neighbors.add(parseChar(matrix[tempX][tempY + 1], tempX, tempY + 1));
        }


        //RightDown
        if(tempX + 1 < xAxisSize && tempY + 1 < xAxisSize && matrix[tempX + 1][tempY + 1] != 'W') {
            if(matrix[tempX + 1][tempY] != 'W') {
                if(matrix[tempX][tempY + 1] != 'W') {
                    neighbors.add(parseChar(matrix[tempX + 1][tempY + 1], tempX + 1, tempY + 1));
                }
            }
        }

        //Down
        if(tempX + 1 < xAxisSize && matrix[tempX + 1][tempY] != 'W') {
            neighbors.add(parseChar(matrix[tempX + 1][tempY], tempX + 1, tempY));
        }


        //LeftDown
        if(tempX + 1 < xAxisSize && tempY - 1 >= 0 && matrix[tempX + 1][tempY - 1] != 'W') {
            if(matrix[tempX + 1][tempY] != 'W') {
                if(matrix[tempX][tempY - 1] != 'W') {
                    neighbors.add(parseChar(matrix[tempX + 1][tempY - 1], tempX + 1, tempY - 1));
                }
            }
        }

        //LEFT
        if(tempY - 1 >= 0 && matrix[tempX][tempY - 1] != 'W') {
            neighbors.add(parseChar(matrix[tempX][tempY - 1], tempX, tempY - 1));
        }

        //UpLeft
        if(tempX - 1 >= 0 && tempY - 1 >= 0 && matrix[tempX - 1][tempY - 1] != 'W') {
            if(matrix[tempX - 1][tempY] != 'W') {
                if(matrix[tempX][tempY - 1] != 'W') {
                    neighbors.add(parseChar(matrix[tempX - 1][tempY - 1], tempX - 1, tempY - 1));
                }
            }
        }

        //UP
        if(tempX - 1 >= 0 && matrix[tempX - 1][tempY] != 'W') {
            neighbors.add(parseChar(matrix[tempX - 1][tempY], tempX - 1, tempY));
        }

        //UpRight
        if(tempX - 1 >= 0 && tempY + 1 < xAxisSize && matrix[tempX - 1][tempY + 1] != 'W') {
            if(matrix[tempX][tempY + 1] != 'W') {
                if (matrix[tempX - 1][tempY] != 'W') {
                    neighbors.add(parseChar(matrix[tempX - 1][tempY + 1], tempX - 1, tempY + 1));
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean isCrossable() {
        return true;
    }

    private AbstractTile parseChar(char c, int x, int y) {
        switch(c) {
            case 'S': {
                return Main.start;
            }

            case 'G': {
                return Main.end;
            }

            case 'R': {
                return new RoadTile(x, y);
            }

            case 'D': {
                return new FieldTile(x,y);
            }

            case 'H': {
                return new HillTile(x, y);
            }

            default: {
            }
        }
        return null;
    }
}
