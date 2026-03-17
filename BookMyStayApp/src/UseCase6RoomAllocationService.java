import java.util.*;

/**
 * Book My Stay App - Use Case 6
 * Reservation Confirmation & Room Allocation
 * Version: 6.1
 *
 * Processes booking requests in FIFO order, assigns unique room IDs,
 * updates inventory, and prevents double-booking.
 */
public class UseCase6RoomAllocationService {

    // ---------- Reservation class ----------
    public static class ReservationUC6 {
        private String guestName;
        private String roomType;
        private int nights;

        public ReservationUC6(String guestName, String roomType, int nights) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.nights = nights;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getRoomType() {
            return roomType;
        }

        @Override
        public String toString() {
            return "Reservation[Guest: " + guestName +
                    ", Room: " + roomType +
                    ", Nights: " + nights + "]";
        }
    }

    // ---------- Booking Queue ----------
    public static class BookingQueueUC6 {
        private Queue<ReservationUC6> queue;

        public BookingQueueUC6() {
            queue = new LinkedList<>();
        }

        public void addRequest(ReservationUC6 reservation) {
            queue.offer(reservation);
            System.out.println("Added to queue: " + reservation);
        }

        public ReservationUC6 pollNext() {
            return queue.poll();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    // ---------- Inventory Service ----------
    public static class InventoryUC6 {
        private Map<String, Integer> roomAvailability; // Room type -> available count

        public InventoryUC6() {
            roomAvailability = new HashMap<>();
        }

        public void addRoomType(String roomType, int count) {
            roomAvailability.put(roomType, count);
        }

        public boolean isAvailable(String roomType) {
            return roomAvailability.getOrDefault(roomType, 0) > 0;
        }

        public void decrement(String roomType) {
            roomAvailability.put(roomType, roomAvailability.get(roomType) - 1);
        }

        public int getAvailability(String roomType) {
            return roomAvailability.getOrDefault(roomType, 0);
        }
    }

    // ---------- Room Allocation Service ----------
    public static class RoomAllocationServiceUC6 {
        private InventoryUC6 inventory;
        private BookingQueueUC6 bookingQueue;
        private Map<String, Set<String>> allocatedRooms; // Room type -> allocated IDs
        private int roomIdCounter;

        public RoomAllocationServiceUC6(InventoryUC6 inventory, BookingQueueUC6 bookingQueue) {
            this.inventory = inventory;
            this.bookingQueue = bookingQueue;
            allocatedRooms = new HashMap<>();
            roomIdCounter = 100; // starting room ID
        }

        // Process all pending reservations
        public void processBookings() {
            while (!bookingQueue.isEmpty()) {
                ReservationUC6 reservation = bookingQueue.pollNext();
                allocateRoom(reservation);
            }
        }

        // Allocate a room for a reservation
        private void allocateRoom(ReservationUC6 reservation) {
            String type = reservation.getRoomType();

            if (!inventory.isAvailable(type)) {
                System.out.println("No availability for " + type + " for " + reservation.getGuestName());
                return;
            }

            // Generate unique room ID
            String roomId = type.substring(0, 2).toUpperCase() + roomIdCounter++;
            allocatedRooms.putIfAbsent(type, new HashSet<>());
            Set<String> allocatedSet = allocatedRooms.get(type);

            // Ensure uniqueness
            while (allocatedSet.contains(roomId)) {
                roomId = type.substring(0, 2).toUpperCase() + roomIdCounter++;
            }

            // Allocate room
            allocatedSet.add(roomId);
            inventory.decrement(type);

            System.out.println("Confirmed Reservation: " + reservation.getGuestName() +
                    " assigned Room ID: " + roomId + " (" + type + "), Remaining: " + inventory.getAvailability(type));
        }
    }

    // ---------- Demo Main ----------
    public static void main(String[] args) {
        // Initialize inventory
        InventoryUC6 inventory = new InventoryUC6();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 2);
        inventory.addRoomType("Suite Room", 1);

        // Initialize booking queue
        BookingQueueUC6 bookingQueue = new BookingQueueUC6();
        bookingQueue.addRequest(new ReservationUC6("Alice", "Single Room", 2));
        bookingQueue.addRequest(new ReservationUC6("Bob", "Double Room", 3));
        bookingQueue.addRequest(new ReservationUC6("Charlie", "Suite Room", 1));
        bookingQueue.addRequest(new ReservationUC6("Diana", "Single Room", 1));
        bookingQueue.addRequest(new ReservationUC6("Evan", "Single Room", 1)); // Should fail, only 2 singles available

        // Allocate rooms
        RoomAllocationServiceUC6 allocationService = new RoomAllocationServiceUC6(inventory, bookingQueue);
        allocationService.processBookings();
    }
}

