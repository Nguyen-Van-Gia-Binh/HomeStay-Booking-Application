package Controler;

import Models.Booking;
import Models.Homestay;
import Models.Tour;
import Service.*;
import Utilities.*;

import java.time.LocalDate;

public class Controler {
    private TourList tourList;
    private BookingList bookingList;
    private HomestayList homestayList;

    public Controler() {
        this.tourList = new TourList();
        this.bookingList = new BookingList();
        this.homestayList = new HomestayList();
        loadData();
    }

    private void loadData() {
        homestayList.loadFromFile("Data/Homestays.txt");
        tourList.loadFromFile("Data/Tours.txt");
        bookingList.loadFromFile("Data/Bookings.txt");
        System.out.println("Data loaded successfully!");
    }

    public void saveData() {
        tourList.saveToFile("Data/Tours.txt");
        bookingList.saveToFile("Data/Bookings.txt");
        System.out.println("Data saved successfully!");
    }

    public void addTour() {
        do {
            System.out.println("--- Add new Tour ---");
            String tourID = Inputter.getString("Enter Tour ID (TXXXXX): ", "^T\\d{5}$", false);
            if (tourList.searchByID(tourID) != null) {
                System.out.println("Tour ID already exists.");
                continue;
            }
            String tourName = Inputter.getString("Enter Tour Name: ", null, false);
            String time = Inputter.getString("Enter Time: ", null, false);
            double price = Inputter.getDouble("Enter Price: ");
            String homeID = Inputter.getString("Enter Home ID (HSXXXX): ", Validator.HOME_ID_VALID, false);
            Homestay homestay = homestayList.searchByID(homeID);
            if (homestay == null) {
                System.out.println("Homestay not found.");
                continue;
            }
            LocalDate departureDate, endDate;
            while (true) {
                departureDate = Inputter.getLocalDate("Enter Departure Date (dd/MM/yyyy): ", false);
                endDate = Inputter.getLocalDate("Enter End Date (dd/MM/yyyy): ", false);
                if (endDate.isBefore(departureDate)) {
                    System.out.println("End date must be >= departure date.");
                    continue;
                }
                boolean overlap = false;
                for (Tour t : tourList) {
                    if (t.getHomeID().equalsIgnoreCase(homeID)) {
                        if (!(endDate.isBefore(t.getDeparture_date()) || departureDate.isAfter(t.getEnd_date()))) {
                            overlap = true;
                            break;
                        }
                    }
                }
                if (overlap) {
                    System.out.println("Tour date overlaps with another tour in the same homestay.");
                    continue;
                }
                break;
            }

            int numTourist;
            while (true) {
                numTourist = Inputter.getInt("Enter Number of Tourists: ");
                if (numTourist >= homestay.getMaximumcapacity()) {
                    System.out.println("Number of tourists must be less than maximum capacity ("
                            + homestay.getMaximumcapacity() + ").");
                } else {
                    break;
                }
            }

            Tour tour = new Tour(tourID, tourName, time, price, homeID, departureDate, endDate, numTourist, false);
            tourList.addNew(tour);
            System.out.println("Added tour successfully!");
        } while (Inputter.getYesNo("Do you want to add another tour?"));
    }

    public void updateTour() {
        System.out.println("--- Update Tour ---");
        String tourID = Inputter.getString("Enter Tour ID to update: ", "^T\\d{5}$", false);
        Tour t = tourList.searchByID(tourID);
        if (t == null) {
            System.out.println("Tour not found!");
            return;
        }

        String tourName = Inputter.getString("Enter new Tour Name (Enter to skip): ", null, true);
        if (!tourName.isEmpty())
            t.setTourName(tourName);

        String time = Inputter.getString("Enter new Time (Enter to skip): ", null, true);
        if (!time.isEmpty())
            t.setTime(time);

        String priceStr = Inputter.getString("Enter new Price (Enter to skip): ", null, true);
        if (!priceStr.isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                if (price > 0)
                    t.setPrice(price);
            } catch (NumberFormatException ignored) {
            }
        }

        System.out.println(
                "Note: Tour dates and homestay cannot be easily updated due to constraint checks. Please delete and add a new tour if these need to be changed.");

        tourList.update(t);
        System.out.println("Update successful.");
    }

    public void listToursEarlierThanCurrentDate() {
        System.out.println("--- Tours with departure dates earlier than today ---");
        LocalDate today = LocalDate.now();
        boolean found = false;
        for (Tour t : tourList) {
            if (t.getDeparture_date().isBefore(today)) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found)
            System.out.println("No tours found.");
    }

    public void listTotalBookingAmountLaterThanCurrentDate() {
        System.out.println("--- Total Booking Amount for tours with departure > today ---");
        // We will calculate this based on Bookings and Tours. Wait, the requirement
        // says "List Total Booking amount for tours"
        double total = 0;
        LocalDate today = LocalDate.now();
        for (Tour t : tourList) {
            if (t.getDeparture_date().isAfter(today) && t.isBooking()) {
                total += t.getPrice() * t.getNumber_Tourist(); // Assumption: amount = price * tourists
            }
        }
        System.out.println("Total Amount: " + total);
    }

    public void addBooking() {
        System.out.println("--- Add new Booking ---");
        String bookingID = Inputter.getString("Enter Booking ID (BXXXXX): ", "^B\\d{5}$", false);
        if (bookingList.searchByID(bookingID) != null) {
            System.out.println("Booking ID already exists.");
            return;
        }

        String fullName = Inputter.getString("Enter Full Name: ", null, false);
        String tourID = Inputter.getString("Enter Tour ID to book (TXXXXX): ", "^T\\d{5}$", false);
        Tour t = tourList.searchByID(tourID);
        if (t == null) {
            System.out.println("Tour not found.");
            return;
        }

        LocalDate bookingDate;
        while (true) {
            bookingDate = Inputter.getLocalDate("Enter Booking Date (dd/MM/yyyy): ", false);
            if (!bookingDate.isBefore(t.getDeparture_date())) {
                System.out.println("Booking date must be before tour departure date.");
            } else {
                break;
            }
        }

        String phone = Inputter.getString("Enter Phone Number (10 digits): ", Validator.PHONE_VALID, false);

        Booking booking = new Booking(bookingID, fullName, tourID, bookingDate, phone);
        bookingList.addNew(booking);
        t.setBooking(true); // Mark tour as booked
        tourList.update(t);

        System.out.println("Added booking successfully.");
    }

    public void removeBooking() {
        System.out.println("--- Remove Booking ---");
        String bookingID = Inputter.getString("Enter Booking ID to remove: ", "^B\\d{5}$", false);
        Booking b = bookingList.searchByID(bookingID);
        if (b == null) {
            System.out.println("Booking not found.");
            return;
        }
        bookingList.removeBooking(bookingID);
        System.out.println("Booking removed successfully.");
    }

    public void updateBooking() {
        System.out.println("--- Update Booking ---");
        String bookingID = Inputter.getString("Enter Booking ID to update: ", "^B\\d{5}$", false);
        Booking b = bookingList.searchByID(bookingID);
        if (b == null) {
            System.out.println("Booking not found.");
            return;
        }

        String name = Inputter.getString("Enter new Full Name (Enter to skip): ", null, true);
        if (!name.isEmpty())
            b.setFullName(name);

        String phone = Inputter.getString("Enter new Phone Number (Enter to skip): ", Validator.PHONE_VALID, true);
        if (!phone.isEmpty())
            b.setPhone(phone);

        System.out.println(
                "Note: Tour ID and Booking Date cannot be updated. Please delete and recreate booking if needed.");
        bookingList.update(b);
        System.out.println("Booking updated successfully.");
    }

    public void searchBookingByName() {
        System.out.println("--- Search Booking by Name ---");
        String search = Inputter.getString("Enter name to search: ", null, false);
        java.util.ArrayList<Booking> results = bookingList.searchByName(search);
        if (results.isEmpty()) {
            System.out.println("No booking found.");
        } else {
            for (Booking b : results) {
                System.out.println(b);
            }
        }
    }

    public void statisticsTourists() {
        System.out.println("--- Statistics: Tourists per Homestay ---");
        java.util.Map<String, Integer> homestayTouristCount = new java.util.HashMap<>();

        // Initialize all homestays to 0
        for (Homestay h : homestayList) {
            homestayTouristCount.put(h.getHomeID(), 0);
        }

        // Count tourists based on booked tours
        for (Booking b : bookingList) {
            Tour t = tourList.searchByID(b.getTourID());
            if (t != null) {
                int count = homestayTouristCount.getOrDefault(t.getHomeID(), 0);
                homestayTouristCount.put(t.getHomeID(), count + t.getNumber_Tourist());
            }
        }

        for (Homestay h : homestayList) {
            int count = homestayTouristCount.get(h.getHomeID());
            System.out.printf("Homestay: %s | Tourists: %d\n", h.getHomeName(), count);
        }
    }
}
