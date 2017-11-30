import javafx.util.Pair;

import java.util.*;

public class IDS {
    private AbstractTile start;
    private AbstractTile end;
    private ArrayList<AbstractTile> finalPath;
    private ArrayList<AbstractTile> duplicateList;

    public IDS()  {
        this.start = Main.start;
        this.end = Main.end;
        duplicateList = new ArrayList<>();
    }

    public Pair<ArrayList<String>, Integer> runAlgo() {
        int size = Main.matirx[0].length * Main.matirx[0].length;
        ArrayList<AbstractTile> path = new ArrayList<>();
        path.add(this.start);
        for (int i = 0; i < size; i++) {
            if(runDLS(Main.start,Main.end, i, path)) {
                return backTracePath();
            }
        }
        return null;
    }

    private Pair<ArrayList<String>, Integer> backTracePath() {
        int cost = 0;
        ArrayList<String> pathString = new ArrayList<>();
        int size = this.finalPath.size();
        for (int i = 0; i < size - 1; i++) {
            int deltaX = this.finalPath.get(i + 1).cordinate.getKey() - this.finalPath.get(i).cordinate.getKey();
            int deltaY = this.finalPath.get(i + 1).cordinate.getValue() - this.finalPath.get(i).cordinate.getValue();
            pathString.add(parseMovement(deltaX, deltaY));
            cost += this.finalPath.get(i).getCost();
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

    private boolean runDLS(AbstractTile start, AbstractTile end, int depth, ArrayList<AbstractTile> trackBack) {
        if(start.equals(end)) {
            this.finalPath = trackBack;
            return true;
        }
        if(depth <= 0) {
            return false;
        }
        ArrayList<AbstractTile> neighbors = start.getNeighbors(Main.matirx[0].length, Main.matirx);
        for (AbstractTile item: neighbors) {
            ArrayList<AbstractTile> newPath = new ArrayList<>(trackBack);
            newPath.add(item);
            if(runDLS(item, end, depth - 1, newPath)) {
                return true;
            }
        }
        return false;
    }
}
