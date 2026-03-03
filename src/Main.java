
import controller.Controller;
import view.Menu;
import utilities.Inputter;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
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
                    controller.addTour();
                    break;
                case 2:
                    controller.updateTour();
                    break;
                case 3:
                    controller.listToursEarlierThanCurrentDate();
                    break;
                case 4:
                    controller.listTotalBookingAmountLaterThanCurrentDate();
                    break;
                case 5:
                    controller.addBooking();
                    break;
                case 6:
                    controller.removeBooking();
                    break;
                case 7:
                    controller.updateBooking();
                    break;
                case 8:
                    controller.searchBookingByName();
                    break;
                case 9:
                    controller.statisticsTourists();
                    break;
                case 10:
                    if (Inputter.getYesNo("Do you want to save data before quitting?")) {
                        controller.saveData();
                    }
                    System.out.println("Goodbye!");
                    break;
            }
        } while (choice != 10);
    }
}
