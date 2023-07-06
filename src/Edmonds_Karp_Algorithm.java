import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Edmonds_Karp_Algorithm {
    private static int[] breadth_first_search(int[][] graph, int s) {
        int n = graph.length;
        char[] color = new char[n];
        int[] pre = new int[n];
        Queue<Integer> q = new ArrayDeque<>(n);
        Arrays.fill(color, 'W');
        Arrays.fill(pre, -1);
        color[s] = 'G';
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int i = 0; i < n; i++) {
                if (graph[u][i] > 0 && color[i] == 'W') {
                    color[i] = 'G';
                    pre[i] = u;
                    q.add(i);
                }
            }
        }
        return pre;
    }

    private static int min_cost_in_path(int[][] c, int[] pre, int s, int t) {
        int x = t;
        int min = Integer.MAX_VALUE;
        while (x != -1 && x != s) {
            if (pre[x] == -1) break;
            if(c[pre[x]][x] < min)
                min = c[pre[x]][x];
            x = pre[x];
        }
        if(x == s) return min;
        else return -1;
    }

    private static int[][] edmonds_karp(int[][] c, int s, int t) {
        int n = c.length;
        int[][] f = new int[n][n];
        // residual network
        int[][] cf = Arrays.stream(c).map(int[]::clone).toArray(int[][]::new);
        int[] pre = breadth_first_search(cf, 0);
        int min = min_cost_in_path(cf,pre,s,t);
        while (min > -1){
            int x = t;
            while (x != -1 && x != s){
                if (pre[x] == -1) break;
                if (c[pre[x]][x] > 0)
                    f[pre[x]][x] += min;
                else
                    f[x][pre[x]] -= min;
                cf[pre[x]][x] -= min;
                cf[x][pre[x]] = min;
                x = pre[x];
            }
            pre = breadth_first_search(cf, 0);
            min = min_cost_in_path(cf,pre,s,t);
        }
        return f;
    }

    public static void main(String[] args) {
        int n = 6;
        int[][] c = new int[n][n]; // capacity matrix;
        c[0][1] = 16;
        c[0][5] = 13;
        c[1][2] = 12;
        c[2][3] = 20;
        c[2][5] = 9;
        c[4][3] = 4;
        c[4][2] = 7;
        c[5][4] = 14;
        c[5][1] = 4;

        int[][] f = edmonds_karp(c, 0, 3);
        for(int[] row : f)
            System.out.println(Arrays.toString(row));
    }
}
