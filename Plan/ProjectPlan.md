# Kế Hoạch Dự Án: Homestay Booking Management

## Thông Tin Chung

- **Mã dự án**: J1.L.P0034
- **Môn học**: LAB211
- **Loại**: Long Assignment (300 LOCs)
- **Tiêu đề**: Homestay Booking Management

---

## Yêu Cầu Nộp Bài

1. **Source code** - Mã nguồn Java hoàn chỉnh
2. **Diagram** - Sơ đồ lớp (Class Diagram)
3. **Flow-chart** - Lưu đồ thuật toán

---

## Giai Đoạn 1: Phân Tích Yêu Cầu

### Checklist

- [X] Đọc và hiểu tài liệu yêu cầu
- [X] Xác định các entity chính (Homestay, Tour, Booking)
- [X] Xác định các constraint/ràng buộc dữ liệu
- [X] Liệt kê 11 chức năng cần implement
- [ ] Xác định cấu trúc file dữ liệu (Homestays.txt, Tours.txt, Bookings.txt)

### Chi tiết các Entity

#### Homestay

| Thuộc tính    | Mô tả                       |
| --------------- | ----------------------------- |
| homeID          | Mã homestay (format: HS0000) |
| homeName        | Tên homestay                 |
| roomNumber      | Số phòng                    |
| address         | Địa chỉ                    |
| maximumCapacity | Sức chứa tối đa           |

#### Tour

| Thuộc tính   | Mô tả                                   |
| -------------- | ----------------------------------------- |
| tourID         | Mã tour (unique)                         |
| tourName       | Tên tour                                 |
| time           | Thời gian (ví dụ: 3 days 2 nights)     |
| price          | Giá/tourist                              |
| homeID         | Mã homestay (tồn tại trong danh sách) |
| departure_date | Ngày khởi hành (dd/MM/yyyy)            |
| end_date       | Ngày kết thúc (dd/MM/yyyy)             |
| number_Tourist | Số lượng khách                        |
| booking        | Đã đặt hay chưa (TRUE/FALSE)         |

#### Booking

| Thuộc tính | Mô tả                               |
| ------------ | ------------------------------------- |
| bookingID    | Mã booking (format: B00000)          |
| fullName     | Họ tên khách                       |
| tourID       | Mã tour (tồn tại trong danh sách) |
| booking_date | Ngày đặt (< departure_date)        |
| phone        | Số điện thoại (10 chữ số)       |

---

## Giai Đoạn 2: Thiết Kế Kiến Trúc

### Checklist

- [ ] Thiết kế cấu trúc package
- [ ] Thiết kế các class Model
- [ ] Thiết kế các class Service
- [ ] Thiết kế class Controller
- [ ] Thiết kế class Menu/View
- [ ] Thiết kế class FileUtils
- [ ] Thiết kế class Validation/Utilities

### Cấu Trúc Package Đề Xuất

```
src/
├── Models/
│   ├── Homestay.java
│   ├── Tour.java
│   └── Booking.java
├── Service/
│   ├── HomestayService.java
│   ├── TourService.java
│   ├── BookingService.java
│   └── IService.java
├── Controler/
│   └── Controler.java
├── View/
│   └── Menu.java
├── FileUtils/
│   └── FileUtils.java
├── Utilities/
│   ├── Validator.java
│   └── InputUtils.java
└── Main.java
```

---

## Giai Đoạn 3: Implement Source Code

### Checklist - Models (Các lớp dữ liệu)

- [ ] Homestay.java - Thuộc tính, constructor, getter/setter, toString
- [ ] Tour.java - Thuộc tính, constructor, getter/setter, toString
- [ ] Booking.java - Thuộc tính, constructor, getter/setter, toString

### Checklist - FileUtils (Đọc/ghi file)

- [ ] Đọc file Homestays.txt
- [ ] Đọc file Tours.txt
- [ ] Đọc file Bookings.txt
- [ ] Ghi file Tours.txt
- [ ] Ghi file Bookings.txt

### Checklist - Utilities (Validation & Input)

- [ ] Validate tourID unique
- [ ] Validate homeID format (HS0000) và tồn tại
- [ ] Validate date format (dd/MM/yyyy)
- [ ] Validate phone (10 chữ số)
- [ ] Validate bookingID format (B00000)
- [ ] Validate số dương
- [ ] Validate không trống

### Checklist - Services (Logic nghiệp vụ)

#### HomestayService

- [ ] Load danh sách Homestay từ file
- [ ] Tìm Homestay theo ID
- [ ] Lấy danh sách tất cả Homestay

#### TourService

- [ ] Load danh sách Tour từ file
- [ ] Thêm Tour mới (Function 1)
- [ ] Cập nhật Tour (Function 2)
- [ ] Lấy Tour có departure < ngày hiện tại (Function 3)
- [ ] Lưu Tour vào file

#### BookingService

- [ ] Load danh sách Booking từ file
- [ ] Thêm Booking mới (Function 5)
- [ ] Xóa Booking (Function 6)
- [ ] Cập nhật Booking (Function 7)
- [ ] Tìm Booking theo tên (Function 8)
- [ ] Lưu Booking vào file

### Checklist - Controller

- [ ] Khởi tạo các Service
- [ ] Xử lý menu option 1 - Add Tour
- [ ] Xử lý menu option 2 - Update Tour
- [ ] Xử lý menu option 3 - List Tours (departure < today)
- [ ] Xử lý menu option 4 - List Total Booking Amount
- [ ] Xử lý menu option 5 - Add Booking
- [ ] Xử lý menu option 6 - Remove Booking
- [ ] Xử lý menu option 7 - Update Booking
- [ ] Xử lý menu option 8 - Search Booking by name
- [ ] Xử lý menu option 9 - Statistics
- [ ] Xử lý menu option 10 - Quit (save prompt)

### Checklist - View/Menu

- [ ] Hiển thị menu chính
- [ ] Nhận input từ người dùng
- [ ] Hiển thị kết quả dạng bảng
- [ ] Hiển thị thông báo lỗi/thành công

---

## Giai Đoạn 4: Implement 11 Chức Năng

| #  | LOCs | Chức năng                        | Status |
| -- | ---- | ---------------------------------- | ------ |
| 1  | 20   | Auto load data từ file            | [ ]    |
| 2  | 30   | Add new Tour                       | [ ]    |
| 3  | 30   | Update Tour by ID                  | [ ]    |
| 4  | 30   | List Tours (departure < today)     | [ ]    |
| 5  | 30   | List Total Booking Amount          | [ ]    |
| 6  | 20   | Add new Booking                    | [ ]    |
| 7  | 20   | Update Booking by ID               | [ ]    |
| 8  | 20   | Remove Booking by ID               | [ ]    |
| 9  | 20   | Search Booking by name             | [ ]    |
| 10 | 50   | Statistics (tourists per homestay) | [ ]    |
| 11 | 30   | Quit program (save prompt)         | [ ]    |

---

## Giai Đoạn 5: Tạo Tài Liệu

### Checklist - Source Code Document

- [ ] Thêm comment cho các class
- [ ] Thêm Javadoc cho các method quan trọng
- [ ] Đảm bảo code style nhất quán

### Checklist - Class Diagram

- [ ] Vẽ diagram cho Models
- [ ] Vẽ diagram cho Services
- [ ] Vẽ diagram cho Controller
- [ ] Vẽ quan hệ giữa các class
- [ ] Export diagram (PNG/PDF)

### Checklist - Flow-chart

- [ ] Flow-chart cho luồng chính của chương trình
- [ ] Flow-chart cho thêm Tour
- [ ] Flow-chart cho thêm Booking
- [ ] Flow-chart cho tìm kiếm
- [ ] Export flow-chart (PNG/PDF)

---

## Giai Đoạn 6: Testing & Debug

### Checklist

- [ ] Test load data từ file
- [ ] Test thêm Tour (valid/invalid data)
- [ ] Test cập nhật Tour
- [ ] Test thêm Booking
- [ ] Test xóa Booking
- [ ] Test tìm kiếm Booking
- [ ] Test statistics
- [ ] Test save data khi quit
- [ ] Test các edge cases

---

## Giai Đoạn 7: Chuẩn Bị Nộp Bài

### Checklist

- [ ] Review code theo OOP principles
- [ ] Kiểm tra Computational Thinking aspects
- [ ] Đóng gói source code
- [ ] Hoàn thiện Diagram
- [ ] Hoàn thiện Flow-chart
- [ ] Tạo file README hướng dẫn
- [ ] Zip toàn bộ project

---

## Lưu Ý Quan Trọng

> [!IMPORTANT]
>
> - Tuân thủ OOP principles: encapsulation, inheritance, polymorphism, abstraction
> - Áp dụng Computational Thinking: Decomposition, Pattern Recognition, Abstraction, Algorithm
> - Validation input phải tuân thủ chặt chẽ các constraint
> - Hiển thị message phù hợp cho invalid/missing data

> [!WARNING]
>
> - Không tạo tour khi departure_date hoặc end_date nằm trong khoảng của tour khác cùng homeID
> - end_date phải >= departure_date
> - number_Tourist phải < maximumCapacity của Homestay

---

## Timeline Đề Xuất

| Giai đoạn               | Thời gian ước tính |
| ------------------------- | ---------------------- |
| Phân tích yêu cầu     | 1-2 giờ               |
| Thiết kế kiến trúc    | 2-3 giờ               |
| Implement Models          | 2 giờ                 |
| Implement Services        | 4-6 giờ               |
| Implement Controller/View | 3-4 giờ               |
| Testing & Debug           | 2-3 giờ               |
| Tạo Diagram & Flow-chart | 2-3 giờ               |
| Review & Đóng gói      | 1 giờ                 |
| **Tổng cộng**     | **17-24 giờ**   |
