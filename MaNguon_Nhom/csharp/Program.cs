using CSharp.Data;
using CSharp.DAO;
using CSharp.Entities;
using PhamQuocDien.TruyVan;
var dao = new InMemoryDAO<NhomTho>(nhom => nhom.MaNhomTho);
var nhom = new NhomTho("NT01", "Nhom sua chua", "T01");
dao.Add(nhom);

Console.WriteLine("So nhom tho: " + dao.GetAll().Count);
Console.WriteLine("Ten nhom: " + dao.FindById("NT01")?.TenNhom);
Console.WriteLine("Xoa thanh cong: " + dao.RemoveById("NT01"));
Console.WriteLine("So luong sau xoa: " + dao.GetAll().Count);
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
Console.WriteLine("\n=== KIEM THU 10 TRUY VAN CA NHAN ===");
var q21 = TruyVanCaNhan
    .TimNhanVienLapNhieuHopDongNhatTheoThang(duLieu, 7, 2026);
Console.WriteLine($"Cau 21: {q21.Count} ket qua");
var maNhom = duLieu.NhomThos.First().MaNhomTho;
var q22 = TruyVanCaNhan.LayDanhSachThoTheoNhom(duLieu, maNhom);
Console.WriteLine($"Cau 22: {q22.Count} ket qua");
var q23 = TruyVanCaNhan.TimTruongNhomCuaTatCaNhom(duLieu);
Console.WriteLine($"Cau 23: {q23.Count} ket qua");

var soHD = duLieu.ChiTietHopDongs.First().SoHD;
var q24 = TruyVanCaNhan.LayCongViecVaThoTheoHopDong(duLieu, soHD);
Console.WriteLine($"Cau 24: {q24.Count} ket qua");

var q25 = TruyVanCaNhan.LayChiTietChuaHoacDangThucHien(duLieu);
Console.WriteLine($"Cau 25: {q25.Count} ket qua");

var maTho = duLieu.Thos.First().MaTho;
var q26 = TruyVanCaNhan.DemHangMucThoDaHoacDangThucHien(duLieu, maTho);
Console.WriteLine($"Cau 26: {q26} ket qua");

var soHDThu = duLieu.PhieuThus.First().SoHD;
var q27 = TruyVanCaNhan.TimPhieuThuTheoHopDong(duLieu, soHDThu);
Console.WriteLine($"Cau 27: {q27.Count} ket qua");

var q28 = TruyVanCaNhan.LayPhieuThuChuyenKhoan(duLieu);
Console.WriteLine($"Cau 28: {q28.Count} ket qua");

var tuNgay = duLieu.PhieuThus.Min(p => p.NgayTT);
var denNgay = duLieu.PhieuThus.Max(p => p.NgayTT);
var q29 = TruyVanCaNhan.TinhDoanhThuTheoKhoangNgay(
    duLieu, tuNgay, denNgay);
Console.WriteLine($"Cau 29: Tong doanh thu = {q29:N0}");

var q30 = TruyVanCaNhan.TinhTongTienTheoPhuongThuc(duLieu);
Console.WriteLine($"Cau 30: {q30.Count} nhom thanh toan");

Console.WriteLine("=== KET THUC KIEM THU ===");