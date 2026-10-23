using CSharp.Data;
using CSharp.DAO;
using CSharp.Entities;
using LeNguyenMinhThu.TruyVan;
using PhamQuocDien.TruyVan;

Console.OutputEncoding = System.Text.Encoding.UTF8;

// ============================================================
// 1. KIỂM TRA IN-MEMORY DAO
// ============================================================
Console.WriteLine("========== KIỂM TRA IN-MEMORY DAO ==========");

var dao = new InMemoryDAO<NhomTho>(nhom => nhom.MaNhomTho);
var nhom = new NhomTho("NT01", "Nhom sua chua", "T01");
dao.Add(nhom);

Console.WriteLine("So nhom tho: " + dao.GetAll().Count);
Console.WriteLine("Ten nhom: " + dao.FindById("NT01")?.TenNhom);
Console.WriteLine("Xoa thanh cong: " + dao.RemoveById("NT01"));
Console.WriteLine("So luong sau xoa: " + dao.GetAll().Count);

// ============================================================
// 2. KIỂM TRA DATA SEEDER
// ============================================================
Console.WriteLine("\n=== KIEM TRA DATA SEEDER ===");

var duLieu = DataSeeder.TaoDuLieuMau();

Console.WriteLine("Nhom tho: " + duLieu.NhomThos.Count);
Console.WriteLine("Tho: " + duLieu.Thos.Count);
Console.WriteLine("Nhom cong viec: " + duLieu.NhomCongViecs.Count);
Console.WriteLine("Cong viec: " + duLieu.CongViecs.Count);
Console.WriteLine("Khach hang: " + duLieu.KhachHangs.Count);
Console.WriteLine("Thiet bi: " + duLieu.ThietBis.Count);
Console.WriteLine("Nhan vien: " + duLieu.NhanViens.Count);
Console.WriteLine("Hop dong: " + duLieu.HopDongs.Count);
Console.WriteLine("Chi tiet hop dong: " + duLieu.ChiTietHopDongs.Count);
Console.WriteLine("Phieu thu: " + duLieu.PhieuThus.Count);
Console.WriteLine("Kha nang tho: " + duLieu.KhaNangThos.Count);

// ============================================================
// 3. KIỂM THỬ 10 CÂU TRUY VẤN ĐẦU TIÊN 1-10
// ============================================================
Console.WriteLine("\n========== KIỂM THỬ 10 CÂU TRUY VẤN ĐẦU TIÊN 1-10 ==========");

var truyVanKhachHang = duLieu.KhachHangs.First();
var truyVanThietBi = duLieu.ThietBis.First(tb => !string.IsNullOrWhiteSpace(tb.HangSX));
var truyVanHopDong = duLieu.HopDongs.First();
var ngayLapDauTien = duLieu.HopDongs.Min(hd => hd.Ngaylap);
var ngayLapCuoiCung = duLieu.HopDongs.Max(hd => hd.Ngaylap);

// Câu 1
Console.WriteLine("\n[CÂU 1] Lấy danh sách tất cả khách hàng.");
var q1Thu = TruyVan10CauDau.LayTatCaKhachHang(duLieu);
Console.WriteLine($"Số kết quả: {q1Thu.Count}");
foreach (var item in q1Thu)
{
    Console.WriteLine(
        $"Mã khách hàng: {item.MaKH} | " +
        $"Họ tên: {item.Hoten} | " +
        $"SĐT: {item.Sdt} | " +
        $"Địa chỉ: {item.Diachi}");
}

// Câu 2
Console.WriteLine(
    $"\n[CÂU 2] Tìm khách hàng theo số điện thoại {truyVanKhachHang.Sdt}.");
var q2Thu = TruyVan10CauDau.TimKhachHangTheoSoDienThoai(
    duLieu, truyVanKhachHang.Sdt);
Console.WriteLine($"Số kết quả: {q2Thu.Count}");
foreach (var item in q2Thu)
{
    Console.WriteLine(
        $"Mã khách hàng: {item.MaKH} | " +
        $"Họ tên: {item.Hoten} | " +
        $"SĐT: {item.Sdt}");
}

// Câu 3
Console.WriteLine(
    "\n[CÂU 3] Tìm khách hàng có hợp đồng chưa phát sinh phiếu thu.");
var q3Thu = TruyVan10CauDau.LayKhachHangCoHopDongChuaThanhToan(duLieu);
Console.WriteLine($"Số kết quả: {q3Thu.Count}");
foreach (var item in q3Thu)
{
    Console.WriteLine(
        $"Mã khách hàng: {item.MaKH} | " +
        $"Họ tên: {item.Hoten} | " +
        $"SĐT: {item.Sdt}");
}

// Câu 4
Console.WriteLine(
    "\n[CÂU 4] Đếm số hợp đồng của từng khách hàng.");
var q4Thu = TruyVan10CauDau.DemHopDongTheoKhachHang(duLieu);
Console.WriteLine($"Số kết quả: {q4Thu.Count}");
foreach (var item in q4Thu)
{
    Console.WriteLine(
        $"Mã khách hàng: {item.KhachHang.MaKH} | " +
        $"Họ tên: {item.KhachHang.Hoten} | " +
        $"Số hợp đồng: {item.SoHopDong}");
}

// Câu 5
Console.WriteLine(
    $"\n[CÂU 5] Lấy danh sách thiết bị của khách hàng {truyVanKhachHang.MaKH}.");
var q5Thu = TruyVan10CauDau.LayThietBiTheoKhachHang(
    duLieu, truyVanKhachHang.MaKH);
Console.WriteLine($"Số kết quả: {q5Thu.Count}");
foreach (var item in q5Thu)
{
    Console.WriteLine(
        $"Mã thiết bị: {item.MaThietBi} | " +
        $"Loại: {item.LoaiTB} | " +
        $"Hãng sản xuất: {item.HangSX} | " +
        $"Model: {item.Model} | " +
        $"Tình trạng: {item.Tinhtrang}");
}

// Câu 6
Console.WriteLine(
    $"\n[CÂU 6] Lọc thiết bị theo hãng sản xuất {truyVanThietBi.HangSX}.");
var q6Thu = TruyVan10CauDau.LayThietBiTheoHangSanXuat(
    duLieu, truyVanThietBi.HangSX);
Console.WriteLine($"Số kết quả: {q6Thu.Count}");
foreach (var item in q6Thu)
{
    Console.WriteLine(
        $"Mã thiết bị: {item.MaThietBi} | " +
        $"Loại: {item.LoaiTB} | " +
        $"Hãng sản xuất: {item.HangSX} | " +
        $"Model: {item.Model} | " +
        $"Mã khách hàng: {item.MaKH}");
}

// Câu 7
Console.WriteLine("\n[CÂU 7] Thống kê số thiết bị theo từng loại.");
var q7Thu = TruyVan10CauDau.DemThietBiTheoLoai(duLieu);
Console.WriteLine($"Số loại thiết bị: {q7Thu.Count}");
foreach (var item in q7Thu)
{
    Console.WriteLine(
        $"Loại thiết bị: {item.LoaiThietBi} | " +
        $"Số lượng: {item.SoLuong}");
}

// Câu 8
Console.WriteLine(
    $"\n[CÂU 8] Tìm hợp đồng theo số hợp đồng {truyVanHopDong.SoHD}.");
var q8Thu = TruyVan10CauDau.TimHopDongTheoSoHD(
    duLieu, truyVanHopDong.SoHD);
Console.WriteLine($"Số kết quả: {(q8Thu is null ? 0 : 1)}");
if (q8Thu is not null)
{
    Console.WriteLine(
        $"Số hợp đồng: {q8Thu.SoHD} | " +
        $"Ngày lập: {q8Thu.Ngaylap:dd/MM/yyyy} | " +
        $"Trạng thái: {q8Thu.TinhtrangHD} | " +
        $"Tổng tiền: {q8Thu.Tongtien:N0} đồng | " +
        $"Mã khách hàng: {q8Thu.MaKH}");
}

// Câu 9
Console.WriteLine(
    "\n[CÂU 9] Lấy hợp đồng mới tiếp nhận hoặc đang sửa chữa.");
var q9Thu = TruyVan10CauDau.LayHopDongDangXuLy(duLieu);
Console.WriteLine($"Số kết quả: {q9Thu.Count}");
foreach (var item in q9Thu)
{
    Console.WriteLine(
        $"Số hợp đồng: {item.SoHD} | " +
        $"Ngày lập: {item.Ngaylap:dd/MM/yyyy} | " +
        $"Trạng thái: {item.TinhtrangHD} | " +
        $"Tổng tiền: {item.Tongtien:N0} đồng | " +
        $"Mã khách hàng: {item.MaKH}");
}

// Câu 10
Console.WriteLine(
    $"\n[CÂU 10] Lấy hợp đồng từ {ngayLapDauTien:dd/MM/yyyy} " +
    $"đến {ngayLapCuoiCung:dd/MM/yyyy}.");
var q10Thu = TruyVan10CauDau.LayHopDongTheoKhoangNgay(
    duLieu, ngayLapDauTien, ngayLapCuoiCung);
Console.WriteLine($"Số kết quả: {q10Thu.Count}");
foreach (var item in q10Thu)
{
    Console.WriteLine(
        $"Số hợp đồng: {item.SoHD} | " +
        $"Ngày lập: {item.Ngaylap:dd/MM/yyyy} | " +
        $"Ngày hẹn trả: {item.NgayHenTra:dd/MM/yyyy} | " +
        $"Trạng thái: {item.TinhtrangHD} | " +
        $"Mã khách hàng: {item.MaKH}");
}

Console.WriteLine("\n========== KẾT THÚC KIỂM THỬ ==========");

// ============================================================
// 4. KIỂM THỬ 10 CÂU TRUY VẤN TỪ CÂU 11 ĐẾN CÂU 20
// ============================================================
Console.WriteLine("\n========== KIỂM THỬ 10 CÂU TRUY VẤN TỪ 11 ĐẾN 20 ==========");

// Chuẩn bị dữ liệu mẫu cho các tham số đầu vào
var nhomCVMau = duLieu.NhomCongViecs.First();
var chucVuMau = duLieu.NhanViens.First().ChucVu;

// Câu 11
Console.WriteLine("\n[CÂU 11] Top 5 hợp đồng có tổng tiền cao nhất:");
var q11 = LeNhatNinh.TruyVan.TruyVanCaNhan.LayTop5HopDongTongTienCaoNhat(duLieu);
Console.WriteLine($"Số kết quả: {q11.Count}");
foreach (var item in q11)
{
    Console.WriteLine($"Số HĐ: {item.SoHD} | Ngày lập: {item.Ngaylap:dd/MM/yyyy} | Tổng tiền: {item.Tongtien:N0} VNĐ");
}

// Câu 12
Console.WriteLine("\n[CÂU 12] Thống kê số lượng hợp đồng theo từng trạng thái:");
var q12 = LeNhatNinh.TruyVan.TruyVanCaNhan.DemHopDongTheoTrangThai(duLieu);
Console.WriteLine($"Số kết quả: {q12.Count}");
foreach (var item in q12)
{
    Console.WriteLine($"Trạng thái: {item.TrangThai} | Số lượng: {item.SoLuong}");
}

// Câu 13
Console.WriteLine("\n[CÂU 13] Danh sách tất cả nhóm công việc:");
var q13 = LeNhatNinh.TruyVan.TruyVanCaNhan.LayTatCaNhomCongViec(duLieu);
Console.WriteLine($"Số kết quả: {q13.Count}");
foreach (var item in q13)
{
    Console.WriteLine($"Mã nhóm CV: {item.MaNhomCV} | Tên nhóm: {item.TenNhom}");
}

// Câu 14
Console.WriteLine($"\n[CÂU 14] Tìm nhóm công việc theo tên: '{nhomCVMau.TenNhom}'");
var q14 = LeNhatNinh.TruyVan.TruyVanCaNhan.TimNhomCongViecTheoTen(duLieu, nhomCVMau.TenNhom);
Console.WriteLine($"Số kết quả: {q14.Count}");
foreach (var item in q14)
{
    Console.WriteLine($"Mã nhóm CV: {item.MaNhomCV} | Tên nhóm: {item.TenNhom}");
}

// Câu 15
Console.WriteLine($"\n[CÂU 15] Liệt kê công việc thuộc mã nhóm CV: {nhomCVMau.MaNhomCV}");
var q15 = LeNhatNinh.TruyVan.TruyVanCaNhan.LayCongViecTheoNhom(duLieu, nhomCVMau.MaNhomCV);
Console.WriteLine($"Số kết quả: {q15.Count}");
foreach (var item in q15)
{
    Console.WriteLine($"Mã CV: {item.MaCV} | Tên CV: {item.TenCV} | Đơn giá: {item.Dongia:N0} VNĐ");
}

// Câu 16
Console.WriteLine("\n[CÂU 16] Lọc công việc có đơn giá từ 500.000 VNĐ trở lên:");
var q16 = LeNhatNinh.TruyVan.TruyVanCaNhan.LayCongViecTheoDonGia(duLieu);
Console.WriteLine($"Số kết quả: {q16.Count}");
foreach (var item in q16)
{
    Console.WriteLine($"Mã CV: {item.MaCV} | Tên CV: {item.TenCV} | Đơn giá: {item.Dongia:N0} VNĐ");
}

// Câu 17
Console.WriteLine("\n[CÂU 17] Thống kê số lần thực hiện của từng công việc:");
var q17 = LeNhatNinh.TruyVan.TruyVanCaNhan.DemSoLanThucHienCuaTungCongViec(duLieu);
Console.WriteLine($"Số kết quả: {q17.Count}");
foreach (var item in q17)
{
    Console.WriteLine($"Mã CV: {item.CongViec.MaCV} | Tên CV: {item.CongViec.TenCV} | Số lần thực hiện: {item.SoLanThucHien}");
}

// Câu 18
Console.WriteLine("\n[CÂU 18] Công việc mang lại tổng doanh thu cao nhất:");
var q18 = LeNhatNinh.TruyVan.TruyVanCaNhan.TimCongViecCoDoanhThuCaoNhat(duLieu);
if (q18 != null)
{
    Console.WriteLine($"Mã CV: {q18.MaCV} | Tên CV: {q18.TenCV} | Đơn giá: {q18.Dongia:N0} VNĐ");
}
else
{
    Console.WriteLine("Không tìm thấy công việc nào.");
}

// Câu 19
Console.WriteLine($"\n[CÂU 19] Liệt kê nhân viên theo chức vụ: {chucVuMau}");
var q19 = LeNhatNinh.TruyVan.TruyVanCaNhan.LayNhanVienTheoChucVu(duLieu, chucVuMau);
Console.WriteLine($"Số kết quả: {q19.Count}");
foreach (var item in q19)
{
    Console.WriteLine($"Mã NV: {item.MaNV} | Họ tên: {item.Hoten} | Chức vụ: {item.ChucVu}");
}

// Câu 20
Console.WriteLine("\n[CÂU 20] Thống kê số hợp đồng do từng nhân viên lập:");
var q20 = LeNhatNinh.TruyVan.TruyVanCaNhan.DemHopDongTheoNhanVien(duLieu);
Console.WriteLine($"Số kết quả: {q20.Count}");
foreach (var item in q20)
{
    Console.WriteLine($"Mã NV: {item.NhanVien.MaNV} | Họ tên: {item.NhanVien.Hoten} | Số hợp đồng: {item.SoHopDong}");
}

Console.WriteLine("\n========== KẾT THÚC KIỂM THỬ CÂU 11 - 20 ==========");

Console.WriteLine("\n========== KIỂM THỬ 10 CÂU TRUY VẤN CÁ NHÂN ==========");

// Câu 21
Console.WriteLine(
    "\n[CÂU 21] Tìm nhân viên lập nhiều hợp đồng nhất trong tháng 7/2026.");

var q21 = TruyVanCaNhan.TimNhanVienLapNhieuHopDongNhatTheoThang(
    duLieu, 7, 2026);

Console.WriteLine($"Số kết quả: {q21.Count}");

foreach (var item in q21)
{
    Console.WriteLine(
        $"Mã nhân viên: {item.NhanVien.MaNV} | " +
        $"Họ tên: {item.NhanVien.Hoten} | " +
        $"Chức vụ: {item.NhanVien.ChucVu} | " +
        $"Số hợp đồng: {item.SoHopDong}");
}

// Câu 22
var maNhom = duLieu.NhomThos.First().MaNhomTho;

Console.WriteLine($"\n[CÂU 22] Liệt kê các thợ thuộc nhóm {maNhom}.");

var q22 = TruyVanCaNhan.LayDanhSachThoTheoNhom(duLieu, maNhom);

Console.WriteLine($"Số kết quả: {q22.Count}");

foreach (var item in q22)
{
    Console.WriteLine(
        $"Mã thợ: {item.MaTho} | " +
        $"Tên thợ: {item.TenTho} | " +
        $"Giới tính: {item.Phai} | " +
        $"Ngày sinh: {item.Ngaysinh:dd/MM/yyyy} | " +
        $"SĐT: {item.Sdt} | " +
        $"Mã nhóm: {item.MaNhomTho}");
}

// Câu 23
Console.WriteLine("\n[CÂU 23] Tìm trưởng nhóm của tất cả các nhóm thợ.");

var q23 = TruyVanCaNhan.TimTruongNhomCuaTatCaNhom(duLieu);

Console.WriteLine($"Số kết quả: {q23.Count}");

foreach (var item in q23)
{
    Console.WriteLine(
        $"Mã nhóm: {item.MaNhomTho} | " +
        $"Tên nhóm: {item.TenNhom} | " +
        $"Mã trưởng nhóm: {item.TruongNhom.MaTho} | " +
        $"Tên trưởng nhóm: {item.TruongNhom.TenTho} | " +
        $"SĐT: {item.TruongNhom.Sdt}");
}

// Câu 24
var soHD = duLieu.ChiTietHopDongs.First().SoHD;

Console.WriteLine(
    $"\n[CÂU 24] Liệt kê công việc và thợ thực hiện theo hợp đồng {soHD}.");

var q24 = TruyVanCaNhan.LayCongViecVaThoTheoHopDong(duLieu, soHD);

Console.WriteLine($"Số kết quả: {q24.Count}");

foreach (var item in q24)
{
    Console.WriteLine(
        $"Số hợp đồng: {item.ChiTiet.SoHD} | " +
        $"Mã công việc: {item.CongViec.MaCV} | " +
        $"Tên công việc: {item.CongViec.TenCV} | " +
        $"Đơn giá: {item.CongViec.Dongia:N0} đồng | " +
        $"Mã thợ: {item.Tho.MaTho} | " +
        $"Tên thợ: {item.Tho.TenTho} | " +
        $"Trạng thái: {item.ChiTiet.TrangThaiCV}");
}

// Câu 25
Console.WriteLine(
    "\n[CÂU 25] Liệt kê các chi tiết công việc chưa thực hiện hoặc đang thực hiện.");

var q25 = TruyVanCaNhan.LayChiTietChuaHoacDangThucHien(duLieu);

Console.WriteLine($"Số kết quả: {q25.Count}");

foreach (var item in q25)
{
    var congViec = duLieu.CongViecs
        .FirstOrDefault(cv => cv.MaCV == item.MaCV);

    var tho = duLieu.Thos
        .FirstOrDefault(t => t.MaTho == item.MaTho);

    var tenCongViec = congViec?.TenCV ?? "Không tìm thấy";
    var tenTho = tho?.TenTho ?? "Không tìm thấy";

    Console.WriteLine(
        $"Số hợp đồng: {item.SoHD} | " +
        $"Mã công việc: {item.MaCV} | " +
        $"Tên công việc: {tenCongViec} | " +
        $"Mã thợ: {item.MaTho} | " +
        $"Tên thợ: {tenTho} | " +
        $"Trạng thái: {item.TrangThaiCV}");
}

// Câu 26
var maTho = duLieu.Thos.First().MaTho;

Console.WriteLine(
    $"\n[CÂU 26] Đếm số hạng mục đã hoàn thành hoặc đang thực hiện của thợ {maTho}.");

var q26 = TruyVanCaNhan.DemHangMucThoDaHoacDangThucHien(
    duLieu, maTho);

Console.WriteLine($"Số hạng mục: {q26}");

// Câu 27
var soHDThu = duLieu.PhieuThus.First().SoHD;

Console.WriteLine(
    $"\n[CÂU 27] Liệt kê các phiếu thu thuộc hợp đồng {soHDThu}.");

var q27 = TruyVanCaNhan.TimPhieuThuTheoHopDong(duLieu, soHDThu);

Console.WriteLine($"Số kết quả: {q27.Count}");

foreach (var item in q27)
{
    Console.WriteLine(
        $"Mã phiếu thu: {item.MaPhieuThu} | " +
        $"Số hợp đồng: {item.SoHD} | " +
        $"Ngày thanh toán: {item.NgayTT:dd/MM/yyyy} | " +
        $"Số tiền: {item.SoTienTT:N0} đồng | " +
        $"Phương thức: {item.PhuongThucTT} | " +
        $"Mã nhân viên: {item.MaNV}");
}

// Câu 28
Console.WriteLine(
    "\n[CÂU 28] Liệt kê các phiếu thu thanh toán bằng chuyển khoản.");

var q28 = TruyVanCaNhan.LayPhieuThuChuyenKhoan(duLieu);

Console.WriteLine($"Số kết quả: {q28.Count}");

foreach (var item in q28)
{
    Console.WriteLine(
        $"Mã phiếu thu: {item.MaPhieuThu} | " +
        $"Số hợp đồng: {item.SoHD} | " +
        $"Ngày thanh toán: {item.NgayTT:dd/MM/yyyy} | " +
        $"Số tiền: {item.SoTienTT:N0} đồng | " +
        $"Phương thức: {item.PhuongThucTT} | " +
        $"Mã nhân viên: {item.MaNV}");
}

// Câu 29
var tuNgay = duLieu.PhieuThus.Min(p => p.NgayTT);
var denNgay = duLieu.PhieuThus.Max(p => p.NgayTT);

Console.WriteLine(
    $"\n[CÂU 29] Tính tổng doanh thu từ {tuNgay:dd/MM/yyyy} đến {denNgay:dd/MM/yyyy}.");

var q29 = TruyVanCaNhan.TinhDoanhThuTheoKhoangNgay(
    duLieu, tuNgay, denNgay);

Console.WriteLine($"Tổng doanh thu: {q29:N0} đồng");

// Câu 30
Console.WriteLine(
    "\n[CÂU 30] Thống kê tổng số tiền theo từng phương thức thanh toán.");

var q30 = TruyVanCaNhan.TinhTongTienTheoPhuongThuc(duLieu);

Console.WriteLine($"Số nhóm phương thức thanh toán: {q30.Count}");

foreach (var item in q30)
{
    Console.WriteLine(
        $"Phương thức: {item.PhuongThuc} | " +
        $"Tổng tiền: {item.TongTien:N0} đồng");
}
