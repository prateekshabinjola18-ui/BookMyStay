import java.util.*;

public class UseCase9ErrorHandlingValidation {

    // Custom Exception inside same class
    static class InvalidBookingException extends Exception {
        public InvalidBookingException(String message) {
            super(message);
        }
    }

    // Inventory Map
    static Map<String, Integer> rooms = new HashMap<>();

    // Initialize inventory
    static {
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Deluxe", 1);
    }

    // Validation Method (Fail-Fast)
    public static void validateBooking(String id, String name, String roomType)
            throws InvalidBookingException {

        if (id == null || id.trim().isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty!");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty!");
        }

        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type!");
        }

        if (rooms.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }
    }

    // Book Room (Safe update)
    public static void bookRoom(String roomType) {
        rooms.put(roomType, rooms.get(roomType) - 1);
    }

    // Display Inventory
    public static void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : rooms.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n===== BOOK MY STAY =====");

                System.out.print("Enter Reservation ID: ");
                String id = scanner.nextLine();

                System.out.print("Enter Guest Name: ");
                String name = scanner.nextLine();

                System.out.print("Enter Room Type (Single/Double/Deluxe): ");
                String roomType = scanner.nextLine();

                // VALIDATION
                validateBooking(id, name, roomType);

                // BOOKING
                bookRoom(roomType);

                System.out.println("\n✅ Booking Successful!");
                System.out.println("Reservation ID: " + id +
                        ", Guest: " + name +
                        ", Room: " + roomType);

                displayInventory();

            } catch (InvalidBookingException e) {
                System.out.println("\n❌ Booking Failed: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n⚠ Unexpected Error: " + e.getMessage());
            }

            System.out.print("\nContinue? (yes/no): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("no")) {
                break;
            }
        }

        scanner.close();
        System.out.println("Application Closed.");
    }
}