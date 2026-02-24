import java.util.Scanner;

public class PasswordStrengthCheck {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Password Strength Evaluator ---");
        System.out.print("Enter a password to analyze: ");
        String password = sc.nextLine();

        evaluateStrength(password);

        sc.close();
    }

    //check the string(pass)
    public static void evaluateStrength(String password) {
        int length = password.length();
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // String manipulation using Character API
        for (int i = 0; i < length; i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) hasUpper = true;
            else if (Character.isLowerCase(ch)) hasLower = true;
            else if (Character.isDigit(ch)) hasDigit = true;
            else if (!Character.isWhitespace(ch)) hasSpecial = true;
        }

        // Strength Calculation Logic
        int score = 0;
        if (length >= 8) score++;
        if (hasUpper && hasLower) score++;
        if (hasDigit) score++;
        if (hasSpecial) score++;

        displayResult(score, length, hasUpper, hasLower, hasDigit, hasSpecial);
    }

    private static void displayResult(int score, int len, boolean up, boolean low, boolean num, boolean spec) {
        System.out.println("\n--- Analysis Report ---");
        System.out.println("Your Score : "+score);
        System.out.println("Length: " + len + (len >= 8 ? " (Pass)" : " (Too short)"));
        System.out.println("Uppercase & Lowercase: " + (up && low ? "Yes" : "No"));
        System.out.println("Numeric Digits: " + (num ? "Yes" : "No"));
        System.out.println("Special Characters: " + (spec ? "Yes" : "No"));
        System.out.println("-----------------------");

        String category;
        switch (score) {
            case 4: category = "VERY STRONG"; break;
            case 3: category = "STRONG"; break;
            case 2: category = "MODERATE"; break;
            default: category = "WEAK"; break;
        }

        System.out.println("PASSWORD STRENGTH: " + category);
        
        if (score < 4) {
            System.out.println("Suggestion: Add capital, small, Spacial char, Number to make strong pass.");
        }
    }
}