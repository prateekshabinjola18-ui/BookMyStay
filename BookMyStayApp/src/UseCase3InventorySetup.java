import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App - Use Case 3
 * Centralized Room Inventory Management
 * Version: 3.1
 *
 * This program demonstrates centralized room inventory management
 * using a HashMap to track room availability.
 *
 * @author You
 */
public class UseCase3InventorySetup {

    // Abstract room class with unique name for UC3
    public static abstract class RoomUC3 {
        protected String name;
        protected int beds;
        protected double pricePerNight;

        public RoomUC3(String name, int beds, double pricePerNight) {
            this.name = name;
            this.beds = beds;
            this.pricePerNight = pricePerNight;
        }

        public String getRoomDetails() {
            return name + " | Beds: " + beds + " | Price: $" + pricePerNight;
        }
    }

    // Concrete room types
    public static class SingleRoomUC3 extends RoomUC3 {
        public SingleRoomUC3() {
            super("Single Room", 1, 50.0);
        }
    }

    public static class DoubleRoomUC3 extends RoomUC3 {
        public DoubleRoomUC3() {
            super("Double Room", 2, 90.0);
        }
    }

    public static class SuiteRoomUC3 extends RoomUC3 {
        public SuiteRoomUC3() {
            super("Suite Room", 4, 200.0);
        }
    }

    // Inventory manager
    public static class RoomInventoryUC3 {
        private Map<String, Integer> availability;

        public RoomInventoryUC3() {
            availability = new HashMap<>();
        }

        // Add rooms to inventory
        public void addRoomType(String roomName, int count) {
            availability.put(roomName, count);
        }

        // Get availability for a room type
        public int getAvailability(String roomName) {
            return availability.getOrDefault(roomName, 0);
        }

        // Update availability
        public void updateAvailability(String roomName, int newCount) {
            availability.put(roomName, newCount);
        }

        // Display all inventory
        public void displayInventory() {
            System.out.println("---- Room Inventory ----");
            for (Map.Entry<String, Integer> entry : availability.entrySet()) {
                System.out.println(entry.getKey() + " | Available: " + entry.getValue());
            }
        }
    }

    // Main method
    public static void main(String[] args) {
        // Initialize rooms
        RoomUC3 single = new SingleRoomUC3();
        RoomUC3 doubleR = new DoubleRoomUC3();
        RoomUC3 suite = new SuiteRoomUC3();

        // Initialize inventory
        RoomInventoryUC3 inventory = new RoomInventoryUC3();
        inventory.addRoomType(single.getRoomDetails(), 10);
        inventory.addRoomType(doubleR.getRoomDetails(), 5);
        inventory.addRoomType(suite.getRoomDetails(), 2);

        // Display inventory
        inventory.displayInventory();

        // Update availability example
        inventory.updateAvailability(single.getRoomDetails(), 8);
        System.out.println("\nAfter updating Single Room availability:");
        inventory.displayInventory();
    }
}