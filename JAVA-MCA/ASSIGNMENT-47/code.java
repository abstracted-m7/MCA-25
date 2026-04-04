// Custom Exception
class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}

// Numeric Validation Thread
class NumericValidator extends Thread {
    private String input;

    public NumericValidator(String input) {
        this.input = input;
    }

    public void run() {
        try {
            validate();
            System.out.println("Numeric Validation Passed: " + input);
        } catch (ValidationException e) {
            System.out.println("Numeric Validation Failed: " + e.getMessage());
        }
    }

    private void validate() throws ValidationException {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ValidationException("Not a valid number!");
        }
    }
}

// String Validation Thread
class StringValidator extends Thread {
    private String input;

    public StringValidator(String input) {
        this.input = input;
    }

    public void run() {
        try {
            validate();
            System.out.println("String Validation Passed: " + input);
        } catch (ValidationException e) {
            System.out.println("String Validation Failed: " + e.getMessage());
        }
    }

    private void validate() throws ValidationException {
        if (!input.matches("[a-zA-Z]+")) {
            throw new ValidationException("String contains invalid characters!");
        }
    }
}

// Range Validation Thread
class RangeValidator extends Thread {
    private int number;

    public RangeValidator(int number) {
        this.number = number;
    }

    public void run() {
        try {
            validate();
            System.out.println("Range Validation Passed: " + number);
        } catch (ValidationException e) {
            System.out.println("Range Validation Failed: " + e.getMessage());
        }
    }

    private void validate() throws ValidationException {
        if (number < 1 || number > 100) {
            throw new ValidationException("Number not in range (1-100)!");
        }
    }
}

// Main Class
public class code {
    public static void main(String[] args) {

        // Sample inputs
        String numInput = "123a";
        String strInput = "Hello123";
        int rangeInput = 150;

        // Create threads
        Thread t1 = new NumericValidator(numInput);
        Thread t2 = new StringValidator(strInput);
        Thread t3 = new RangeValidator(rangeInput);

        // Start threads
        t1.start();
        t2.start();
        t3.start();
    }
}