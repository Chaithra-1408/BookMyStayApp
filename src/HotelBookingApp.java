import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

// RoomInventory
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

// AddOnService
class AddOnService {
    private String serviceName;
    private double serviceCost;

    public AddOnService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void displayService() {
        System.out.println("Service : " + serviceName
                + " | Cost : Rs." + serviceCost);
    }
}

// AddOnServiceManager
class AddOnServiceManager {
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId,
                           AddOnService service) {
        if (!serviceMap.containsKey(reservationId)) {
            serviceMap.put(reservationId, new ArrayList<>());
        }
        serviceMap.get(reservationId).add(service);
    }

    public double calculateTotalCost(String reservationId) {
        double total = 0;
        if (serviceMap.containsKey(reservationId)) {
            for (AddOnService service :
                    serviceMap.get(reservationId)) {
                total += service.getServiceCost();
            }
        }
        return total;
    }

    public void displayServices(String reservationId) {
        System.out.println("===== Add-On Services for "
                + reservationId + " =====");
        if (serviceMap.containsKey(reservationId)) {
            for (AddOnService service :
                    serviceMap.get(reservationId)) {
                service.displayService();
            }
            System.out.println("Total Add-On Cost : Rs."
                    + calculateTotalCost(reservationId));
        } else {
            System.out.println("No services selected.");
        }
        System.out.println("====================================");
    }
}

// Reservation - represents a confirmed booking
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int numberOfNights;
    private double totalCost;

    public Reservation(String reservationId, String guestName,
                       String roomType, int numberOfNights,
                       double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.numberOfNights = numberOfNights;
        this.totalCost = totalCost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void displayReservation() {
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Guest Name     : " + guestName);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Nights         : " + numberOfNights);
        System.out.println("Total Cost     : Rs." + totalCost);
    }
}

// Booking History - List preserves insertion order
// Ordered Storage - chronological records
class BookingHistory {

    // List<Reservation> - stores confirmed bookings
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation to history
    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    // Retrieve all stored reservations
    public List<Reservation> getHistory() {
        return history;
    }

    // Display all bookings
    public void displayHistory() {
        System.out.println("========== Booking History ==========");
        if (history.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Reservation r : history) {
                r.displayReservation();
                System.out.println("------------------------------------");
            }
        }
        System.out.println("=====================================");
    }
}

// Booking Report Service
// Separation of Data Storage and Reporting
class BookingReportService {

    // Reporting does not modify stored booking data
    public void generateReport(BookingHistory bookingHistory) {
        List<Reservation> history =
                bookingHistory.getHistory();

        System.out.println("========== Booking Report ==========");
        System.out.println("Total Bookings : " + history.size());

        double totalRevenue = 0;
        for (Reservation r : history) {
            totalRevenue += r.getTotalCost();
        }
        System.out.println("Total Revenue  : Rs." + totalRevenue);
        System.out.println("====================================");
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        // UC1: Welcome Message
        System.out.println("Welcome to the Hotel Booking Management System");
        System.out.println("Application Name : BookMyStayApp");
        System.out.println("Version          : 8.0");
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

        // UC7: Add-On Services
        AddOnServiceManager serviceManager =
                new AddOnServiceManager();
        AddOnService breakfast =
                new AddOnService("Breakfast", 500.0);
        AddOnService airportPickup =
                new AddOnService("Airport Pickup", 1500.0);
        AddOnService spa =
                new AddOnService("Spa", 2000.0);

        serviceManager.addService("RES001", breakfast);
        serviceManager.addService("RES001", airportPickup);
        serviceManager.addService("RES001", spa);
        serviceManager.displayServices("RES001");

        // UC8: Booking History & Reporting
        // Historical Tracking - audit trail
        BookingHistory bookingHistory = new BookingHistory();

        // Add confirmed reservations to history
        // Ordered Storage - insertion order preserved
        Reservation r1 = new Reservation(
                "RES001", "Alice", "Single Room", 2, 2000.0);
        Reservation r2 = new Reservation(
                "RES002", "Bob", "Double Room", 3, 6000.0);
        Reservation r3 = new Reservation(
                "RES003", "Charlie", "Suite Room", 1, 5000.0);

        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Display booking history
        bookingHistory.displayHistory();

        // Generate report
        // Reporting Readiness - no reprocessing needed
        BookingReportService reportService =
                new BookingReportService();
        reportService.generateReport(bookingHistory);

    }

}
