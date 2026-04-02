// Custom Exception: Buffer Full
class BufferFullException extends Exception {
    public BufferFullException(String message) {
        super(message);
    }
}

// Custom Exception: Buffer Empty
class BufferEmptyException extends Exception {
    public BufferEmptyException(String message) {
        super(message);
    }
}

// Shared Resource: Buffer
class Buffer {
    private int data;
    private boolean isAvailable = false;

    // Produce data
    public synchronized void produce(int value)
            throws BufferFullException {

        if (isAvailable) {
            throw new BufferFullException("Buffer is full! Cannot produce.");
        }

        data = value;
        isAvailable = true;

        System.out.println("Produced: " + data);
    }

    // Consume data
    public synchronized void consume()
            throws BufferEmptyException {

        if (!isAvailable) {
            throw new BufferEmptyException("Buffer is empty! Cannot consume.");
        }

        System.out.println("Consumed: " + data);
        isAvailable = false;
    }
}

// Producer Thread
class Producer extends Thread {
    private Buffer buffer;
    private int count;

    Producer(Buffer buffer, int count) {
        this.buffer = buffer;
        this.count = count;
    }

    public void run() {
        for (int i = 1; i <= count; i++) {
            try {
                buffer.produce(i);
                Thread.sleep(500);
            } catch (BufferFullException e) {
                System.out.println("Producer Error: " + e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Consumer Thread
class Consumer extends Thread {
    private Buffer buffer;
    private int count;

    Consumer(Buffer buffer, int count) {
        this.buffer = buffer;
        this.count = count;
    }

    public void run() {
        for (int i = 1; i <= count; i++) {
            try {
                buffer.consume();
                Thread.sleep(800);
            } catch (BufferEmptyException e) {
                System.out.println("Consumer Error: " + e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main Class
public class ProducerConsumerDemo {
    public static void main(String[] args) {

        Buffer buffer = new Buffer();

        Producer producer = new Producer(buffer, 5); // produce 5 items
        Consumer consumer = new Consumer(buffer, 5); // consume 5 items

        producer.start();
        consumer.start();
    }
}