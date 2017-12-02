import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;

public class Main {
    static char[][] matrix;
    static AbstractTile start = null;
    static AbstractTile end = null;
    public static void main(String[] args) {
        try {
            BufferedReader fileStream = new BufferedReader(new FileReader(args[0]));
            String algo = fileStream.readLine();
            int size = Integer.parseInt(fileStream.readLine());
            Main.matrix = new char[size][size];
            for (int i = 0 ; i < size; i++) {
                String tiles = fileStream.readLine();
                for (int j = 0; j < size; j++) {
                    if(tiles.charAt(j) == 'S') {
                        start = new StartTile(i, j);
                    }
                    if(tiles.charAt(j) == 'G') {
                        end = new FinishTile(i, j);
                    }
                    matrix[i][j] = tiles.charAt(j);
                }
            }
            if(algo.equals("IDS")) {
                IDS ids = new IDS();
                Pair<ArrayList<String>, Integer> finalSolution = ids.runAlgo();
                if(finalSolution == null) {
                    int x = 5;
                }
                String finalSolString ="";
                for (String item: finalSolution.getKey()) {
                    finalSolString += item;
                    finalSolString += '-';
                }


                finalSolString += finalSolution.getValue();
                StringBuilder sBuilder = new StringBuilder(finalSolString);
                int index = finalSolString.lastIndexOf('-');
                sBuilder.setCharAt(index,' ');
                PrintWriter writer = new PrintWriter("myoutput.txt");
                writer.println(sBuilder.toString());
                writer.close();
            } else {
                AStar astar = new AStar(start, end);
                Pair<ArrayList<String>, Integer> finalSolution = astar.runAlgo();
                String finalSolString ="";
                for (String item: finalSolution.getKey()) {
                    finalSolString += item;
                    finalSolString += '-';
                }
                int index = finalSolString.lastIndexOf('-');

                finalSolString += finalSolution.getValue();
                StringBuilder sBuilder = new StringBuilder(finalSolString);
                sBuilder.setCharAt(index,' ');
                PrintWriter writer = new PrintWriter("myoutput.txt");
                writer.println(sBuilder.toString());
                writer.close();
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
