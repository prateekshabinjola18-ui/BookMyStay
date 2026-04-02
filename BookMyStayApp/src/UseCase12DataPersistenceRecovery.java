import java.io.*;
import java.util.*;

public class UseCase12DataPersistenceRecovery {

    static Map<String, Integer> inventory = new HashMap<>();
    static Map<String, String> bookings = new HashMap<>();

    static final String FILE_NAME = "hotelData.ser";

    static {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Deluxe", 2);
    }

    // Save system state
    public static void saveData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            out.writeObject(inventory);
            out.writeObject(bookings);

            out.close();

            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error while saving data.");
        }
    }

    // Load system state
    public static void loadData() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));

            inventory = (Map<String, Integer>) in.readObject();
            bookings = (Map<String, String>) in.readObject();

            in.close();

            System.out.println("System state restored successfully.");

        } catch (FileNotFoundException e) {
            System.out.println("No previous saved data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error while loading saved data.");
        }
    }

    // Display inventory
    public static void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    // Add booking
    public static void addBooking(String reservationId, String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            bookings.put(reservationId, roomType);
            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking successful.");
        } else {
            System.out.println("Room not available.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Restore previous saved state
        loadData();

        while (true) {
            System.out.println("\n===== BOOK MY STAY =====");
            System.out.println("1. Add Booking");
            System.out.println("2. View Inventory");
            System.out.println("3. Save and Exit");
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
                    displayInventory();
                    break;

                case 3:
                    saveData();
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}