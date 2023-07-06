import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Simplex_linear_program {
    private static class Slack_form {
        int m; // number of equations in slack form
        int n; // number of variables
        Set<Integer> N; // N[i] denotes ith variable or x[i]
        Set<Integer> B; // B[i] denotes ith equation
        double[][] A; // A[i][j] denotes -ve of coefficient of x[j] in equation B[i]
        double[] b; // b[i] is constant term of B[i]th equation
        double[] c; // c[i] is coefficient of x[i] in objective function
        double v; // constant term in objective function

        public Slack_form(int m, int n) {
            this.m = m;
            this.n = n;
            N = new TreeSet<>();
            B = new TreeSet<>();
            A = new double[n + m][n + m];
            b = new double[m + n];
            c = new double[n + m];
        }

        public int first_pos_coefficient() {
            for (int j : N)
                if (c[j] > 0) return j;
            return -1;
        }
    }

    private static Slack_form pivot(Slack_form in, int l, int e) {
        Slack_form f = new Slack_form(in.m, in.n);
        f.b[e] = in.b[l] / in.A[l][e];
        for (int j : in.N) {
            if (j == e) continue;
            f.A[e][j] = in.A[l][j] / in.A[l][e];
        }
        f.A[e][l] = 1 / in.A[l][e];

        for (int i : in.B) {
            if (i == l) continue;
            f.b[i] = in.b[i] - in.A[i][e] * f.b[e];
            for (int j : in.N) {
                if (j == e) continue;
                f.A[i][j] = in.A[i][j] - in.A[i][e] * f.A[e][j];
            }
            f.A[i][l] = -in.A[i][e] * f.A[e][l];
        }
        f.v = in.v + in.c[e] * f.b[e];
        for (int j : in.N) {
            if (j == e) continue;
            f.c[j] = in.c[j] - in.c[e] * f.A[e][j];
        }
        f.c[l] = -in.c[e] * f.A[e][l];
        f.N.addAll(in.N);
        f.N.remove(e);
        f.N.add(l);
        f.B.addAll(in.B);
        f.B.remove(l);
        f.B.add(e);
        return f;
    }

    private static double[] simplex(Slack_form prob) {
        double[] d = new double[prob.n + prob.m];
        Arrays.fill(d, Double.POSITIVE_INFINITY);
        double[] x = new double[prob.n + prob.m];
        Slack_form curr = prob;
        int e = 0;
        while (e > -1) {
            e = curr.first_pos_coefficient();
            if (e == -1) break;
            for (int i : curr.B) {
                if (curr.A[i][e] > 0) d[i] = curr.b[i] / curr.A[i][e];
                else d[i] = Double.POSITIVE_INFINITY;
            }
            double min = d[0];
            int l = 0;
            for (int k : curr.B) {
                if (d[k] < min) {
                    min = d[k];
                    l = k;
                }
            }
            if (d[l] == Double.POSITIVE_INFINITY) {
                System.out.println("Unbounded");
                return null;
            } else curr = pivot(curr, l, e);
        }

        for (int i = 0; i < x.length; i++) {
            if (curr.B.contains(i)) x[i] = curr.b[i];
        }
        return x;
    }

    public static void main(String[] args) {
        Slack_form init = new Slack_form(3, 3);
        for (int i = 0; i < 3; i++) {
            init.N.add(i);
            init.B.add(i + 3);
        }
        init.v = 0;
        init.b[3] = 30;
        init.b[4] = 24;
        init.b[5] = 36;
        init.c[0] = 3;
        init.c[1] = 1;
        init.c[2] = 2;
        init.A[3][0] = 1;
        init.A[3][1] = 1;
        init.A[3][2] = 3;
        init.A[4][0] = 2;
        init.A[4][1] = 2;
        init.A[4][2] = 5;
        init.A[5][0] = 4;
        init.A[5][1] = 1;
        init.A[5][2] = 2;

        double[] x = simplex(init);
        System.out.println(Arrays.toString(x));
    }
}
