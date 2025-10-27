Main.java

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel();
        while (true) {
            System.out.println
                    ("\n--- Hotel Reservation System ---");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Show All Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> hotel.showAvailableRooms();
                case 2 -> {
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room type (Standard/Deluxe/Suite): ");
                    String type = sc.nextLine();
                    hotel.bookRoom(name, type);
                    Payment.processPayment(name, 1000); // Simulate payment amount
                }
                case 3 -> {
                    System.out.print("Enter your name to cancel booking: ");
                    String name = sc.nextLine();
                    hotel.cancelBooking(name);
                }
                case 4 -> hotel.showAllBookings();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}


Room.java

import java.io.Serializable;

public class Room implements Serializable {
    private int roomNumber;
    private String type; // Standard, Deluxe, Suite
    private boolean isAvailable;

    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", type='" + type + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}


Booking.java

import java.io.Serializable;

public class Booking implements Serializable {
    private String customerName;
    private Room room;

    public Booking(String customerName, Room room) {
        this.customerName = customerName;
        this.room = room;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "customerName='" + customerName + '\'' +
                ", room=" + room +
                '}';
    }
}


Hotel.java

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;

    private final String ROOM_FILE = "rooms.dat";
    private final String BOOKING_FILE = "bookings.dat";

    public Hotel() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        loadRooms();
        loadBookings();
    }

    // Initialize rooms if file is empty
    private void loadRooms() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOM_FILE))) {
            rooms = (List<Room>) ois.readObject();
        } catch (Exception e) {
            // Create some default rooms if file not found
            rooms.add(new Room(101, "Standard"));
            rooms.add(new Room(102, "Deluxe"));
            rooms.add(new Room(103, "Suite"));
            rooms.add(new Room(104, "Standard"));
            rooms.add(new Room(105, "Deluxe"));
            saveRooms();
        }
    }

    private void saveRooms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROOM_FILE))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBookings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKING_FILE))) {
            bookings = (List<Booking>) ois.readObject();
        } catch (Exception e) {
            bookings = new ArrayList<>();
        }
    }

    private void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKING_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public Room findAvailableRoom(String type) {
        for (Room room : rooms) {
            if (room.isAvailable() && room.getType().equalsIgnoreCase(type)) {
                return room;
            }
        }
        return null;
    }

    public void bookRoom(String customerName, String type) {
        Room room = findAvailableRoom(type);
        if (room != null) {
            room.setAvailable(false);
            Booking booking = new Booking(customerName, room);
            bookings.add(booking);
            saveRooms();
            saveBookings();
            System.out.println("Booking Successful!");
            System.out.println(booking);
        } else {
            System.out.println("No available room of type " + type);
        }
    }

    public void cancelBooking(String customerName) {
        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.getCustomerName().equalsIgnoreCase(customerName)) {
                b.getRoom().setAvailable(true);
                toRemove = b;
                break;
            }
        }
        if (toRemove != null) {
            bookings.remove(toRemove);
            saveRooms();
            saveBookings();
            System.out.println("Booking cancelled for " + customerName);
        } else {
            System.out.println("No booking found for " + customerName);
        }
    }

    public void showAllBookings() {
        System.out.println("All Bookings:");
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }
}

Payment.java

public class Payment {
    public static void processPayment(String customerName, double amount) {
        // Simulate payment processing
        System.out.println("Processing payment of $" + amount + " for " + customerName);
        System.out.println("Payment Successful!");
    }
}

