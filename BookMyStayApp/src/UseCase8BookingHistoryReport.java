import java.util.*;

// Reservation Class
class Reservation {
    private final String reservationId;
    private final String guestName;
    private final String roomType;
    private final double price;

    public Reservation(String reservationId, String guestName, String roomType, double price) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Price: ₹" + price;
    }
}

// Booking History (Storage Layer)
class BookingHistory {

    private final List<Reservation> reservations;

    public BookingHistory() {
        reservations = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added to history: " + reservation.getReservationId());
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations); // return copy (safe)
    }
}

// Reporting Service (Read-only operations)
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<Reservation> reservations) {
        System.out.println("\n--- Booking Summary Report ---");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        Map<String, Integer> roomTypeCount = new HashMap<>();

        for (Reservation r : reservations) {
            totalRevenue += r.getPrice();

            roomTypeCount.put(
                    r.getRoomType(),
                    roomTypeCount.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);

        System.out.println("\nRoom Type Distribution:");
        for (Map.Entry<String, Integer> entry : roomTypeCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        int choice;

        do {
            System.out.println("\n===== BOOK MY STAY - ADMIN PANEL =====");
            System.out.println("1. Add Confirmed Booking");
            System.out.println("2. View Booking History");
            System.out.println("3. Generate Report");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Deluxe): ");
                    String roomType = scanner.nextLine();

                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();

                    Reservation reservation = new Reservation(id, name, roomType, price);
                    history.addReservation(reservation);
                    break;

                case 2:
                    reportService.displayAllBookings(history.getAllReservations());
                    break;

                case 3:
                    reportService.generateSummaryReport(history.getAllReservations());
                    break;

                case 4:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 4);

        scanner.close();
    }
}
