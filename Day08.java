import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day08 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        ArrayList<JunctionBox> allBoxes = new ArrayList<JunctionBox>();

        for (String line : fileData) {
            long x = Long.parseLong(line.split(",")[0]);
            long y = Long.parseLong(line.split(",")[1]);
            long z = Long.parseLong(line.split(",")[2]);
            JunctionBox j = new JunctionBox(x, y, z);
            allBoxes.add(j);
        }

        ArrayList<JunctionBoxPairs> pairs = new ArrayList<JunctionBoxPairs>();

        for (int i = 0; i < allBoxes.size(); i++) {
            JunctionBox one = allBoxes.get(i);

            for (int j = i + 1; j < allBoxes.size(); j++) {
                JunctionBox two = allBoxes.get(j);

                JunctionBoxPairs pair = new JunctionBoxPairs(one, two);
                pairs.add(pair);
            }
        }

        Collections.sort(pairs);

        ArrayList<Circuit> circuits = new ArrayList<Circuit>();

        for (JunctionBox j : allBoxes) {
            circuits.add(new Circuit(j));
        }

        processPairs(pairs, circuits, false);

        long partOneAnswer = 1;
        for (int i = 0; i < 3; i++) {
            partOneAnswer *= circuits.get(i).boxes.size();
        }
        System.out.println("Part one answer: " + partOneAnswer);

        // reset circuits for Part Two
        circuits = new ArrayList<Circuit>();

        for (JunctionBox j : allBoxes) {
            circuits.add(new Circuit(j));
        }

        long partTwoAnswer = processPairs(pairs, circuits, true);
        System.out.println("Part two answer: " + partTwoAnswer);


    }

    public static long processPairs(ArrayList<JunctionBoxPairs> pairs, ArrayList<Circuit> circuits, boolean partTwo) {
        int amount = -1;
        if (circuits.size() == 20) {
            amount = 10;
        }
        else {
            amount = 1000;
        }
        int connected = 0;
        int currentPair = 0;
        boolean condition = true;
        if (!partTwo) {
            condition = connected != amount;
        }
        else {
            condition = circuits.get(1).boxes.size() != 0;
        }
        while (condition) {
            JunctionBoxPairs p = pairs.get(currentPair);
            // if both are unpaired: remove one from one circuit and add to the other circuit
            JunctionBox one = p.one;
            JunctionBox two = p.two;
            if (isAlone(circuits, one) && isAlone(circuits, two)) {
                removeFromCircuit(circuits, two);
                connect(circuits, one, two);
            }

            // if one is unpaired and two is paired
            // remove one from its circuit and add to two's circuit
            else if (isAlone(circuits, one) && !isAlone(circuits, two)) {
                removeFromCircuit(circuits, one);
                connect(circuits, two, one);
            }

            // if two is unpaired, and one is paired
            // remove two from its circuit and add to one's circuit
            else if (isAlone(circuits, two) && !isAlone(circuits, one)) {
                removeFromCircuit(circuits, two);
                connect(circuits, one, two);
            }

            // if neither is unpaired, but they are not together
            // remove all boxes from one circuit
            // add to the other circuit
            else if (!isAlone(circuits, one) && !isAlone(circuits, two)) {
                if (!checkConnected(circuits, one, two)) {
                    mergeBoxes(circuits, one, two);
                }
            }

            currentPair++;
            connected++;
            Collections.sort(circuits);

            if (!partTwo && connected == amount) {
                return 0;
            }

            if (partTwo && circuits.get(1).boxes.size() == 0) {
                return p.one.x * p.two.x;
            }
        }
        return 0;
    }


    public static void mergeBoxes(ArrayList<Circuit> circuits, JunctionBox one, JunctionBox two) {
        ArrayList<JunctionBox> removed = new ArrayList<JunctionBox>();
        for (int i = 0; i < circuits.size(); i++) {
            Circuit c = circuits.get(i);
            if (c.boxes.contains(one)) {
                removed.addAll(c.boxes);
                c.boxes = new ArrayList<JunctionBox>();
            }
        }

        for (int i = 0; i < circuits.size(); i++) {
            Circuit c = circuits.get(i);
            if (c.boxes.contains(two)) {
                c.boxes.addAll(removed);
            }
        }
    }

    public static boolean checkConnected(ArrayList<Circuit> circuits, JunctionBox one, JunctionBox two) {
        for (Circuit c : circuits) {
            if (c.boxes.contains(one) && c.boxes.contains(two)) {
                return true;
            }
        }
        return false;
    }

    public static void removeFromCircuit(ArrayList<Circuit> circuits, JunctionBox j) {
        for (Circuit c : circuits) {
            if (c.boxes.contains(j)) {
                c.boxes.remove(j);
            }
        }
    }

    public static void connect(ArrayList<Circuit> circuits, JunctionBox source, JunctionBox connect) {
        for (Circuit c : circuits) {
            if (c.boxes.contains(source)) {
                if (!c.boxes.contains(connect)) {
                    c.boxes.add(connect);
                }
            }
        }
    }

    public static boolean isAlone(ArrayList<Circuit> circuits, JunctionBox j) {
        for (Circuit c : circuits) {
            if (c.boxes.contains(j) && c.boxes.size() == 1) {
                return true;
            }
        }
        return false;
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

class JunctionBox {
    public long x;
    public long y;
    public long z;

    public JunctionBox(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return x + "," + y + "," + z;
    }

    public boolean equals(Object o) {
        JunctionBox j = (JunctionBox) o;
        return this.x == j.x && this.y == j.y && this.z == j.z;
    }

}

class JunctionBoxPairs implements Comparable {
    public JunctionBox one;
    public JunctionBox two;
    public double distance;

    public JunctionBoxPairs(JunctionBox one, JunctionBox two) {
        this.one = one;
        this.two = two;
        setDistance();
    }

    public String toString() {
        return this.one.toString() + " and " + this.two.toString() + ": " + distance;
    }

    public void setDistance() {
        double x = Math.pow(two.x - one.x, 2);
        double y = Math.pow(two.y - one.y, 2);
        double z = Math.pow(two.z - one.z, 2);

        double sum = x + y + z;

        distance = Math.sqrt(sum);
    }

    public int compareTo(Object o) {
        JunctionBoxPairs jp = (JunctionBoxPairs) o;

        return Double.compare(this.distance, jp.distance);
    }
}

class Circuit implements Comparable {
    public ArrayList<JunctionBox> boxes;

    public Circuit(JunctionBox initial) {
        boxes = new ArrayList<JunctionBox>();
        boxes.add(initial);
    }

    public String toString() {
        return boxes.size() + " " + boxes.toString() + "\n";
    }

    public int compareTo(Object o) {
        Circuit c = (Circuit) o;
        return (c.boxes.size() - this.boxes.size());
    }
}
