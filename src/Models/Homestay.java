package Models;

public class Homestay {
    private String homeID;
    private String homeName;
    private String roomNumber;
    private String address;
    private int maximumcapacity;

    public Homestay() {
    }

    public Homestay(String homeID, String homeName, String roomNumber, String address, int maximumcapacity) {
        this.homeID = homeID;
        this.homeName = homeName;
        this.roomNumber = roomNumber;
        this.address = address;
        this.maximumcapacity = maximumcapacity;
    }

    public String getHomeID() {
        return homeID;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaximumcapacity() {
        return maximumcapacity;
    }

    public void setMaximumcapacity(int maximumcapacity) {
        this.maximumcapacity = maximumcapacity;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%d",
                homeID, homeName, roomNumber, address, maximumcapacity);
    }
}
