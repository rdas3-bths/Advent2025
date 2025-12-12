import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day11 {

    public static HashMap<String, ArrayList<String>> circuit = new HashMap<>();
    public static HashMap<String, Long> cache = new HashMap<>();

    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");

        for (String line : fileData) {
            String node = line.split(":")[0].trim();
            String[] destinations = line.split(":")[1].trim().split(" ");
            ArrayList<String> nodeDestinations = new ArrayList<String>();
            for (String dest : destinations) {
                nodeDestinations.add(dest);
            }
            circuit.put(node, nodeDestinations);
        }

        System.out.println("Part one answer: " + traverseCircuit("you"));
        System.out.println("Part two answer: " + traverseCircuitPartTwo("svr", false, false));
    }

    public static long traverseCircuit(String currentNode) {
        if (currentNode.equals("out")) {
            return 1;
        }

        long total = 0;
        for (String nextNode : circuit.get(currentNode)) {
            total += traverseCircuit(nextNode);
        }

        return total;
    }

    public static long traverseCircuitPartTwo(String currentNode, boolean foundDAC, boolean foundFFT) {

        String t = currentNode + "," + foundDAC + "," + foundFFT;
        if (cache.containsKey(t)) {
            return cache.get(t);
        }

        if (currentNode.equals("out")) {
            if (foundDAC && foundFFT) {
                cache.put(t, 1L);
                return 1;
            }
            else {
                cache.put(t, 0L);
                return 0;
            }
        }

        if (currentNode.equals("dac")) foundDAC = true;
        if (currentNode.equals("fft")) foundFFT = true;

        long total = 0;
        for (String nextNode : circuit.get(currentNode)) {
            total += traverseCircuitPartTwo(nextNode, foundDAC, foundFFT);
        }

        cache.put(t, total);
        return total;
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