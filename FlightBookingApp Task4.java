package com.task;

	import java.util.ArrayList;
	import java.util.List;
	import java.util.Scanner;

	abstract class Passenger {
	    private String name;
	    private int age;

	    public Passenger(String name, int age) {
	        this.name = name;
	        this.age = age;
	    }

	    public String getName() {
	        return name;
	    }

	    public int getAge() {
	        return age;
	    }

	    public abstract double getDiscountPercentage();

	    public abstract String getPassengerType();
	}

	class RegularPassenger extends Passenger {

	    public RegularPassenger(String name, int age) {
	        super(name, age);
	    }

	    @Override
	    public double getDiscountPercentage() {
	        return 0.0;
	    }

	    @Override
	    public String getPassengerType() {
	        return "Regular";
	    }
	}

	class StudentPassenger extends Passenger {

	    public StudentPassenger(String name, int age) {
	        super(name, age);
	    }

	    @Override
	    public double getDiscountPercentage() {
	        return 0.10;
	    }

	    @Override
	    public String getPassengerType() {
	        return "Student";
	    }
	}

	class Flight {
	    private String code;
	    private String airline;
	    private String source;
	    private String destination;
	    private int availableSeats;
	    private double baseFare;

	    public Flight(String code, String airline, String source,
	                  String destination, int availableSeats,
	                  double baseFare) {
	        this.code = code;
	        this.airline = airline;
	        this.source = source;
	        this.destination = destination;
	        this.availableSeats = availableSeats;
	        this.baseFare = baseFare;
	    }

	    public String getCode() {
	        return code;
	    }

	    public String getAirline() {
	        return airline;
	    }

	    public String getSource() {
	        return source;
	    }

	    public String getDestination() {
	        return destination;
	    }

	    public int getAvailableSeats() {
	        return availableSeats;
	    }

	    public double getBaseFare() {
	        return baseFare;
	    }

	    public boolean hasEnoughSeats(int tickets) {
	        return tickets > 0 && tickets <= availableSeats;
	    }

	    public void reserveSeats(int tickets) {
	        if (hasEnoughSeats(tickets)) {
	            availableSeats -= tickets;
	        }
	    }

	    public void displayFlight() {
	        System.out.println(
	            code + " | " + airline + " | " + source
	            + " to " + destination
	            + " | Seats: " + availableSeats
	            + " | Fare: Rs. " + baseFare
	        );
	    }
	}

	class Booking {
	    private String bookingId;
	    private Flight flight;
	    private Passenger passenger;
	    private int ticketCount;
	    private double subtotal;
	    private double discount;
	    private double finalAmount;

	    public Booking(String bookingId, Flight flight,
	                   Passenger passenger, int ticketCount) {
	        this.bookingId = bookingId;
	        this.flight = flight;
	        this.passenger = passenger;
	        this.ticketCount = ticketCount;

	        subtotal = flight.getBaseFare() * ticketCount;
	        discount = subtotal * passenger.getDiscountPercentage();
	        finalAmount = subtotal - discount;
	    }

	    public void displaySummary() {
	        System.out.println("\n===== BOOKING SUMMARY =====");
	        System.out.println("Booking ID: " + bookingId);
	        System.out.println("Flight Code: " + flight.getCode());
	        System.out.println("Airline: " + flight.getAirline());
	        System.out.println("Route: " + flight.getSource()
	                           + " to " + flight.getDestination());
	        System.out.println("Passenger Name: " + passenger.getName());
	        System.out.println("Age: " + passenger.getAge());
	        System.out.println("Passenger Type: "
	                           + passenger.getPassengerType());
	        System.out.println("Tickets: " + ticketCount);
	        System.out.printf("Base Fare per Ticket: Rs. %.2f%n",
	                          flight.getBaseFare());
	        System.out.printf("Subtotal: Rs. %.2f%n", subtotal);
	        System.out.printf("Discount (%.0f%%): Rs. %.2f%n",
	                          passenger.getDiscountPercentage() * 100,
	                          discount);
	        System.out.printf("Final Amount: Rs. %.2f%n", finalAmount);
	        System.out.println("===========================\n");
	    }
	}

	public class FlightBookingApp {
	    private List<Flight> flights = new ArrayList<>();
	    private Scanner scanner = new Scanner(System.in);
	    private Booking latestBooking;
	    private int bookingNumber = 1001;

	    public FlightBookingApp() {
	        flights.add(new Flight("PN101", "Kiran Air",
	                "Pune", "Delhi", 20, 4000));

	        flights.add(new Flight("MB202", "SkyConnect",
	                "Mumbai", "Bengaluru", 25, 3500));

	        flights.add(new Flight("PN303", "Kiran Air",
	                "Pune", "Hyderabad", 18, 2800));
	    }

	    public static void main(String[] args) {
	        FlightBookingApp app = new FlightBookingApp();
	        app.run();
	    }

	    public void run() {
	        System.out.println("Welcome to Kiran Air Booking");

	        while (true) {
	            System.out.println("\n1. View Flights");
	            System.out.println("2. Book Flight");
	            System.out.println("3. View Booking");
	            System.out.println("4. Exit");
	            System.out.print("Enter choice: ");

	            String choice = scanner.nextLine().trim();

	            switch (choice) {
	                case "1":
	                    displayFlights();
	                    break;

	                case "2":
	                    bookFlight();
	                    break;

	                case "3":
	                    displayLatestBooking();
	                    break;

	                case "4":
	                    System.out.println(
	                        "Thank you for using Kiran Air Booking!"
	                    );
	                    scanner.close();
	                    return;

	                default:
	                    System.out.println(
	                        "Invalid choice. Please enter 1, 2, 3, or 4."
	                    );
	            }
	        }
	    }

	    private void displayFlights() {
	        System.out.println("\n===== AVAILABLE FLIGHTS =====");

	        for (Flight flight : flights) {
	            flight.displayFlight();
	        }
	    }

	    private Flight findFlightByCode(String code) {
	        for (Flight flight : flights) {
	            if (flight.getCode().equalsIgnoreCase(code)) {
	                return flight;
	            }
	        }
	        return null;
	    }

	    private void bookFlight() {
	        System.out.print("Enter flight code: ");
	        String code = scanner.nextLine().trim();

	        Flight flight = findFlightByCode(code);

	        if (flight == null) {
	            System.out.println("Booking failed: Invalid flight code.");
	            return;
	        }

	        System.out.print("Enter passenger name: ");
	        String name = scanner.nextLine().trim();

	        if (name.isEmpty()) {
	            System.out.println("Booking failed: Name cannot be blank.");
	            return;
	        }

	        System.out.print("Enter age: ");
	        int age;

	        try {
	            age = Integer.parseInt(scanner.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Booking failed: Invalid age.");
	            return;
	        }

	        if (age <= 0) {
	            System.out.println("Booking failed: Age must be greater than 0.");
	            return;
	        }

	        System.out.print("Passenger type (Regular/Student): ");
	        String type = scanner.nextLine().trim();

	        Passenger passenger;

	        if (type.equalsIgnoreCase("Regular")) {
	            passenger = new RegularPassenger(name, age);
	        } else if (type.equalsIgnoreCase("Student")) {
	            passenger = new StudentPassenger(name, age);
	        } else {
	            System.out.println(
	                "Booking failed: Enter Regular or Student."
	            );
	            return;
	        }

	        System.out.print("Enter number of tickets: ");
	        int tickets;

	        try {
	            tickets = Integer.parseInt(scanner.nextLine().trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Booking failed: Invalid ticket count.");
	            return;
	        }

	        if (tickets <= 0) {
	            System.out.println(
	                "Booking failed: Tickets must be greater than 0."
	            );
	            return;
	        }

	        if (!flight.hasEnoughSeats(tickets)) {
	            System.out.println(
	                "Booking failed: Not enough seats available."
	            );
	            System.out.println("Available seats remain unchanged.");
	            return;
	        }

	        Booking booking = new Booking(
	            "BK" + bookingNumber, flight, passenger, tickets
	        );

	        flight.reserveSeats(tickets);
	        latestBooking = booking;
	        bookingNumber++;

	        System.out.println("\nBooking confirmed!");
	        latestBooking.displaySummary();
	    }

	    private void displayLatestBooking() {
	        if (latestBooking == null) {
	            System.out.println("No confirmed booking exists yet.");
	        } else {
	            latestBooking.displaySummary();
	        }
	    }
	}

