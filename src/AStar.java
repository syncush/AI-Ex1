import javafx.util.Pair;

import java.util.*;

public class AStar {
    private PriorityQueue<AbstractTile> que;
    private AbstractTile start;
    private AbstractTile end;
    ArrayList<AbstractTile> matrix;
    Map<Pair<Integer, Integer>, Integer> euristicMap;
    public AStar(ArrayList<AbstractTile> tiles, AbstractTile start, AbstractTile end)  {
        this.matrix = tiles;
        this.start = start;
        this.end = end;
        this.calculateEuristic();
    }
    public ArrayList<AbstractTile> runAlgo() {
        int size = (int) Math.sqrt(euristicMap.size());
        HashSet<AbstractTile> closedSet = new HashSet<>();
        HashSet<AbstractTile> openSet = new HashSet<>();
        openSet.add(start);

        HashMap<AbstractTile, Integer> fScore = new HashMap<>();
        HashMap<AbstractTile, Integer> gScore = new HashMap<>();
        doStuff(fScore, gScore);
        fScore.put(this.start , heuristicCostEstimate(this.start, this.end));

        while(!openSet.isEmpty()) {
            AbstractTile tile = getLowestFromList(fScore);
            if(this.start == this.end) {
                //TODO Recursive path
                return null;
            }
            openSet.remove(tile);
            closedSet.add(tile);
            ArrayList<Pair<Integer, Integer>> neighbors = tile.getNeighbors(size, size);

        }

    }

    private AbstractTile getLowestFromList(HashMap<AbstractTile, Integer> fScore) {
        int min = Integer.MAX_VALUE;
        AbstractTile temp = null;
        for (Map.Entry<AbstractTile, Integer> item: fScore.entrySet()) {
            if(item.getValue() < min) {
                temp = item.getKey();
                min = item.getValue();
            }
        }
        return temp;
    }

    private void doStuff(HashMap<AbstractTile, Integer> fScore, HashMap<AbstractTile, Integer> gScore) {
        for (AbstractTile item:this.matrix) {
            fScore.put(item, Integer.MAX_VALUE);
            fScore.put(item, Integer.MAX_VALUE);
        }
    }

    private Integer heuristicCostEstimate(AbstractTile start, AbstractTile end) {
        return Math.abs(this.start.cordinate.getKey() - end.cordinate.getKey()) +
                Math.abs(this.start.cordinate.getValue() - end.cordinate.getValue());
    }

    private void calculateEuristic() {
        for (AbstractTile item: this.matrix) {
            int euristicValue  = Math.abs(this.start.cordinate.getKey() - item.cordinate.getKey()) +
                                 Math.abs(this.start.cordinate.getValue() - item.cordinate.getValue());
            this.euristicMap.put(item.cordinate, euristicValue);
        }
    }
}
