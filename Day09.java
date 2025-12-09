import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day09 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("src/data");
        ArrayList<Point> allPoints = new ArrayList<Point>();
        ArrayList<VerticalLine> verticalLines = new ArrayList<VerticalLine>();

        for (String line : fileData) {
            long x = Long.parseLong(line.split(",")[0]);
            long y = Long.parseLong(line.split(",")[1]);
            Point p = new Point(x, y);
            allPoints.add(p);
        }

        ArrayList<Rectangle> allRectangles = new ArrayList<Rectangle>();
        for (int i = 0; i < allPoints.size(); i++) {
            for (int j = i + 1; j < allPoints.size(); j++) {
                Point one = allPoints.get(i);
                Point two = allPoints.get(j);
                if (one.x != two.x && one.y != two.y) {
                    Rectangle r = new Rectangle(one, two);
                    allRectangles.add(r);
                }

            }
        }

        Collections.sort(allRectangles);
        long partOneAnswer = allRectangles.get(0).area;
        System.out.println("Part one answer: " + partOneAnswer);

        for (int i = 0; i < allPoints.size(); i++) {
            int currentIndex = i;
            int nextIndex = i + 1;
            if (nextIndex == allPoints.size()) {
                nextIndex = 0;
            }
            Point one = allPoints.get(currentIndex);
            Point two = allPoints.get(nextIndex);
            if (one.x == two.x) {
                long minY = Math.min(one.y, two.y);
                long maxY = Math.max(one.y, two.y);
                VerticalLine v = new VerticalLine(one.x, minY, maxY);
                verticalLines.add(v);
            }
        }

        System.out.println(verticalLines);

        for (Rectangle r : allRectangles) {
            System.out.println("Checking Rectangle " + r);
            Point three = r.vertexThree;
            Point four = r.vertexFour;
            boolean check1 = isWithinPolygon(three, verticalLines, allPoints);
            boolean check2 = isWithinPolygon(four, verticalLines, allPoints);
            if (check1 && check2) {
                System.out.println("Part two answer: " + r.area);
                break;
            }
            System.out.println("-----------------------------------");
        }
    }

    public static boolean isWithinPolygon(Point p, ArrayList<VerticalLine> lines, ArrayList<Point> vertexes) {
        System.out.println("Checking point " + p);
        long maxX = getMaxXValue(lines);
        long intersections = 0;
        for (long x = p.x; x <= maxX; x++) {
            Point currentPoint = new Point(x, p.y);
            for (VerticalLine line : lines) {
                if (line.pointOnLine(currentPoint)) {
                    System.out.println("Found " + currentPoint + " on line " + line);
                    intersections++;
                }
            }
        }
        if (intersections == 0) {
            return false;
        }
        if (intersections % 2 == 0)
            return false;
        else
            return true;
    }

    public static long getMaxXValue(ArrayList<VerticalLine> lines) {
        long x = -1;
        for (VerticalLine line : lines) {
            if (line.x > x) {
                x = line.x;
            }
        }
        return x;
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

class Point {
    public long x;
    public long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object o) {
        Point p = (Point) o;
        return (p.x == this.x && p.y == this.y);
    }
}

class Rectangle implements Comparable {
    public Point vertexOne;
    public Point vertexTwo;
    public Point vertexThree;
    public Point vertexFour;
    public long area;

    public Rectangle(Point one, Point two) {
        vertexOne = one;
        vertexTwo = two;
        long length = Math.abs(vertexOne.x - vertexTwo.x) + 1;
        long height = Math.abs(vertexOne.y - vertexTwo.y) + 1;
        area = length * height;
        vertexThree = new Point(vertexOne.x, vertexTwo.y);
        vertexFour = new Point(vertexTwo.x, vertexOne.y);
    }

    public String toString() {
        String one = vertexOne + " " + vertexTwo + "\n";
        one += vertexThree + " " + vertexFour + "\n";
        one += area;
        return one;
    }

    public int compareTo(Object o) {
        Rectangle r = (Rectangle) o;
        return Long.compare(r.area, this.area);
    }
}

class VerticalLine {
    public long x;
    public long startY;
    public long endY;

    public VerticalLine(long x, long startY, long endY) {
        this.x = x;
        this.startY = startY;
        this.endY = endY;
    }

    public boolean pointOnLine(Point p) {
        if (p.x == this.x) {
            if (p.y >= startY && p.y <= endY) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return "(" + x + "," + startY + "-" + endY + ")";
    }
}