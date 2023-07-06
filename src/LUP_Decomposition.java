import java.util.Arrays;

public class LUP_Decomposition {
    private static double[] LUP_solve(double[][] L, double[][] U, int[] p, double[] b) {
        int n = L.length;
        double[] x = new double[n];
        double[] y = new double[n];
        double sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            for (int j = 0; j < i; j++)
                sum += L[i][j] * y[j];
            y[i] = b[p[i]] - sum;
        }
        for (int i = n - 1; i >= 0; i--) {
            sum = 0;
            for (int j = i; j < n; j++)
                sum += U[i][j] * x[j];
            x[i] = (y[i] - sum) / U[i][i];
        }
        return x;
    }

    private static void LU_decompose(double[][] A, double[][] L, double[][] U) {
        int n = A.length;
        for (int i = 0; i < n; i++) L[i][i] = 1;
        for (int k = 0; k < n; k++) {
            U[k][k] = A[k][k];
            for (int i = k + 1; i < n; i++) {
                L[i][k] = A[i][k] / U[k][k];
                U[k][i] = A[k][i];
            }
            for (int i = k + 1; i < n; i++) {
                for (int j = k + 1; j < n; j++)
                    A[i][j] = A[i][j] - L[i][k] * U[k][j];
            }
        }
    }

    private static int[] LUP_decompose(double[][] A, double[][] L, double[][] U) throws Exception {
        int n = A.length;
        int[] P = new int[n];
        for (int i = 0; i < n; i++) {
            P[i] = i;
            L[i][i] = 1;
        }
        for (int k = 0; k < n; k++) {
            double p = 0;
            int m = k;
            for (int i = k; i < n; i++) {
                if (Math.abs(A[i][k]) > p) {
                    p = Math.abs(A[i][k]);
                    m = i;
                }
            }
            if (p == 0) throw new Exception("Not a singular matrix");
            int temp = P[k];
            P[k] = P[m];
            P[m] = temp;
            for (int i = 0; i < n; i++) {
                double t = A[k][i];
                A[k][i] = A[m][i];
                A[m][i] = t;
            }
            for (int i = k + 1; i < n; i++) {
                A[i][k] = A[i][k] / A[k][k];
                for (int j = k + 1; j < n; j++)
                    A[i][j] = A[i][j] - A[i][k] * A[k][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i > j) L[i][j] = A[i][j];
                else U[i][j] = A[i][j];
            }
        }
        return P;
    }

    public static void main(String[] args) {
        double[][] L = new double[3][3];
        double[][] U = new double[3][3];
        double[][] A = new double[3][3];
        double[] B = new double[3];
        A[0][0] = 1;
        A[0][1] = 5;
        A[0][2] = 4;
        A[1][0] = 2;
        A[1][1] = 0;
        A[1][2] = 3;
        A[2][0] = 5;
        A[2][1] = 8;
        A[2][2] = 2;
        B[0] = 12;
        B[1] = 9;
        B[2] = 5;
        try {
            int[] P = LUP_decompose(A, L, U);
            double[] x = LUP_solve(L,U,P,B);
            System.out.println(Arrays.toString(x));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
