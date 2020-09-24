package astar;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @see ShortestPathsSolver for more method documentation
 */
public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private AStarGraph<Vertex> input;
    private Map<Vertex, Vertex> edgeTo;
    private Map<Vertex, Double> distTo;
    private Vertex start;
    private Vertex goal;
    private double time;
    private int count;
    private SolverOutcome res;
    private TreeMapMinPQ<Vertex> fringe;

    /**
     * Immediately solves and stores the result of running memory optimized A*
     * search, computing everything necessary for all other methods to return
     * their results in constant time. The timeout is given in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();
        Stopwatch sw = new Stopwatch();
        fringe = new TreeMapMinPQ<>();

        this.input = input;
        this.count = 0;
        this.goal = end;
        this.start = start;

        fringe.add(start, 0);
        distTo.put(start, 0.0);
        res = SolverOutcome.UNSOLVABLE;
        Vertex current = start;

        while (!fringe.isEmpty()) {
            current = fringe.removeSmallest();
            time = sw.elapsedTime();
            if (current.equals(goal)) {
                res = SolverOutcome.SOLVED;
                break;
            }
            count++;
            if (time >= timeout) {
                res = SolverOutcome.TIMEOUT;
                break;
            }
            for (WeightedEdge<Vertex> item: input.neighbors(current)) {
                if (!visited.contains(item.to())) {
                    relax(item);
                }
            }
            visited.add(current);
        }
    }

    private void relax(WeightedEdge<Vertex> cur) {
        Vertex from = cur.from();
        Vertex to = cur.to();
        double weight = cur.weight();
        double h = input.estimatedDistanceToGoal(to, goal);
        double newPriority = distTo.get(from) + weight + h;
        if (!fringe.contains(to)) {
            fringe.add(to, newPriority);
            distTo.put(to, distTo.get(from) + weight);
            edgeTo.put(to, from);
        } else {
            double oldPriority = distTo.get(to) + h;
            if (newPriority < oldPriority) {
                distTo.replace(to, distTo.get(from) + weight);
                edgeTo.replace(to, from);
                fringe.changePriority(to, newPriority);
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return res;
    }

    @Override
    public List<Vertex> solution() {
        List<Vertex> ans = new ArrayList<>();
        if (res == SolverOutcome.SOLVED) {
            ans.add(goal);
            Vertex temp = goal;
            while (edgeTo.containsKey(temp)) {
                temp = edgeTo.get(temp);
                ans.add(temp);
            }
            Collections.reverse(ans);
        }
        return ans;
    }


    @Override
    public double solutionWeight() {
        double weight = 0;
        if (res == SolverOutcome.SOLVED) {
            weight = distTo.get(goal);
        }
        return weight;
    }

    /** The total number of priority queue removeSmallest operations. */
    @Override
    public int numStatesExplored() {
        return count;
    }

    @Override
    public double explorationTime() {
        return time;
    }
}
