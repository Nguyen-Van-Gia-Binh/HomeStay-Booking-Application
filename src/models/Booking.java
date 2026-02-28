package models;

import utilities.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String bookingID;
    private String fullName;
    private String tourID;
    private LocalDate booking_date;
    private String phone;

    public Booking() {
    }

    public Booking(String bookingID, String fullName, String tourID, LocalDate booking_date, String phone) {
        this.bookingID = bookingID;
        this.fullName = fullName;
        this.tourID = tourID;
        this.booking_date = booking_date;
        this.phone = phone;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public LocalDate getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(LocalDate booking_date) {
        this.booking_date = booking_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",
                bookingID, fullName, tourID,
                booking_date.format(Validator.FORMATTER), phone);
    }
}
