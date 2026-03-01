package service;

import fileUtils.FileUtils;
import models.Booking;
import models.Tour;
import utilities.Inputter;
import utilities.Validator;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * List of bookings
 */
public class BookingList extends ArrayList<Booking> implements IService<Booking> {

    private TourList tourList;

    public BookingList() {
    }

    // Solve the circle dependency problem
    public void setTourList(TourList tourList) {
        this.tourList = tourList;
    }

    @Override
    public void readFromFile(String fileName) {
        this.clear();
        this.addAll(FileUtils.readFromFile(fileName, (String line) -> {
            // B00002,Binh,T00007,27/02/2026,0869089715
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                return new Booking(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        LocalDate.parse(parts[3].trim(), Validator.FORMATTER),
                        parts[4].trim());
            }
            return null;
        }));
    }

    @Override
    public void saveToFile(String fileName) {
        FileUtils.writeToFile(fileName, this);
    }

    @Override
    public void addNew() {
        System.out.println("--- Add new Booking ---");
        LocalDate today = LocalDate.now();
        String bookingID;
        while (true) {
            bookingID = Inputter.getString("Enter Booking ID (BXXXXX): ", Validator.BOOKING_ID_VALID, false);
            if (this.searchById(bookingID) != null) {
                System.out.println("Booking ID already exists.");
                continue;
            }
            break;
        }

        String fullName = Inputter.getString("Enter Full Name: ", null, false);
        String tourID;
        Tour tour;
        while (true) {
            tourID = Inputter.getString("Enter Tour ID to book (TXXXXX): ", Validator.TOUR_ID_VALID, false);
            tour = tourList.searchById(tourID);
            if (tour == null || tour.isBooking()) {
                System.out.println("Tour not found or already booked.");
                continue;
            }

            if (tour.getDeparture_date().isBefore(today)) {
                System.out.println("Can not book tour has already started.");
                continue;
            }

            break;
        }

        LocalDate bookingDate;
        while (true) {
            bookingDate = Inputter.getLocalDate("Enter Booking Date (dd/MM/yyyy): ", false);
            if (bookingDate.isBefore(today)) {
                System.out.println("Can not book tour in the past.");
                continue;
            }

            if (bookingDate.isAfter(tour.getDeparture_date())) {
                System.out.println("Can not book tour after departure date.");
                continue;
            }
            break;
        }

        String phone = Inputter.getString("Enter Phone Number (10 digits): ", Validator.PHONE_VALID, false);

        Booking booking = new Booking(bookingID, fullName, tourID, bookingDate, phone);
        this.add(booking);
        tour.setBooking(true);

        System.out.println("Added booking: " + booking + " successfully.");
    }

    @Override
    public void update() {
        System.out.println("--- Update Booking ---");

        Booking booking;
        LocalDate today = LocalDate.now();
        while (true) {
            String bookingID = Inputter.getString("Enter Booking ID to update: ", Validator.BOOKING_ID_VALID, false);
            booking = this.searchById(bookingID);
            if (booking == null) {
                System.out.println("This Booking does not exist!");
                continue;
            }
            break;
        }

        String name = Inputter.getString("Enter new Full Name (Enter to skip): ", null, true);
        if (!name.isEmpty()) {
            booking.setFullName(name);
        }

        String phone = Inputter.getString("Enter new Phone Number (Enter to skip): ", Validator.PHONE_VALID, true);
        if (!phone.isEmpty()) {
            booking.setPhone(phone);
        }

        String tourID;
        while (true) {
            tourID = Inputter.getString("Enter new Tour ID to book (TXXXXX) (Enter to skip): ", Validator.TOUR_ID_VALID,
                    true);
            if (tourID.isEmpty() || tourID.equalsIgnoreCase(booking.getTourID())) {
                break;
            }
            Tour newTour = tourList.searchById(tourID);
            if (newTour == null || newTour.isBooking() || newTour.getDeparture_date().isBefore(today)) {
                System.out.println("Tour not found, or already booked, or already started.");
                continue;
            }

            Tour oldTour = tourList.searchById(booking.getTourID());
            if (oldTour != null) {
                oldTour.setBooking(false);
            }
            booking.setTourID(tourID);
            newTour.setBooking(true);
            break;
        }

        LocalDate bookingDate;
        while (true) {
            bookingDate = Inputter.getLocalDate("Enter new Booking Date (dd/MM/yyyy) (Enter to skip): ", true);

            bookingDate = (bookingDate != null) ? bookingDate : booking.getBooking_date();

            Tour currentTour = tourList.searchById(booking.getTourID());
            if (!((bookingDate.isAfter(today) || bookingDate.isEqual(today))
                    && bookingDate.isBefore(currentTour.getDeparture_date()))) {
                System.out.println("Booking date must be before tour departure date and >= today.");
                continue;
            }
            booking.setBooking_date(bookingDate);
            break;
        }

        System.out.println("Booking" + booking + " has been updated successfully.");
    }

    @Override
    public Booking searchById(String id) {
        for (Booking b : this) {
            if (b.getBookingID().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
    }

    public void removeBooking() {
        System.out.println("--- Remove Booking ---");
        Booking booking;
        while (true) {
            String bookingID = Inputter.getString("Enter Booking ID to remove: ", Validator.BOOKING_ID_VALID, false);
            booking = this.searchById(bookingID);
            if (booking == null) {
                System.out.println("This booking does not exist!");
                continue;
            }

            Tour tour = tourList.searchById(booking.getTourID());
            if (tour.getDeparture_date().isBefore(LocalDate.now())) {
                System.out.println("Can not remove tour has already started.");
                continue;
            }
            break;
        }

        // Make sure the tour is not booked
        Tour tour = tourList.searchById(booking.getTourID());
        if (tour != null) {
            tour.setBooking(false);
        }
        this.remove(booking);
        System.out.println("Booking " + booking + " has been removed successfully.");
    }

    public void removeBooking(String bookingID) {
        Booking b = searchById(bookingID);
        if (b != null) {
            this.remove(b);
        }
    }

    public void searchByName() {
        System.out.println("--- Search Bookings ---");
        String name = Inputter.getString("Enter Name to search: ", null, false);
        System.out.println("| BookingID | Full Name       | Tour ID | Booking Date | Phone      |");
        for (Booking b : this) {
            if (b.getFullName().toLowerCase().contains(name.toLowerCase())) {
                System.out.printf("| %-9s | %-15s | %-7s | %-12s | %-10s |\n",
                        b.getBookingID(), b.getFullName(), b.getTourID(),
                        b.getBooking_date().format(Validator.FORMATTER), b.getPhone());
            }
        }
    }
}
