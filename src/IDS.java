import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class IDS {
    private AbstractTile start;
    private AbstractTile end;
    ArrayList<AbstractTile> matrix;
    private int size;

    public IDS(ArrayList<AbstractTile> tiles, AbstractTile start, AbstractTile end, int size)  {
        this.matrix = tiles;
        this.start = start;
        this.end = end;
        this.size = size;
    }

    public AbstractTile runAlgo() {
        List<AbstractTile> path = new ArrayList<>();
        for (int i = 0; i < this.size * this.size; i++) {
            AbstractTile tile = runDLS(this.start ,i);
            if(tile != null) {
                return tile;
            }
        }
        int x = 5;
        return null;

    }

    private AbstractTile runDLS(AbstractTile tile, int depth) {
        if(depth == 0 && tile == this.end) {
            return this.end;
        }
        if(depth > 0) {
            ArrayList<Pair<Integer, Integer>> neighbors = tile.getNeighbors(this.size, this.matrix);
            for(Pair<Integer, Integer> item: neighbors) {
                AbstractTile neighbor = this.matrix.get((item.getKey() * this.size) + item.getValue());
                AbstractTile found = runDLS(neighbor, --depth);
                if(found != null) {
                    found.cameFrom = neighbor;
                    return found;
                }
            }
        }
        return null;
    }
}
