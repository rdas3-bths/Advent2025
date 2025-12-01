import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day01 {

    public static int partOneAnswer = 0;
    public static int partTwoAnswer = 0;

    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");

        int position = 50;
        for (String line : fileData) {
            String direction = line.substring(0,1);
            int amount = Integer.parseInt(line.substring(1));
            position = doRotation(direction, amount, position);

        }
        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static int doOneMove(String direction, int position) {
        if (direction.equals("L")) {
            position--;
            if (position == -1) {
                position = 99;
            }
        }
        if (direction.equals("R")) {
            position++;
            if (position == 100) {
                position = 0;
            }
        }
        if (position == 0) {
            partTwoAnswer++;
        }
        return position;
    }

    public static int doRotation(String direction, int amount, int position) {

        for (int i = 0; i < amount; i++) {
            position = doOneMove(direction, position);
        }

        if (position == 0) {
            partOneAnswer++;
        }
        return position;
    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
