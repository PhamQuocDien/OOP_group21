
using System;
using System.Collections.Generic;
using System.Linq;
using CSharp.Data;
using CSharp.Entities;
using CSharp.Enums;

namespace LeNhatNinh.TruyVan
{
    /// <summary>
    /// Cài đặt 10 câu truy vấn từ câu 11 đến câu 20 trong README bằng LINQ.
    /// Dữ liệu lấy từ DuLieuMau.
    /// </summary>
    public static class TruyVanCaNhan
    {
        // Câu 11: Tìm top 5 hợp đồng sửa chữa có tổng tiền cao nhất.
        public static List<HopDong> LayTop5HopDongTongTienCaoNhat(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.HopDongs
                .OrderByDescending(hd => hd.Tongtien)
                .ThenBy(hd => hd.SoHD)
                .Take(5)
                .ToList();
        }

        // Câu 12: Thống kê số lượng hợp đồng theo từng trạng thái.
        public static List<(TrangThaiHopDong TrangThai, int SoLuong)>
            DemHopDongTheoTrangThai(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.HopDongs
                .GroupBy(hd => hd.TinhtrangHD)
                .Select(g => (
                    TrangThai: g.Key,
                    SoLuong: g.Count()))
                .OrderBy(x => x.TrangThai)
                .ToList();
        }

        // Câu 13: Hiển thị danh sách tất cả nhóm công việc.
        public static List<NhomCongViec> LayTatCaNhomCongViec(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.NhomCongViecs
                .OrderBy(n => n.MaNhomCV)
                .ToList();
        }

        // Câu 14: Tìm nhóm công việc theo tên nhóm.
        public static List<NhomCongViec> TimNhomCongViecTheoTen(
            DuLieuMau data, string tenNhom)
        {
            ArgumentNullException.ThrowIfNull(data);

            if (string.IsNullOrWhiteSpace(tenNhom))
                throw new ArgumentException(
                    "Tên nhóm công việc không được để trống.", nameof(tenNhom));

            return data.NhomCongViecs
                .Where(n => string.Equals(
                    n.TenNhom, tenNhom, StringComparison.OrdinalIgnoreCase))
                .OrderBy(n => n.MaNhomCV)
                .ToList();
        }

        // Câu 15: Liệt kê công việc thuộc một nhóm công việc theo maNhomCV.
        public static List<CongViec> LayCongViecTheoNhom(
            DuLieuMau data, string maNhomCV)
        {
            ArgumentNullException.ThrowIfNull(data);

            if (string.IsNullOrWhiteSpace(maNhomCV))
                throw new ArgumentException(
                    "Mã nhóm công việc không được để trống.", nameof(maNhomCV));

            return data.CongViecs
                .Where(cv => cv.MaNhomCV == maNhomCV)
                .OrderBy(cv => cv.MaCV)
                .ToList();
        }

        // Câu 16: Lọc công việc có đơn giá từ 500.000 VNĐ trở lên.
        public static List<CongViec> LayCongViecTheoDonGia(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.CongViecs
                .Where(cv => cv.Dongia >= 500000)
                .OrderBy(cv => cv.Dongia)
                .ThenBy(cv => cv.MaCV)
                .ToList();
        }

        // Câu 17: Thống kê số lần xuất hiện của từng công việc trong các hợp đồng.
        public static List<(CongViec CongViec, int SoLanThucHien)>
            DemSoLanThucHienCuaTungCongViec(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.CongViecs
                .Select(cv => (
                    CongViec: cv,
                    SoLanThucHien: data.ChiTietHopDongs
                        .Count(ct => ct.MaCV == cv.MaCV)))
                .OrderByDescending(x => x.SoLanThucHien)
                .ThenBy(x => x.CongViec.MaCV)
                .ToList();
        }

        // Câu 18: Tìm công việc mang lại tổng doanh thu cao nhất.
        // Doanh thu ước tính = đơn giá * số lần xuất hiện trong chi tiết hợp đồng.
        public static CongViec? TimCongViecCoDoanhThuCaoNhat(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.CongViecs
                .Select(cv => new
                {
                    CongViec = cv,
                    DoanhThu = cv.Dongia * data.ChiTietHopDongs
                        .Count(ct => ct.MaCV == cv.MaCV)
                })
                .OrderByDescending(x => x.DoanhThu)
                .ThenBy(x => x.CongViec.MaCV)
                .Select(x => x.CongViec)
                .FirstOrDefault();
        }

        // Câu 19: Liệt kê nhân viên theo chức vụ cụ thể.
        public static List<NhanVien> LayNhanVienTheoChucVu(
            DuLieuMau data, ChucVu chucVu)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.NhanViens
                .Where(nv => nv.ChucVu == chucVu)
                .OrderBy(nv => nv.MaNV)
                .ToList();
        }

        // Câu 20: Thống kê số hợp đồng do từng nhân viên lập,
        // kể cả nhân viên chưa lập hợp đồng nào.
        public static List<(NhanVien NhanVien, int SoHopDong)>
            DemHopDongTheoNhanVien(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.NhanViens
                .Select(nv => (
                    NhanVien: nv,
                    SoHopDong: data.HopDongs.Count(hd => hd.MaNV == nv.MaNV)))
                .OrderBy(x => x.NhanVien.MaNV)
                .ToList();
        }
    }
}
