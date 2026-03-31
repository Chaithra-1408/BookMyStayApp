/**
 * =========================================================
 * MAIN CLASS - UseCase4RoomSearch
 * =========================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class represents the entry point of the
 * Hotel Booking Management System.
 *
 * At this stage, the application:
 * - Enables guests to search available rooms
 * - Performs read-only access to inventory
 * - Filters unavailable room types
 *
 * @author Developer
 * @version 4.0
 */

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

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }
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

// Centralized Inventory
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0); // intentionally 0 to test filtering
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
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

// Search Service - Read-Only Access
// Separation of Concerns - isolated from booking logic
class RoomSearchService {

    private RoomInventory roomInventory;
    private HashMap<String, Room> roomCatalog;

    public RoomSearchService(RoomInventory roomInventory) {
        this.roomInventory = roomInventory;

        // Domain Model Usage - room objects provide details
        roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoom());
        roomCatalog.put("Double Room", new DoubleRoom());
        roomCatalog.put("Suite Room", new SuiteRoom());
    }

    // Read-Only Search - does not modify inventory
    public void searchAvailableRooms() {
        System.out.println("========== Available Rooms ==========");

        boolean anyAvailable = false;

        // Defensive Programming - check validity before display
        for (String roomType : roomCatalog.keySet()) {

            // Validation Logic - filter out zero availability
            int availability = roomInventory.getAvailability(roomType);
            if (availability > 0) {
                anyAvailable = true;
                System.out.println("------------------------------");
                roomCatalog.get(roomType).displayRoomDetails();
                System.out.println("Availability : "
                        + availability + " rooms available");
            }
        }

        if (!anyAvailable) {
            System.out.println("No rooms available at the moment.");
        }

        System.out.println("======================================");
        // System state remains unchanged - Inventory as State Holder
    }
}

public class HotelBookingApp {

    public static void ma