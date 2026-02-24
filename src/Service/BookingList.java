package Service;

import FileUtils.FileUtils;
import Models.Booking;

import java.time.LocalDate;
import java.util.ArrayList;

public class BookingList extends ArrayList<Booking> implements IService<Booking> {

    /**
     * Load bookings from file
     * 
     * @param fileName
     */
    public void loadFromFile(String fileName) {
        // Clear the list
        this.clear();
        // Add all bookings from file
        this.addAll(FileUtils.readFromFile(fileName, line -> {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                return new Booking(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        LocalDate.parse(parts[3].trim(), Booking.FORMATTER),
                        parts[4].trim());
            }
            return null;
        }));
    }

    /**
     * Save bookings to file
     * 
     * @param fileName
     */
    public void saveToFile(String fileName) {
        FileUtils.writeToFile(fileName, this);
    }

    /**
     * Add a new booking
     * 
     * @param booking
     */
    @Override
    public void addNew(Booking booking) {
        this.add(booking);
    }

    /**
     * Update a booking
     * 
     * @param booking
     */
    @Override
    public void update(Booking booking) {
        // Find the booking with the given ID
        Booking existing = searchByID(booking.getBookingID());
        // Update if found
        if (existing != null) {
            int index = this.indexOf(existing);
            this.set(index, booking);
        }
    }

    /**
     * Search for a booking by ID
     * 
     * @param bookingID
     * @return Booking with the given ID
     */
    public Booking searchByID(String bookingID) {
        // Traverse the list to find the booking with the given ID
        for (Booking b : this) {
            if (b.getBookingID().equalsIgnoreCase(bookingID)) {
                return b;
            }
        }
        return null;
    }

    /**
     * Delete a booking by ID
     * 
     * @param bookingID
     */
    public void removeBooking(String bookingID) {
        // Find the booking with the given ID
        Booking b = searchByID(bookingID);
        // Remove if found
        if (b != null) {
            this.remove(b);
        }
    }

    /**
     * Search for bookings by name
     * 
     * @param name
     * @return ArrayList of bookings with the given name
     */
    public ArrayList<Booking> searchByName(String name) {
        ArrayList<Booking> result = new ArrayList<>();
        // Traverse the list to find the booking with the given name
        for (Booking b : this) {
            if (b.getFullName().toLowerCase().contains(name.toLowerCase())) {
                result.add(b);
            }

        }
        return result;
    }
}
