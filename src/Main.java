import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static char[][] matirx;
    static AbstractTile start = null;
    static AbstractTile end = null;
    public static void main(String[] args) {
        try {
            BufferedReader fileStream = new BufferedReader(new FileReader(args[0]));
            String algo = fileStream.readLine();
            int size = Integer.parseInt(fileStream.readLine());
            Main.matirx = new char[size][size];
            for (int i = 0 ; i < size; i++) {
                String tiles = fileStream.readLine();
                for (int j = 0; j < size; j++) {
                    if(tiles.charAt(j) == 'S') {
                        start = new StartTile(i, j);
                    }
                    if(tiles.charAt(j) == 'G') {
                        end = new FinishTile(i, j);
                    }
                    matirx[i][j] = tiles.charAt(j);
                }
            }
            if(algo.equals("IDS")) {
                IDS ids = new IDS();
                Pair<ArrayList<String>, Integer> finalSolution = ids.runAlgo();
                for (String item: finalSolution.getKey()) {
                    System.out.print(item);
                    System.out.print("-");
                }
                System.out.print("\b");
                System.out.print(" " + finalSolution.getValue());
            } else {
                AStar astar = new AStar(start, end);
                Pair<ArrayList<String>, Integer> finalSolution = astar.runAlgo();
                for (String item: finalSolution.getKey()) {
                    System.out.print(item);
                    System.out.print("-");
                }
                System.out.print("\b");
                System.out.print(" " + finalSolution.getValue());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

        } catch (IOException ex) {
            ex.printStackTrace();

        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }
}
