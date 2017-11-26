import javafx.util.Pair;

public class RoadTile extends AbstractTile {
    public RoadTile(int x, int y)  {
        super(1, x, y, 'R');
    }

    /**
     * Getter.
     *
     * @return cordinate of the tile , in a pair of x,y cordinate
     */
    @Override
    public Pair<Integer, Integer> getCordinate() {
        return this.cordinate;
    }
}
