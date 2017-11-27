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
        this.que = new PriorityQueue<>();
        this.euristicMap = new HashMap<>();
        //this.calculateEuristic();
    }
    public ArrayList<AbstractTile> runAlgo() throws Exception {
        int size = (int) Math.sqrt(matrix.size());
        HashSet<AbstractTile> closedSet = new HashSet<>();
        HashSet<AbstractTile> openSet = new HashSet<>();
        openSet.add(start);

        HashMap<AbstractTile, Integer> fScore = new HashMap<>();
        HashMap<AbstractTile, Integer> gScore = new HashMap<>();
        doStuff(fScore, gScore);
        fScore.put(this.start , heuristicCostEstimate(this.start, this.end));

        while(!openSet.isEmpty()) {
            AbstractTile tile = getLowestFromList(fScore);
            if(tile == this.end) {

                return backTracePath();
            }
            openSet.remove(tile);
            closedSet.add(tile);
            ArrayList<Pair<Integer, Integer>> neighbors = tile.getNeighbors(size, size);
            for (Pair<Integer, Integer> item:
                 neighbors) {
                AbstractTile neighbor = matrix.get(item.getKey() * size + item.getValue());
                if(closedSet.contains(neighbor)) {
                    continue;
                } else {
                    if(!openSet.contains(neighbor)) {
                        if(neighbor.isCrossable()) {
                            openSet.add(neighbor);
                        }
                    }
                }
                int score = gScore.get(tile) + neighbor.getCost();
                if(score >= gScore.get(neighbor)) {
                    continue;
                }
                neighbor.cameFrom = tile;
                gScore.put(neighbor, score);
                fScore.put(neighbor, gScore.get(neighbor) + heuristicCostEstimate(neighbor, this.end));
            }

        }
        throw new Exception("Path not found");

    }

    private AbstractTile getLowestFromList(HashMap<AbstractTile, Integer> fScore) {
        int min = Integer.MAX_VALUE / 4;
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
            fScore.put(item, Integer.MAX_VALUE / 4);
            gScore.put(item, Integer.MAX_VALUE / 4);
        }
    }

    private ArrayList<AbstractTile> backTracePath() {
        ArrayList<AbstractTile> path = new ArrayList<>();
        AbstractTile temp = this.end;
        while(temp != this.start) {
            path.add(temp);
            temp = temp.cameFrom;
        }
        path.add(this.start);
        return path;
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
