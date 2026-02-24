# Ràng Buộc Dữ Liệu (Constraints)

## 1. Ràng Buộc Tour

| Thuộc tính | Ràng buộc |
|------------|-----------|
| tourID | Phải unique |
| tourName | Không được rỗng |
| time | Không được rỗng |
| price | Số nguyên dương |
| homeID | Không rỗng, format "HS0000", phải tồn tại trong danh sách Homestay |
| departure_date | Không rỗng, format "dd/MM/yyyy" |
| end_date | Không rỗng, format "dd/MM/yyyy", phải >= departure_date |
| number_Tourist | Số nguyên dương, < maximumCapacity của Homestay tương ứng |

> [!CAUTION]
> **Ràng buộc đặc biệt về ngày:**
> Không thể tạo tour mà departure_date hoặc end_date nằm trong khoảng [departure_date, end_date] của một tour khác có cùng homeID.

---

## 2. Ràng Buộc Booking

| Thuộc tính | Ràng buộc |
|------------|-----------|
| bookingID | Unique, format "B00000" |
| fullName | Không được rỗng |
| tourID | Phải tồn tại trong danh sách Tours.txt |
| booking_date | Không rỗng, phải < departure_date của tour tương ứng |
| phone | Không rỗng, gồm đúng 10 chữ số |

---

## 3. Date Handling với LocalDate (Java 8+)

### Regex Patterns cho ID/Phone

```java
// HomeID format: HS0000
String HOME_ID_PATTERN = "HS\\d{4}";

// BookingID format: B00000
String BOOKING_ID_PATTERN = "B\\d{5}";

// Phone: 10 digits
String PHONE_PATTERN = "\\d{10}";
```

### Date Validation với LocalDate

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    // Validate date format
    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    // Parse string to LocalDate
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, FORMATTER);
    }
    
    // Format LocalDate to string (cho file I/O)
    public static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
```

### So sánh ngày với LocalDate

```java
LocalDate date1 = LocalDate.parse("10/01/2026", FORMATTER);
LocalDate date2 = LocalDate.parse("15/01/2026", FORMATTER);
LocalDate today = LocalDate.now();

// So sánh
date1.isBefore(date2);      // true - date1 < date2
date1.isAfter(date2);       // false - date1 > date2
date1.isEqual(date2);       // false - date1 == date2
!date1.isAfter(date2);      // true - date1 <= date2
!date1.isBefore(date2);     // false - date1 >= date2

// So sánh với ngày hiện tại
date1.isBefore(today);      // Kiểm tra departure < today
date1.isAfter(today);       // Kiểm tra departure > today
```

---

## 4. Validation Logic

### Kiểm tra Tour trùng ngày

```
Pseudo-code:
FOR each existing tour WITH same homeID:
    IF new_departure_date >= existing_departure_date 
       AND new_departure_date <= existing_end_date:
        REJECT (ngày khởi hành nằm trong tour khác)
    
    IF new_end_date >= existing_departure_date 
       AND new_end_date <= existing_end_date:
        REJECT (ngày kết thúc nằm trong tour khác)
    
    IF new_departure_date <= existing_departure_date 
       AND new_end_date >= existing_end_date:
        REJECT (tour mới bao trọn tour cũ)
```

### Kiểm tra number_Tourist

```
Kiểm tra: number_Tourist < homestay.maximumCapacity
Nếu không thỏa mãn: "Number of tourists exceeds maximum capacity!"
```
