import java.util.*;

/*
 * Use Case 10: Booking Cancellation & Inventory Rollback
 * Version 10.1
 */

class Reservation {

    String reservationId;
    String roomType;
    String roomId;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class CancellationService {

    private Map<String, Reservation> confirmedBookings;
    private Stack<String> rollbackStack;
    private RoomInventory inventory;

    public CancellationService(RoomInventory inventory) {

        confirmedBookings = new HashMap<>();
        rollbackStack = new Stack<>();
        this.inventory = inventory;
    }

    public void addConfirmedReservation(Reservation reservation) {
        confirmedBookings.put(reservation.reservationId, reservation);
    }

    public void cancelBooking(String reservationId) {

        System.out.println("\nCancellation Request for: " + reservationId);

        if (!confirmedBookings.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }

        Reservation reservation = confirmedBookings.remove(reservationId);

        rollbackStack.push(reservation.roomId);

        inventory.incrementRoom(reservation.roomType);

        System.out.println("Cancellation successful.");
        System.out.println("Released Room ID: " + reservation.roomId);
    }

    public void displayRollbackHistory() {

        System.out.println("\nRollback Stack (Recently Released Rooms):");

        for (String roomId : rollbackStack) {
            System.out.println(roomId);
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 10.1");
        System.out.println("Booking Cancellation & Rollback");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        CancellationService service = new CancellationService(inventory);

        Reservation r1 = new Reservation("RES-201", "Single Room", "SR-1");
        Reservation r2 = new Reservation("RES-202", "Double Room", "DR-1");

        service.addConfirmedReservation(r1);
        service.addConfirmedReservation(r2);

        service.cancelBooking("RES-201");

        service.cancelBooking("RES-999");

        service.displayRollbackHistory();

        inventory.displayInventory();
    }
}