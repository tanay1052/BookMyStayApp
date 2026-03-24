import java.util.HashMap;
import java.util.Map;

/*
 * Use Case 4: Room Search & Availability Check
 * Version 4.1
 */

abstract class Room {

    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Price     : ₹" + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return inventory;
    }
}

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int availability = inventory.getAvailability(room.getRoomType());

            if (availability > 0) {

                room.displayDetails();
                System.out.println("Available Rooms : " + availability);
                System.out.println("-------------------------------");
            }
        }
    }
}

public class BookMyStayApp{

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 4.1");
        System.out.println("Room Search & Availability Check");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms(rooms);
    }
}