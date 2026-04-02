import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

// Helper class to represent an individual booking request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class UseCase11ConcurrentBookingSimulation {
    // Shared Mutable State: The inventory
    private static final Map<String, Integer> rooms = new HashMap<>();

    // Shared Mutable State: The booking queue
    private static final Queue<BookingRequest> bookingQueue = new LinkedList<>();

    // Initialize inventory
    static {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Deluxe", 2);
    }

    // Guests call this method to submit requests (Simulated)
    public static synchronized void addRequest(BookingRequest request) {
        bookingQueue.add(request);
    }

    // This method is executed by multiple concurrent processor threads
    public static void processRequests() {
        while (true) {
            BookingRequest request = null;

            // CRITICAL SECTION 1: Synchronized access to the shared queue
            // Ensures only one thread retrieves a request at a time
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break; // Exit loop if no more requests
                }
                request = bookingQueue.poll();
            }

            if (request != null) {
                // CRITICAL SECTION 2: Synchronized access to shared inventory
                // Prevents race conditions and double-booking
                synchronized (rooms) {
                    int available = rooms.getOrDefault(request.roomType, 0);

                    if (available > 0) {
                        // Simulating a slight processing delay to expose race conditions (if it weren't synchronized)
                        try { Thread.sleep(50); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                        rooms.put(request.roomType, available - 1);
                        System.out.println("[SUCCESS] " + Thread.currentThread().getName() +
                                " allocated a " + request.roomType + " room to " + request.guestName +
                                ". Remaining " + request.roomType + "s: " + (available - 1));
                    } else {
                        System.out.println("[FAILED]  " + Thread.currentThread().getName() +
                                " could not book " + request.roomType + " for " + request.guestName +
                                ". Reason: Sold out.");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("===== USE CASE 11: CONCURRENT BOOKING SIMULATION =====");
        System.out.println("Initial Inventory: " + rooms);
        System.out.println("------------------------------------------------------");

        // 1. Simulate multiple guests submitting requests simultaneously
        // 10 guests want a "Single" room, but we only have 5.
        String[] guests = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi", "Ivan", "Judy"};
        for (String guest : guests) {
            addRequest(new BookingRequest(guest, "Single"));
        }

        // 2 extra guests requesting Double rooms
        addRequest(new BookingRequest("Mallory", "Double"));
        addRequest(new BookingRequest("Trent", "Double"));

        // 2. Create multiple Concurrent Booking Processor threads
        Thread processor1 = new Thread(UseCase11ConcurrentBookingSimulation::processRequests, "Processor-A");
        Thread processor2 = new Thread(UseCase11ConcurrentBookingSimulation::processRequests, "Processor-B");
        Thread processor3 = new Thread(UseCase11ConcurrentBookingSimulation::processRequests, "Processor-C");

        // 3. Start the threads (Simulation begins)
        processor1.start();
        processor2.start();
        processor3.start();

        // Wait for all threads to finish processing before showing final inventory
        try {
            processor1.join();
            processor2.join();
            processor3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------------------");
        System.out.println("Final Inventory: " + rooms);
        System.out.println("======================================================");
    }
}