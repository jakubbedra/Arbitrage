package pl.edu.pg.kaims.aod.arbitrage;

import java.util.ArrayList;
import java.util.List;

public class CycleDetector {

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
                if (i == start) {
                    path.add(i);
                    cycles.add(new ArrayList<>(path));
                    path.remove((Integer)i);
                } else if (!visited[i]) {
                    dfs(start, i, path);
                }
            }
        }

        visited[current] = false;
        path.remove(path.size() - 1);
    }
}