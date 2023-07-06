import java.util.Arrays;

public class Euclid_Algorithm {
    public static int Euclid(int a, int b) {
        if (b == 0) return a;
        else return Euclid(b, a % b);
    }

    public static int[] Extended_Euclid(int a, int b) {
        int[] ans;
        if (b == 0) {
            ans = new int[3];
            ans[0] = a;
            ans[1] = 1;
        } else {
            ans = Extended_Euclid(b, a % b);
            int x = ans[1];
            ans[1] = ans[2];
            ans[2] = x - (a / b) * ans[2];
        }
        return ans;
    }

    public static void Modular_Linear_Equation_Solver(int a, int b, int n) {
        int[] arr = Extended_Euclid(a, n);
        if (b % arr[0] == 0) {
            int x = (arr[1] * (b / arr[0]) + n) % n;
            for (int i = 0; i < arr[0]; i++)
                System.out.println((x + i * (n / arr[0]) + n) % n);
        }
        else System.out.println("No solution");
    }

    public static void main(String[] args) {
        System.out.println(Euclid(25, 10));
        System.out.println(Arrays.toString(Extended_Euclid(25, 10)));
        Modular_Linear_Equation_Solver(14, 30, 100);
    }
}
