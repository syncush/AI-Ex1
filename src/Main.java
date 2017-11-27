import java.io.*;
import java.util.ArrayList;

public class Main
{
    public static void main(String [] args)
    {

        try {
            BufferedReader fileStream = new BufferedReader(new FileReader(args[0]));
            String algo = fileStream.readLine();
            int size = Integer.parseInt(fileStream.readLine());
            ArrayList<AbstractTile> matrix = new ArrayList<>();
            AbstractTile start = null;
            AbstractTile end = null;
            for (int i = 0 ; i < size; i++) {
                String temp = fileStream.readLine();
                int y = 0;
                for (char item: temp.toCharArray()) {
                    switch(item) {
                        case 'S': {
                            start = new StartTile(i, y);
                            matrix.add(start);

                        } break;

                        case 'G': {
                            end = new FinishTile(i, y);
                            matrix.add(end);
                        } break;

                        case 'R': {
                            matrix.add(new RoadTile(i, y));
                        } break;

                        case 'D': {
                            matrix.add(new FieldTile(i, y));
                        } break;

                        case 'H': {
                            matrix.add(new HillTile(i, y));
                        } break;

                        case 'W': {
                            matrix.add(new WaterTile(i, y));
                        } break;

                        default: {

                        }
                    }
                    y++;
                }
            }
            AStar astar = new AStar(matrix, start , end );
            astar.runAlgo();

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
        catch (Exception e) {
            e.printStackTrace();

        }


    }

}