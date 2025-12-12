import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day12 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        ArrayList<String> onlyGrids = new ArrayList<String>();

        for (String line : fileData) {
            if (line.contains("x")) {
                onlyGrids.add(line);
            }
        }

        int answer = 0;
        for (String grid : onlyGrids) {
            String first = grid.split(":")[0];
            String second = grid.split(":")[1].trim();
            int fullArea = Integer.parseInt(first.split("x")[0]) * Integer.parseInt(first.split("x")[1]);
            String[] counts = second.split(" ");
            int[] presentCounts = new int[counts.length];
            int i = 0;
            for (String c : counts) {
                presentCounts[i] = Integer.parseInt(c);
                i++;
            }
            int presentArea = 0;
            for (int present : presentCounts) {
                presentArea += (present * 9);
            }
            if (presentArea <= fullArea) {
                answer += 1;
            }
        }
        System.out.println("Answer: " + answer);
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
