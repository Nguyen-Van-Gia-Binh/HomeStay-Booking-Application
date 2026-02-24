# Hướng Dẫn Sử Dụng LocalDate (Cho Người Mới)

## 1. LocalDate là gì?

`LocalDate` là class trong Java 8+ dùng để lưu trữ **ngày tháng năm** (không có giờ phút giây).

```
Ví dụ: 30/01/2026 → chỉ có ngày, tháng, năm
```

**Import cần thiết:**
```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
```

---

## 2. Tạo LocalDate

### Cách 1: Lấy ngày hôm nay
```java
LocalDate today = LocalDate.now();
System.out.println(today);  // Output: 2026-01-30
```

### Cách 2: Tạo từ năm, tháng, ngày
```java
LocalDate date = LocalDate.of(2026, 1, 30);
System.out.println(date);  // Output: 2026-01-30
```

### Cách 3: Tạo từ String (parse) ⭐ QUAN TRỌNG
```java
// Định nghĩa format (dd/MM/yyyy = ngày/tháng/năm)
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

// Parse string thành LocalDate
String dateStr = "30/01/2026";
LocalDate date = LocalDate.parse(dateStr, formatter);

System.out.println(date);  // Output: 2026-01-30
```

---

## 3. Chuyển LocalDate thành String

```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

LocalDate date = LocalDate.of(2026, 1, 30);
String dateStr = date.format(formatter);

System.out.println(dateStr);  // Output: 30/01/2026
```

---

## 4. So sánh 2 ngày ⭐ RẤT QUAN TRỌNG

```java
LocalDate date1 = LocalDate.of(2026, 1, 10);  // 10/01/2026
LocalDate date2 = LocalDate.of(2026, 1, 15);  // 15/01/2026

// Kiểm tra date1 < date2 ?
if (date1.isBefore(date2)) {
    System.out.println("date1 nhỏ hơn date2");  ✅ TRUE
}

// Kiểm tra date1 > date2 ?
if (date1.isAfter(date2)) {
    System.out.println("date1 lớn hơn date2");  ❌ FALSE
}

// Kiểm tra date1 == date2 ?
if (date1.isEqual(date2)) {
    System.out.println("date1 bằng date2");  ❌ FALSE
}
```

### So sánh với ngày hôm nay
```java
LocalDate today = LocalDate.now();
LocalDate departureDate = LocalDate.of(2026, 2, 15);

if (departureDate.isBefore(today)) {
    System.out.println("Tour đã khởi hành (ngày departure < hôm nay)");
} else {
    System.out.println("Tour chưa khởi hành");
}
```

---

## 5. Lấy thông tin từ LocalDate

```java
LocalDate date = LocalDate.of(2026, 1, 30);

int day = date.getDayOfMonth();    // 30
int month = date.getMonthValue();  // 1
int year = date.getYear();         // 2026

System.out.println("Ngày: " + day);
System.out.println("Tháng: " + month);
System.out.println("Năm: " + year);
```

---

## 6. Cộng/Trừ ngày

```java
LocalDate today = LocalDate.now();

LocalDate tomorrow = today.plusDays(1);      // Cộng 1 ngày
LocalDate nextWeek = today.plusWeeks(1);     // Cộng 1 tuần
LocalDate nextMonth = today.plusMonths(1);   // Cộng 1 tháng

LocalDate yesterday = today.minusDays(1);    // Trừ 1 ngày
```

---

## 7. Ví Dụ Thực Tế cho Dự Án

### 7.1. Tạo class Tour với LocalDate

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tour {
    private String tourID;
    private String tourName;
    private LocalDate departureDate;
    private LocalDate endDate;
    
    // Formatter dùng chung
    public static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    // Constructor nhận String
    public Tour(String tourID, String tourName, 
                String departureDateStr, String endDateStr) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.departureDate = LocalDate.parse(departureDateStr, FORMATTER);
        this.endDate = LocalDate.parse(endDateStr, FORMATTER);
    }
    
    // Getter trả về LocalDate
    public LocalDate getDepartureDate() {
        return departureDate;
    }
    
    // Getter trả về String (cho file I/O)
    public String getDepartureDateString() {
        return departureDate.format(FORMATTER);
    }
    
    // Kiểm tra tour đã khởi hành chưa
    public boolean hasStarted() {
        return departureDate.isBefore(LocalDate.now());
    }
    
    // Kiểm tra tour chưa khởi hành
    public boolean isUpcoming() {
        return departureDate.isAfter(LocalDate.now());
    }
}
```

### 7.2. Đọc Tour từ file

```java
// Dòng trong file: T00001,TPHCM-Da Lat,3 days 2 nights,300,HS0001,10/01/2026,12/01/2026,5,FALSE
String line = "T00001,TPHCM-Da Lat,3 days 2 nights,300,HS0001,10/01/2026,12/01/2026,5,FALSE";
String[] parts = line.split(",");

String tourID = parts[0];           // T00001
String tourName = parts[1];         // TPHCM-Da Lat
String departureDateStr = parts[5]; // 10/01/2026
String endDateStr = parts[6];       // 12/01/2026

// Parse thành LocalDate
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
LocalDate departureDate = LocalDate.parse(departureDateStr, formatter);
LocalDate endDate = LocalDate.parse(endDateStr, formatter);

System.out.println("Departure: " + departureDate);  // 2026-01-10
System.out.println("End: " + endDate);              // 2026-01-12
```

### 7.3. Lọc tour có departure < today

```java
import java.util.ArrayList;
import java.util.List;

public List<Tour> getToursBeforeToday(List<Tour> allTours) {
    List<Tour> result = new ArrayList<>();
    LocalDate today = LocalDate.now();
    
    for (Tour tour : allTours) {
        if (tour.getDepartureDate().isBefore(today)) {
            result.add(tour);
        }
    }
    
    return result;
}
```

### 7.4. Validate booking_date < departure_date

```java
public boolean isValidBookingDate(LocalDate bookingDate, LocalDate departureDate) {
    // booking phải trước ngày khởi hành
    return bookingDate.isBefore(departureDate);
}

// Sử dụng:
LocalDate bookingDate = LocalDate.parse("05/01/2026", formatter);
LocalDate departureDate = LocalDate.parse("10/01/2026", formatter);

if (isValidBookingDate(bookingDate, departureDate)) {
    System.out.println("Ngày booking hợp lệ!");
} else {
    System.out.println("Lỗi: Ngày booking phải trước ngày khởi hành!");
}
```

### 7.5. Validate ngày nhập từ user

```java
import java.time.format.DateTimeParseException;

public static boolean isValidDateFormat(String dateStr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    try {
        LocalDate.parse(dateStr, formatter);
        return true;  // Parse thành công = format đúng
    } catch (DateTimeParseException e) {
        return false; // Parse thất bại = format sai
    }
}

// Sử dụng:
String input = "30/01/2026";
if (isValidDateFormat(input)) {
    System.out.println("Format đúng!");
} else {
    System.out.println("Format sai! Vui lòng nhập dd/MM/yyyy");
}
```

---

## 8. Lỗi Thường Gặp

### ❌ Lỗi 1: Quên import
```java
// Thiếu import → lỗi "cannot find symbol"
// Giải pháp: Thêm import ở đầu file
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
```

### ❌ Lỗi 2: Sai format pattern
```java
// Format file: 10/01/2026 (ngày/tháng/năm)
// Pattern SAI: "MM/dd/yyyy" → parse thành tháng 10, ngày 1
// Pattern ĐÚNG: "dd/MM/yyyy" → parse thành ngày 10, tháng 1
```

### ❌ Lỗi 3: Dùng == để so sánh
```java
// SAI! Không nên dùng == với object
if (date1 == date2) { ... }

// ĐÚNG! Dùng isEqual()
if (date1.isEqual(date2)) { ... }
```

### ❌ Lỗi 4: Không bắt exception khi parse
```java
// Nếu user nhập sai format, chương trình sẽ crash!
// Giải pháp: Dùng try-catch
try {
    LocalDate date = LocalDate.parse(userInput, formatter);
} catch (DateTimeParseException e) {
    System.out.println("Ngày không hợp lệ!");
}
```

---

## 9. Tóm Tắt Nhanh

| Thao tác | Code |
|----------|------|
| Lấy ngày hôm nay | `LocalDate.now()` |
| Tạo ngày cụ thể | `LocalDate.of(2026, 1, 30)` |
| Parse từ String | `LocalDate.parse("30/01/2026", formatter)` |
| Format thành String | `date.format(formatter)` |
| So sánh < | `date1.isBefore(date2)` |
| So sánh > | `date1.isAfter(date2)` |
| So sánh = | `date1.isEqual(date2)` |
| Cộng ngày | `date.plusDays(5)` |
| Trừ ngày | `date.minusDays(5)` |

---

## 10. DateTimeFormatter Pattern

| Pattern | Ý nghĩa | Ví dụ |
|---------|---------|-------|
| `dd` | Ngày (2 chữ số) | 01, 30 |
| `MM` | Tháng (2 chữ số) | 01, 12 |
| `yyyy` | Năm (4 chữ số) | 2026 |
| `dd/MM/yyyy` | Ngày/Tháng/Năm | 30/01/2026 |
| `yyyy-MM-dd` | ISO format | 2026-01-30 |
