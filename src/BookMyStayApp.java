import java.util.LinkedList;
import java.util.Queue;

/*
 * Use Case 5: Booking Request (First-Come-First-Served)
 * Version 5.1
 */

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request to queue
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Display all requests
    public void displayRequests() {

        System.out.println("\nCurrent Booking Requests (FIFO Order):\n");

        for (Reservation reservation : requestQueue) {
            reservation.displayReservation();
        }
    }

    // Peek next request (without removing)
    public void viewNextRequest() {

        Reservation next = requestQueue.peek();

        if (next != null) {
            System.out.println("\nNext Request to Process:");
            next.displayReservation();
        } else {
            System.out.println("No booking requests available.");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 5.1");
        System.out.println("Booking Request Queue (FIFO)");
        System.out.println("=================================");

        BookingRequestQueue queue = new BookingRequestQueue();

        // Guests submit booking requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));

        // Display queued requests
        queue.displayRequests();

        // View next request
        queue.viewNextRequest();
    }
}