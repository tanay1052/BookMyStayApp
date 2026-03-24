import java.util.*;

/*
 * Use Case 8: Booking History & Reporting
 * Version 8.1
 */

class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println(
                "Reservation ID: " + reservationId +
                        " | Guest: " + guestName +
                        " | Room Type: " + roomType
        );
    }
}

class BookingHistory {

    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // store confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Booking stored in history: " + reservation.getReservationId());
    }

    public List<Reservation> getHistory() {
        return history;
    }
}

class BookingReportService {

    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    // display all bookings
    public void displayAllBookings() {

        System.out.println("\n=== Booking History ===");

        for (Reservation r : bookingHistory.getHistory()) {
            r.displayReservation();
        }
    }

    // simple summary report
    public void generateSummaryReport() {

        System.out.println("\n=== Booking Summary Report ===");

        Map<String, Integer> roomCount = new HashMap<>();

        for (Reservation r : bookingHistory.getHistory()) {
            roomCount.put(
                    r.getRoomType(),
                    roomCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        for (Map.Entry<String, Integer> entry : roomCount.entrySet()) {
            System.out.println(entry.getKey() + " bookings: " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Version 8.1");
        System.out.println("Booking History & Reporting");
        System.out.println("=================================");

        BookingHistory history = new BookingHistory();

        // confirmed bookings
        Reservation r1 = new Reservation("RES-101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES-102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES-103", "Charlie", "Single Room");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        BookingReportService reportService = new BookingReportService(history);

        reportService.displayAllBookings();

        reportService.generateSummaryReport();
    }
}