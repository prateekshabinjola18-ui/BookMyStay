/**
 * Use Case 2: Basic Room Types & Static Availability
 * Book My Stay App
 *
 * This code demonstrates:
 * - Abstract Room class with common attributes
 * - Concrete room classes for Single, Double, and Suite rooms
 * - Static availability variables
 * - Printing room details and availability
 *
 * @author YourName
 * @version 2.1
 */

abstract class Room {
    protected String roomType;
    protected int beds;
    protected double size;    // in square meters
    protected double price;   // per night

    public Room(String roomType, int beds, double size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    // Abstract method to describe the room
    public abstract void displayRoomDetails();
}

// SingleRoom class
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 20.0, 50.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(roomType + ":");
        System.out.println(" - Beds: " + beds);
        System.out.println(" - Size: " + size + " sqm");
        System.out.println(" - Price: $" + price + " per night");
    }
}

// DoubleRoom class
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 30.0, 80.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(roomType + ":");
        System.out.println(" - Beds: " + beds);
        System.out.println(" - Size: " + size + " sqm");
        System.out.println(" - Price: $" + price + " per night");
    }
}

// SuiteRoom class
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 50.0, 150.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(roomType + ":");
        System.out.println(" - Beds: " + beds);
        System.out.println(" - Size: " + size + " sqm");
        System.out.println(" - Price: $" + price + " per night");
    }
}

public class UseCase2RoomInitialization {
    // Static availability variables
    private static int availableSingleRooms = 10;
    private static int availableDoubleRooms = 5;
    private static int availableSuiteRooms = 2;

    public static void main(String[] args) {
        System.out.println("=======================================");
        System.out.println("   Welcome to Book My Stay App v2.1   ");
        System.out.println("=======================================");
        System.out.println();

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display room details and availability
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + availableSingleRooms + " rooms");
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + availableDoubleRooms + " rooms");
        System.out.println();

        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + availableSuiteRooms + " rooms");
        System.out.println();

        System.out.println("Program executed successfully and will now terminate.");
    }
}