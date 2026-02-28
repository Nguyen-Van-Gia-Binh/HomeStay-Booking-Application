package controller;

import models.Booking;
import models.Homestay;
import models.Tour;
import service.BookingList;
import service.HomestayList;
import service.TourList;

import java.util.HashMap;
import java.util.Map;

/**
 * The Controller class is the main class of the application.
 * It controls the flow of the application.
 */
public class Controller {
    private HomestayList homestayList;
    private TourList tourList;
    private BookingList bookingList;

    public Controller() {
        homestayList = new HomestayList();
        bookingList = new BookingList();
        tourList = new TourList(homestayList, bookingList);
        bookingList.setTourList(tourList);
    }


    public void loadData() {
        homestayList.loadFromFile("Data/Homestays.txt");
        tourList.readFromFile("Data/Tours.txt");
        bookingList.readFromFile("Data/Bookings.txt");
    }

    public void saveData() {
        tourList.saveToFile("Data/Tours.txt");
        bookingList.saveToFile("Data/Bookings.txt");
    }

    public void addTour() {
        tourList.addNew();
    }

    public void updateTour() {
        tourList.update();
    }

    public void listToursEarlierThanCurrentDate() {
        tourList.listToursEarlierThanCurrentDate();
    }

    public void listTotalBookingAmountLaterThanCurrentDate() {
        tourList.listTotalBookingAmountLaterThanCurrentDate();
    }

    public void addBooking() {
        bookingList.addNew();
    }

    public void removeBooking() {
        bookingList.removeBooking();
    }

    public void updateBooking() {
        bookingList.update();
    }

    public void searchBookingByName() {
        bookingList.searchByName();
    }

    public void statisticsTourists() {
        System.out.println("--- Statistics: Tourists per Homestay ---");
        Map<String, Integer> homestayTouristCount = new HashMap<>();
        for (Homestay h : homestayList) {
            homestayTouristCount.put(h.getHomeID(), 0);
        }

        for (Booking b : bookingList) {
            Tour t = tourList.searchById(b.getTourID());
            if (t != null) {
                int count = homestayTouristCount.getOrDefault(t.getHomeID(), 0);
                homestayTouristCount.put(t.getHomeID(), count + t.getNumber_Tourist());
            }
        }

        System.out.println("| Homestay Name                       | Tourists |");
        for (Homestay h : homestayList) {
            int count = homestayTouristCount.get(h.getHomeID());
            System.out.printf("| %-35s | %-8d |\n", h.getHomeName(), count);
        }
    }
}
