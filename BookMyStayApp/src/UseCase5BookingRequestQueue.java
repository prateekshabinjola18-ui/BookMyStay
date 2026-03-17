import java.util.LinkedList;
import java.util.Queue;

/**
 * Book My Stay App - Use Case 5
 * Booking Request (First-Come-First-Served)
 * Version: 5.1
 *
 * Demonstrates fair request intake using a FIFO queue
 * without modifying inventory.
 */
public class UseCase5BookingRequestQueue {

    // Reservation request
    public static class ReservationUC5 {
        private String guestName;
        private String roomType;
        private int nights;

        public ReservationUC5(String guestName, String roomType, int nights) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.nights = nights;
        }

        @Override
        public String toString() {
            return "Reservation[Guest: " + guestName +
                    ", Room: " + roomType +
                    ", Nights: " + nights + "]";
        }
    }

    // Booking request queue
    public static class BookingQueueUC5 {
        private Queue<ReservationUC5> queue;

        public BookingQueueUC5() {
            queue = new LinkedList<>();
        }

        // Add a reservation request
        public void addRequest(ReservationUC5 reservation) {
            queue.offer(reservation); // FIFO insertion
            System.out.println("Added to queue: " + reservation);
        }

        // Peek at the next request (without removing)
        public ReservationUC5 peekNext() {
            return queue.peek();
        }

        // Process next request (remove from queue)
        public ReservationUC5 processNext() {
            ReservationUC5 next = queue.poll();
            if (next != null) {
                System.out.println("Processing request: " + next);
            } else {
                System.out.println("No booking requests to process.");
            }
            return next;
        }

        // Check number of pending requests
        public int pendingRequests() {
            return queue.size();
        }
    }

    // Demo main method
    public static void main(String[] args) {
        BookingQueueUC5 bookingQueue = new BookingQueueUC5();

        // Guests submit booking requests
        bookingQueue.addRequest(new ReservationUC5("Alice", "Single Room", 2));
        bookingQueue.addRequest(new ReservationUC5("Bob", "Double Room", 3));
        bookingQueue.addRequest(new ReservationUC5("Charlie", "Suite Room", 1));

        System.out.println("\nTotal pending requests: " + bookingQueue.pendingRequests());

        // Process requests in order
        bookingQueue.processNext(); // Alice
        bookingQueue.processNext(); // Bob
        bookingQueue.processNext(); // Charlie
        bookingQueue.processNext(); // No requests left
    }
}