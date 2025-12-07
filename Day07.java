import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day07 {

    public static long[] beamCounter;

    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        int rows = fileData.size();
        int cols = fileData.get(0).length();
        beamCounter = new long[cols];
        String[][] grid = new String[rows][cols];


        for (int i = 0; i < fileData.size(); i++) {
            String currentLine = fileData.get(i);
            for (int j = 0; j < currentLine.length(); j++) {
                String c = currentLine.charAt(j) + "";
                if (c.equals("S")) {
                    beamCounter[j] = 1;
                }
                grid[i][j] = c;
            }
        }

        int partOneAnswer = 0;
        for (int i = 0; i < rows-1; i++) {
            partOneAnswer += processRow(i, grid);
        }
        System.out.println("Part one answer: " + partOneAnswer);
        long partTwoAnswer = 0;
        for (long beam : beamCounter) {
            partTwoAnswer += beam;
        }
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static int processRow(int row, String[][] grid) {
        int splits = 0;
        long[] newBeamCounter = new long[beamCounter.length];
        for (int c = 0; c < grid[row].length; c++) {
            if (grid[row][c].equals("S") || grid[row][c].equals("|")) {
                if (grid[row+1][c].equals("^")) {
                    splits++;
                    newBeamCounter[c-1] += beamCounter[c];
                    newBeamCounter[c+1] += beamCounter[c];
                    if (grid[row+1][c-1].equals(".")) {
                        grid[row+1][c-1] = "|";
                    }
                    if (grid[row+1][c+1].equals(".")) {
                        grid[row+1][c+1] = "|";
                    }
                }
                else {
                    grid[row+1][c] = "|";
                    newBeamCounter[c] += beamCounter[c];
                }
            }
        }
        beamCounter = newBeamCounter;
        return splits;
    }

    public static void printGrid(String[][] grid) {
        for (String[] row : grid) {
            for (String item : row) {
                System.out.print(item);
            }
            System.out.println();
        }
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
