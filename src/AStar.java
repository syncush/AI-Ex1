import java.util.*;
public class AStar {
    private AbstractTile start;
    private AbstractTile end;

    public AStar(AbstractTile start, AbstractTile end)  {
        this.start = java_ex1.start;
        this.end = java_ex1.end;
    }
    public Pair<ArrayList<String>, Integer>  runAlgo() throws Exception {
        ArrayList<AbstractTile> closedSet = new ArrayList<>();
        ArrayList<AbstractTile> openSet = new ArrayList<>();
        int blockNum = 10 * java_ex1.matrix[0].length * java_ex1.matrix[0].length * java_ex1.matrix[0].length;
        int size = java_ex1.matrix[0].length;
        int globalTime = 1;

        openSet.add(start);
        this.start.timeDiscovered = 1;



        java_ex1.start.groundCost = 0.0;
        java_ex1.start.huristicCost = heuristicCostEstimate(java_ex1.start, java_ex1.end);

        while(!openSet.isEmpty()) {
            if(blockNum < -1) {
                return null;
            }
            AbstractTile tile = getLowestFromList(openSet);
            if(tile.equals(java_ex1.end)) {
                return backTracePath();
            }
            openSet.remove(tile);
            closedSet.add(tile);

            ArrayList<AbstractTile> neighbors = tile.getNeighbors(size, java_ex1.matrix);
            for (AbstractTile item: neighbors) {

                if(closedSet.contains(item)) {
                    continue;
                }
                if(!openSet.contains(item)) {
                        --blockNum;
                        if(item.timeDiscovered == 0) {
                            item.timeDiscovered += globalTime + 1;
                            globalTime++;
                        }
                        openSet.add(item);
                        Double score = tile.groundCost + item.getCost();
                        if(score >= item.groundCost) {
                            continue;
                        }
                        item.cameFrom = tile;
                        item.cameFromDirection = parseDirection(tile, item);
                        item.groundCost = score;
                        item.huristicCost = heuristicCostEstimate(item, java_ex1.end);
                }
            }
        }
        return null;
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

    private AbstractTile getLowestFromList(ArrayList<AbstractTile> openSet) {
        Double min = Double.MAX_VALUE / 4;
        AbstractTile temp = null;
        for (AbstractTile item: openSet) {
            if(item.isCrossable()) {
                Double val = item.groundCost + item.huristicCost;
                if(val < min) {
                    temp = item;
                    min = item.groundCost + item.huristicCost;
                    continue;
                }
                if(val.equals(min)) {
                    if(item.timeDiscovered < temp.timeDiscovered) {
                        temp = item;
                        min = item.groundCost + item.huristicCost;
                        continue;
                    }
                    if(item.timeDiscovered == temp.timeDiscovered) {
                        if(item.cameFromDirection.getValue() < temp.cameFromDirection.getValue()) {
                            temp = item;
                            min = item.groundCost + item.huristicCost;
                        }
                    }
                }

            }
        }
        return temp;
    }

    private Pair<ArrayList<String>, Integer> backTracePath() {
        ArrayList<AbstractTile> path = new ArrayList<>();
        AbstractTile temp = this.end;
        while(!temp.equals(java_ex1.start)) {
            path.add(temp);
            temp = temp.cameFrom;
        }
        path.add(java_ex1.start);
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
            return "U";
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
            return "L";
        }
        if(deltaX == 0 && deltaY == 1) {
            return "R";
        }
        return "pie";
    }

    private Double heuristicCostEstimate(AbstractTile start, AbstractTile end) {
        Double e =  (double) Math.max(Math.abs(end.getCordinate().getKey() - start.getCordinate().getKey()), Math.abs(end.getCordinate().getValue() - start.getCordinate().getValue()));
        return e;
    }

}
