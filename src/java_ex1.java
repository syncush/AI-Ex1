import java.io.*;
import java.util.ArrayList;

public class java_ex1 {
    static char[][] matrix;
    static AbstractTile start = null;
    static AbstractTile end = null;

    /**
     * Main function which gets a file and runs the specified algorithm
     * @param args file path is in args[0]
     */
    public static void main(String[] args) {
        try {
            BufferedReader fileStream = new BufferedReader(new FileReader("input.txt"));
            String algo = fileStream.readLine();
            int size = Integer.parseInt(fileStream.readLine());
            java_ex1.matrix = new char[size][size];
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
                    String finalSolString = "no path";
                    PrintWriter pWriter = new PrintWriter("output.txt");
                    pWriter.println(finalSolString);
                    pWriter.close();
                    return;
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
                PrintWriter writer = new PrintWriter("output.txt");
                writer.println(sBuilder.toString());
                writer.close();
            } else {
                AStar astar = new AStar(start, end);
                Pair<ArrayList<String>, Integer> finalSolution = astar.runAlgo();
                if(finalSolution == null) {
                    String finalSolString ="no path";
                    PrintWriter pWriter = new PrintWriter("output.txt");
                    pWriter.println(finalSolString);
                    pWriter.close();
                    return;
                }
                String finalSolString ="";
                for (String item: finalSolution.getKey()) {
                    finalSolString += item;
                    finalSolString += '-';
                }
                int index = finalSolString.lastIndexOf('-');

                finalSolString += finalSolution.getValue();
                StringBuilder sBuilder = new StringBuilder(finalSolString);
                sBuilder.setCharAt(index,' ');
                PrintWriter writer = new PrintWriter("output.txt");
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
