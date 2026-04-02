import java.util.*;

public class UseCase10BookingCancellation {

    static Map<String, Integer> rooms = new HashMap<>();
    static Map<String, String> bookings = new HashMap<>();
    static Stack<String> rollbackStack = new Stack<>();

    static {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Deluxe", 2);
    }

    // Display inventory
    public static void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Add booking
    public static void addBooking(String reservationId, String roomType) {
        if (rooms.containsKey(roomType) && rooms.get(roomType) > 0) {
            bookings.put(reservationId, roomType);
            rooms.put(roomType, rooms.get(roomType) - 1);

            String roomId = roomType + "_" + reservationId;
            rollbackStack.push(roomId);

            System.out.println("Booking confirmed.");
            System.out.println("Allocated Room ID: " + roomId);
        } else {
            System.out.println("Room not available.");
        }
    }

    // Cancel booking
    public static void cancelBooking(String reservationId) {
        if (!bookings.containsKey(reservationId)) {
            System.out.println("Cancellation failed: Reservation does not exist.");
            return;
        }

        String roomType = bookings.get(reservationId);

        // rollback room ID
        String releasedRoom = rollbackStack.pop();

        // restore inventory
        rooms.put(roomType, rooms.get(roomType) + 1);

        // remove booking
        bookings.remove(reservationId);

        System.out.println("Booking cancelled successfully.");
        System.out.println("Released Room ID: " + releasedRoom);
        System.out.println("Inventory restored.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== BOOK MY STAY =====");
            System.out.println("1. Add Booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Inventory");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter Reservation ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Enter Room Type (Single/Double/Deluxe): ");
                    String roomType = scanner.nextLine();

                    addBooking(id, roomType);
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to cancel: ");
                    String cancelId = scanner.nextLine();

                    cancelBooking(cancelId);
                    break;

                case 3:
                    displayInventory();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}