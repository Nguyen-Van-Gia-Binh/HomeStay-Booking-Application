# Cấu Trúc Dự Án Đề Xuất

## Package Structure

```
src/
├── Models/                    # Data classes
│   ├── Homestay.java         # Homestay entity
│   ├── Tour.java             # Tour entity
│   └── Booking.java          # Booking entity
│
├── Service/                   # Business logic
│   ├── IService.java         # Interface chung
│   ├── HomestayService.java  # Xử lý Homestay
│   ├── TourService.java      # Xử lý Tour
│   └── BookingService.java   # Xử lý Booking
│
├── Controler/                # Controller layer
│   └── Controler.java        # Điều phối logic
│
├── View/                     # User interface
│   └── Menu.java             # Hiển thị menu và output
│
├── FileUtils/                # File operations
│   └── FileUtils.java        # Đọc/ghi file
│
├── Utilities/                # Helper classes
│   ├── Validator.java        # Validation logic
│   └── InputUtils.java       # Input handling
│
└── Main.java                 # Entry point

Data/
├── Homestays.txt             # Dữ liệu homestay
├── Tours.txt                 # Dữ liệu tour
└── Bookings.txt              # Dữ liệu booking (generated)

Plan/                         # Thư mục tài liệu
├── ProjectPlan.md            # Kế hoạch dự án
├── Constraints.md            # Ràng buộc dữ liệu
├── Functions.md              # Mô tả chức năng
├── ProjectStructure.md       # Cấu trúc dự án (file này)
├── ClassDiagram.md           # Mô tả class diagram
└── FlowChart.md              # Mô tả flowchart
```

---

## Class Design

### Models

#### Homestay.java
```java
public class Homestay {
    private String homeID;
    private String homeName;
    private int roomNumber;
    private String address;
    private int maximumCapacity;
    
    // Constructor, Getters, Setters, toString
}
```

#### Tour.java
```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tour {
    private String tourID;
    private String tourName;
    private String time;
    private int price;
    private String homeID;
    private LocalDate departureDate;    // ✅ Dùng LocalDate
    private LocalDate endDate;           // ✅ Dùng LocalDate
    private int numberTourist;
    private boolean booking;
    
    // Date formatter cho đọc/ghi file
    public static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    // Constructor, Getters, Setters, toString
    
    // Helper methods cho File I/O
    public String getDepartureDateString() {
        return departureDate.format(DATE_FORMATTER);
    }
    
    public String getEndDateString() {
        return endDate.format(DATE_FORMATTER);
    }
    
    public void setDepartureDate(String dateStr) {
        this.departureDate = LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    public void setEndDate(String dateStr) {
        this.endDate = LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    // So sánh ngày
    public boolean isDepartureBeforeToday() {
        return departureDate.isBefore(LocalDate.now());
    }
    
    public boolean isDepartureAfterToday() {
        return departureDate.isAfter(LocalDate.now());
    }
}
```

#### Booking.java
```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Booking {
    private String bookingID;
    private String fullName;
    private String tourID;
    private LocalDate bookingDate;       // ✅ Dùng LocalDate
    private String phone;
    
    // Date formatter (dùng chung với Tour)
    public static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    // Constructor, Getters, Setters, toString
    
    // Helper methods cho File I/O
    public String getBookingDateString() {
        return bookingDate.format(DATE_FORMATTER);
    }
    
    public void setBookingDate(String dateStr) {
        this.bookingDate = LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    // Kiểm tra booking date < departure date của tour
    public boolean isValidBookingDate(LocalDate tourDepartureDate) {
        return bookingDate.isBefore(tourDepartureDate);
    }
}
```

---

### Services

#### IService.java (Interface)
```java
public interface IService<T> {
    void add(T item);
    void update(T item);
    void delete(String id);
    T findById(String id);
    List<T> getAll();
}
```

#### HomestayService.java
- `loadFromFile()` - Đọc Homestays.txt
- `findById(String homeID)` - Tìm homestay
- `getAllHomestays()` - Lấy danh sách

#### TourService.java
- `loadFromFile()` / `saveToFile()` - I/O file
- `addTour(Tour tour)` - Thêm tour
- `updateTour(Tour tour)` - Cập nhật
- `findById(String tourID)` - Tìm tour
- `getToursBeforeToday()` - Lọc tour có departure < today
- `getToursAfterToday()` - Lọc tour có departure > today
- `isDateConflict(Tour newTour)` - Kiểm tra trùng ngày

#### BookingService.java
- `loadFromFile()` / `saveToFile()` - I/O file
- `addBooking(Booking booking)` - Thêm booking
- `updateBooking(Booking booking)` - Cập nhật
- `deleteBooking(String bookingID)` - Xóa
- `findByName(String name)` - Tìm theo tên
- `getStatistics()` - Thống kê

---

### Controller

#### Controler.java
```java
public class Controler {
    private HomestayService homestayService;
    private TourService tourService;
    private BookingService bookingService;
    private Menu menu;
    
    public void run() {
        // Main application loop
        loadData();
        while (true) {
            int choice = menu.displayMenu();
            switch (choice) {
                case 1: addTour(); break;
                case 2: updateTour(); break;
                // ... other cases
                case 10: quit(); return;
            }
        }
    }
}
```

---

### View

#### Menu.java
```java
public class Menu {
    public int displayMenu();
    public void displayTours(List<Tour> tours);
    public void displayBookings(List<Booking> bookings);
    public void displayMessage(String message);
    public void displayStatistics(Map<String, Integer> stats);
}
```

---

### Utilities

#### Validator.java
```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static boolean isValidHomeID(String homeID);
    public static boolean isValidBookingID(String bookingID);
    public static boolean isValidPhone(String phone);
    public static boolean isPositiveNumber(int number);
    public static boolean isNotEmpty(String value);
    
    // Validate và parse date
    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    // So sánh ngày
    public static boolean isDateBefore(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2);
    }
    
    public static boolean isDateAfterOrEqual(LocalDate date1, LocalDate date2) {
        return !date1.isBefore(date2);
    }
}
```

#### InputUtils.java
```java
public class InputUtils {
    public static String getString(String prompt);
    public static int getInt(String prompt);
    public static boolean getYesNo(String prompt);
}
```
