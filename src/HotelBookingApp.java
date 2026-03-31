import java.util.HashMap;

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

// Encapsulation of Inventory Logic
// Single Source of Truth for room availability
class RoomInventory {

    // HashMap - maps room types to available counts
    // O(1) Lookup - constant time access and updates
    private HashMap<String, Integer> inventory;

    // Constructor - initialize room availability
    public RoomInventory() {
        inventory = new HashMap<>();
        // Scalability - adding new room type = new entry only
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Retrieve current availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Controlled update to room availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display current inventory state
    public void displayInventory() {
        System.out.println("========== Room Inventory ==========");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : "
                    + inventory.get(roomType) + " rooms available");
        }
        System.out.println("====================================");
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        // UC1: Welcome Message
        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("Application Name : BookMyStayApp");
        System.out.println("Version          : 3.0");
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
        System.out.println("====================================");

        // UC3: Centralized Room Inventory Management
        // Separation of Concerns - inventory manages availability only
        RoomInventory roomInventory = new RoomInventory();

        // Display current inventory state
        roomInventory.displayInventory();

        // Controlled update to availability
        roomInventory.updateAvailability("Single Room", 4);
        System.out.println("Availability updated successfully.");

        // Display updated inventory
        roomInventory.displayInventory();

    }

}