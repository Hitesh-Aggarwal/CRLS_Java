public class String_Matching {
    public static void naive_string_matcher(String T, String P) {
        int n = T.length();
        int m = P.length();
        for (int s = 0; s <= n - m; s++) {
            if (T.regionMatches(s, P, 0, m)) System.out.println("Pattern occurs with shift " + s);
        }
    }

    public static void rabin_karp_matcher(String T, String P) {
        int n = T.length();
        int m = P.length();
        final int d = 26;
        final int q = 13;
        final long h = (int) Math.pow(d, m - 1) % q;
        long t = 0;
        long p = 0;
        for (int i = 0; i < m; i++) {
            p = (d * p + P.charAt(i)) % q;
            t = (d * t + T.charAt(i)) % q;
        }
        for (int s = 0; s <= n - m; s++) {
            if (p == t) if (T.regionMatches(s, P, 0, m)) System.out.println("Pattern occurs with shift " + s);
            if (s < n - m) t = (d * (t - T.charAt(s) * h) + T.charAt(s + m)) % q;
        }
    }

    private static boolean check_suffix(String P, int k, int q, char a) {
        if (k == 0) return true;
        if (P.charAt(k - 1) != a) return false;
        for (int i = k - 2; i >= 0; i--)
            if (P.charAt(i) != P.charAt(i + q - k + 1)) return false;
        return true;
    }

    private static int[][] compute_transition_function(String P) {
        int m = P.length();
        int[][] delta = new int[m + 1][26];
        for (int q = 0; q <= m; q++) {
            for (int i = 0; i < 26; i++) {
                int k = Math.min(m + 1, q + 2);
                do {
                    k = k - 1;
                } while (!check_suffix(P, k, q, (char) (i + 97)));
                delta[q][i] = k;
            }
        }
        return delta;
    }

    public static void finite_automaton_matcher(String T, String P) {
        int n = T.length();
        int m = P.length();
        int[][] delta = compute_transition_function(P);
        int q = 0;

        for (int i = 0; i < n; i++) {
            q = delta[q][(int) T.charAt(i) - 97];
            if (q == m) System.out.println("Pattern occurs with shift " + (i - m + 1));
        }
    }

    private static int[] compute_prefix_function(String P) {
        int m = P.length();
        int[] pi = new int[m + 1];
        int k = 0;
        for (int q = 1; q < m; q++) {
            while (k > 0 && P.charAt(k) != P.charAt(q)) k = pi[k];
            if (P.charAt(k) == P.charAt(q)) k++;
            pi[q] = k;
        }
        return pi;
    }

    public static void kmp_matcher(String T, String P) {
        int n = T.length();
        int m = P.length();
        int[] pi = compute_prefix_function(P);
        int q = 0;
        for (int i = 0; i < n; i++) {
            while (q > 0 && P.charAt(q) != T.charAt(i)) q = pi[q - 1];
            if (P.charAt(q) == T.charAt(i)) q = q + 1;
            if (q == m) {
                System.out.println("Pattern occurs with shift " + (i - m + 1));
                q = pi[q - 1];
            }
        }
    }

    public static void main(String[] args) {
        String T = "abababacaba";
        String P = "ababaca";
        naive_string_matcher(T, P);
        rabin_karp_matcher(T, P);
        finite_automaton_matcher(T, P);
        kmp_matcher(T, P);
    }
}
