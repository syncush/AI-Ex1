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
    ArrayList<AbstractTile> finalPath;

    public IDS(ArrayList<AbstractTile> tiles, AbstractTile start, AbstractTile end, int size)  {
        this.matrix = tiles;
        this.start = start;
        this.end = end;
        this.size = size;
    }

    public ArrayList<AbstractTile> runAlgo() {
        ArrayList<AbstractTile> path = new ArrayList<>();
        path.add(this.start);
        for (int i = 0; i < this.size * this.size; i++) {
            AbstractTile tile = runDLS(path ,this.start ,i);
            if(tile != null) {
                return this.finalPath;
            }
        }
        return null;
    }

    private AbstractTile runDLS(ArrayList<AbstractTile> path, AbstractTile tile, int depth) {
        if(depth == 0 && tile == this.end) {
            finalPath = path;
            return this.end;
        }
        if(depth > 0) {
            ArrayList<Pair<Integer, Integer>> neighbors = tile.getNeighbors(this.size, this.matrix);
            for(Pair<Integer, Integer> item: neighbors) {
                AbstractTile neighbor = this.matrix.get((item.getKey() * this.size) + item.getValue());
                ArrayList<AbstractTile> newPath = new ArrayList<>(path);
                newPath.add(neighbor);
                AbstractTile found = runDLS(newPath,neighbor, --depth);
                if(found != null) {
                    found.cameFrom = tile;
                    return found;
                }
            }
        }
        return null;
    }
}
