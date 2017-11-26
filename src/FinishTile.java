import javafx.util.Pair;

public class FinishTile extends AbstractTile {
    public FinishTile(int x, int y)  {
        super(3, x, y, 'G');
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
