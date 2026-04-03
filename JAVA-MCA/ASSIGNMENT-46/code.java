// Inventory class
class Inventory {
    private int stock;

    public Inventory(int stock) {
        this.stock = stock;
    }

    // Method to add stock
    public synchronized void addStock(int quantity) {
        stock += quantity;
        System.out.println(Thread.currentThread().getName() +
                " added " + quantity + " items. Stock: " + stock);
    }

    // Method to remove stock
    public synchronized void removeStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            System.out.println(Thread.currentThread().getName() +
                    " removed " + quantity + " items. Stock: " + stock);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " tried to remove " + quantity +
                    " items, but not enough stock! Current: " + stock);
        }
    }
}

// Thread class for adding stock
class AddStockThread extends Thread {
    private Inventory inventory;

    public AddStockThread(Inventory inventory) {
        this.inventory = inventory;
    }

    public void run() {
        inventory.addStock(10);
    }
}

// Thread class for removing stock
class RemoveStockThread extends Thread {
    private Inventory inventory;

    public RemoveStockThread(Inventory inventory) {
        this.inventory = inventory;
    }

    public void run() {
        inventory.removeStock(5);
    }
}

// Main class
public class code {
    public static void main(String[] args) {
        Inventory inventory = new Inventory(20);

        // Create threads
        Thread t1 = new AddStockThread(inventory);
        Thread t2 = new RemoveStockThread(inventory);
        Thread t3 = new RemoveStockThread(inventory);
        Thread t4 = new AddStockThread(inventory);

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}