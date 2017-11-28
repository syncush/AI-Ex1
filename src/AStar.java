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
    }
    public Pair<ArrayList<String>, Integer>  runAlgo() throws Exception {
        int size = ((Double)Math.sqrt(matrix.size())).intValue();
        HashSet<AbstractTile> closedSet = new HashSet<>();
        HashSet<AbstractTile> openSet = new HashSet<>();
        openSet.add(start);

        HashMap<AbstractTile, Integer> fScore = new HashMap<>();
        HashMap<AbstractTile, Integer> gScore = new HashMap<>();
        doStuff(fScore, gScore);
        fScore.put(this.start , heuristicCostEstimate(this.start, this.end));
        gScore.put(this.start, 0);

        while(!openSet.isEmpty()) {
            AbstractTile tile = getLowestFromList(openSet, fScore);
            if(tile == this.end) {
                return backTracePath();
            }
            openSet.remove(tile);
            closedSet.add(tile);
            ArrayList<Pair<Integer, Integer>> neighbors = tile.getNeighbors(size, this.matrix);
            for (Pair<Integer, Integer> item: neighbors) {
                AbstractTile neighbor = matrix.get((item.getKey()) * size + item.getValue());
                if(closedSet.contains(neighbor) || !neighbor.isCrossable()) {
                    continue;
                }
                if(!openSet.contains(neighbor)) {
                    if(neighbor.isCrossable()) {
                        openSet.add(neighbor);
                    } else {
                        continue;
                    }
                }
                int score = gScore.get(tile) + neighbor.getCost();
                if(score >= gScore.get(neighbor)) {
                    continue;
                }
                neighbor.cameFrom = tile;
                gScore.put(neighbor, score);
                fScore.put(neighbor, score + heuristicCostEstimate(neighbor, this.end));
            }
        }
        throw new Exception("Path not found");
    }

    private AbstractTile getLowestFromList(HashSet<AbstractTile> openSet, HashMap<AbstractTile, Integer> fScore) {
        int min = Integer.MAX_VALUE / 4;
        AbstractTile temp = null;
        for (Map.Entry<AbstractTile, Integer> item: fScore.entrySet()) {
            if(openSet.contains(item.getKey()) && item.getKey().isCrossable()) {
                if(item.getValue() < min) {
                    temp = item.getKey();
                    min = item.getValue();
                }
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

    private Pair<ArrayList<String>, Integer> backTracePath() {
        ArrayList<AbstractTile> path = new ArrayList<>();
        AbstractTile temp = this.end;
        while(temp != this.start) {
            path.add(temp);
            temp = temp.cameFrom;
        }
        path.add(this.start);
        Collections.reverse(path);
        int cost = 0;
        ArrayList<String> pathString = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            int deltaX = path.get(i + 1).cordinate.getKey() - path.get(i).cordinate.getKey();
            int deltaY = path.get(i + 1).cordinate.getValue() - path.get(i).cordinate.getValue();
            pathString.add(parseMovement(deltaX, deltaY));
            cost += path.get(i).getCost();
        }
        return new Pair<>(pathString, cost);
    }

    private String parseMovement(int deltaX, int deltaY) {
        if(deltaX == 1 && deltaY == 0) {
                return "R";
        }
        if(deltaX == 1 && deltaY == 1) {
            return "RD";
        }
        if(deltaX == -1 && deltaY == 0) {
            return "L";
        }
        if(deltaX == -1 && deltaY == 1) {
            return "LD";
        }
        if(deltaX == 1 && deltaY == -1) {
            return "RU";
        }
        if(deltaX == -1 && deltaY == -1) {
            return "LU";
        }
        if(deltaX == 0 && deltaY == -1) {
            return "U";
        }
        if(deltaX == 0 && deltaY == 1) {
            return "D";
        }
        return "pie";
    }

    private Integer heuristicCostEstimate(AbstractTile start, AbstractTile end) {
        Double e =   Math.sqrt(Math.pow(start.cordinate.getKey() - end.cordinate.getKey(), 2) +
                Math.pow(start.cordinate.getValue() - end.cordinate.getValue(), 2));
        return e.intValue();
    }

}
