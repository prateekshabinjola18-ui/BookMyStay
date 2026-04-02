import java.util.*;

public class UseCase9ErrorHandlingValidation {

    static Map<String, Integer> rooms = new HashMap<>();

    static {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Deluxe", 2);
    }

    // Display available rooms
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

                if (id.isEmpty()) {
                    throw new Exception("Reservation ID cannot be empty.");
                }

                System.out.print("Enter Guest Name: ");
                String name = scanner.nextLine();

                if (name.isEmpty()) {
                    throw new Exception("Guest name cannot be empty.");
                }

                System.out.print("Enter Room Type (Single/Double/Deluxe): ");
                String roomType = scanner.nextLine();

                if (!rooms.containsKey(roomType)) {
                    throw new Exception("Invalid room type entered.");
                }

                System.out.print("Enter Number of Nights: ");
                int nights = Integer.parseInt(scanner.nextLine());

                if (nights <= 0) {
                    throw new Exception("Nights must be greater than zero.");
                }

                if (rooms.get(roomType) > 0) {
                    rooms.put(roomType, rooms.get(roomType) - 1);

                    System.out.println("\nBooking Successful!");
                    System.out.println("Reservation ID: " + id);
                    System.out.println("Guest Name: " + name);
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Nights: " + nights);
                } else {
                    System.out.println("No rooms available in selected category.");
                }

                displayInventory();

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Nights must be a number.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nDo you want to continue? (yes/no): ");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("no")) {
                break;
            }
        }

        scanner.close();
    }
}