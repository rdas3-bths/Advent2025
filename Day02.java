import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        ArrayList<Long> productIDs = new ArrayList<Long>();
        for (String s : fileData.get(0).split(",")) {
            long first = Long.parseLong(s.split("-")[0]);
            long second = Long.parseLong(s.split("-")[1]);
            for (long i = first; i <= second; i++) {
                productIDs.add(i);
            }
        }

        long partOneAnswer = 0;
        long partTwoAnswer = 0;
        for (Long p : productIDs) {
            if (isValidProductID(p, false)) {
                partOneAnswer += p;
            }
            if (isValidProductIDAlternate(p, true)) {
                partTwoAnswer += p;
            }
        }

        System.out.println("Part one answer: " + partOneAnswer);
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static boolean isValidProductID(long productID, boolean partTwo) {
        String pID = productID + "";
        String regex = "^(.+)\\1$";
        if (partTwo) {
            regex = "^(.+)\\1+$";
        }
        Matcher matches = Pattern.compile(regex).matcher(pID);

        return matches.find();
    }

    public static boolean isValidProductIDAlternate(long productID, boolean partTwo) {
        String pID = productID + "";
        if (partTwo) {
            return (pID + pID).substring(1, pID.length() * 2 - 1).contains(pID);
        }
        else {
            return (pID.substring(0, pID.length()/2).equals(pID.substring(pID.length()/2)));
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
