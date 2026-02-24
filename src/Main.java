
import Controler.Controler;
import View.Menu;
import Utilities.Inputter;

public class Main {
    public static void main(String[] args) {
        Controler controler = new Controler();
        Menu menu = new Menu();
        String[] options = {
                "Add new Tour",
                "Update Tour by ID",
                "List Tours with departure dates earlier than the current date",
                "List total Booking amount for tours with departure > today",
                "Add new Booking",
                "Remove Booking by ID",
                "Update Booking by ID",
                "Search Booking by name",
                "Statistics on tourists per homestay",
                "Quit"
        };

        int choice;
        do {
            choice = menu.getChoice(options);
            switch (choice) {
                case 1:
                    controler.addTour();
                    break;
                case 2:
                    controler.updateTour();
                    break;
                case 3:
                    controler.listToursEarlierThanCurrentDate();
                    break;
                case 4:
                    controler.listTotalBookingAmountLaterThanCurrentDate();
                    break;
                case 5:
                    controler.addBooking();
                    break;
                case 6:
                    controler.removeBooking();
                    break;
                case 7:
                    controler.updateBooking();
                    break;
                case 8:
                    controler.searchBookingByName();
                    break;
                case 9:
                    controler.statisticsTourists();
                    break;
                case 10:
                    if (Inputter.getYesNo("Do you want to save data before quitting?")) {
                        controler.saveData();
                    }
                    System.out.println("Goodbye!");
                    break;
            }
        } while (choice != 10);
    }
}
