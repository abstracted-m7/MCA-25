// Custom Exception
class NoTicketAvailableException extends Exception {
    public NoTicketAvailableException(String message) {
        super(message);
    }
}

// Shared Resource: Ticket Counter
class TicketCounter {
    private int availableTickets;

    TicketCounter(int tickets) {
        this.availableTickets = tickets;
    }

    // synchronized method to avoid race condition
    public synchronized void bookTicket(String userName, int ticketsRequested)
            throws NoTicketAvailableException {

        System.out.println(userName + " is trying to book " + ticketsRequested + " ticket(s)...");

        if (ticketsRequested <= availableTickets) {
            System.out.println("Booking successful for " + userName);
            availableTickets -= ticketsRequested;
            System.out.println("Remaining tickets: " + availableTickets);
        } else {
            throw new NoTicketAvailableException(
                    "Not enough tickets for " + userName + "!");
        }
    }
}

// User Thread
class UserThread extends Thread {
    private TicketCounter counter;
    private String userName;
    private int ticketsRequested;

    UserThread(TicketCounter counter, String userName, int ticketsRequested) {
        this.counter = counter;
        this.userName = userName;
        this.ticketsRequested = ticketsRequested;
    }

    public void run() {
        try {
            counter.bookTicket(userName, ticketsRequested);
        } catch (NoTicketAvailableException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }
    }
}

// Main Class
public class OnlineTicketBooking {
    public static void main(String[] args) throws InterruptedException {

        TicketCounter counter = new TicketCounter(5); // only 5 tickets available

        // Multiple users (threads)
        UserThread u1 = new UserThread(counter, "User1", 2);
        UserThread u2 = new UserThread(counter, "User2", 3);
        UserThread u3 = new UserThread(counter, "User3", 1);
        UserThread u4 = new UserThread(counter, "User4", 2);
        u1.start();
        u1.join();
        u2.start();
        u2.join();  
        u3.start();
        u3.join();
        u4.start();
        u4.join();

    }
}