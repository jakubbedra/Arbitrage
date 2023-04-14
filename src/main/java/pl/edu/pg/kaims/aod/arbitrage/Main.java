package pl.edu.pg.kaims.aod.arbitrage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {
    static class CycleDetector {

        private double[][] g;
        private boolean[] visited;
        private int n;
        private List<List<Integer>> cycles;

        public List<List<Integer>> findAllCycles(double[][] g) {
            this.g = g;
            n = g.length;
            visited = new boolean[n];
            cycles = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                dfs(i, i, new ArrayList<>());
            }

            return cycles;
        }

        private void dfs(int start, int current, List<Integer> path) {
            visited[current] = true;
            path.add(current);

            for (int i = 0; i < n; i++) {
                if (g[current][i] != 0) {
                    if (i == path.get(0)) {
                        path.add(i);
                        cycles.add(new ArrayList<>(path));
                        path.remove((Integer) i);
                    } else if (!visited[i]) {
                        dfs(start, i, path);
                    }
                }
            }

            visited[current] = false;
            path.remove(path.size() - 1);
        }
    }

    public static boolean checkIfArbitragePresent(double[][] graph, List<List<Integer>> cycles) {
        for (List<Integer> cycle : cycles) {
            double tmp = 1.0;
            for (int i = 0; i < cycle.size() - 1; i++) {
                tmp *= graph[cycle.get(i)][cycle.get(i + 1)];
            }
            if (tmp > 1.0) {
                return true;
            }
        }
        return false;
    }

    public static void arbitrage() throws IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String tmp = br.readLine();
            List<String> results = new LinkedList<>();
            Map<String, Integer> namesToVertices = new HashMap<>();
            int n = Integer.parseInt(tmp);
            int xd = 1;
            while (n != 0) {
                double[][] graph = new double[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        graph[i][j] = 0.0;
                    }
                }

                // vertices
                for (int i = 0; i < n; i++) {
                    tmp = br.readLine();
                    namesToVertices.put(tmp, i);
                }

                // edges
                br.readLine();
                tmp = br.readLine();
                int m = Integer.parseInt(tmp);
                for (int i = 0; i < m; i++) {
                    tmp = br.readLine();
                    String[] split = tmp.split(" ");
                    String v1 = split[0];
                    double w = Double.parseDouble(split[1]);
                    String v2 = split[2];
                    graph[namesToVertices.get(v1)][namesToVertices.get(v2)] = w;
                }
                br.readLine();

                // digraph created, now get all cycles and check if arbitrage is present
                CycleDetector detector = new CycleDetector();
                List<List<Integer>> cycles = detector.findAllCycles(graph);

                boolean arbitragePresent = checkIfArbitragePresent(graph, cycles);
                if (arbitragePresent) {
                    results.add("Case " + xd + ": Yes");
                } else {
                    results.add("Case " + xd + ": No");
                }
                for (List<Integer> cycle : cycles) {
                    //System.out.println(cycle);
                }
                tmp = br.readLine();
                n = Integer.parseInt(tmp);
                xd++;
            }
            results.forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        //arbitrage();
//        double[][] g = {
//                {0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0},
//                {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0},
//                {1.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0},
//                {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
//                {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
//                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0},
//                {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0},
//        };
//        int n = 9;
        arbitrage();
    }

}
