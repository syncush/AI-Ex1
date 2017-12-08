
public class StartTile extends AbstractTile {
    public StartTile(int x, int y)  {
        super(0, x, y, 'S');
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
