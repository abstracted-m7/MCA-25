// Custom Exception
class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
}

// Base Service Class (Modular Design)
abstract class SmartService extends Thread {
    protected String user;

    public SmartService(String user) {
        this.user = user;
    }

    public abstract void execute() throws ServiceException;

    public void run() {
        try {
            execute();
        } catch (ServiceException e) {
            System.out.println(getName() + " Error: " + e.getMessage());
        }
    }
}

// Ride Service
class RideService extends SmartService {

    public RideService(String user) {
        super(user);
    }

    public void execute() throws ServiceException {
        if (user == null || user.isEmpty()) {
            throw new ServiceException("Invalid user for Ride Service");
        }
        System.out.println(user + " booked a ride");
    }
}

// Food Service
class FoodService extends SmartService {

    public FoodService(String user) {
        super(user);
    }

    public void execute() throws ServiceException {
        if (user.length() < 3) {
            throw new ServiceException("Username too short for Food Service");
        }
        System.out.println(user + " ordered food");
    }
}

// Payment Service
class PaymentService extends SmartService {

    private double amount;

    public PaymentService(String user, double amount) {
        super(user);
        this.amount = amount;
    }

    public void execute() throws ServiceException {
        if (amount <= 0) {
            throw new ServiceException("Invalid payment amount");
        }
        System.out.println(user + " made payment of rs: " + amount);
    }
}

// Main System
public class code {
    public static void main(String[] args) {

        // Create service threads
        Thread ride = new RideService("MGiri");
        Thread food = new FoodService("KB");
        Thread payment = new PaymentService("SBajani", 500);

        // Start all services (Multithreading)
        ride.start();
        food.start();
        payment.start();
    }
}