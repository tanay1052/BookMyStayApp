import java.util.LinkedList;
import java.util.Queue;

/*
 * Use Case 11: Concurrent Booking Simulation
 * Demonstrates thread-safe booking allocation
 */

class BookingRequest {

    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService {

    private int singleRooms = 2;

    // Critical section
    public synchronized boolean allocateRoom(BookingRequest request) {

        if (singleRooms > 0) {

            System.out.println(
                    Thread.currentThread().getName() +
                            " allocating room to " + request.guestName
            );

            singleRooms--;

            System.out.println(
                    "Remaining Single Rooms: " + singleRooms
            );

            return true;

        } else {

            System.out.println(
                    "No rooms available for " + request.guestName
            );

            return false;
        }
    }
}

class BookingProcessor implements Runnable {

    private Queue<BookingRequest> queue;
    private InventoryService inventory;

    public BookingProcessor(Queue<BookingRequest> queue,
                            InventoryService inventory) {

        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            BookingRequest request;

            synchronized (queue) {

                if (queue.isEmpty()) {
                    return;
                }

                request = queue.poll();
            }

            if (request != null) {

                inventory.allocateRoom(request);
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 11.0");
        System.out.println("Concurrent Booking Simulation");
        System.out.println("=================================");

        Queue<BookingRequest> queue = new LinkedList<>();

        queue.add(new BookingRequest("Alice", "Single Room"));
        queue.add(new BookingRequest("Bob", "Single Room"));
        queue.add(new BookingRequest("Charlie", "Single Room"));
        queue.add(new BookingRequest("David", "Single Room"));

        InventoryService inventory = new InventoryService();

        Thread t1 = new Thread(
                new BookingProcessor(queue, inventory),
                "Thread-1"
        );

        Thread t2 = new Thread(
                new BookingProcessor(queue, inventory),
                "Thread-2"
        );

        Thread t3 = new Thread(
                new BookingProcessor(queue, inventory),
                "Thread-3"
        );

        t1.start();
        t2.start();
        t3.start();
    }
}