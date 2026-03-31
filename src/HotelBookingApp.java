import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

// Abstract Class
abstract class Room {
    protected String roomType;
    protected int numberOfBeds;
    protected double price;

    public Room(String roomType, int numberOfBeds, double price) {
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
        System.out.println("Price      : Rs." + price + " per night");
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
        System.out.println("Price      : Rs." + price + " per night");
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
        System.out.println("Price      : Rs." + price + " per night");
    }
}

// RoomInventory - HashMap based
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public void displayInventory() {
        System.out.println("========== Room Inventory ==========");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : "
                    + inventory.get(roomType) + " rooms available");
        }
        System.out.println("====================================");
    }
}

// Reservation - represents guest's intent to book
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest Name : " + guestName);
        System.out.println("Room Type  : " + roomType);
    }
}

// Booking Request Queue - FIFO principle
// Decoupling Request Intake from Allocation
class BookingRequestQueue {

    // Queue Data Structure - preserves arrival order
    private Queue<Reservation> bookingQueue;

    public BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }

    // Add request to queue - FIFO
    // Fairness - no request can bypass another
    public void addRequest(Reservation reservation) {
        bookingQueue.add(reservation);
        System.out.println("Booking request added for : "
                + reservation.getGuestName()
                + " | Room Type : " + reservation.getRoomType());
    }

    // Display all queued requests
    // Request Ordering - insertion order preserved
    public void displayQueue() {
        System.out.println("===== Booking Request Queue =====");
        if (bookingQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            int position = 1;
            for (Reservation r : bookingQueue) {
                System.out.println("Position " + position + ":");
                r.displayReservation();
                positio