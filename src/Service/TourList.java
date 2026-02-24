package Service;

import FileUtils.FileUtils;
import Models.Tour;

import java.time.LocalDate;
import java.util.ArrayList;

public class TourList extends ArrayList<Tour> implements IService<Tour> {

    public void loadFromFile(String fileName) {
        this.clear();
        this.addAll(FileUtils.readFromFile(fileName, line -> {
            String[] parts = line.split(",");
            if (parts.length >= 9) {
                return new Tour(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        Double.parseDouble(parts[3].trim()),
                        parts[4].trim(),
                        LocalDate.parse(parts[5].trim(), Tour.FORMATTER),
                        LocalDate.parse(parts[6].trim(), Tour.FORMATTER),
                        Integer.parseInt(parts[7].trim()),
                        Boolean.parseBoolean(parts[8].trim()));
            }
            return null;
        }));
    }

    public void saveToFile(String fileName) {
        FileUtils.writeToFile(fileName, this);
    }

    @Override
    public void addNew(Tour tour) {
        this.add(tour);
    }

    @Override
    public void update(Tour tour) {
        Tour existingTour = searchByID(tour.getTourID());
        if (existingTour != null) {
            int index = this.indexOf(existingTour);
            this.set(index, tour);
        }
    }

    public Tour searchByID(String tourID) {
        for (Tour t : this) {
            if (t.getTourID().equalsIgnoreCase(tourID)) {
                return t;
            }
        }
        return null;
    }
}
