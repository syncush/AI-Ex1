import javafx.util.Pair;

public class HillTile extends AbstractTile {
    public HillTile(int x, int y)  {
        super(10, x, y, 'H');
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
