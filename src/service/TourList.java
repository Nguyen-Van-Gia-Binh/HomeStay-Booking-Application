package service;

import fileUtils.FileUtils;
import models.Booking;
import models.Homestay;
import models.Tour;
import utilities.Inputter;
import utilities.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * List of tours
 */
public class TourList extends ArrayList<Tour> implements IService<Tour> {

    private HomestayList homestayList;
    private BookingList bookingList;

    public TourList(HomestayList homestayList, BookingList bookingList) {
        this.homestayList = homestayList;
        this.bookingList = bookingList;
    }

    @Override
    public void readFromFile(String fileName) {
        // Make the clean list
        this.clear();
        // Add new
        this.addAll(FileUtils.readFromFile(fileName, line -> {
            String[] parts = line.split(",");
            // T00008,TPHCM-Nha Trang,6 days 5 nights,600.000000,HS0003,10/03/2026,16/03/2026,10,true
            if (parts.length >= 9) {
                String tourId = parts[0].trim();
                String tourName = parts[1].trim();
                String time =parts[2].trim();
                // Avoid break the program
                double price;
                try {
                    price = Double.parseDouble(parts[3].trim());
                }catch (Exception e){
                    return null;
                }
                String homeId = parts[4].trim();
                // Clean dirty date
                if (homestayList.searchById(homeId) == null){
                    return null;
                }
                LocalDate departureDate = LocalDate.parse(parts[5].trim(), Validator.FORMATTER);
                LocalDate endDate = LocalDate.parse(parts[6].trim(), Validator.FORMATTER);
                int numTourist = Integer.parseInt(parts[7].trim());
                boolean booking = Boolean.parseBoolean(parts[8].trim());
                return new Tour(tourId, tourName, time, price, homeId, departureDate, endDate, numTourist, booking);
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
        System.out.println("--- Add new Tour ---");
        String tourID;
        while (true) {
            tourID = Inputter.getString("Enter Tour ID (TXXXXX): ", Validator.TOUR_ID_VALID, false);
            if (this.searchById(tourID) != null) {
                System.out.println("Tour ID already exists.");
                continue;
            }
            break;
        }

        String tourName = Inputter.getString("Enter Tour Name: ", null, false);
        String time = Inputter.getString("Enter Time: ", null, false);
        double price = Inputter.getDouble("Enter Price: ", false);

        String homeID;
        Homestay homestay;
        while (true) {
            homeID = Inputter.getString("Enter Home ID (HSXXXX): ", Validator.HOME_ID_VALID, false);
            homestay = homestayList.searchById(homeID);
            if (homestay == null) {
                System.out.println("Homestay not found.");
                continue;
            }
            break;
        }

        LocalDate departureDate, endDate;
        while (true) {
            departureDate = Inputter.getLocalDate("Enter Departure Date (dd/MM/yyyy): ", false);
            endDate = Inputter.getLocalDate("Enter End Date (dd/MM/yyyy): ", false);
            if (endDate.isBefore(departureDate)) {
                System.out.println("End date must be >= departure date.");
                continue;
            }
            if (isOverlap(departureDate, endDate, homeID)) {
                System.out.println("Tour date overlaps with another tour in the same homestay.");
                continue;
            }
            break;
        }

        int numTourist;
        while (true) {
            numTourist = Inputter.getInt("Enter Number of Tourists: ", false);
            if (numTourist >= homestay.getMaximumCapacity()) {
                System.out.println("Number of tourists must be less than maximum capacity ("
                        + homestay.getMaximumCapacity() + ").");
                continue;
            }
            break;
        }

        Tour tour = new Tour(tourID, tourName, time, price, homeID, departureDate, endDate, numTourist, false);
        this.add(tour);
        System.out.println("[Success] Tour " + tourID + " has been added successfully.");
    }

    @Override
    public void update() {
        System.out.println("--- Update Tour ---");
        String tourID = Inputter.getString("Enter Tour ID to update: ", Validator.TOUR_ID_VALID, false);
        Tour tour = searchById(tourID);
        if (tour == null) {
            System.out.println("This tour does not exist!");
            return;
        }

        System.out.println("Current Name: " + tour.getTourName());
        String tourName = Inputter.getString("Enter new Tour Name (Enter to skip): ", null, true);
        if (!tourName.isEmpty())
            tour.setTourName(tourName);

        System.out.println("Current Time: " + tour.getTime());
        String time = Inputter.getString("Enter new Time (Enter to skip): ", null, true);
        if (!time.isEmpty())
            tour.setTime(time);

        System.out.println("Current Price: " + tour.getPrice());
        double price = Inputter.getDouble("Enter new Price (Enter (or input 0) to skip): ", true);
        if (price > 0)
            tour.setPrice(price);

        LocalDate departureDate = tour.getDeparture_date();
        LocalDate endDate = tour.getEnd_date();
        String homeID = tour.getHomeID();

        while (true) {
            LocalDate inDep = Inputter.getLocalDate("Enter new Departure Date (dd/MM/yyyy) (Enter to skip): ", true);
            LocalDate inEnd = Inputter.getLocalDate("Enter new End Date (dd/MM/yyyy) (Enter to skip): ", true);
            String inHome = Inputter.getString("Enter new Home ID (Enter to skip): ", Validator.HOME_ID_VALID, true);

            if (inDep == null && inEnd == null && inHome.isEmpty())
                break;

            LocalDate testDep = (inDep != null) ? inDep : departureDate;
            LocalDate testEnd = (inEnd != null) ? inEnd : endDate;
            String testHome = (!inHome.isEmpty()) ? inHome : homeID;

            if (homestayList.searchById(testHome) == null) {
                System.out.println("Homestay not found.");
                continue;
            }

            if (testEnd.isBefore(testDep)) {
                System.out.println("End date must be >= departure date.");
                continue;
            }

            boolean overlap = false;
            for (Tour t : this) {
                if (!t.getTourID().equals(tour.getTourID()) && t.getHomeID().equalsIgnoreCase(testHome)) {
                    if (!(testEnd.isBefore(t.getDeparture_date()) || testDep.isAfter(t.getEnd_date()))) {
                        overlap = true;
                        break;
                    }
                }
            }
            if (overlap) {
                System.out.println("Tour date overlaps with another tour in the same homestay.");
                continue;
            }

            departureDate = testDep;
            endDate = testEnd;
            homeID = testHome;

            tour.setDeparture_date(departureDate);
            tour.setEnd_date(endDate);
            tour.setHomeID(homeID);
            break;
        }

        while (true) {
            System.out.println("Current Number of Tourists: " + tour.getNumber_Tourist());
            int numTourist = Inputter.getInt("Enter new Number of Tourists (Enter (or input 0) to skip): ", true);
            if (numTourist == 0)
                break;

            int capacity = homestayList.searchById(tour.getHomeID()).getMaximumCapacity();
            if (numTourist >= capacity) {
                System.out.println("Number of tourists must be less than maximum capacity (" + capacity + ").");
                continue;
            }
            tour.setNumber_Tourist(numTourist);
            break;
        }

        Boolean booking = Inputter.getBoolean("Enter new Booking status (Enter to skip): ", true);
        if (booking != null) {
            if (!booking) {
                for (Booking b : new ArrayList<>(bookingList)) {
                    if (b.getTourID().equalsIgnoreCase(tour.getTourID())) {
                        bookingList.removeBooking(b.getBookingID());
                    }
                }
            }
            tour.setBooking(booking);
        }

        System.out.println("[Success] Tour updated!");
    }

    @Override
    public Tour searchById(String id) {
        for (Tour t : this) {
            if (t.getTourID().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    public void listToursEarlierThanCurrentDate() {
        System.out.println("--- Tours with departure dates earlier than today ---");
        LocalDate today = LocalDate.now();
        boolean found = false;
        System.out.println("| TourID | Name            | Departure  |");
        for (Tour t : this) {
            if (t.getDeparture_date().isBefore(today)) {
                System.out.printf("| %-6s | %-15s | %-10s |\n", t.getTourID(), t.getTourName(),
                        t.getDeparture_date().format(Validator.FORMATTER));
                found = true;
            }
        }
        if (!found)
            System.out.println("No tours found.");
    }

    // Understand that is the value (price * number_Tourist) of one booking (Remember check).
    // Des list
    public void listTotalBookingAmountLaterThanCurrentDate() {
        System.out.println("--- Tours with departure > today (Sorted by Total Amount Descending) ---");
        LocalDate today = LocalDate.now();

        List<Tour> validTours = new ArrayList<>();
        for (Tour t : this) {
            if (t.getDeparture_date().isAfter(today) && t.isBooking()) {
                validTours.add(t);
            }
        }

        if (validTours.isEmpty()) {
            System.out.println("No tours found.");
            return;
        }

        validTours.sort((t1, t2) -> {
            double amount1 = t1.getPrice() * t1.getNumber_Tourist();
            double amount2 = t2.getPrice() * t2.getNumber_Tourist();
            return Double.compare(amount2, amount1);
        });

        for (Tour t : validTours) {
            double amount = t.getPrice() * t.getNumber_Tourist();
            System.out.printf("Tour ID: %s | Departure: %s | Price: %.2f | Tourists: %d | Total Amount: %.2f\n",
                    t.getTourID(),
                    t.getDeparture_date().format(Validator.FORMATTER),
                    t.getPrice(),
                    t.getNumber_Tourist(),
                    amount);
        }
    }
    public boolean isOverlap(LocalDate departureDate, LocalDate endDate, String homeID) {
        for (Tour t : this) {
            if (t.getHomeID().equalsIgnoreCase(homeID)) {
                // Violate the condition (opposite logic)
                if (!(endDate.isBefore(t.getDeparture_date()) || departureDate.isAfter(t.getEnd_date()))) {
                    return true;
                }
            }
        }
        return false;
    }
}

