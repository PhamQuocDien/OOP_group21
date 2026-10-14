# ĐẶC TẢ ĐỒ ÁN
## Hệ thống dịch vụ cứu hộ và sửa chữa thiết bị điện tử

---

## 1. Mô tả đề tài

Hệ thống được xây dựng nhằm quản lý hoạt động **cứu hộ và sửa chữa thiết bị điện tử** tại trung tâm dịch vụ.

Hệ thống quản lý thông tin khách hàng, thiết bị, hợp đồng sửa chữa, công việc sửa chữa, thợ sửa chữa, nhóm thợ, nhân viên và các khoản thanh toán.

Quy trình hoạt động chính:

1. Khách hàng mang thiết bị đến trung tâm để sửa chữa.
2. Nhân viên tiếp nhận thông tin khách hàng và thiết bị.
3. Nhân viên lập hợp đồng sửa chữa.
4. Hợp đồng có thể bao gồm nhiều công việc sửa chữa.
5. Công việc được phân công cho thợ phù hợp với khả năng.
6. Thợ được tổ chức thành các nhóm thợ và mỗi nhóm có một trưởng nhóm.
7. Trong quá trình sửa chữa, trạng thái công việc và hợp đồng được cập nhật.
8. Khách hàng có thể thanh toán một hoặc nhiều lần.
9. Mỗi lần thanh toán được lập thành một phiếu thu và do nhân viên ghi nhận.

---

## 2. Sơ đồ ERD

![Sơ đồ ERD](https://lh3.google.com/u/0/d/1lXFMZlqRxuN4yFlcSGZLlGZiEVhgTTwL=w522-h391-p-k-nu-iv8?auditContext=thumbnail)

**Link sơ đồ ERD:**  
https://lh3.google.com/u/0/d/1lXFMZlqRxuN4yFlcSGZLlGZiEVhgTTwL=w522-h391-p-k-nu-iv8?auditContext=thumbnail

---

## 3. Các thực thể (Entity)

### 3.1. NHOM_THO

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maNhomTho | String | **PK** |
| tenNhom | String | Tên nhóm |
| maTruongNhom | String | **FK → THO.maTho** |

**Ý nghĩa:** Quản lý các nhóm thợ sửa chữa. Mỗi nhóm có một trưởng nhóm.

---

### 3.2. THO

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maTho | String | **PK** |
| tenTho | String | Họ tên thợ |
| phai | String | Giới tính |
| ngaysinh | LocalDate | Ngày sinh |
| sdt | String | Số điện thoại |
| maNhomTho | String | **FK → NHOM_THO.maNhomTho** |

**Ý nghĩa:** Lưu thông tin các thợ sửa chữa và nhóm thợ mà thợ thuộc về.

---

### 3.3. NHOM_CONG_VIEC

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maNhomCV | String | **PK** |
| tenNhom | String | Tên nhóm công việc |
| noidung | String | Nội dung nhóm |

**Ý nghĩa:** Phân loại các công việc sửa chữa thành từng nhóm.

---

### 3.4. CONG_VIEC

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maCV | String | **PK** |
| dongia | double | Đơn giá |
| tenCV | String | Tên công việc |
| noidungCV | String | Nội dung công việc |
| maNhomCV | String | **FK → NHOM_CONG_VIEC.maNhomCV** |

**Ý nghĩa:** Lưu các công việc sửa chữa mà trung tâm cung cấp.

---

### 3.5. KHACH_HANG

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maKH | String | **PK** |
| hoten | String | Họ tên |
| phai | String | Giới tính |
| ngaysinh | LocalDate | Ngày sinh |
| diachi | String | Địa chỉ |
| sdt | String | Số điện thoại |

**Ý nghĩa:** Lưu thông tin khách hàng sử dụng dịch vụ.

---

### 3.6. THIET_BI

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maThietBi | String | **PK** |
| loaiTB | String | Loại thiết bị |
| hangSX | String | Hãng sản xuất |
| model | String | Model |
| tinhtrang | String | Tình trạng thiết bị |
| maKH | String | **FK → KHACH_HANG.maKH** |

**Ý nghĩa:** Lưu các thiết bị thuộc sở hữu của khách hàng và được đưa đến trung tâm sửa chữa.

---

### 3.7. NHAN_VIEN

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maNV | String | **PK** |
| hoten | String | Họ tên |
| phai | String | Giới tính |
| ngaysinh | LocalDate | Ngày sinh |
| diachi | String | Địa chỉ |
| sdt | String | Số điện thoại |
| ChucVu | ChucVu | Chức vụ |

**Ý nghĩa:** Lưu thông tin nhân viên thực hiện các công việc tiếp nhận, lập hợp đồng và lập phiếu thu.

---

### 3.8. HOP_DONG

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| soHD | String | **PK** |
| ngaylap | LocalDate | Ngày lập |
| ngayHenTra | LocalDate | Ngày hẹn trả |
| tinhtrangHD | TrangThaiHopDong | Tình trạng hợp đồng |
| tongtien | double | Tổng tiền |
| maKH | String | **FK → KHACH_HANG.maKH** |
| maNV | String | **FK → NHAN_VIEN.maNV** |
| maThietBi | String | **FK → THIET_BI.maThietBi** |

**Ý nghĩa:** Lưu thông tin hợp đồng sửa chữa giữa khách hàng và trung tâm.

---

### 3.9. PHIEU_THU

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maPhieuThu | String | **PK** |
| soTienTT | double | Số tiền thanh toán |
| ngayTT | LocalDate | Ngày thanh toán |
| PhuongThucTT | PhuongThucThanhToan | Phương thức thanh toán |
| soHD | String | **FK → HOP_DONG.soHD** |
| maNV | String | **FK → NHAN_VIEN.maNV** |

**Ý nghĩa:** Ghi nhận từng lần khách hàng thanh toán cho hợp đồng.

---

## 4. Các thực thể kết hợp

### 4.1. CHI_TIET_HOP_DONG

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| soHD | String | **PK, FK → HOP_DONG.soHD** |
| maCV | String | **PK, FK → CONG_VIEC.maCV** |
| maTho | String | **FK → THO.maTho** |
| trangThaiCV | TrangThaiCongViec | Trạng thái công việc |

**Khóa chính:** `(soHD, maCV)`

**Ý nghĩa:** Cho biết một hợp đồng gồm những công việc nào, công việc được giao cho thợ nào và trạng thái thực hiện ra sao.

---

### 4.2. KHA_NANG_THO

| Thuộc tính | Kiểu dữ liệu | Ghi chú |
|---|---|---|
| maTho | String | **PK, FK → THO.maTho** |
| maCV | String | **PK, FK → CONG_VIEC.maCV** |
| Capdo | CapDo | Cấp độ kỹ năng |

**Khóa chính:** `(maTho, maCV)`

**Ý nghĩa:** Lưu khả năng thực hiện từng công việc của mỗi thợ và cấp độ kỹ năng tương ứng.

---

## 5. Các mối quan hệ (Relationship)

| STT | Thực thể 1 | Quan hệ | Thực thể 2 | Bội số |
|---:|---|---|---|---|
| 1 | NHOM_THO | THUOC_NHOM | THO | 1:N |
| 2 | NHOM_THO | LAM_TRUONG_NHOM | THO | 1:1 |
| 3 | NHOM_CONG_VIEC | BAO_GOM | CONG_VIEC | 1:N |
| 4 | THO | CO_KHA_NANG | CONG_VIEC | N:N |
| 5 | KHACH_HANG | SO_HUU | THIET_BI | 1:N |
| 6 | KHACH_HANG | CO_HOP_DONG | HOP_DONG | 1:N |
| 7 | THIET_BI | DUOC_TIEP_NHAN | HOP_DONG | 1:N |
| 8 | NHAN_VIEN | LAP_HOP_DONG | HOP_DONG | 1:N |
| 9 | HOP_DONG | CO_CONG_VIEC | CONG_VIEC | N:N |
| 10 | THO | DUOC_PHAN_CONG | CHI_TIET_HOP_DONG | 1:N |
| 11 | HOP_DONG | THANH_TOAN | PHIEU_THU | 1:N |
| 12 | NHAN_VIEN | LAP_PHIEU_THU | PHIEU_THU | 1:N |

---

## 6. Mô tả các mối quan hệ

### 6.1. NHOM_THO — THUOC_NHOM — THO
Một nhóm thợ có nhiều thợ. Mỗi thợ thuộc một nhóm thợ.

### 6.2. NHOM_THO — LAM_TRUONG_NHOM — THO
Một nhóm có đúng một trưởng nhóm, và một thợ chỉ làm trưởng tối đa một nhóm.
### 6.3. NHOM_CONG_VIEC — BAO_GOM — CONG_VIEC
Một nhóm công việc có nhiều công việc. Mỗi công việc thuộc một nhóm công việc.

### 6.4. THO — CO_KHA_NANG — CONG_VIEC
Một thợ có thể thực hiện nhiều công việc và một công việc có thể do nhiều thợ thực hiện. Quan hệ được quản lý thông qua `KHA_NANG_THO`.

### 6.5. KHACH_HANG — SO_HUU — THIET_BI
Một khách hàng có thể sở hữu nhiều thiết bị. Mỗi thiết bị thuộc về một khách hàng.

### 6.6. KHACH_HANG — CO_HOP_DONG — HOP_DONG
Một khách hàng có thể có nhiều hợp đồng sửa chữa. Mỗi hợp đồng thuộc về một khách hàng.

### 6.7. THIET_BI — DUOC_TIEP_NHAN — HOP_DONG
Một thiết bị có thể được tiếp nhận nhiều lần thông qua các hợp đồng khác nhau. Mỗi hợp đồng gắn với một thiết bị.

### 6.8. NHAN_VIEN — LAP_HOP_DONG — HOP_DONG
Một nhân viên có thể lập nhiều hợp đồng. Mỗi hợp đồng do một nhân viên lập.

### 6.9. HOP_DONG — CO_CONG_VIEC — CONG_VIEC
Một hợp đồng có thể bao gồm nhiều công việc và một công việc có thể xuất hiện trong nhiều hợp đồng. Quan hệ được quản lý thông qua `CHI_TIET_HOP_DONG`.

### 6.10. THO — DUOC_PHAN_CONG — CHI_TIET_HOP_DONG
Một thợ có thể được phân công nhiều công việc trong các hợp đồng. Mỗi chi tiết hợp đồng được phân công cho một thợ.

### 6.11. HOP_DONG — THANH_TOAN — PHIEU_THU
Một hợp đồng có thể có nhiều lần thanh toán. Mỗi phiếu thu thuộc về một hợp đồng.

### 6.12. NHAN_VIEN — LAP_PHIEU_THU — PHIEU_THU
Một nhân viên có thể lập nhiều phiếu thu. Mỗi phiếu thu do một nhân viên lập.

---

## 7. Các lớp Java

Hệ thống được cài đặt theo hướng đối tượng, trong đó các thực thể và mối liên kết trong mô hình được chuyển thành các lớp Java tương ứng.

### 7.1. Các lớp Entity

- **NhomTho:** `maNhomTho`, `tenNhom`, `maTruongNhom`
- **Tho:** `maTho`, `tenTho`, `phai`, `ngaysinh`, `sdt`, `maNhomTho`
- **NhomCongViec:** `maNhomCV`, `tenNhom`, `noidung`
- **CongViec:** `maCV`, `dongia`, `tenCV`, `noidungCV`, `maNhomCV`
- **KhachHang:** `maKH`, `hoten`, `phai`, `ngaysinh`, `diachi`, `sdt`
- **ThietBi:** `maThietBi`, `loaiTB`, `hangSX`, `model`, `tinhtrang`, `maKH`
- **NhanVien:** `maNV`, `hoten`, `phai`, `ngaysinh`, `diachi`, `sdt`, `ChucVu`
- **HopDong:** `soHD`, `ngaylap`, `ngayHenTra`, `tinhtrangHD`, `tongtien`, `maKH`, `maNV`, `maThietBi`
- **PhieuThu:** `maPhieuThu`, `soTienTT`, `ngayTT`, `PhuongThucTT`, `soHD`, `maNV`
- **ChiTietHopDong:** `soHD`, `maCV`, `maTho`, `trangThaiCV`
- **KhaNangTho:** `maTho`, `maCV`, `Capdo`

### 7.2. Các Enum

- **ChucVu:** `TU_VAN`, `THU_NGAN`
- **TrangThaiHopDong:** `MOI_TIEP_NHAN`, `DANG_SUA_CHUA`, `HOAN_THANH`, `DA_TRA`
- **TrangThaiCongViec:** `CHUA_THUC_HIEN`, `DANG_THUC_HIEN`, `HOAN_THANH`
- **PhuongThucThanhToan:** `TIEN_MAT`, `CHUYEN_KHOAN`
- **CapDo:** `CO_BAN`, `KHA`, `TOT`, `CHUYEN_GIA`

## 8. Kiểu dữ liệu sử dụng

- Mã định danh: `String`
- Số tiền, đơn giá: `double`
- Ngày tháng: `LocalDate`
- Các trạng thái và loại dữ liệu cố định: `enum`
- Quan hệ giữa các đối tượng: tham chiếu trực tiếp đến đối tượng tương ứng.
- Danh sách đối tượng: `List<T>`

---
## 9. Dữ liệu mẫu

### 9.1. Nhóm công việc

- **Nhóm công việc 1:** Mã nhóm `NCV01`; tên nhóm **Sửa chữa điện thoại**; nội dung **Các công việc sửa chữa điện thoại**.
- **Nhóm công việc 2:** Mã nhóm `NCV02`; tên nhóm **Sửa chữa máy tính**; nội dung **Các công việc sửa chữa máy tính**.
- **Nhóm công việc 3:** Mã nhóm `NCV03`; tên nhóm **Bảo trì thiết bị**; nội dung **Các công việc bảo trì và kiểm tra thiết bị**.

### 9.2. Công việc

- **Công việc 1:** Mã công việc `CV01`; tên công việc **Thay màn hình điện thoại**; đơn giá **1.200.000 VNĐ**; nội dung **Thay màn hình bị hỏng**; thuộc nhóm **NCV01 – Sửa chữa điện thoại**.
- **Công việc 2:** Mã công việc `CV02`; tên công việc **Thay pin điện thoại**; đơn giá **500.000 VNĐ**; nội dung **Thay pin mới**; thuộc nhóm **NCV01 – Sửa chữa điện thoại**.
- **Công việc 3:** Mã công việc `CV03`; tên công việc **Sửa chân sạc**; đơn giá **350.000 VNĐ**; nội dung **Sửa hoặc thay chân sạc**; thuộc nhóm **NCV01 – Sửa chữa điện thoại**.
- **Công việc 4:** Mã công việc `CV04`; tên công việc **Cài lại hệ điều hành**; đơn giá **400.000 VNĐ**; nội dung **Cài đặt lại hệ điều hành**; thuộc nhóm **NCV02 – Sửa chữa máy tính**.
- **Công việc 5:** Mã công việc `CV05`; tên công việc **Thay bàn phím laptop**; đơn giá **800.000 VNĐ**; nội dung **Thay bàn phím mới**; thuộc nhóm **NCV02 – Sửa chữa máy tính**.
- **Công việc 6:** Mã công việc `CV06`; tên công việc **Thay ổ cứng SSD**; đơn giá **1.500.000 VNĐ**; nội dung **Thay và nâng cấp SSD**; thuộc nhóm **NCV02 – Sửa chữa máy tính**.
- **Công việc 7:** Mã công việc `CV07`; tên công việc **Vệ sinh thiết bị**; đơn giá **250.000 VNĐ**; nội dung **Vệ sinh và kiểm tra thiết bị**; thuộc nhóm **NCV03 – Bảo trì thiết bị**.
- **Công việc 8:** Mã công việc `CV08`; tên công việc **Kiểm tra tổng quát**; đơn giá **300.000 VNĐ**; nội dung **Kiểm tra tình trạng thiết bị**; thuộc nhóm **NCV03 – Bảo trì thiết bị**.

### 9.3. Khách hàng

- **Khách hàng 1:** Mã khách hàng `KH01`; họ tên **Nguyễn Minh Anh**; giới tính **Nữ**; ngày sinh **10/02/1999**; địa chỉ **Quận 1, TP.HCM**; số điện thoại **0911111111**.
- **Khách hàng 2:** Mã khách hàng `KH02`; họ tên **Trần Quốc Bảo**; giới tính **Nam**; ngày sinh **18/07/1997**; địa chỉ **Quận 3, TP.HCM**; số điện thoại **0911111112**.
- **Khách hàng 3:** Mã khách hàng `KH03`; họ tên **Lê Thị Lan**; giới tính **Nữ**; ngày sinh **25/04/2000**; địa chỉ **Quận 5, TP.HCM**; số điện thoại **0911111113**.
- **Khách hàng 4:** Mã khách hàng `KH04`; họ tên **Phạm Hoàng Nam**; giới tính **Nam**; ngày sinh **08/09/1996**; địa chỉ **Quận 10, TP.HCM**; số điện thoại **0911111114**.
- **Khách hàng 5:** Mã khách hàng `KH05`; họ tên **Đỗ Thu Hà**; giới tính **Nữ**; ngày sinh **05/12/2001**; địa chỉ **Thủ Đức, TP.HCM**; số điện thoại **0911111115**.

### 9.4. Thiết bị

- **Thiết bị 1:** Mã thiết bị `TB01`; loại thiết bị **Điện thoại**; hãng sản xuất **Apple**; model **iPhone 13**; tình trạng **Hỏng màn hình**; thuộc khách hàng **KH01 – Nguyễn Minh Anh**.
- **Thiết bị 2:** Mã thiết bị `TB02`; loại thiết bị **Điện thoại**; hãng sản xuất **Samsung**; model **Galaxy S22**; tình trạng **Pin chai**; thuộc khách hàng **KH01 – Nguyễn Minh Anh**.
- **Thiết bị 3:** Mã thiết bị `TB03`; loại thiết bị **Laptop**; hãng sản xuất **Dell**; model **Inspiron 15**; tình trạng **Không khởi động**; thuộc khách hàng **KH02 – Trần Quốc Bảo**.
- **Thiết bị 4:** Mã thiết bị `TB04`; loại thiết bị **Laptop**; hãng sản xuất **Asus**; model **Vivobook 14**; tình trạng **Hỏng bàn phím**; thuộc khách hàng **KH03 – Lê Thị Lan**.
- **Thiết bị 5:** Mã thiết bị `TB05`; loại thiết bị **Điện thoại**; hãng sản xuất **Xiaomi**; model **Redmi Note 12**; tình trạng **Lỗi chân sạc**; thuộc khách hàng **KH04 – Phạm Hoàng Nam**.
- **Thiết bị 6:** Mã thiết bị `TB06`; loại thiết bị **Laptop**; hãng sản xuất **Lenovo**; model **IdeaPad 3**; tình trạng **Máy chạy chậm**; thuộc khách hàng **KH05 – Đỗ Thu Hà**.

### 9.5. Nhân viên

- **Nhân viên 1:** Mã nhân viên `NV01`; họ tên **Nguyễn Thị Hoa**; giới tính **Nữ**; ngày sinh **12/03/1994**; địa chỉ **Quận 1, TP.HCM**; số điện thoại **0981111111**; chức vụ **Tư vấn**.
- **Nhân viên 2:** Mã nhân viên `NV02`; họ tên **Trần Văn Hùng**; giới tính **Nam**; ngày sinh **20/06/1992**; địa chỉ **Quận 3, TP.HCM**; số điện thoại **0981111112**; chức vụ **Thu ngân**.
- **Nhân viên 3:** Mã nhân viên `NV03`; họ tên **Lê Hoàng Yến**; giới tính **Nữ**; ngày sinh **02/10/1998**; địa chỉ **Quận 10, TP.HCM**; số điện thoại **0981111113**; chức vụ **Tư vấn**.

### 9.6. Hợp đồng

- **Hợp đồng 1:** Số hợp đồng `HD01`; ngày lập **20/09/2026**; ngày hẹn trả **23/09/2026**; tình trạng **Đang sửa chữa**; tổng tiền **1.700.000 VNĐ**; khách hàng **KH01 – Nguyễn Minh Anh**; nhân viên lập hợp đồng **NV01 – Nguyễn Thị Hoa**; thiết bị **TB01 – iPhone 13**.
- **Hợp đồng 2:** Số hợp đồng `HD02`; ngày lập **21/09/2026**; ngày hẹn trả **24/09/2026**; tình trạng **Hoàn thành**; tổng tiền **500.000 VNĐ**; khách hàng **KH01 – Nguyễn Minh Anh**; nhân viên lập hợp đồng **NV01 – Nguyễn Thị Hoa**; thiết bị **TB02 – Galaxy S22**.
- **Hợp đồng 3:** Số hợp đồng `HD03`; ngày lập **22/09/2026**; ngày hẹn trả **26/09/2026**; tình trạng **Đang sửa chữa**; tổng tiền **1.900.000 VNĐ**; khách hàng **KH02 – Trần Quốc Bảo**; nhân viên lập hợp đồng **NV03 – Lê Hoàng Yến**; thiết bị **TB03 – Inspiron 15**.
- **Hợp đồng 4:** Số hợp đồng `HD04`; ngày lập **23/09/2026**; ngày hẹn trả **27/09/2026**; tình trạng **Mới tiếp nhận**; tổng tiền **800.000 VNĐ**; khách hàng **KH03 – Lê Thị Lan**; nhân viên lập hợp đồng **NV01 – Nguyễn Thị Hoa**; thiết bị **TB04 – Vivobook 14**.
- **Hợp đồng 5:** Số hợp đồng `HD05`; ngày lập **24/09/2026**; ngày hẹn trả **26/09/2026**; tình trạng **Hoàn thành**; tổng tiền **350.000 VNĐ**; khách hàng **KH04 – Phạm Hoàng Nam**; nhân viên lập hợp đồng **NV03 – Lê Hoàng Yến**; thiết bị **TB05 – Redmi Note 12**.
- **Hợp đồng 6:** Số hợp đồng `HD06`; ngày lập **25/09/2026**; ngày hẹn trả **29/09/2026**; tình trạng **Đã trả**; tổng tiền **1.750.000 VNĐ**; khách hàng **KH05 – Đỗ Thu Hà**; nhân viên lập hợp đồng **NV01 – Nguyễn Thị Hoa**; thiết bị **TB06 – IdeaPad 3**.

### 9.7. Phiếu thu

- **Phiếu thu 1:** Mã phiếu thu `PT01`; ngày thanh toán **20/09/2026**; số tiền **1.000.000 VNĐ**; phương thức **Tiền mặt**; thuộc hợp đồng **HD01**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.
- **Phiếu thu 2:** Mã phiếu thu `PT02`; ngày thanh toán **22/09/2026**; số tiền **700.000 VNĐ**; phương thức **Chuyển khoản**; thuộc hợp đồng **HD01**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.
- **Phiếu thu 3:** Mã phiếu thu `PT03`; ngày thanh toán **21/09/2026**; số tiền **500.000 VNĐ**; phương thức **Tiền mặt**; thuộc hợp đồng **HD02**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.
- **Phiếu thu 4:** Mã phiếu thu `PT04`; ngày thanh toán **22/09/2026**; số tiền **1.000.000 VNĐ**; phương thức **Chuyển khoản**; thuộc hợp đồng **HD03**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.
- **Phiếu thu 5:** Mã phiếu thu `PT05`; ngày thanh toán **23/09/2026**; số tiền **800.000 VNĐ**; phương thức **Tiền mặt**; thuộc hợp đồng **HD04**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.
- **Phiếu thu 6:** Mã phiếu thu `PT06`; ngày thanh toán **24/09/2026**; số tiền **350.000 VNĐ**; phương thức **Chuyển khoản**; thuộc hợp đồng **HD05**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.
- **Phiếu thu 7:** Mã phiếu thu `PT07`; ngày thanh toán **25/09/2026**; số tiền **1.000.000 VNĐ**; phương thức **Tiền mặt**; thuộc hợp đồng **HD06**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.
- **Phiếu thu 8:** Mã phiếu thu `PT08`; ngày thanh toán **27/09/2026**; số tiền **750.000 VNĐ**; phương thức **Chuyển khoản**; thuộc hợp đồng **HD06**; nhân viên lập phiếu **NV02 – Trần Văn Hùng**.

### 9.8. Khả năng thợ

- **Khả năng 1:** Thợ `T01` thực hiện công việc `CV01` với cấp độ **Chuyên gia**.
- **Khả năng 2:** Thợ `T01` thực hiện công việc `CV02` với cấp độ **Tốt**.
- **Khả năng 3:** Thợ `T01` thực hiện công việc `CV03` với cấp độ **Tốt**.
- **Khả năng 4:** Thợ `T02` thực hiện công việc `CV02` với cấp độ **Chuyên gia**.
- **Khả năng 5:** Thợ `T02` thực hiện công việc `CV03` với cấp độ **Khá**.
- **Khả năng 6:** Thợ `T03` thực hiện công việc `CV04` với cấp độ **Chuyên gia**.
- **Khả năng 7:** Thợ `T03` thực hiện công việc `CV05` với cấp độ **Tốt**.
- **Khả năng 8:** Thợ `T03` thực hiện công việc `CV06` với cấp độ **Chuyên gia**.
- **Khả năng 9:** Thợ `T04` thực hiện công việc `CV07` với cấp độ **Tốt**.
- **Khả năng 10:** Thợ `T04` thực hiện công việc `CV08` với cấp độ **Khá**.
- **Khả năng 11:** Thợ `T01` thực hiện công việc `CV07` với cấp độ **Khá**.
- **Khả năng 12:** Thợ `T03` thực hiện công việc `CV08` với cấp độ **Tốt**.

### 9.9. Chi tiết hợp đồng

- **Chi tiết 1:** Hợp đồng `HD01`; công việc `CV01`; thợ thực hiện `T01`; trạng thái công việc **Hoàn thành**.
- **Chi tiết 2:** Hợp đồng `HD01`; công việc `CV02`; thợ thực hiện `T02`; trạng thái công việc **Đang thực hiện**.
- **Chi tiết 3:** Hợp đồng `HD02`; công việc `CV02`; thợ thực hiện `T02`; trạng thái công việc **Hoàn thành**.
- **Chi tiết 4:** Hợp đồng `HD03`; công việc `CV04`; thợ thực hiện `T03`; trạng thái công việc **Đang thực hiện**.
- **Chi tiết 5:** Hợp đồng `HD03`; công việc `CV06`; thợ thực hiện `T03`; trạng thái công việc **Chưa thực hiện**.
- **Chi tiết 6:** Hợp đồng `HD04`; công việc `CV05`; thợ thực hiện `T03`; trạng thái công việc **Chưa thực hiện**.
- **Chi tiết 7:** Hợp đồng `HD05`; công việc `CV03`; thợ thực hiện `T01`; trạng thái công việc **Hoàn thành**.
- **Chi tiết 8:** Hợp đồng `HD06`; công việc `CV06`; thợ thực hiện `T03`; trạng thái công việc **Hoàn thành**.
- **Chi tiết 9:** Hợp đồng `HD06`; công việc `CV07`; thợ thực hiện `T04`; trạng thái công việc **Hoàn thành**.
### 9.10. Nhóm thợ

- **Nhóm thợ 1:** Mã nhóm thợ `NT01`; tên nhóm thợ **Nhóm sửa chữa điện thoại**; danh sách thợ **T01 - Nguyễn Văn An; T02 - Trần Văn Bình**; trưởng nhóm **T01**.
- **Nhóm thợ 2:** Mã nhóm thợ `NT02`; tên nhóm thợ **Nhóm sửa chữa máy tính**; danh sách thợ **T03 - Lê Văn Cường; T04 - Phạm Văn Dũng**; trưởng nhóm **T04**.

### 9.11. Thợ

- **Thợ 1:** Mã thợ `T01`; họ và tên **Nguyễn Văn An**; giới tính **Nam**; ngày sinh **12/05/1995**; số điện thoại **0901234567**; thuộc nhóm **NT01 - Nhóm sửa chữa điện thoại**.
- **Thợ 2:** Mã thợ `T02`; họ và tên **Trần Văn Bình**; giới tính **Nam**; ngày sinh **20/08/1998**; số điện thoại **0901234568**; thuộc nhóm **NT01 - Nhóm sửa chữa điện thoại**.
- **Thợ 3:** Mã thợ `T03`; họ và tên **Lê Văn Cường**; giới tính **Nam**; ngày sinh **15/03/1997**; số điện thoại **0901234569**; thuộc nhóm **NT02 - Nhóm sửa chữa máy tính**.
- **Thợ 4:** Mã thợ `T04`; họ và tên **Phạm Văn Dũng**; giới tính **Nam**; ngày sinh **10/11/1996**; số điện thoại **0901234570**; thuộc nhóm **NT02 - Nhóm sửa chữa máy tính**.
---
# 10. Các câu truy vấn

## 10.1. Truy vấn khách hàng

1. Lấy danh sách tất cả khách hàng đang lưu trữ trong hệ thống.
2. Tìm kiếm thông tin chi tiết của một khách hàng dựa trên số điện thoại.
3. Tìm danh sách những khách hàng đã lập hợp đồng nhưng chưa thực hiện thanh toán.
4. Thống kê tổng số lượng hợp đồng sửa chữa mà từng khách hàng đã thực hiện tại trung tâm.

## 10.2. Truy vấn thiết bị

5. Lấy danh sách thiết bị thuộc sở hữu của một khách hàng cụ thể dựa vào `maKH`.
6. Lọc danh sách thiết bị theo hãng sản xuất cụ thể.
7. Thống kê tổng số lượng thiết bị mang đến sửa chữa phân loại theo từng loại thiết bị.

## 10.3. Truy vấn hợp đồng sửa chữa

8. Tìm thông tin chi tiết của một hợp đồng theo số hợp đồng.
9. Lọc danh sách các hợp đồng có trạng thái MOI_TIEP_NHAN hoặc DANG_SUA_CHUA
10. Liệt kê các hợp đồng sửa chữa được lập trong một khoảng thời gian cụ thể (từ ngày A đến ngày B).
11. Tìm top 5 hợp đồng sửa chữa có giá trị `tongtien` cao nhất.
12. Thống kê số lượng hợp đồng sửa chữa phân theo từng trạng thái hợp đồng (`tinhtrangHD`).

## 10.4. Truy vấn nhóm công việc và công việc

13. Hiển thị danh sách tất cả các nhóm công việc hiện có trong trung tâm.
14. Lấy thông tin nhóm công việc dựa trên tên nhóm (`tenNhom`).
15. Liệt kê danh sách tất cả các công việc thuộc về một nhóm công việc cụ thể theo `maNhomCV`.
16. Lọc ra danh sách các công việc/dịch vụ có đơn giá (`dongia`) từ 500.000 VNĐ trở lên.
17. Thống kê số lần xuất hiện (tần suất thực hiện) của từng công việc trong tất cả các hợp đồng.
18. Tìm công việc/dịch vụ mang lại tổng doanh thu cao nhất cho trung tâm.

## 10.5. Truy vấn nhân viên

19. Liệt kê danh sách nhân viên theo chức vụ (`ChucVu`) cụ thể (ví dụ: "Tư Vấn", "Thu Ngân").
20. Thống kê tổng số lượng hợp đồng sửa chữa mà từng nhân viên đã tiếp nhận và lập cho khách hàng.
21. Tìm nhân viên đã thực hiện lập nhiều hợp đồng nhất trong tháng.

## 10.6. Truy vấn nhóm thợ

22. Lấy danh sách tất cả các thợ/kỹ thuật viên thuộc về một nhóm thợ theo `maNhomTho`.
23. Hiển thị thông tin thợ đang đóng vai trò là trưởng nhóm (`maTruongNhom`) của từng nhóm thợ.

## 10.7. Truy vấn chi tiết công việc trong hợp đồng

24. Hiển thị tất cả các hạng mục công việc, đơn giá và thợ thực hiện trong một hợp đồng theo `soHD`.
25. Lọc các chi tiết công việc có trạng thái CHUA_THUC_HIEN hoặc DANG_THUC_HIEN
26. Thống kê tổng số hạng mục công việc mà một thợ kỹ thuật (`maTho`) đã/đang thực hiện.

## 10.8. Truy vấn phiếu thu và thanh toán

27. Tìm thông tin phiếu thu thanh toán tương ứng của một hợp đồng dựa theo `soHD`.
28. Liệt kê danh sách các phiếu thu được thanh toán bằng phương thức (`PhuongThucTT`) "Chuyển khoản".
29. Tính tổng doanh thu thu được từ tất cả các phiếu thu trong một khoảng thời gian cụ thể.
30. Thống kê tổng số tiền thu được phân loại theo từng phương thức thanh toán (`PhuongThucTT`).

---

