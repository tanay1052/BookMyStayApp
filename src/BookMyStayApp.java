import java.util.*;

/*
 * Use Case 6: Reservation Confirmation & Room Allocation
 * Version 6.1
 */

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingService {

    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    // Map room type → allocated room IDs
    private HashMap<String, Set<String>> allocatedRooms;

    // Global set to ensure uniqueness
    private Set<String> allRoomIds;

    public BookingService(Queue<Reservation> bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;

        allocatedRooms = new HashMap<>();
        allRoomIds = new HashSet<>();
    }

    private String generateRoomId(String roomType, int number) {
        return roomType.replace(" ", "") + "-" + number;
    }

    public void processBookings() {

        while (!bookingQueue.isEmpty()) {

            Reservation request = bookingQueue.poll();

            System.out.println("\nProcessing request for: " + request.guestName);

            int available = inventory.getAvailability(request.roomType);

            if (available > 0) {

                int roomNumber = allRoomIds.size() + 1;
                String roomId = generateRoomId(request.roomType, roomNumber);

                if (!allRoomIds.contains(roomId)) {

                    allRoomIds.add(roomId);

                    allocatedRooms
                            .computeIfAbsent(request.roomType, k -> new HashSet<>())
                            .add(roomId);

                    inventory.decrementRoom(request.roomType);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest: " + request.guestName);
                    System.out.println("Room Type: " + request.roomType);
                    System.out.println("Assigned Room ID: " + roomId);

                }

            } else {

                System.out.println("No rooms available for " + request.roomType);
            }
        }
    }

    public void displayAllocatedRooms() {

        System.out.println("\nAllocated Rooms:");

        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {

            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 6.1");
        System.out.println("Reservation Confirmation & Allocation");
        System.out.println("=================================");

        Queue<Reservation> queue = new LinkedList<>();

        queue.add(new Reservation("Alice", "Single Room"));
        queue.add(new Reservation("Bob", "Double Room"));
        queue.add(new Reservation("Charlie", "Single Room"));
        queue.add(new Reservation("David", "Suite Room"));

        RoomInventory inventory = new RoomInventory();

        BookingService service = new BookingService(queue, inventory);

        service.processBookings();

        service.displayAllocatedRooms();

        inventory.displayInventory();
    }
}