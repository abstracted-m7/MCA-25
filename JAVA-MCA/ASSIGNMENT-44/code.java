
// Custom Exception
class InvalidLoginException extends Exception {
    public InvalidLoginException(String message) {
        super(message);
    }
}

class Login{
    String validUsername = "MG@123"; //prev declared username, password
    String validPassword = "1234";

    // Synchronized struct
    public synchronized void login(String username, String password) throws InvalidLoginException {
        System.out.println(Thread.currentThread().getName() + " trying to login...");

        if (username.equals(validUsername) && password.equals(validPassword)) {
            System.out.println(Thread.currentThread().getName() + " login Successfully..!!");
        } else {
            throw new InvalidLoginException(Thread.currentThread().getName() + " login Failed..!!");
        }
    }

}

// Thread Class
class LoginThread extends Thread {
    private Login login;
    private String username;
    private String password;

    public LoginThread(Login system, String username, String password) {
        this.login = system;
        this.username = username;
        this.password = password;
    }

    public void run() {
        try {
            login.login(username, password);
        } catch (InvalidLoginException e) {
            System.out.println(e.getMessage());
        }
    }
}



public class code {
    public static void main(String[] args) {

        Login login = new Login();

        // Creating multiple login threads
        LoginThread t1 = new LoginThread(login, "MG@123", "1234"); // correct
        LoginThread t2 = new LoginThread(login, "user", "1234");  // wrong
        LoginThread t3 = new LoginThread(login, "admin", "0000"); // wrong
        LoginThread t4 = new LoginThread(login, "admin", "1234"); // correct

        // Start threads
        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");
        t4.setName("Thread-4");

        t1.start();
        t2.start();
        t3.start();
        t4.start(); 
    }
}
