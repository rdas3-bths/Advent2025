import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day04 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        String[][] grid = get2DArray(fileData);

        int partOneAnswer = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                String e = grid[i][j];
                if (e.equals("@")) {
                    int count = countAdjacent(grid, i, j);
                    if (count < 4) {
                        partOneAnswer++;
                    }
                }
            }
        }
        System.out.println("Part one answer: " + partOneAnswer);

        int partTwoAnswer = removePaper(grid);
        // this is how I originally did Day 4 Part 2. Decided to try to it recursively as well.
//        while (hasPaperToRemove(grid)) {
//            for (int i = 0; i < grid.length; i++) {
//                for (int j = 0; j < grid[0].length; j++) {
//                    String e = grid[i][j];
//                    if (e.equals("@")) {
//                        int count = countAdjacent(grid, i, j);
//                        if (count < 4) {
//                            grid[i][j] = ".";
//                            partTwoAnswer++;
//                        }
//                    }
//                }
//            }
//        }
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static int removePaper(String[][] grid) {
        if (!hasPaperToRemove(grid)) {
            return 0;
        }
        else {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    String e = grid[i][j];
                    if (e.equals("@")) {
                        int count = countAdjacent(grid, i, j);
                        if (count < 4) {
                            grid[i][j] = ".";
                            return 1 + removePaper(grid);
                        }
                    }
                }
            }
        }
        return -1;
    }

    public static boolean hasPaperToRemove(String[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                String e = grid[i][j];
                if (e.equals("@")) {
                    int count = countAdjacent(grid, i, j);
                    if (count < 4) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int countAdjacent(String[][] grid, int row, int col) {
        int count = 0;
        String left = grid[row][col-1];
        String right = grid[row][col+1];
        String up = grid[row-1][col];
        String down = grid[row+1][col];

        String upLeft = grid[row-1][col-1];
        String upRight = grid[row-1][col+1];
        String downLeft = grid[row+1][col-1];
        String downRight = grid[row+1][col+1];

        if (left.equals("@")) count++;
        if (right.equals("@")) count++;
        if (up.equals("@")) count++;
        if (down.equals("@")) count++;
        if (upLeft.equals("@")) count++;
        if (upRight.equals("@")) count++;
        if (downLeft.equals("@")) count++;
        if (downRight.equals("@")) count++;

        return count;
    }

    public static String[][] get2DArray(ArrayList<String> fileData) {

        String borderRow = "";
        for (int i = 0; i < fileData.get(0).length(); i++) {
            borderRow += ".";
        }

        fileData.add(0, borderRow);
        fileData.add(borderRow);

        for (int i = 0; i < fileData.size(); i++) {
            String s = fileData.get(i);
            s = "." + s + ".";
            fileData.set(i, s);
        }

        int rows = fileData.size();
        int cols = fileData.get(0).length();
        String[][] grid = new String[rows][cols];


        for (int i = 0; i < fileData.size(); i++) {
            String row = fileData.get(i);
            for (int j = 0; j < row.length(); j++) {
                String entry = row.substring(j, j+1);
                grid[i][j] = entry;
            }
        }

        return grid;
    }

    public static void print2DArray(String[][] grid) {
        for (String[] row : grid) {
            for (String e : row) {
                System.out.print(e);
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
