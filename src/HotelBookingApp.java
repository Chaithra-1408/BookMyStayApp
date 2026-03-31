import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

// Abstract Room class
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

// Centralized Inventory Management
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
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

// Booking Request
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
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

// Room Allocation Service
// Set - enforces uniqueness of room IDs
// HashMap - maps room types to allocated room IDs
class RoomAllocationService {

    // Set - prevents double booking
    private Set<String> allocatedRoomIds;

    // HashMap - maps room type to set of assigned room IDs
    private HashMap<String, Set<String>> roomTypeAllocations;

    private RoomInventory inventory;
    private int roomCounter;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomTypeAllocations = new HashMap<>();
        this.roomCounter = 1;
    }

    // Atomic Logical Operation - assignment and inventory update together
    public void allocateRoom(BookingRequest request) {
        String roomType = request.getRoomType();
        String guestName = request.getGuestName();

        // Check availability
        int available = inventory.getAvailability(roomType);

        if (available <= 0) {
            System.out.println("Booking FAILED for " + guestName
                    + " - No " + roomType + " available!");
            return;
        }

        // Generate unique room ID
        String roomId = roomType.substring(0, 1).toUpperCase()
                + String.format("%03d", roomCounter++);

        // Uniqueness Enforcement - check Set before assigning
        while (allocatedRoomIds.contains(roomId)) {
            roomId = roomType.substring(0, 1).toUpperCase()
                    + String.format("%03d", roomCounter++);
        }

        // Add to allocated set - prevents reuse
        allocatedRoomIds.add(roomId);

        // Map room type to allocated room IDs
        roomTypeAllocations.computeIfAbsent(roomType,
                k -> new HashSet<>()).add(roomId);

        // Inventory Synchronization - update immediately
        inventory.updateAvailability(roomType, available - 1);

        System.out.println("Booking CONFIRMED for " + guestName
                + " | Room Type: " + roomType
                + " | Room ID: " + roomId);
    }

    public void displayAllocations() {
        System.out.println("========== Room Allocations ==========");
        for (Map.Entry<String, Set<String>> entry
                : roomTypeAllocations.entrySet()) {
            System.out.println(entry.getKey() + " : "
                    + entry.getValue());
        }
        System.out.println("======================================");
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        // UC1: Welcome Message
        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("Application Name : BookMyStayApp");
        System.out.println("Version          : 6.0");
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

        // UC3: Centralized Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        // UC6: Reservation Confirmation & Room Allocation
        // Queue - FIFO order for booking requests
        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        bookingQueue.add(new BookingRequest("Alice", "Single Room"));
        bookingQueue.add(new BookingRequest("Bob", "Double Room"));
        bookingQueue.add(new BookingRequest("Charlie", "Suite Room"));
        bookingQueue.add(new BookingRequest("Diana", "Single Room"));
        bookingQueue.add(new BookingRequest("Eve", "Single Room"));
        bookingQueue.add(new BookingRequest("Frank", "Single Room"));

        // Room Allocation Service
        RoomAllocationService allocationService =
                new RoomAllocationService(inventory);

        System.out.println("========== Processing Bookings ==========");

        // Dequeue and process in FIFO order
        while (!bookingQueue.isEmpty()) {
            BookingRequest request = bookingQueue.poll();
            allocationService.allocateRoom(request);
        }

        System.out.println("=========================================");

        // Display final allocations
        allocationService.displayAllocations();

        // Display updated inventory
        inventory.displayInventory();

    }

}