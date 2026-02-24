package Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tour {
    private String tourID;
    private String tourName;
    private String time;
    private double price;
    private String homeID;
    private LocalDate departure_date;
    private LocalDate end_date;
    private int number_Tourist;
    private boolean booking;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Tour() {
    }

    public Tour(String tourID, String tourName, String time, double price, String homeID, LocalDate departure_date,
            LocalDate end_date, int number_Tourist, boolean booking) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.time = time;
        this.price = price;
        this.homeID = homeID;
        this.departure_date = departure_date;
        this.end_date = end_date;
        this.number_Tourist = number_Tourist;
        this.booking = booking;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHomeID() {
        return homeID;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public LocalDate getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(LocalDate departure_date) {
        this.departure_date = departure_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getNumber_Tourist() {
        return number_Tourist;
    }

    public void setNumber_Tourist(int number_Tourist) {
        this.number_Tourist = number_Tourist;
    }

    public boolean isBooking() {
        return booking;
    }

    public void setBooking(boolean booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%f,%s,%s,%s,%d,%b",
                tourID, tourName, time, price, homeID,
                departure_date.format(FORMATTER), end_date.format(FORMATTER),
                number_Tourist, booking);
    }
}
