import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

// Room Class
class Room {
    private String roomType;
    private double price;
    private boolean isAvailable;

    public Room(String roomType, double price, boolean isAvailable) {
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return roomType + " ($" + price + ") - " + (isAvailable ? "Available" : "Not Available");
    }
}

// Hotel Class
class Hotel {
    private String name;
    private Room[] rooms;

    public Hotel(String name, Room[] rooms) {
        this.name = name;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public Room[] getRooms() {
        return rooms;
    }
}

// Flight Class
abstract class Flight {
    private String flightNumber;
    private String destination;
    private double price;

    public Flight(String flightNumber, String destination, double price) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.price = price;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getFlightType();
}

class DomesticFlight extends Flight {
    public DomesticFlight(String flightNumber, String destination, double price) {
        super(flightNumber, destination, price);
    }

    @Override
    public String getFlightType() {
        return "Domestic";
    }
}

class InternationalFlight extends Flight {
    public InternationalFlight(String flightNumber, String destination, double price) {
        super(flightNumber, destination, price);
    }

    @Override
    public String getFlightType() {
        return "International";
    }
}

// Combined Booking System
public class CombinedBookingSystem extends JFrame {
    private JComboBox<String> hotelDropdown;
    private JComboBox<String> roomDropdown;
    private JButton bookRoomButton;
    private JButton viewFlightsButton;
    private JButton doneButton;
    private JTextField passengerNameField;

    private Hotel[] hotels;
    private ArrayList<Flight> domesticFlights = new ArrayList<>();
    private ArrayList<Flight> internationalFlights = new ArrayList<>();
    private ArrayList<String> bookedRooms = new ArrayList<>();
    private ArrayList<String> bookedFlights = new ArrayList<>();
    private double totalCost = 0.0;
    private String passengerName = "";

    private JTabbedPane flightTabs;  // For displaying Domestic and International Tabs

    public CombinedBookingSystem() {
        setupHotels();
        setupFlights();
        setupUI();
    }

    private void setupHotels() {
        Room[] rooms1 = {
            new Room("Single", 100.0, true),
            new Room("Double", 150.0, true),
            new Room("Suite", 300.0, false)
        };
        Room[] rooms2 = {
            new Room("Single", 90.0, true),
            new Room("Double", 120.0, true)
        };

        hotels = new Hotel[] {
            new Hotel("Ocean View", rooms1),
            new Hotel("City Stay", rooms2)
        };
    }

    private void setupFlights() {
        domesticFlights.add(new DomesticFlight("DF123", "Mumbai", 150.0));
        domesticFlights.add(new DomesticFlight("DF456", "Chennai", 200.0));
        domesticFlights.add(new DomesticFlight("DF789", "Delhi", 180.0));

        internationalFlights.add(new InternationalFlight("IF123", "New York", 800.0));
        internationalFlights.add(new InternationalFlight("IF456", "London", 600.0));
        internationalFlights.add(new InternationalFlight("IF789", "Dubai", 450.0));
    }

    private void setupUI() {
        setTitle("Combined Booking System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Hotel and Flight Booking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Hotel Booking Section
        JLabel hotelLabel = new JLabel("Book Hotel");
        hotelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        hotelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        hotelDropdown = new JComboBox<>();
        for (Hotel hotel : hotels) {
            hotelDropdown.addItem(hotel.getName());
        }
        hotelDropdown.addActionListener(e -> updateRoomDropdown());
        hotelDropdown.setMaximumSize(new Dimension(300, 30));

        roomDropdown = new JComboBox<>();
        roomDropdown.setMaximumSize(new Dimension(300, 30));
        updateRoomDropdown();

        bookRoomButton = new JButton("Book Room");
        bookRoomButton.addActionListener(e -> bookRoom());

        // Flight Booking Section
        JLabel flightLabel = new JLabel("Book Flight");
        flightLabel.setFont(new Font("Arial", Font.BOLD, 16));
        flightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passengerNameField = new JTextField(20);
        passengerNameField.setMaximumSize(new Dimension(300, 30));

        viewFlightsButton = new JButton("View Flights");
        viewFlightsButton.addActionListener(e -> showFlights());

        // Done Button
        doneButton = new JButton("Done");
        doneButton.addActionListener(e -> showBill());

        // Flight Tabs (Initially hidden)
        flightTabs = new JTabbedPane();
        flightTabs.setVisible(false);  // Hide tabs initially

        JPanel domesticPanel = new JPanel();
        domesticPanel.setLayout(new BoxLayout(domesticPanel, BoxLayout.Y_AXIS));
        domesticFlights.forEach(flight -> {
            JButton flightButton = new JButton(flight.getFlightNumber() + " - " + flight.getDestination() + " ($" + flight.getPrice() + ")");
            flightButton.addActionListener(e -> bookFlight(flight));
            domesticPanel.add(flightButton);
        });
        flightTabs.addTab("Domestic", domesticPanel);

        JPanel internationalPanel = new JPanel();
        internationalPanel.setLayout(new BoxLayout(internationalPanel, BoxLayout.Y_AXIS));
        internationalFlights.forEach(flight -> {
            JButton flightButton = new JButton(flight.getFlightNumber() + " - " + flight.getDestination() + " ($" + flight.getPrice() + ")");
            flightButton.addActionListener(e -> bookFlight(flight));
            internationalPanel.add(flightButton);
        });
        flightTabs.addTab("International", internationalPanel);

        // Add components to layout
        add(titleLabel);
        add(Box.createVerticalStrut(10));
        add(hotelLabel);
        add(hotelDropdown);
        add(Box.createVerticalStrut(5));
        add(roomDropdown);
        add(Box.createVerticalStrut(5));
        add(bookRoomButton);
        add(Box.createVerticalStrut(10));
        add(flightLabel);
        add(new JLabel("Passenger Name:"));
        add(passengerNameField);
        add(Box.createVerticalStrut(5));
        add(viewFlightsButton);
        add(flightTabs);
        add(Box.createVerticalStrut(10));
        add(doneButton);
    }

    private void updateRoomDropdown() {
        roomDropdown.removeAllItems();
        int hotelIndex = hotelDropdown.getSelectedIndex();
        if (hotelIndex >= 0) {
            Room[] rooms = hotels[hotelIndex].getRooms();
            for (Room room : rooms) {
                roomDropdown.addItem(room.toString());
            }
        }
    }

    private void bookRoom() {
        int hotelIndex = hotelDropdown.getSelectedIndex();
        int roomIndex = roomDropdown.getSelectedIndex();
        if (roomIndex >= 0 && hotelIndex >= 0) {
            Room selectedRoom = hotels[hotelIndex].getRooms()[roomIndex];
            if (selectedRoom.isAvailable()) {
                String booking = "Hotel: " + hotels[hotelIndex].getName() +
                                 ", Room: " + selectedRoom.getRoomType() +
                                 ", Price: $" + selectedRoom.getPrice();
                bookedRooms.add(booking);
                totalCost += selectedRoom.getPrice();
                JOptionPane.showMessageDialog(this, "Room booked successfully: " + booking);
            } else {
                JOptionPane.showMessageDialog(this, "Selected room is not available.");
            }
        }
    }

    private void showFlights() {
        // Show the flight tabs when the button is clicked
        flightTabs.setVisible(true);
    }

    private void bookFlight(Flight flight) {
        String booking = "Flight: " + flight.getFlightNumber() + " - " + flight.getDestination() + " ($" + flight.getPrice() + ")";
        bookedFlights.add(booking);
        totalCost += flight.getPrice();
        JOptionPane.showMessageDialog(this, "Flight booked successfully: " + booking);
    }

    private void showBill() {
        passengerName = passengerNameField.getText().trim();
        if (bookedRooms.isEmpty() && bookedFlights.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No bookings made yet!");
            return;
        }

        StringBuilder bill = new StringBuilder("-------- Combined Booking Bill --------\n");
        bill.append("Passenger: ").append(passengerName).append("\n\n");

        // Add hotel bookings to the bill
        if (!bookedRooms.isEmpty()) {
            bill.append("Hotel Bookings:\n");
            for (String room : bookedRooms) {
                bill.append(room).append("\n");
            }
        }

        // Add flight bookings to the bill
        if (!bookedFlights.isEmpty()) {
            bill.append("\nFlight Bookings:\n");
            for (String flight : bookedFlights) {
                bill.append(flight).append("\n");
            }
        }

        // Display total cost
        bill.append("\nTotal Cost: $").append(totalCost).append("\n-----------------------------");

        // Create and display the bill window
        JFrame billFrame = new JFrame("Booking Bill");
        billFrame.setSize(400, 300);
        billFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea billArea = new JTextArea(bill.toString());
        billArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(billArea);

        billFrame.add(scrollPane);
        billFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CombinedBookingSystem().setVisible(true));
    }
}