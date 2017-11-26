import javafx.util.Pair;

public class WaterTile extends AbstractTile {
    public WaterTile(int x, int y)  {
        super(1000, x, y, 'H');
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
