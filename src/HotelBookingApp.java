import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

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

// Custom Exceptions
// Domain-specific exceptions for invalid booking scenarios

// Invalid Room Type Exception
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

// Invalid Booking Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Room Not Available Exception
class RoomNotAvailableException extends Exception {
    public RoomNotAvailableException(String message) {
        super(message);
    }
}

// RoomInventory with Validation
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Input Validation - validate room type
    // Fail-Fast Design - detect errors early
    public void validateRoomType(String roomType)
            throws InvalidRoomTypeException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidRoomTypeException(
                    "Invalid room type : " + roomType
                            + ". Valid types are : "
                            + inventory.keySet());
        }
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Guarding System State - check before allocation
    public void allocateRoom(String roomType)
            throws InvalidRoomTypeException,
            RoomNotAvailableException {

        // Validate room type first
        validateRoomType(roomType);

        int available = getAvailability(roomType);

        // Prevent inventory from reaching negative values
        if (available <= 0) {
            throw new RoomNotAvailableException(
                    "No rooms available for : " + roomType);
        }

        inventory.put(roomType, available - 1);
        System.out.println("Room allocated : " + roomType);
    }

    public void displayInventory() {
        System.out.println("========== Room Inventory ==========");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " : "
                    + inventory.get(roomType)
                    + " rooms available");
        }
        System.out.println("====================================");
    }
}

// Reservation
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int numberOfNights;
    private double totalCost;

    public Reservation(String reservationId,
                       String guestName, String roomType,
                       int numberOfNights, double totalCost)
            throws InvalidBookingException {

        // Input Validation - validate booking data
        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException(
                    "Guest name cannot be empty.");
        }

        if (numberOfNights <= 0) {
            throw new InvalidBookingException(
                    "Number of nights must be greater than 0."
                            + " Provided : " + numberOfNights);
        }

        if (totalCost < 0) {
            throw new InvalidBookingException(
                    "Total cost cannot be negative."
                            + " Provided : " + totalCost);
        }

        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
        this.totalCost = totalCost;
    }

    public void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Nights         : " + numberOfNights);
        System.out.println("Total Cost     : Rs." + totalCost);
    }
}

// Booking History
class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public void displayHistory() {
        System.out.println("========== Booking History ==========");
        if (history.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Reservation r : history) {
                r.displayReservation();
                System.out.println(
                        "------------------------------------");
            }
        }
        System.out.println("=====================================");
    }
}

// Invalid Booking Validator
class InvalidBookingValidator {

    private RoomInventory roomInventory;
    private BookingHistory bookingHistory;

    public InvalidBookingValidator(
            RoomInventory roomInventory,
            BookingHistory bookingHistory) {
        this.roomInventory = roomInventory;
        this.bookingHistory = bookingHistory;
    }

    // Graceful Failure Handling
    // Correctness over Happy Path
    public void processBooking(String reservationId,
                               String guestName, String roomType,
                               int numberOfNights, double totalCost) {

        System.out.println("Processing booking for : "
                + guestName + " | Room : " + roomType);

        try {
            // Fail-Fast - validate room type first
            roomInventory.validateRoomType(roomType);

            // Validate booking data
            Reservation reservation = new Reservation(
                    reservationId, guestName, roomType,
                    numberOfNights, totalCost);

            // Guarding System State
            roomInventory.allocateRoom(roomType);

            // Add to history only if valid
            bookingHistory.addReservation(reservation);

            System.out.println("Booking successful for : "
                    + guestName);

        } catch (InvalidRoomTypeException e) {
            // Clear failure message
            System.out.println("Booking failed : "
                    + e.getMessage());
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed : "
                    + e.getMessage());
        } catch (RoomNotAvailableException e) {
            System.out.println("Booking failed : "
                    + e.getMessage());
        }

        System.out.println(
                "------------------------------------");
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        // UC1: Welcome Message
        System.out.println(
                "Welcome to the Hotel Booking Management System");
        System.out.println("Application Name : BookMyStayApp");
        System.out.println("Version          : 9.0");
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

        // UC3: Inventory
        RoomInventory roomInventory = new RoomInventory();
        roomInventory.displayInventory();

        // UC8: Booking History
        BookingHistory bookingHistory = new BookingHistory();

        // UC9: Error Handling & Validation
        System.out.println(
                "===== Error Handling & Validation =====");

        InvalidBookingValidator validator =
                new InvalidBookingValidator(
                        roomInventory, bookingHistory);

        // Valid booking
        validator.processBooking(
                "RES001", "Alice", "Single Room", 2, 2000.0);

        // Invalid room type - case sensitive!
        validator.processBooking(
                "RES002", "Bob", "single room", 2, 2000.0);

        // Invalid - empty guest name
        validator.processBooking(
                "RES003", "", "Double Room", 2, 4000.0);

        // Invalid - zero nights
        validator.processBooking(
                "RES004", "Charlie", "Suite Room", 0, 0.0);

        // Invalid - negative cost
        validator.processBooking(
                "RES005", "Diana", "Double Room", 2, -500.0);

        // Valid booking
        validator.processBooking(
                "RES006", "Eve", "Suite Room", 3, 15000.0);

        // Display inventory after validation
        roomInventory.displayInventory();

        // Display booking history
        bookingHistory.displayHistory();

    }

}