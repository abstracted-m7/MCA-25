// Custom Exception for Missing Data
class MissingDataException extends Exception {
    public MissingDataException(String message) {
        super(message);
    }
}

// Shared resource class
class Data {
    String content;
    boolean isAvailable = false;
}

// Thread 1: Read Data
class ReaderThread extends Thread {
    Data data;

    ReaderThread(Data data) {
        this.data = data;
    }

    public void run() {
        try {
            System.out.println("Reading data...");
            Thread.sleep(1000);

            // Simulate missing data (change to false to test normal flow)
            boolean isMissing = false;

            if (isMissing) {
                throw new MissingDataException("No data found in file!");
            }

            data.content = "Sample file data";
            data.isAvailable = true;

            System.out.println("Data read successfully.");
        } catch (MissingDataException e) {
            System.out.println("Reader Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Thread 2: Process Data
class ProcessorThread extends Thread {
    Data data;

    ProcessorThread(Data data) {
        this.data = data;
    }

    public void run() {
        try {
            Thread.sleep(1500);

            if (!data.isAvailable) {
                throw new MissingDataException("Cannot process: Data not available!");
            }

            System.out.println("Processing data...");
            data.content = data.content.toUpperCase();

            System.out.println("Data processed.");
        } catch (MissingDataException e) {
            System.out.println("Processor Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Thread 3: Display Data
class DisplayThread extends Thread {
    Data data;

    DisplayThread(Data data) {
        this.data = data;
    }

    public void run() {
        try {
            Thread.sleep(2000);

            if (!data.isAvailable) {
                throw new MissingDataException("Nothing to display!");
            }

            System.out.println("Displaying data:");
            System.out.println(data.content);

        } catch (MissingDataException e) {
            System.out.println("Display Error: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Main Class
public class FileProcessingSimulation {
    public static void main(String[] args) {

        Data sharedData = new Data();

        ReaderThread t1 = new ReaderThread(sharedData);
        ProcessorThread t2 = new ProcessorThread(sharedData);
        DisplayThread t3 = new DisplayThread(sharedData);

        t1.start();
        t2.start();
        t3.start();
    }
}