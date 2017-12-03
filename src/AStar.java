import javafx.util.Pair;

import java.util.*;
public class AStar {
    private AbstractTile start;
    private AbstractTile end;
    HashMap<AbstractTile, Double> hScore;

    public AStar(AbstractTile start, AbstractTile end)  {
        this.start = Main.start;
        this.end = Main.end;
    }
    public Pair<ArrayList<String>, Integer>  runAlgo() throws Exception {
        hScore = new HashMap<>();
        HashSet<AbstractTile> closedSet = new HashSet<>();
        HashSet<AbstractTile> openSet = new HashSet<>();

        int size = Main.matrix[0].length;


        openSet.add(start);
        this.start.timeDiscovered = 1;


        HashMap<AbstractTile, Double> fScore = new HashMap<>();
        HashMap<AbstractTile, Double> gScore = new HashMap<>();
        //doStuff(hScore, gScore);

        fScore.put(Main.start , heuristicCostEstimate(Main.start, Main.end));
        gScore.put(Main.start, 0.0);
        hScore.put(Main.start, heuristicCostEstimate(Main.start, Main.end));

        while(!openSet.isEmpty()) {
            AbstractTile tile = getLowestFromList(openSet, fScore, gScore);
            if(tile.equals(Main.end)) {
                return backTracePath();
            }
            openSet.remove(tile);
            closedSet.add(tile);

            ArrayList<AbstractTile> neighbors = tile.getNeighbors(size, Main.matrix);
            for (AbstractTile item: neighbors) {
                if(closedSet.contains(item)) {
                    continue;
                }
                if(!openSet.contains(item)) {
                        if(item.timeDiscovered == 0) {
                            item.timeDiscovered += tile.timeDiscovered + 1;
                        }
                        fScore.put(item, Double.MAX_VALUE / 4);
                        gScore.put(item, Double.MAX_VALUE / 4);
                        openSet.add(item);
                }
                Double score = gScore.get(tile) + item.getCost();
                if(score >= gScore.get(item)) {
                    continue;
                }
                item.cameFrom = tile;
                item.cameFromDirection = parseDirection(tile, item);
                gScore.put(item, score);
                hScore.put(item, heuristicCostEstimate(item, Main.end));
                fScore.put(item, score + heuristicCostEstimate(item, Main.end));
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
    /**
    private void doStuff(HashMap<AbstractTile, Double> fScore, HashMap<AbstractTile, Double> gScore) {
        for (AbstractTile item:this.matrix) {
            fScore.put(item, Double.MAX_VALUE / 4);
            gScore.put(item, Double.MAX_VALUE / 4);
        }
    }
     **/

    private Pair<ArrayList<String>, Integer> backTracePath() {
        ArrayList<AbstractTile> path = new ArrayList<>();
        AbstractTile temp = this.end;
        while(!temp.equals(Main.start)) {
            path.add(temp);
            temp = temp.cameFrom;
        }
        path.add(Main.start);
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
