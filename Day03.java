import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day03 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        long partOneAnswer = 0;
        long partTwoAnswer = 0;

        for (String batteries : fileData) {
            long voltage = getLargestNumber(batteries, 2);
            partOneAnswer += voltage;
            long voltageTwo = getLargestNumber(batteries, 12);
            partTwoAnswer += voltageTwo;
        }

        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    // I originally wrote this method for part one
    // I realized I could use my method for part two to solve part one
    // now I don't need this!
    public static int getLargestCombination(String batteries) {

        ArrayList<Integer> allCombinations = new ArrayList<Integer>();

        for (int i = 0; i < batteries.length()-1; i++) {
            String first = batteries.substring(i,i+1);

            for (int j = i+1; j < batteries.length(); j++) {
                String second = batteries.substring(j, j+1);
                String number = first + second;
                int n = Integer.parseInt(number);
                allCombinations.add(n);
            }
        }

        return Collections.max(allCombinations);
    }

    public static long getLargestNumber(String batteries, int l) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i < batteries.length(); i++) {
            int n = Integer.parseInt(batteries.substring(i, i+1));
            numbers.add(n);
        }

        String largestNumber = "";
        int startIndex = 0;
        while (largestNumber.length() != l) {
            int digitsRemaining = l - largestNumber.length();
            int lastIndex = numbers.size() - digitsRemaining;
            int digit = getLargestWithRange(numbers, startIndex, lastIndex);
            int indexOfDigit = getIndexOfDigit(numbers, startIndex, digit);
            largestNumber = largestNumber + digit;
            startIndex = indexOfDigit+1;
        }

        return Long.parseLong(largestNumber);
    }

    public static int getIndexOfDigit(ArrayList<Integer> numbers, int startingIndex, int digit) {
        for (int i = startingIndex; i < numbers.size(); i++) {
            if (numbers.get(i) == digit) {
                return i;
            }
        }
        return -1;
    }

    public static int getLargestWithRange(ArrayList<Integer> numbers, int first, int last) {

        ArrayList<Integer> subset = new ArrayList<Integer>();

        for (int i = first; i <= last; i++) {
            subset.add(numbers.get(i));
        }

        return Collections.max(subset);
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
