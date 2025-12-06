import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day06 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        int maxLength = 0;
        for (String line : fileData) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }
        for (int i = 0; i < fileData.size(); i++) {
            if (fileData.get(i).length() != maxLength) {
                int amount = maxLength - fileData.get(i).length();
                String add = "";
                for (int c = 0; c < amount; c++) {
                    add += ".";
                }
                fileData.set(i, fileData.get(i) + add);
            }
        }

        ArrayList<String[]> problems = new ArrayList<String[]>();
        for (String line : fileData) {
            String[] items = line.trim().split(" +");
            problems.add(items);
        }

        long partOneAnswer = 0;
        for (int c = 0; c < problems.get(0).length; c++) {
            String operation = problems.get(problems.size()-1)[c];
            ArrayList<String> numbers = new ArrayList<String>();
            long answer;
            if (operation.equals("*")) {
                answer = 1;
            }
            else {
                answer = 0;
            }

            for (int r = 0; r < problems.size() - 1; r++) {
                long item = Long.parseLong(problems.get(r)[c].replace(".", ""));
                numbers.add(problems.get(r)[c]);
                if (operation.equals("*")) {
                    answer *= item;
                }
                else {
                    answer += item;
                }
            }
            partOneAnswer += answer;
        }

        System.out.println("Part one answer: " + partOneAnswer);
        String line = fileData.get(0);
        for (int c = 0; c < line.length(); c++) {
            boolean allSpaces = true;
            for (int l = 0; l < fileData.size(); l++) {
                char item = fileData.get(l).charAt(c);
                if (item != ' ') {
                    allSpaces = false;
                }
            }
            if (allSpaces) {
                for (int j = 0; j < fileData.size(); j++) {
                    StringBuilder sb = new StringBuilder(fileData.get(j));
                    sb.setCharAt(c, '|');
                    fileData.set(j, sb.toString());
                }
            }
        }

        for (int i = 0; i < fileData.size(); i++) {
            StringBuilder sb = new StringBuilder(fileData.get(i));
            for (int cc = 0; cc < sb.length(); cc++) {
                char cur = sb.charAt(cc);
                if (cur == ' ') {
                    sb.setCharAt(cc, '.');
                }
            }
            fileData.set(i, sb.toString());
        }

        int numberOfProblems = fileData.get(0).split("\\|").length;
        long partTwoAnswer = 0;
        for (int n = 0; n < numberOfProblems; n++) {
            partTwoAnswer += doPartTwoProblem(n, fileData);
        }
        System.out.println("Part two answer: " + partTwoAnswer);

    }

    public static long doPartTwoProblem(int problemNumber, ArrayList<String> fileData) {
        String numberOne = fileData.get(0).split("\\|")[problemNumber];
        String numberTwo = fileData.get(1).split("\\|")[problemNumber];
        String numberThree = fileData.get(2).split("\\|")[problemNumber];
        String numberFour = fileData.get(3).split("\\|")[problemNumber];
        String operator = fileData.get(4).split("\\|")[problemNumber];
        long answer = 0;
        if (operator.contains("*")) {
            answer = 1;
        }


        for (int i = 0; i < numberOne.length(); i++) {
            char one = numberOne.charAt(i);
            char two = numberTwo.charAt(i);
            char three = numberThree.charAt(i);
            char four = numberFour.charAt(i);

            String combined = "" + one + two + three + four;
            long actualNumber = Long.parseLong(combined.replace(".", ""));
            if (operator.contains("*")) {
                answer *= actualNumber;
            }
            else {
                answer += actualNumber;
            }

        }
        return answer;
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
