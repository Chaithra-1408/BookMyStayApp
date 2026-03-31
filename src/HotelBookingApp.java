// Abstract Class - generalized concept, not instantiated directly
abstract class Room {

    // Encapsulation - attributes controlled through defined behavior
    protected String roomType;
    protected int numberOfBeds;
    protected double price;

    // Constructor
    public Room(String roomType, int numberOfBeds, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
    }

    // Abstract method - enforces consistent structure
    public abstract void displayRoomDetails();

}

// Inheritance - SingleRoom extends Room
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 1000.0);
    }

    // Polymorphism - overrides displayRoomDetails()
    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type    : " + roomType);
        System.out.println("No of Beds   : " + numberOfBeds);
        System.out.println("Price        : Rs." + price + " per night");
    }

}

// Inheritance - DoubleRoom extends Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 2000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type    : " + roomType);
        System.out.println("No of Beds   : " + numberOfBeds);
        System.out.println("Price        : Rs." + price + " per night");
    }

}

// Inheritance - SuiteRoom extends Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 5000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type    : " + roomType);
        System.out.println("No of Beds   : " + numberOfBeds);
        System.out.println("Price        : Rs." + price + " per night");
    }

}

public class HotelBookingApp {

    public static void main(String[] args) {

        // UC1: Welcome Message
        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("Application Name : BookMyStayApp");
        System.out.println("Version          : 2.0");
        System.out.println("System initialized successfully.");
        System.out.println("==========================================");

        // UC2: Basic Room Types & Static Availability
        // Static Availability - stored using simple variables
        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        // Polymorphism - Room objects referenced using Room type
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display room details and availability
        System.out.println("---------- Single Room ----------");
        singleRoom.displayRoomDetails();
        System.out.println("Availability : " + singleRoomAvailability + " rooms available");

        System.out.println("---------- Double Room ----------");
        doubleRoom.displayRoomDetails();
        System.out.println("Availability : " + doubleRoomAvailability + " rooms available");

        System.out.println("---------- Suite Room ----------");
        suiteRoom.displayRoomDetails();
        System.out.println("Availability : " + suiteRoomAvailability + " rooms available");

        System.out.println("==========================================");

    }

}