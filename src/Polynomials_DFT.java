import java.util.Arrays;

public class Polynomials_DFT {
    /**
     * Class to represent complex numbers
     */
    public static class Complex {
        double real;
        double img;

        public Complex(double r, double i) {
            real = r;
            img = i;
        }

        public void multiply(Complex b) {
            double o_real = real;
            real = o_real * b.real - img * b.img;
            img = o_real * b.img + img * b.real;
        }

        @Override
        public String toString() {
            return String.format("%.2f  +  i %.2f", real, img);
        }
    }

    /**
     * Evaluates the value of polynomial using Horner's method
     *
     * @param P the polynomial in coefficient form
     * @param x the value at which to evaluate the polynomial
     * @return the value of P at x
     */
    public static Complex evaluate(double[] P, Complex x) {
        Complex val = new Complex(P[P.length - 1], 0);
        for (int i = P.length - 2; i >= 0; i--) {
            val.multiply(x);
            val.real += P[i];
        }
        return val;
    }

    /**
     * Returns the addition of two polynomials
     *
     * @param P 1st polynomial
     * @param Q 2nd polynomial
     * @return P + Q
     */
    public static double[] add(double[] P, double[] Q) {
        if (P.length > Q.length) {
            double[] temp = P;
            P = Q;
            Q = temp;
        }
        double[] R = new double[Q.length];
        for (int i = 0; i < Q.length; i++) {
            if (i < P.length) R[i] = P[i] + Q[i];
            else R[i] = Q[i];
        }
        return R;
    }

    /**
     * Multiplies two Polynomials using standard method in O(n^2) time
     *
     * @param P 1st polynomial
     * @param Q 2nd polynomial
     * @return P * Q
     */
    public static double[] multiply(double[] P, double[] Q) {
        int n = P.length + Q.length - 1;
        double[] R = new double[n];
        for (int j = 0; j < n; j++) {
            R[j] = 0;
            for (int k = 0; k <= j; k++)
                if (k < P.length && k < Q.length && (j - k) < P.length && (j - k) < Q.length)
                    R[j] = R[j] + P[k] * Q[j - k];
        }
        return R;
    }

    public static Complex[] DFT(double[] P) {
        int n = P.length;
        Complex[] res = new Complex[P.length];
        double theta = ((2 * Math.PI) / n);
        for (int k = 0; k < n; k++)
            res[k] = evaluate(P, new Complex(Math.sin(k*theta), Math.cos(k*theta)));
        return res;
    }

    public static void main(String[] args) {
        double[] P = {9, -10, 7, 6};
        double[] Q = {-5, 4, 0, -2};
        System.out.println("P = " + Arrays.toString(P));
        System.out.println("P = " + Arrays.toString(Q));
        System.out.println("Addition = " + Arrays.toString(add(P, Q)));
        System.out.println("Multiplication = " + Arrays.toString(multiply(P, Q)));
        System.out.println("Value of P at 1 + i: " + evaluate(P, new Complex(1, 1)));
        System.out.println("DFT of P = " + Arrays.toString(DFT(P)));
    }
}
