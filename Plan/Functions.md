# Mô Tả 11 Chức Năng Chi Tiết

## 1. Automatic Data Loading (20 LOCs)

**Mô tả**: Khi khởi động, chương trình tải dữ liệu từ các file bắt buộc.

**Input**: Không có

**Output**: 
- Thành công: Load dữ liệu vào memory
- Thất bại: Hiển thị lỗi và thoát chương trình

**Luồng xử lý**:
1. Kiểm tra file Homestays.txt tồn tại
2. Kiểm tra file Tours.txt tồn tại
3. Đọc và parse dữ liệu Homestay
4. Đọc và parse dữ liệu Tour
5. Đọc Bookings.txt (nếu có)

---

## 2. Add New Tour (30 LOCs)

**Mô tả**: Thêm tour mới vào hệ thống.

**Input**: tourID, tourName, time, price, homeID, departure_date, end_date, number_Tourist

**Output**: 
- Thành công: "Tour added successfully!"
- Thất bại: Message lỗi tương ứng

**Validation**:
- [ ] tourID unique
- [ ] tourName not empty
- [ ] time not empty
- [ ] price > 0
- [ ] homeID exists in Homestay list
- [ ] departure_date valid format
- [ ] end_date valid format và >= departure_date
- [ ] number_Tourist > 0 và < maximumCapacity
- [ ] Không trùng ngày với tour khác cùng homeID

---

## 3. Update Tour by ID (30 LOCs)

**Mô tả**: Cập nhật thông tin tour theo tourID.

**Input**: tourID, các field muốn update

**Output**:
- Tour không tồn tại: "This tour does not exist!"
- Thành công: "Tour updated successfully!"

**Logic**:
- Input rỗng = skip update field đó
- Cho phép update: tourName, time, price, homeID, departure_date, end_date, number_Tourist, booking

---

## 4. List Tours with Departure < Today (30 LOCs)

**Mô tả**: Liệt kê các tour có ngày khởi hành trước ngày hiện tại.

**Input**: Không có

**Output**: Bảng danh sách tour (hoặc "No tours found")

**Format bảng**:
```
| TourID | TourName | Time | Price | HomeID | Departure | End | Tourists | Booking |
|--------|----------|------|-------|--------|-----------|-----|----------|---------|
```

---

## 5. List Total Booking Amount (30 LOCs)

**Mô tả**: Liệt kê tổng số tiền booking cho các tour có departure > today.

**Input**: Không có

**Output**: Bảng sắp xếp giảm dần theo Total Amount

**Tính toán**:
```
Total Amount = price × number_Tourist (cho các tour đã có booking = TRUE)
```

---

## 6. Add New Booking (20 LOCs)

**Mô tả**: Thêm booking mới.

**Input**: bookingID, fullName, tourID, booking_date, phone

**Validation**:
- [ ] bookingID format B00000 và unique
- [ ] fullName not empty
- [ ] tourID exists
- [ ] booking_date < departure_date của tour
- [ ] phone 10 digits

**Side effect**: Update tour.booking = TRUE

---

## 7. Update Booking by ID (20 LOCs)

**Mô tả**: Cập nhật thông tin booking.

**Input**: bookingID, các field muốn update

**Output**:
- Không tồn tại: "This Booking does not exist!"
- Thành công: "Booking updated successfully!"

**Logic**: Input rỗng = skip update

---

## 8. Remove Booking by ID (20 LOCs)

**Mô tả**: Xóa booking theo ID.

**Input**: bookingID

**Output**:
- Không tồn tại: "This booking does not exist!"
- Thành công: "Booking removed successfully!"

**Side effect**: 
- Tour tương ứng có thể update lại booking và number_Tourist

---

## 9. Search Booking by Name (20 LOCs)

**Mô tả**: Tìm kiếm booking theo tên đầy đủ hoặc một phần.

**Input**: fullName hoặc partial fullName

**Output**: Bảng danh sách booking matching

**Logic**: Case-insensitive search, contains match

---

## 10. Statistics - Tourists per Homestay (50 LOCs)

**Mô tả**: Thống kê tổng số khách du lịch đã đặt theo từng homestay.

**Input**: Không có

**Output**: Bảng (HomeName, Number_Tourist)

**Tính toán**:
```
Với mỗi Homestay:
    Tổng = SUM(number_Tourist) của tất cả Tour 
           có booking = TRUE và homeID = homestay.homeID
```

---

## 11. Quit Program with Save Prompt (30 LOCs)

**Mô tả**: Thoát chương trình, hỏi lưu nếu có thay đổi.

**Input**: Không có (hoặc Y/N để xác nhận save)

**Luồng xử lý**:
1. Kiểm tra có thay đổi dữ liệu không
2. Nếu có: Hỏi "Do you want to save changes? (Y/N)"
3. Nếu Y: Lưu Tours.txt và Bookings.txt
4. Thoát chương trình

**Side effect**: Ghi file nếu user đồng ý
