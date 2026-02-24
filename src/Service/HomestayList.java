package Service;

import FileUtils.FileUtils;
import Models.Homestay;

import java.util.ArrayList;

public class HomestayList extends ArrayList<Homestay> {

    public void loadFromFile(String fileName) {
        this.clear();
        this.addAll(FileUtils.readFromFile(fileName, line -> {
            String[] parts = line.replace("\t", "-").split("-");
            if (parts.length >= 5) {
                String homeID = parts[0].trim();
                String homeName = parts[1].trim();
                String roomNumber = parts[2].trim();
                int capacity = Integer.parseInt(parts[parts.length - 1].trim());

                StringBuilder addressBuilder = new StringBuilder();
                for (int i = 3; i < parts.length - 1; i++) {
                    addressBuilder.append(parts[i].trim());
                    if (i < parts.length - 2) {
                        addressBuilder.append("-");
                    }
                }
                return new Homestay(
                        homeID,
                        homeName,
                        roomNumber,
                        addressBuilder.toString(),
                        capacity);
            }
            return null;
        }));
    }

    public Homestay searchByID(String homeID) {
        for (Homestay h : this) {
            if (h.getHomeID().equalsIgnoreCase(homeID)) {
                return h;
            }
        }
        return null;
    }
}
