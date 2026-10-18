using CSharp.Data;
using CSharp.DAO;
using CSharp.Entities;
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
// 3. KIỂM THỬ 10 CÂU TRUY VẤN CÁ NHÂN
// ============================================================
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

Console.WriteLine("\n========== KẾT THÚC KIỂM THỬ ==========");
