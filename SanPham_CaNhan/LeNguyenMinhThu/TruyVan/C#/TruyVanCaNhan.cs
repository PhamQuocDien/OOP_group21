using CSharp.Data;
using CSharp.Entities;
using CSharp.Enums;

namespace LeNguyenMinhThu.TruyVan
{
    
    /// <summary>
    /// Cài đặt 10 câu truy vấn đầu tiên 1-10 bằng LINQ.
    /// </summary>
    public static class TruyVan10CauDau
    {
        // Câu 1: Lấy danh sách tất cả khách hàng đang lưu trữ trong hệ thống.
        public static List<KhachHang> LayTatCaKhachHang(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.KhachHangs
                .OrderBy(kh => kh.MaKH)
                .ToList();
        }

        // Câu 2: Tìm kiếm thông tin chi tiết của một khách hàng dựa trên số điện thoại.
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

        // Câu 3: Tìm danh sách những khách hàng đã lập hợp đồng nhưng chưa thực hiện thanh toán.
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

        // Câu 4: Thống kê tổng số lượng hợp đồng sửa chữa mà từng khách hàng đã thực hiện tại trung tâm.
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

        // Câu 5: Lấy danh sách thiết bị thuộc sở hữu của một khách hàng cụ thể dựa vào maKH.
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

        // Câu 6: Lọc danh sách thiết bị theo hãng sản xuất cụ thể.
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

        // Câu 7: Thống kê tổng số lượng thiết bị mang đến sửa chữa phân loại theo từng loại thiết bị.
        public static List<(string LoaiThietBi, int SoLuong)> DemThietBiTheoLoai(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.ThietBis
                .GroupBy(tb => tb.LoaiTB)
                .Select(g => (LoaiThietBi: g.Key, SoLuong: g.Count()))
                .OrderBy(x => x.LoaiThietBi)
                .ToList();
        }

        // Câu 8: Tìm thông tin chi tiết của một hợp đồng theo số hợp đồng.
        public static HopDong? TimHopDongTheoSoHD(DuLieuMau data, string soHD)
        {
            ArgumentNullException.ThrowIfNull(data);
            if (string.IsNullOrWhiteSpace(soHD))
                throw new ArgumentException("Số hợp đồng không được để trống.", nameof(soHD));

            return data.HopDongs
                .SingleOrDefault(hd => hd.SoHD == soHD);
        }

        // Câu 9: Lọc danh sách các hợp đồng có trạng thái MOI_TIEP_NHAN hoặc DANG_SUA_CHUA
        public static List<HopDong> LayHopDongDangXuLy(DuLieuMau data)
        {
            ArgumentNullException.ThrowIfNull(data);

            return data.HopDongs
                .Where(hd => hd.TinhtrangHD == TrangThaiHopDong.MOI_TIEP_NHAN
                          || hd.TinhtrangHD == TrangThaiHopDong.DANG_SUA_CHUA)
                .OrderBy(hd => hd.SoHD)
                .ToList();
        }

        // Câu 10: Liệt kê các hợp đồng sửa chữa được lập trong một khoảng thời gian cụ thể (từ ngày A đến ngày B).
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