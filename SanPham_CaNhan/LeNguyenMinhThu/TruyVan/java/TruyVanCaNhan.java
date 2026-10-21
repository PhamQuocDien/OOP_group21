package LeNguyenMinhThu.TruyVan;

import data.DataSeeder;
import model.entity.HopDong;
import model.entity.KhachHang;
import model.entity.ThietBi;
import model.enums.TrangThaiHopDong;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
/**
 * Cài đặt 10 câu truy vấn đầu tiên trong README bằng Java Stream.
 */
public final class TruyVanCaNhan {
    private TruyVanCaNhan() {
    }

    public record KetQuaDemHopDong(KhachHang khachHang, long soHopDong) {
    }

    public record KetQuaDemThietBi(String loaiThietBi, long soLuong) {
    }

    // Câu 1: Lấy danh sách tất cả khách hàng.
    public static List<KhachHang> layTatCaKhachHang(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");

        return data.getKhachHangs().stream()
                .sorted(Comparator.comparing(KhachHang::getMaKH))
                .toList();
    }

    // Câu 2: Tìm khách hàng theo số điện thoại.
    public static List<KhachHang> timKhachHangTheoSoDienThoai(
            DataSeeder.DuLieuMau data, String soDienThoai) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");
        kiemTraChuoi(soDienThoai, "Số điện thoại không được để trống.");

        return data.getKhachHangs().stream()
                .filter(kh -> Objects.equals(kh.getSdt(), soDienThoai))
                .sorted(Comparator.comparing(KhachHang::getMaKH))
                .toList();
    }

    // Câu 3: Tìm khách hàng có ít nhất một hợp đồng chưa phát sinh phiếu thu.
    public static List<KhachHang> layKhachHangCoHopDongChuaThanhToan(
            DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");

        return data.getKhachHangs().stream()
                .filter(kh -> data.getHopDongs().stream()
                        .filter(hd -> Objects.equals(hd.getMaKH(), kh.getMaKH()))
                        .anyMatch(hd -> data.getPhieuThus().stream()
                                .noneMatch(pt -> Objects.equals(
                                        pt.getSoHD(), hd.getSoHD()))))
                .sorted(Comparator.comparing(KhachHang::getMaKH))
                .toList();
    }

    // Câu 4: Đếm số hợp đồng của từng khách hàng, kể cả khách hàng chưa có hợp đồng.
    public static List<KetQuaDemHopDong> demHopDongTheoKhachHang(
            DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");

        return data.getKhachHangs().stream()
                .map(kh -> new KetQuaDemHopDong(
                        kh,
                        data.getHopDongs().stream()
                                .filter(hd -> Objects.equals(
                                        hd.getMaKH(), kh.getMaKH()))
                                .count()))
                .sorted(Comparator.comparing(kq -> kq.khachHang().getMaKH()))
                .toList();
    }

    // Câu 5: Lấy danh sách thiết bị thuộc về một khách hàng.
    public static List<ThietBi> layThietBiTheoKhachHang(
            DataSeeder.DuLieuMau data, String maKH) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");
        kiemTraChuoi(maKH, "Mã khách hàng không được để trống.");

        return data.getThietBis().stream()
                .filter(tb -> Objects.equals(tb.getMaKH(), maKH))
                .sorted(Comparator.comparing(ThietBi::getMaThietBi))
                .toList();
    }

    // Câu 6: Lọc thiết bị theo hãng sản xuất.
    public static List<ThietBi> layThietBiTheoHangSanXuat(
            DataSeeder.DuLieuMau data, String hangSX) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");
        kiemTraChuoi(hangSX, "Hãng sản xuất không được để trống.");

        return data.getThietBis().stream()
                .filter(tb -> tb.getHangSX() != null
                        && tb.getHangSX().equalsIgnoreCase(hangSX))
                .sorted(Comparator.comparing(ThietBi::getMaThietBi))
                .toList();
    }

    // Câu 7: Thống kê số thiết bị theo từng loại.
    public static List<KetQuaDemThietBi> demThietBiTheoLoai(
            DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");

        return data.getThietBis().stream()
                .collect(Collectors.groupingBy(
                        ThietBi::getLoaiTB, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> new KetQuaDemThietBi(
                        entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(
                        KetQuaDemThietBi::loaiThietBi,
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList();
    }

    // Câu 8: Tìm hợp đồng theo số hợp đồng.
    public static HopDong timHopDongTheoSoHD(
            DataSeeder.DuLieuMau data, String soHD) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");
        kiemTraChuoi(soHD, "Số hợp đồng không được để trống.");

        List<HopDong> ketQua = data.getHopDongs().stream()
                .filter(hd -> Objects.equals(hd.getSoHD(), soHD))
                .toList();
        if (ketQua.size() > 1) {
            throw new IllegalStateException(
                    "Có nhiều hơn một hợp đồng trùng số " + soHD + ".");
        }
        return ketQua.isEmpty() ? null : ketQua.get(0);
    }

    // Câu 9: Lấy hợp đồng mới tiếp nhận hoặc đang sửa chữa.
    public static List<HopDong> layHopDongDangXuLy(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");

        return data.getHopDongs().stream()
                .filter(hd -> hd.getTinhtrangHD() == TrangThaiHopDong.MOI_TIEP_NHAN
                        || hd.getTinhtrangHD() == TrangThaiHopDong.DANG_SUA_CHUA)
                .sorted(Comparator.comparing(HopDong::getSoHD))
                .toList();
    }

    // Câu 10: Lấy hợp đồng được lập trong khoảng ngày, tính cả hai ngày đầu mút.
    public static List<HopDong> layHopDongTheoKhoangNgay(
            DataSeeder.DuLieuMau data, LocalDate tuNgay, LocalDate denNgay) {
        Objects.requireNonNull(data, "Dữ liệu không được null.");
        Objects.requireNonNull(tuNgay, "Ngày bắt đầu không được null.");
        Objects.requireNonNull(denNgay, "Ngày kết thúc không được null.");
        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException(
                    "Ngày bắt đầu không được sau ngày kết thúc.");
        }

        return data.getHopDongs().stream()
                .filter(hd -> !hd.getNgaylap().isBefore(tuNgay)
                        && !hd.getNgaylap().isAfter(denNgay))
                .sorted(Comparator.comparing(HopDong::getNgaylap)
                        .thenComparing(HopDong::getSoHD))
                .toList();
    }

    private static void kiemTraChuoi(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }
}
