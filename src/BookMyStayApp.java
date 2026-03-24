import java.util.HashMap;
import java.util.Map;

/*
 * Use Case 9: Error Handling & Validation
 * Version 9.1
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory with validation checks
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for " + roomType
            );
        }
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        validateRoomType(roomType);
        validateAvailability(roomType);

        int current = inventory.get(roomType);

        if (current - 1 < 0) {
            throw new InvalidBookingException(
                    "Inventory cannot become negative"
            );
        }

        inventory.put(roomType, current - 1);

        System.out.println("Booking successful for: " + roomType);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 9.1");
        System.out.println("Error Handling & Validation");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        try {

            inventory.bookRoom("Single Room"); // valid
            inventory.bookRoom("Suite Room");  // invalid availability
            inventory.bookRoom("Deluxe Room"); // invalid type

        } catch (InvalidBookingException e) {

            System.out.println("Booking failed: " + e.getMessage());

        }

        inventory.displayInventory();
    }
}