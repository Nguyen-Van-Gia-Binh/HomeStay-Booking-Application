import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SandBox {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate now = LocalDate.now();
        LocalDate dob = LocalDate.of(2006, 07,22);
        String dobCus = "25/05/2006";
        LocalDate dobCustomer = LocalDate.parse(dobCus, formatter);
        System.out.println(dobCustomer.format(formatter));
        System.out.println(now.format(formatter)); // Return String
        System.out.println(dob.format(formatter));

        if (dob.isBefore(now)) {
            System.out.println("Dob is before " + dob.format(formatter));
        }
        if (dob.isAfter(now)){
            System.out.println("Dob is pass");
        }
        if (dob.isEqual(now)){
            System.out.println("Happy birthday !");
        }



        int day = dobCustomer.minusDays(1).getDayOfMonth();
        int month = dobCustomer.getMonthValue();
        int year = dobCustomer.getYear();
        System.out.println(day + "/" + month + "/" + year);

    }
}
