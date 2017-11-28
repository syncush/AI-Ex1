import javafx.util.Pair;

import java.util.*;
public class AStar {
    private PriorityQueue<AbstractTile> que;
    private AbstractTile start;
    private AbstractTile end;
    ArrayList<AbstractTile> matrix;
    Map<Pair<Integer, Integer>, Integer> euristicMap;
    HashMap<AbstractTile, Double> hScore;

    public AStar(ArrayList<AbstractTile> tiles, AbstractTile start, AbstractTile end)  {
        this.matrix = tiles;
        this.start = start;
        this.end = end;
        this.que = new PriorityQueue<>();
        this.euristicMap = new HashMap<>();
    }
    public Pair<ArrayList<String>, Integer>  runAlgo() throws Exception {
        hScore = new HashMap<>();
        int size = ((Double)Math.sqrt(matrix.size())).intValue();
        HashSet<AbstractTile> closedSet = new HashSet<>();
        HashSet<AbstractTile> openSet = new HashSet<>();
        openSet.add(start);
        this.start.timeDiscovered = 1;
        HashMap<AbstractTile, Double> fScore = new HashMap<>();
        HashMap<AbstractTile, Double> gScore = new HashMap<>();
        doStuff(hScore, gScore);
        fScore.put(this.start , heuristicCostEstimate(this.start, this.end));
        gScore.put(this.start, 0.0);
        hScore.put(this.start, heuristicCostEstimate(this.start, this.end));

        while(!openSet.isEmpty()) {
            AbstractTile tile = getLowestFromList(openSet, fScore, gScore);
            if(tile == this.end) {
                return backTracePath();
            }
            openSet.remove(tile);
            closedSet.add(tile);
            ArrayList<Pair<Integer, Integer>> neighbors = tile.getNeighbors(size, this.matrix);
            for (Pair<Integer, Integer> item: neighbors) {
                AbstractTile neighbor = matrix.get((item.getKey()) * size + item.getValue());
                if(closedSet.contains(neighbor)) {
                    continue;
                }
                if(!openSet.contains(neighbor)) {
                        if(neighbor.timeDiscovered == 0) {
                            neighbor.timeDiscovered += tile.timeDiscovered + 1;
                        }
                        openSet.add(neighbor);
                }
                Double score = gScore.get(tile) + neighbor.getCost();
                if(score >= gScore.get(neighbor)) {
                    continue;
                }
                neighbor.cameFrom = tile;
                neighbor.cameFromDirection = parseDirection(tile, neighbor);
                gScore.put(neighbor, score);
                hScore.put(neighbor, heuristicCostEstimate(neighbor, this.end));
                fScore.put(neighbor, score + heuristicCostEstimate(neighbor, this.end));
            }
        }
        throw new Exception("no path");
    }

    private PLACE parseDirection(AbstractTile tile, AbstractTile neighbor) {
        int deltaX = neighbor.cordinate.getKey() - tile.cordinate.getKey();
        int deltaY = neighbor.cordinate.getValue() - tile.cordinate.getValue();
        if(deltaX == 1 && deltaY == 0) {
            return PLACE.RIGHT;
        }
        if(deltaX == 1 && deltaY == 1) {
            return PLACE.RIGHTDOWN;
        }
        if(deltaX == -1 && deltaY == 0) {
            return PLACE.LEFT;
        }
        if(deltaX == -1 && deltaY == 1) {
            return PLACE.DOWNLEFT;
        }
        if(deltaX == 1 && deltaY == -1) {
            return PLACE.UPRIGHT;
        }
        if(deltaX == -1 && deltaY == -1) {
            return PLACE.LEFTUP;
        }
        if(deltaX == 0 && deltaY == -1) {
            return PLACE.UP;
        }
        if(deltaX == 0 && deltaY == 1) {
            return PLACE.DOWN;
        }
        return null;
    }

    private AbstractTile getLowestFromList(HashSet<AbstractTile> openSet, HashMap<AbstractTile, Double> fScore, HashMap<AbstractTile, Double> gScore) {
        Double min = Double.MAX_VALUE / 4;
        AbstractTile temp = null;
        for (AbstractTile item: openSet) {
            if(item.isCrossable()) {
                Double val = fScore.get(item);
                if(val < min) {
                    temp = item;
                    min = fScore.get(item) ;
                    continue;
                }
                if(val == min) {
                    if(item.timeDiscovered < temp.timeDiscovered) {
                        temp = item;
                        min = fScore.get(item);
                        continue;
                    }
                    if(item.timeDiscovered == temp.timeDiscovered) {
                        if(item.cameFromDirection.getValue() < temp.cameFromDirection.getValue()) {
                            temp = item;
                            min = fScore.get(item);
                        }
                    }
                }

            }
        }
        return temp;
    }

    private void doStuff(HashMap<AbstractTile, Double> fScore, HashMap<AbstractTile, Double> gScore) {
        for (AbstractTile item:this.matrix) {
            fScore.put(item, Double.MAX_VALUE / 4);
            gScore.put(item, Double.MAX_VALUE / 4);
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
            return "D";
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
            return "R";
        }
        return "pie";
    }

    private Double heuristicCostEstimate(AbstractTile start, AbstractTile end) {
        Double e =   Math.sqrt(Math.pow(start.cordinate.getKey() - end.cordinate.getKey(), 2) +
                Math.pow(start.cordinate.getValue() - end.cordinate.getValue(), 2));
        return e;
    }

}
