using System;
using System.Collections.Generic;
using System.Linq;
using CSharp.Data;
using CSharp.Entities;
using CSharp.Enums;

namespace PhamQuocDien.TruyVan
{
    /// <summary>
    /// 10 truy vấn cá nhân C# LINQ, tương ứng các câu 21 và 22-30 trong danh sách truy vấn của nhóm.
    /// Dữ liệu lấy từ DataSeeder.TaoDuLieuMau().
    /// </summary>
    public static class TruyVanCaNhan
    {
        // Câu 21: Tìm nhân viên lập nhiều hợp đồng nhất trong tháng/năm; nếu đồng hạng thì trả về tất cả nhân viên đồng hạng.
        public static List<(NhanVien NhanVien, int SoHopDong)> TimNhanVienLapNhieuHopDongNhatTheoThang(
            DuLieuMau data, int thang, int nam)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (thang < 1 || thang > 12)
                throw new ArgumentOutOfRangeException(nameof(thang), "Tháng phải nằm trong khoảng từ 1 đến 12.");

            var thongKe = data.HopDongs
                .Where(h => h.Ngaylap.Month == thang && h.Ngaylap.Year == nam)
                .GroupBy(h => h.MaNV)
                .Select(g => new { MaNV = g.Key, SoHopDong = g.Count() })
                .ToList();

            if (thongKe.Count == 0)
                return new List<(NhanVien NhanVien, int SoHopDong)>();

            int soHopDongNhieuNhat = thongKe.Max(x => x.SoHopDong);

            return thongKe
                .Where(x => x.SoHopDong == soHopDongNhieuNhat)
                .Join(data.NhanViens,
                    x => x.MaNV,
                    nv => nv.MaNV,
                    (x, nv) => (NhanVien: nv, SoHopDong: x.SoHopDong))
                .OrderBy(x => x.NhanVien.MaNV)
                .ToList();
        }

        // Câu 22: Lấy danh sách tất cả thợ/kỹ thuật viên thuộc một nhóm thợ theo maNhomTho.
        public static List<Tho> LayDanhSachThoTheoNhom(DuLieuMau data, string maNhomTho)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(maNhomTho))
                throw new ArgumentException("Mã nhóm thợ không được để trống.", nameof(maNhomTho));

            return data.Thos
                .Where(t => t.MaNhomTho == maNhomTho)
                .OrderBy(t => t.MaTho)
                .ToList();
        }

        // Câu 23: Hiển thị thông tin thợ làm trưởng nhóm (maTruongNhom) của tất cả các nhóm thợ.
        public static List<(string MaNhomTho, string TenNhom, Tho TruongNhom)> TimTruongNhomCuaTatCaNhom(
            DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return (from nhom in data.NhomThos
                    join tho in data.Thos on nhom.MaTruongNhom equals tho.MaTho
                    orderby nhom.MaNhomTho
                    select (MaNhomTho: nhom.MaNhomTho,
                            TenNhom: nhom.TenNhom,
                            TruongNhom: tho))
                .ToList();
        }

        // Câu 24: Hiển thị các hạng mục công việc, đơn giá và thợ thực hiện trong một hợp đồng theo soHD.
        public static List<(ChiTietHopDong ChiTiet, CongViec CongViec, Tho Tho)> LayCongViecVaThoTheoHopDong(
            DuLieuMau data, string soHD)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(soHD))
                throw new ArgumentException("Số hợp đồng không được để trống.", nameof(soHD));

            return (from chiTiet in data.ChiTietHopDongs
                    where chiTiet.SoHD == soHD
                    join congViec in data.CongViecs on chiTiet.MaCV equals congViec.MaCV
                    join tho in data.Thos on chiTiet.MaTho equals tho.MaTho
                    orderby congViec.MaCV
                    select (ChiTiet: chiTiet, CongViec: congViec, Tho: tho))
                .ToList();
        }

        // Câu 25: Lọc chi tiết hợp đồng có trạng thái CHUA_THUC_HIEN hoặc DANG_THUC_HIEN.
        public static List<ChiTietHopDong> LayChiTietChuaHoacDangThucHien(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.ChiTietHopDongs
                .Where(ct => ct.TrangThaiCV == TrangThaiCongViec.CHUA_THUC_HIEN
                          || ct.TrangThaiCV == TrangThaiCongViec.DANG_THUC_HIEN)
                .OrderBy(ct => ct.SoHD)
                .ThenBy(ct => ct.MaCV)
                .ToList();
        }

        // Câu 26: Đếm số hạng mục mà một thợ đã hoàn thành hoặc đang thực hiện theo maTho.
        public static int DemHangMucThoDaHoacDangThucHien(DuLieuMau data, string maTho)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(maTho))
                throw new ArgumentException("Mã thợ không được để trống.", nameof(maTho));

            return data.ChiTietHopDongs.Count(ct =>
                ct.MaTho == maTho
                && (ct.TrangThaiCV == TrangThaiCongViec.HOAN_THANH
                    || ct.TrangThaiCV == TrangThaiCongViec.DANG_THUC_HIEN));
        }

        // Câu 27: Tìm tất cả phiếu thu thanh toán thuộc một hợp đồng theo soHD.
        public static List<PhieuThu> TimPhieuThuTheoHopDong(DuLieuMau data, string soHD)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(soHD))
                throw new ArgumentException("Số hợp đồng không được để trống.", nameof(soHD));

            return data.PhieuThus
                .Where(p => p.SoHD == soHD)
                .OrderBy(p => p.NgayTT)
                .ThenBy(p => p.MaPhieuThu)
                .ToList();
        }

        // Câu 28: Liệt kê các phiếu thu được thanh toán bằng phương thức CHUYEN_KHOAN.
        public static List<PhieuThu> LayPhieuThuChuyenKhoan(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.PhieuThus
                .Where(p => p.PhuongThucTT == PhuongThucThanhToan.CHUYEN_KHOAN)
                .OrderBy(p => p.NgayTT)
                .ThenBy(p => p.MaPhieuThu)
                .ToList();
        }

        // Câu 29: Tính tổng doanh thu từ các phiếu thu trong khoảng ngày; tính cả ngày bắt đầu và ngày kết thúc.
        public static double TinhDoanhThuTheoKhoangNgay(DuLieuMau data, DateOnly tuNgay, DateOnly denNgay)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (tuNgay > denNgay)
                throw new ArgumentException("Ngày bắt đầu không được sau ngày kết thúc.");

            return data.PhieuThus
                .Where(p => p.NgayTT >= tuNgay && p.NgayTT <= denNgay)
                .Sum(p => p.SoTienTT);
        }

        // Câu 30: Thống kê tổng số tiền đã thu, phân nhóm theo từng phương thức thanh toán.
        public static List<(PhuongThucThanhToan PhuongThuc, double TongTien)> TinhTongTienTheoPhuongThuc(
            DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.PhieuThus
                .GroupBy(p => p.PhuongThucTT)
                .Select(g => (PhuongThuc: g.Key, TongTien: g.Sum(p => p.SoTienTT)))
                .OrderBy(x => x.PhuongThuc)
                .ToList();
        }
    }
}
