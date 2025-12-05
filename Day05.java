import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Day05 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        ArrayList<IngredientRange> ranges = new ArrayList<>();
        ArrayList<Long> ingredients = new ArrayList<Long>();

        for (String line : fileData) {
            if (line.contains("-")) {
                long min = Long.parseLong(line.split("-")[0]);
                long max = Long.parseLong(line.split("-")[1]);
                IngredientRange i = new IngredientRange(min, max);
                ranges.add(i);
            }
            else {
                long number = Long.parseLong(line);
                ingredients.add(number);
            }
        }

        int partOneAnswer = 0;
        for (long number : ingredients) {
            for (IngredientRange range : ranges) {
                if (range.checkIfInRange(number)) {
                    partOneAnswer++;
                    break;
                }
            }
        }
        System.out.println("Part one answer: " + partOneAnswer);

        // for part two, you have to sort the ranges by minimum value
        // then merge the ranges that overlap
        // can't brute force!
        Collections.sort(ranges);
        ArrayList<IngredientRange> mergedRanges = mergeRanges(ranges);
        long partTwoAnswer = 0;
        for (IngredientRange r : mergedRanges) {
            partTwoAnswer += (r.maximum - r.minimum + 1);
        }
        System.out.println("Part two answer: " + partTwoAnswer);
    }

    public static ArrayList<IngredientRange> mergeRanges(ArrayList<IngredientRange> allRanges) {
        ArrayList<IngredientRange> merged = new ArrayList<IngredientRange>();

        merged.add(allRanges.get(0));

        for (int i = 1; i < allRanges.size(); i++) {
            IngredientRange r = allRanges.get(i);
            IngredientRange current = merged.getLast();

            // this means you can merge the two ranges
            // by getting the minimum of the two ranges
            // and the maximum of the two ranges
            if (r.minimum <= current.maximum) {
                ArrayList<Long> all_bounds = new ArrayList<Long>();
                all_bounds.add(current.minimum);
                all_bounds.add(current.maximum);
                all_bounds.add(r.minimum);
                all_bounds.add(r.maximum);
                current.minimum = Collections.min(all_bounds);
                current.maximum = Collections.max(all_bounds);
            }
            else {
                merged.add(r);
            }
        }
        return merged;
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

class IngredientRange implements Comparable {
    public long minimum;
    public long maximum;

    public IngredientRange(long min, long max) {
        minimum = min;
        maximum = max;
    }

    public String toString() {
        return minimum + "-" + maximum;
    }

    public boolean checkIfInRange(long i) {
        return i >= minimum && i <= maximum;
    }

    public int compareTo(Object o) {
        IngredientRange i = (IngredientRange) o;
        return Long.compare(this.minimum, i.minimum);
    }
}
