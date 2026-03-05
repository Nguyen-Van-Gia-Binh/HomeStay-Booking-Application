package service;

import fileUtils.FileUtils;
import models.Homestay;

import java.util.ArrayList;

/**
 * List of homestays
 */
public class HomestayList extends ArrayList<Homestay> {

    public HomestayList() {
    }

    public void loadFromFile(String fileName) {
        this.clear();
        this.addAll(FileUtils.readFromFile(fileName, (String line) -> {
            // HS0004-Moc Chau Hill House-3-Tay Tien Hill, Sub-district 12, Moc Chau, Son La Province-12
            String[] parts = line.split("-");
            if (parts.length >= 5) {
                String homeID = parts[0].trim();
                String homeName = parts[1].trim();
                String roomNumber = parts[2].trim();
                int capacity = Integer.parseInt(parts[parts.length - 1].trim());
                String address = "";
                if (parts.length > 5) {
                    StringBuilder addressBuilder = new StringBuilder();
                    for (int i = 3; i < parts.length - 1; i++) {
                        addressBuilder.append(parts[i]).append("-");
                    }
                    address = addressBuilder.substring(0, addressBuilder.length() - 1);
                } else {
                    address = parts[3].trim();
                }
                return new Homestay(homeID, homeName, roomNumber, address, capacity);
            }
            return null;
        }));
    }

    public Homestay searchById(String homeID) {
        for (Homestay h : this) {
            if (h.getHomeID().equalsIgnoreCase(homeID)) {
                return h;
            }
        }
        return null;
    }
}
