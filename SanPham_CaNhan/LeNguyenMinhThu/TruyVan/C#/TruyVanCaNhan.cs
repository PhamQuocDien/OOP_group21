using CSharp.Data;
using CSharp.Entities;
using CSharp.Enums;

namespace LeNguyenMinhThu.TruyVan
{
    
    /// <summary>
    /// Cài đặt 10 câu truy vấn đầu tiên trong README bằng LINQ.
    /// </summary>
    public static class TruyVan10CauDau
    {
        // Câu 1: Lấy danh sách tất cả khách hàng.
        public static List<KhachHang> LayTatCaKhachHang(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.KhachHangs
                .OrderBy(kh => kh.MaKH)
                .ToList();
        }

        // Câu 2: Tìm khách hàng theo số điện thoại.
        public static List<KhachHang> TimKhachHangTheoSoDienThoai(DuLieuMau data, string soDienThoai)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(soDienThoai))
                throw new ArgumentException("Số điện thoại không được để trống.", nameof(soDienThoai));

            return data.KhachHangs
                .Where(kh => kh.Sdt == soDienThoai)
                .OrderBy(kh => kh.MaKH)
                .ToList();
        }

        // Câu 3: Tìm khách hàng có ít nhất một hợp đồng chưa phát sinh phiếu thu.
        public static List<KhachHang> LayKhachHangCoHopDongChuaThanhToan(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.KhachHangs
                .Where(kh => data.HopDongs
                    .Where(hd => hd.MaKH == kh.MaKH)
                    .Any(hd => !data.PhieuThus.Any(pt => pt.SoHD == hd.SoHD)))
                .OrderBy(kh => kh.MaKH)
                .ToList();
        }

        // Câu 4: Đếm số hợp đồng của từng khách hàng, kể cả khách hàng chưa có hợp đồng.
        public static List<(KhachHang KhachHang, int SoHopDong)> DemHopDongTheoKhachHang(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.KhachHangs
                .Select(kh => (
                    KhachHang: kh,
                    SoHopDong: data.HopDongs.Count(hd => hd.MaKH == kh.MaKH)))
                .OrderBy(x => x.KhachHang.MaKH)
                .ToList();
        }

        // Câu 5: Lấy danh sách thiết bị thuộc về một khách hàng.
        public static List<ThietBi> LayThietBiTheoKhachHang(DuLieuMau data, string maKH)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(maKH))
                throw new ArgumentException("Mã khách hàng không được để trống.", nameof(maKH));

            return data.ThietBis
                .Where(tb => tb.MaKH == maKH)
                .OrderBy(tb => tb.MaThietBi)
                .ToList();
        }

        // Câu 6: Lọc thiết bị theo hãng sản xuất.
        public static List<ThietBi> LayThietBiTheoHangSanXuat(DuLieuMau data, string hangSX)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(hangSX))
                throw new ArgumentException("Hãng sản xuất không được để trống.", nameof(hangSX));

            return data.ThietBis
                .Where(tb => string.Equals(tb.HangSX, hangSX, StringComparison.OrdinalIgnoreCase))
                .OrderBy(tb => tb.MaThietBi)
                .ToList();
        }

        // Câu 7: Thống kê số thiết bị theo từng loại.
        public static List<(string LoaiThietBi, int SoLuong)> DemThietBiTheoLoai(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.ThietBis
                .GroupBy(tb => tb.LoaiTB)
                .Select(g => (LoaiThietBi: g.Key, SoLuong: g.Count()))
                .OrderBy(x => x.LoaiThietBi)
                .ToList();
        }

        // Câu 8: Tìm hợp đồng theo số hợp đồng.
        public static HopDong? TimHopDongTheoSoHD(DuLieuMau data, string soHD)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(soHD))
                throw new ArgumentException("Số hợp đồng không được để trống.", nameof(soHD));

            return data.HopDongs
                .SingleOrDefault(hd => hd.SoHD == soHD);
        }

        // Câu 9: Lấy hợp đồng mới tiếp nhận hoặc đang sửa chữa.
        public static List<HopDong> LayHopDongDangXuLy(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.HopDongs
                .Where(hd => hd.TinhtrangHD == TrangThaiHopDong.MOI_TIEP_NHAN
                          || hd.TinhtrangHD == TrangThaiHopDong.DANG_SUA_CHUA)
                .OrderBy(hd => hd.SoHD)
                .ToList();
        }

        // Câu 10: Lấy hợp đồng được lập trong khoảng ngày, tính cả hai ngày đầu mút.
        public static List<HopDong> LayHopDongTheoKhoangNgay(
            DuLieuMau data, DateOnly tuNgay, DateOnly denNgay)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (tuNgay > denNgay)
                throw new ArgumentException("Ngày bắt đầu không được sau ngày kết thúc.");

            return data.HopDongs
                .Where(hd => hd.Ngaylap >= tuNgay && hd.Ngaylap <= denNgay)
                .OrderBy(hd => hd.Ngaylap)
                .ThenBy(hd => hd.SoHD)
                .ToList();
        }
    }
}