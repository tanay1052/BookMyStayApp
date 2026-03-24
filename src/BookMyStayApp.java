import java.io.*;
import java.util.*;

/*
 * Use Case 12: Data Persistence & System Recovery
 */

class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<String> bookingHistory;

    public SystemState() {

        inventory = new HashMap<>();
        bookingHistory = new ArrayList<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    public void save(SystemState state) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(state);

            System.out.println("System state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state: " + e.getMessage());
        }
    }

    public SystemState load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state restored from file.");

            return (SystemState) in.readObject();

        } catch (FileNotFoundException e) {

            System.out.println("No saved state found. Starting fresh.");
            return new SystemState();

        } catch (Exception e) {

            System.out.println("Error loading state. Starting safe default.");
            return new SystemState();
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 12.0");
        System.out.println("Data Persistence & System Recovery");
        System.out.println("=================================");

        PersistenceService persistence = new PersistenceService();

        // Load previous system state
        SystemState state = persistence.load();

        // Display current state
        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : state.inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("\nBooking History:");

        for (String booking : state.bookingHistory) {
            System.out.println(booking);
        }

        // Simulate new booking
        state.bookingHistory.add("RES-301 - Alice - Single Room");

        System.out.println("\nNew booking added.");

        // Save system state before shutdown
        persistence.save(state);
    }
}