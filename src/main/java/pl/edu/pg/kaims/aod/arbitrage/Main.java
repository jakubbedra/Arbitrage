package pl.edu.pg.kaims.aod.arbitrage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
/*
    private class Graph {
        double[][] matrix;
        int n;
        int m;
    }

    private static Set<Integer> blockedSet = new HashSet();
    private static Map<Integer, Set<Integer>> blockedMap = new HashMap<>();
    private static Deque<Integer> stack = new LinkedList<>();
    private static List<List<Integer>> allCycles = new LinkedList<>();

    public static List<List<Integer>> getAllSimpleCycles(double[][] graph, int n, int m) {
        blockedSet = new HashSet();
        blockedMap = new HashMap<>();
        stack = new LinkedList<>();
        allCycles = new LinkedList<>();

        int startVertex = 0;
        while (startVertex < n) {
            double[][] subGraph = createSubGraph(startVertex, graph);

            Optional<Integer> maybeLeastVertex = leastIndexSCC(sccs, subGraph);
            if (maybeLeastVertex.isPresent()) {
                int leastVertex = maybeLeastVertex.get();
                blockedSet.clear();
                blockedMap.clear();
                findCyclesInSCG(leastVertex, leastVertex, n);
                startVertex = leastVertex + 1;
            } else {
                break;
            }
        }

        return allCycles;
    }

    private Optional<Vertex<Integer>> leastIndexSCC(List<Set<Vertex<Integer>>> sccs, Graph<Integer> subGraph) {
        long min = Integer.MAX_VALUE;
        Vertex<Integer> minVertex = null;
        Set<Vertex<Integer>> minScc = null;
        for(Set<Vertex<Integer>> scc : sccs) {
            if(scc.size() == 1) {
                continue;
            }
            for(Vertex<Integer> vertex : scc) {
                if(vertex.getId() < min) {
                    min = vertex.getId();
                    minVertex = vertex;
                    minScc = scc;
                }
            }
        }

        if(minVertex == null) {
            return Optional.empty();
        }
        Graph<Integer> graphScc = new Graph<>(true);
        for(Edge<Integer> edge : subGraph.getAllEdges()) {
            if(minScc.contains(edge.getVertex1()) && minScc.contains(edge.getVertex2())) {
                graphScc.addEdge(edge.getVertex1().getId(), edge.getVertex2().getId());
            }
        }
        return Optional.of(graphScc.getVertex(minVertex.getId()));
    }

    private void unblock(Integer u) {
        blockedSet.remove(u);
        if (blockedMap.get(u) != null) {
            blockedMap.get(u).forEach(v -> {
                if (blockedSet.contains(v)) {
                    unblock(v);
                }
            });
            blockedMap.remove(u);
        }
    }

    private boolean findCyclesInSCG(int startVertex, int currentVertex, int n) {
        boolean foundCycle = false;
        stack.push(currentVertex);
        blockedSet.add(currentVertex);

        for (int neighbor = 0; neighbor < n; neighbor++) {
            if (graph[currentVertex][neighbor] != 0) {
                if (neighbor == startVertex) {
                    List<Integer> cycle = new ArrayList<>();
                    stack.push(startVertex);
                    cycle.addAll(stack);
                    Collections.reverse(cycle);
                    stack.pop();
                    allCycles.add(cycle);
                    foundCycle = true;
                } //explore this neighbor only if it is not in blockedSet.
                else if (!blockedSet.contains(neighbor)) {
                    boolean gotCycle = findCyclesInSCG(startVertex, neighbor, n);
                    foundCycle = foundCycle || gotCycle;
                }
            }
        }
        //if cycle is found with current vertex then recursively unblock vertex and all vertices which are dependent on this vertex.
        if (foundCycle) {
            //remove from blockedSet  and then remove all the other vertices dependent on this vertex from blockedSet
            unblock(currentVertex);
        } else {
            //if no cycle is found with current vertex then don't unblock it. But find all its neighbors and add this
            //vertex to their blockedMap. If any of those neighbors ever get unblocked then unblock current vertex as well.
            for (int w = 0; w < n; w++) {
                if (graph[currentVertex][w] != 0) {
                    Set<Integer> bSet = getBSet(w);
                    bSet.add(currentVertex);
                }
            }
        }
        //remove vertex from the stack.
        stack.pop();
        return foundCycle;
    }

    private Set<Integer> getBSet(int v) {
        return blockedMap.computeIfAbsent(v, (key) ->
                new HashSet<>());
    }

    // create G\v_s
    private double[][] createSubGraph(long startVertex, double[][] graph) {
        Graph g = new Graph();
        g.matrix = new double[n-1][n-1];
        g.n = n-1;
        g.m = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] != 0 && i >= startVertex && j >=startVertex) {
                    g.matrix[i][j] = graph[i][j];
                    g.m++;
                }
            }
        }

        return g.matrix;
    }
*/

    public boolean checkIfArbitragePresent(double[][] graph, List<List<Integer>> cycles) {
        for (List<Integer> cycle : cycles) {
            double tmp = 1.0;
            for (int i = 0; i < cycle.size(); i++) {
                tmp *= i != cycle.size() - 1 ? graph[cycle.get(i)][cycle.get(i + 1)] :
                        graph[cycle.get(i)][cycle.get(0)];
            }
        }
        return false;
    }

    public static void arbitrage() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String tmp = br.readLine();
        String[] split = tmp.split(" ");
        int n = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);

        int[][] graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = 0;
            }
        }

        for (int i = 0; i < m; i++) {
            tmp = br.readLine();
            split = tmp.split(" ");
            int v = Integer.parseInt(split[0]) - 1;
            int u = Integer.parseInt(split[1]) - 1;
            int w = Integer.parseInt(split[2]);
            graph[u][v] = w;
            graph[v][u] = w;
        }


    }

    public static void main(String[] args) throws IOException {
        //arbitrage();
        double[][] g = {
                {0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0},
                {1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
        };
        int n = 9;
        CycleDetector detector = new CycleDetector();
        List<List<Integer>> cycles = detector.findAllCycles(g);

        for (List<Integer> cycle : cycles) {
            System.out.println(cycle);
        }
    }

}
