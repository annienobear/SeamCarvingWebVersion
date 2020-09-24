package seamcarving;

import astar.AStarGraph;
import astar.AStarSolver;
import astar.WeightedEdge;
import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AStarSeamCarver implements SeamCarver {
    private Picture picture;
    private AStarGraph<Point> pointGraph;
    private boolean goLeft;
    private Point start;
    private Point end;

    public AStarSeamCarver(Picture picture) {
        if (picture == null) {
            throw new NullPointerException("Picture cannot be null.");
        }
        this.picture = new Picture(picture);
        start = new Point(-1, -1);
        end = new Point(-2, -2);
        pointGraph = new AStarGraph<Point>() {
            @Override
            public List<WeightedEdge<Point>> neighbors(Point v) {
                int x = v.x;
                int y = v.y;
                List<WeightedEdge<Point>> res = new ArrayList<>();
                if (goLeft) {
                    if (x == width() - 1) {
                        res.add(new WeightedEdge<>(v, end, 0));
                        return res;
                    }
                    if (v.equals(start)) {
                        for (int i = 0; i < height(); i++) {
                            Point temp = new Point(0, i);
                            res.add(new WeightedEdge<>(v, temp, energy(0, i)));
                        }
                        return res;
                    }
                    Point next = new Point(x + 1, y);
                    WeightedEdge<Point> nextP = new WeightedEdge<Point>(v, next, energy(x + 1, y));
                    res.add(nextP);
                    if (y != height() - 1) {
                        Point up = new Point(x + 1, y + 1);
                        WeightedEdge<Point> upP = new WeightedEdge<Point>(v, up, energy(x + 1, y + 1));
                        res.add(upP);
                    }
                    if (y != 0) {
                        Point down = new Point(x + 1, y - 1);
                        WeightedEdge<Point> downP = new WeightedEdge<Point>(v, down, energy(x + 1, y - 1));
                        res.add(downP);
                    }
                } else {
                    if (y == height() - 1) {
                        res.add(new WeightedEdge<>(v, end, 0));
                        return res;
                    }
                    if (v.equals(start)) {
                        for (int i = 0; i < width(); i++) {
                            Point temp = new Point(i, 0);
                            res.add(new WeightedEdge<>(v, temp, energy(i, 0)));
                        }
                        return res;
                    }
                    Point next = new Point(x, y + 1);
                    WeightedEdge<Point> nextP = new WeightedEdge<Point>(v, next, energy(x, y + 1));
                    res.add(nextP);
                    if (x != width() - 1) {
                        Point below = new Point(x + 1, y + 1);
                        WeightedEdge<Point> belowP = new WeightedEdge<Point>(v, below, energy(x + 1, y + 1));
                        res.add(belowP);
                    }
                    if (x != 0) {
                        Point up = new Point(x - 1, y + 1);
                        WeightedEdge<Point> upP = new WeightedEdge<Point>(v, up, energy(x - 1, y + 1));
                        res.add(upP);
                    }
                }
                return res;
            }

            @Override
            public double estimatedDistanceToGoal(Point s, Point goal) {
                return 0;
            }

        };
    }


    public Picture picture() {
        return new Picture(picture);
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public Color get(int x, int y) {
        return picture.get(x, y);
    }

    public double energy(int x, int y) {
        int smallx = x - 1;
        int smally = y - 1;
        int largerx = x + 1;
        int largery = y + 1;
        if (x == 0) {
            smallx = width() - 1;
        }
        if (x == width() - 1) {
            largerx = 0;
        }
        if (y == 0) {
            smally = height() - 1;
        }
        if (y == height() - 1) {
            largery = 0;
        }
        double yRGB = Math.pow(get(x, smally).getRed() - get(x, largery).getRed(), 2) +
                    Math.pow(get(x, smally).getBlue() - get(x, largery).getBlue(), 2) +
                    Math.pow(get(x, smally).getGreen() - get(x, largery).getGreen(), 2);
        double xRGB = Math.pow(get(smallx, y).getRed() - get(largerx, y).getRed(), 2) +
                    Math.pow(get(smallx, y).getBlue() - get(largerx, y).getBlue(), 2) +
                    Math.pow(get(smallx, y).getGreen() - get(largerx, y).getGreen(), 2);
        return Math.sqrt(xRGB + yRGB);
    }

    public int[] findHorizontalSeam() {
        goLeft = true;
        int[] ans = new int[width()];
        AStarSolver<Point> solver = new AStarSolver<>(pointGraph, start, end, 2000);
        List<Point> res = solver.solution();
        for (int i = 1; i < res.size() - 1; i++) {
            ans[i - 1] = res.get(i).y;
        }
        return ans;
    }

    public int[] findVerticalSeam() {
        goLeft = false;
        int[] ans = new int[height()];
        AStarSolver<Point> solver = new AStarSolver<>(pointGraph, start, end, 2000);
        List<Point> res = solver.solution();
        for (int i = 1; i < res.size() - 1; i++) {
            ans[i - 1] = res.get(i).x;
        }
        return ans;
    }
}
