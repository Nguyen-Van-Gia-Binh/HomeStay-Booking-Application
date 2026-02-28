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
        Tour t;
        while (true) {
            tourID = Inputter.getString("Enter Tour ID to book (TXXXXX): ", Validator.TOUR_ID_VALID, false);
            t = tourList.searchById(tourID);
            if (t == null || t.isBooking()) {
                System.out.println("Tour not found or already booked.");
                continue;
            }
            break;
        }

        LocalDate bookingDate;
        while (true) {
            bookingDate = Inputter.getLocalDate("Enter Booking Date (dd/MM/yyyy): ", false);
            LocalDate now = LocalDate.now();
            if (!((bookingDate.isAfter(now) || bookingDate.isEqual(now))
                    && bookingDate.isBefore(t.getDeparture_date()))) {
                System.out.println("Booking date must be before tour departure date and >= today.");
                continue;
            }
            break;
        }

        String phone = Inputter.getString("Enter Phone Number (10 digits): ", Validator.PHONE_VALID, false);

        Booking booking = new Booking(bookingID, fullName, tourID, bookingDate, phone);
        this.add(booking);
        t.setBooking(true);

        System.out.println("Added booking successfully.");
    }

    @Override
    public void update() {
        System.out.println("--- Update Booking ---");
        String bookingID;
        Booking b;
        while (true) {
            bookingID = Inputter.getString("Enter Booking ID to update: ", Validator.BOOKING_ID_VALID, false);
            b = this.searchById(bookingID);
            if (b == null) {
                System.out.println("This Booking does not exist!");
                return;
            }
            break;
        }

        String name = Inputter.getString("Enter new Full Name (Enter to skip): ", null, true);
        if (!name.isEmpty()) {
            b.setFullName(name);
        }

        String phone = Inputter.getString("Enter new Phone Number (Enter to skip): ", Validator.PHONE_VALID, true);
        if (!phone.isEmpty()) {
            b.setPhone(phone);
        }

        String tourID;
        while (true) {
            tourID = Inputter.getString("Enter new Tour ID to book (TXXXXX) (Enter to skip): ", Validator.TOUR_ID_VALID,
                    true);
            if (tourID.isEmpty() || tourID.equalsIgnoreCase(b.getTourID())) {
                break;
            }
            Tour newTour = tourList.searchById(tourID);
            if (newTour == null || newTour.isBooking() || newTour.getDeparture_date().isBefore(LocalDate.now())) {
                System.out.println("Tour not found, already booked, or already started.");
                continue;
            }
            b.setTourID(tourID);
            newTour.setBooking(true);
            Tour oldTour = tourList.searchById(b.getTourID());
            if (oldTour != null) {
                oldTour.setBooking(false);
            }
            break;
        }

        LocalDate bookingDate;
        while (true) {
            bookingDate = Inputter.getLocalDate("Enter new Booking Date (dd/MM/yyyy) (Enter to skip): ", true);
            if (bookingDate == null) {
                break;
            }
            LocalDate now = LocalDate.now();
            Tour currentTour = tourList.searchById(b.getTourID());
            if (!((bookingDate.isAfter(now) || bookingDate.isEqual(now))
                    && bookingDate.isBefore(currentTour.getDeparture_date()))) {
                System.out.println("Booking date must be before tour departure date and >= today.");
                continue;
            }
            b.setBooking_date(bookingDate);
            break;
        }

        System.out.println("Booking updated successfully.");
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
        String bookingID = Inputter.getString("Enter Booking ID to remove: ", Validator.BOOKING_ID_VALID, false);
        Booking b = this.searchById(bookingID);
        if (b == null) {
            System.out.println("This booking does not exist!");
            return;
        }

        // Make sure the tour is not booked
        Tour t = tourList.searchById(b.getTourID());
        if (t != null) {
            t.setBooking(false);
        }
        this.remove(b);
        System.out.println("Booking removed successfully.");
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
