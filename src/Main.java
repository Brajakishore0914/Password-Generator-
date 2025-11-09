import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:',.<>?/`~";
    private static final SecureRandom rnd = new SecureRandom();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Secure Password Generator ===");
        System.out.print("Enter password length: ");
        int length = sc.nextInt();

        System.out.print("Include uppercase letters? (y/n): ");
        boolean includeUpper = sc.next().equalsIgnoreCase("y");

        System.out.print("Include lowercase letters? (y/n): ");
        boolean includeLower = sc.next().equalsIgnoreCase("y");

        System.out.print("Include digits? (y/n): ");
        boolean includeDigits = sc.next().equalsIgnoreCase("y");

        System.out.print("Include symbols? (y/n): ");
        boolean includeSymbols = sc.next().equalsIgnoreCase("y");

        if (!includeUpper && !includeLower && !includeDigits && !includeSymbols) {
            System.out.println("Error: You must select at least one character type.");
            return;
        }

        String password = generatePassword(length, includeUpper, includeLower, includeDigits, includeSymbols);
        System.out.println("\nGenerated Password: " + password);
        sc.close();
    }

    public static String generatePassword(int length, boolean upper, boolean lower, boolean digits, boolean symbols) {
        List<Character> passwordChars = new ArrayList<>(length);
        StringBuilder pool = new StringBuilder();

        if (upper) pool.append(UPPER);
        if (lower) pool.append(LOWER);
        if (digits) pool.append(DIGITS);
        if (symbols) pool.append(SYMBOLS);

        if (upper) passwordChars.add(randomChar(UPPER));
        if (lower) passwordChars.add(randomChar(LOWER));
        if (digits) passwordChars.add(randomChar(DIGITS));
        if (symbols) passwordChars.add(randomChar(SYMBOLS));

        for (int i = passwordChars.size(); i < length; i++) {
            passwordChars.add(randomChar(pool.toString()));
        }

        Collections.shuffle(passwordChars, rnd);

        StringBuilder result = new StringBuilder();
        for (char c : passwordChars) result.append(c);
        return result.toString();
    }

    private static char randomChar(String s) {
        return s.charAt(rnd.nextInt(s.length()));
    }
}
