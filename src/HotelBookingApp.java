import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;

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

// RoomInventory
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 3);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void setInventory(