import java.util.HashMap;
import java.util.Map;

/**
 * Book My Stay App - Use Case 4
 * Room Search & Availability Check
 * Version: 4.1
 *
 * Demonstrates read-only access to room inventory
 * and displays only available rooms with details.
 */
public class UseCase4RoomSearch {

    // Abstract Room class for UC4
    public static abstract class RoomUC4 {
        protected String name;
        protected int beds;
        protected double pricePerNight;

        public RoomUC4(String name, int beds, double pricePerNight) {
            this.name = name;
            this.beds = beds;
            this.pricePerNight = pricePerNight;
        }

        public String getRoomDetails() {
            return name + " | Beds: " + beds + " | Price: $" + pricePerNight;
        }
    }

    // Concrete Room types
    public static class SingleRoomUC4 extends RoomUC4 {
        public SingleRoomUC4() {
            super("Single Room", 1, 50.0);
        }
    }

    public static class DoubleRoomUC4 extends RoomUC4 {
        public DoubleRoomUC4() {
            super("Double Room", 2, 90.0);
        }
    }

    public static class SuiteRoomUC4 extends RoomUC4 {
        public SuiteRoomUC4() {
            super("Suite Room", 4, 200.0);
        }
    }

    // Inventory class (read-only for search)
    public static class RoomInventoryUC4 {
        private Map<String, Integer> availability;

        public RoomInventoryUC4() {
            availability = new HashMap<>();
        }

        public void addRoomType(String roomName, int count) {
            availability.put(roomName, count);
        }

        // Get availability for search (read-only)
        public int getAvailability(String roomName) {
            return availability.getOrDefault(roomName, 0);
        }

        // Display all available rooms
        public void displayAvailableRooms() {
            System.out.println("---- Available Rooms ----");
            for (Map.Entry<String, Integer> entry : availability.entrySet()) {
                if (entry.getValue() > 0) {
                    System.out.println(entry.getKey() + " | Available: " + entry.getValue());
                }
            }
        }
    }

    // Search Service
    public static class RoomSearchServiceUC4 {
        private RoomInventoryUC4 inventory;

        public RoomSearchServiceUC4(RoomInventoryUC4 inventory) {
            this.inventory = inventory;
        }

        public void searchRooms() {
            System.out.println("Searching for available rooms...");
            inventory.displayAvailableRooms();
        }
    }

    // Main method
    public static void main(String[] args) {
        // Initialize rooms
        RoomUC4 single = new SingleRoomUC4();
        RoomUC4 doubleR = new DoubleRoomUC4();
        RoomUC4 suite = new SuiteRoomUC4();

        // Initialize inventory
        RoomInventoryUC4 inventory = new RoomInventoryUC4();
        inventory.addRoomType(single.getRoomDetails(), 10);
        inventory.addRoomType(doubleR.getRoomDetails(), 0); // 0 available to test filtering
        inventory.addRoomType(suite.getRoomDetails(), 2);

        // Search service
        RoomSearchServiceUC4 searchService = new RoomSearchServiceUC4(inventory);
        searchService.searchRooms(); // Only rooms with >0 availability will show
    }
}