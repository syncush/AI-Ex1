import javafx.util.Pair;

public class FieldTile extends AbstractTile {
    public FieldTile(int x, int y)  {
        super(3, x, y, 'D');
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
