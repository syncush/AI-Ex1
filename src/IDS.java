import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IDS {
    private AbstractTile start;
    private AbstractTile end;
    ArrayList<AbstractTile> matrix;

    public IDS(ArrayList<AbstractTile> tiles, AbstractTile start, AbstractTile end)  {
        this.matrix = tiles;
        this.start = start;
        this.end = end;
    }

    public Pair<ArrayList<String>, Integer> runAlgo() {
        return null;
    }
}
