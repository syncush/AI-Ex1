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
            for (int i = 0 ; i < size; i++) {
                String temp = fileStream.readLine();
                int y = 0;
                for (char item: temp.toCharArray()) {
                    switch(item) {
                        case 'S': {
                            matrix.add(new StartTile(i, y));
                        } break;

                        case 'G': {
                            matrix.add(new FinishTile(i, y));
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

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }


    }

}