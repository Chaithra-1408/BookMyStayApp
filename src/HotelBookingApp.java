import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

// Abstract Class
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double price;

    public Room(String roomType, int numberOfBeds,
                double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }

    public abstract void displayRoomDetails();
}

// SingleRoom
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 1000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type  : " + roomType);
        System.out.println("No of Beds : " + numberOfBeds);
        System.out.println("Price      : Rs." + price
                + " per night");
    }
}

// DoubleRoom
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 2000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type  : " + roomType);
        System.out.println("No of Beds : " + numberOfBeds);
        System.out.println("Price      : Rs." + price
                + " per night");
    }
}

// SuiteRoom
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type  : " + roomType);
        System.out.println("No of Beds : " + numberOfBeds);
        System.out.println("Price      : Rs." + price
                + " per night");
    }
}

// Thread-Safe Room Inventory
// Shared Mutable State - protected by synchronization
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    // Synchronized Access - critical section
    // Prevents interleaving operations
    public synchronized int getAvailability(
            String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Critical Section - exclusive thread access
    public synchronized boolean allocateRoom(
            String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void restoreAvailability(
            String roomType) {
        int current = getAvailability(roomType);
        inventory.put(roomType, current + 1);
    }

    public synchronized void displayInventory() {
        System.out.println("========== Room Inventory ==========");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : "
                    + inventory.get(roomType)
                    + " rooms available");
        }
        System.out.println("====================================");
    }
}

// Booking Request
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName,
                          String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Concurrent Booking Processor
// Thread Safety - implements Runnable
class BookingProcessor implements Runnable {

    private String guestName;
    private String roomType;
    private RoomInventory roomInventory;

    public BookingProcessor(String guestName,
                            String roomType,
                            RoomInventory roomInventory) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomInventory = roomInventory;
    }

    // Synchronized Access inside run()
    @Override
    public void run() {
        // Critical Section - one thread at a time
        boolean allocated =
                roomInventory.allocateRoom(roomType);

        if (allocated) {
            System.out.println("Booking confirmed for "
                    + guestName + " | Room : " + roomType
                    + " | Thread : "
                    + Thread.currentThread().getName());
        } else {
            System.out.println("Booking failed for "
                    + guestName + " | Room : " + roomType
                    + " | No rooms available"
                    + " | Thread : "
                    + Thread.currentThread().getName());
        }
    }
}

public class HotelBookingApp {

    public static void main(String[] args)
            throws InterruptedException {

        // UC1: Welcome Message
        System.out.println(
                "Welcome to the Hotel Booking Management System");
        System.out.println("Application Name : BookMyStayApp");
        System.out.println("Version          : 11.0");
        System.out.println("System initialized successfully.");
        System.out.println("====================================");

        // UC2: Room Objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        System.out.println("---------- Single Room ----------");
        singleRoom.displayRoomDetails();
        System.out.println("---------- Double Room ----------");
        doubleRoom.displayRoomDetails();
        System.out.println("---------- Suite Room ----------");
        suiteRoom.displayRoomDetails();