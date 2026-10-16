package jv;
import data.DataSeeder;
import model.entity.ChiTietHopDong;
import model.entity.CongViec;
import model.entity.HopDong;
import model.entity.NhanVien;
import model.entity.NhomTho;
import model.entity.PhieuThu;
import model.entity.Tho;
import model.enums.PhuongThucThanhToan;
import model.enums.TrangThaiCongViec;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TruyVanCaNhan {
    private TruyVanCaNhan() {}

    // Kiểu kết quả cho câu 21.
    public record KetQuaNhanVien(NhanVien nhanVien, long soHopDong) {}

    // Kiểu kết quả cho câu 23.
    public record KetQuaTruongNhom(NhomTho nhomTho, Tho truongNhom) {}

    // Kiểu kết quả cho câu 24.
    public record KetQuaCongViecTho(
            ChiTietHopDong chiTiet, CongViec congViec, Tho tho) {}

    // Kiểu kết quả cho câu 30.
    public record TongTienTheoPhuongThuc(
            PhuongThucThanhToan phuongThuc, double tongTien) {}

    // Câu 21: Tìm nhân viên lập nhiều hợp đồng nhất theo tháng và năm.
    public static List<KetQuaNhanVien> timNhanVienLapNhieuHopDongNhatTheoThang(
            DataSeeder.DuLieuMau data, int thang, int nam) {

        Objects.requireNonNull(data);

        if (thang < 1 || thang > 12) {
            throw new IllegalArgumentException("Tháng phải từ 1 đến 12.");
        }

        Map<String, Long> thongKe = data.getHopDongs().stream()
                .filter(hd -> hd.getNgaylap().getMonthValue() == thang
                        && hd.getNgaylap().getYear() == nam)
                .collect(Collectors.groupingBy(
                        HopDong::getMaNV, Collectors.counting()));

        long max = thongKe.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        if (max == 0) {
            return List.of();
        }

        return thongKe.entrySet().stream()
                .filter(entry -> entry.getValue() == max)
                .flatMap(entry -> data.getNhanViens().stream()
                        .filter(nv -> Objects.equals(
                                nv.getMaNV(), entry.getKey()))
                        .map(nv -> new KetQuaNhanVien(
                                nv, entry.getValue())))
                .sorted(Comparator.comparing(
                        kq -> kq.nhanVien().getMaNV()))
                .toList();
    }

    // Câu 22: Lấy danh sách thợ thuộc một nhóm thợ.
    public static List<Tho> layDanhSachThoTheoNhom(
            DataSeeder.DuLieuMau data, String maNhomTho) {

        Objects.requireNonNull(data);

        if (maNhomTho == null || maNhomTho.isBlank()) {
            throw new IllegalArgumentException("Mã nhóm thợ không được để trống.");
        }

        return data.getThos().stream()
                .filter(tho -> Objects.equals(
                        tho.getMaNhomTho(), maNhomTho))
                .sorted(Comparator.comparing(Tho::getMaTho))
                .toList();
    }

    // Câu 23: Tìm trưởng nhóm của tất cả nhóm thợ.
    public static List<KetQuaTruongNhom> timTruongNhomCuaTatCaNhom(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getNhomThos().stream()
                .flatMap(nhom -> data.getThos().stream()
                        .filter(tho -> Objects.equals(
                                tho.getMaTho(), nhom.getMaTruongNhom()))
                        .map(tho -> new KetQuaTruongNhom(nhom, tho)))
                .sorted(Comparator.comparing(
                        kq -> kq.nhomTho().getMaNhomTho()))
                .toList();
    }

    // Câu 24: Lấy công việc, đơn giá và thợ theo số hợp đồng.
    public static List<KetQuaCongViecTho> layCongViecVaThoTheoHopDong(
            DataSeeder.DuLieuMau data, String soHD) {

        Objects.requireNonNull(data);

        if (soHD == null || soHD.isBlank()) {
            throw new IllegalArgumentException("Số hợp đồng không được để trống.");
        }

        return data.getChiTietHopDongs().stream()
                .filter(ct -> Objects.equals(ct.getSoHD(), soHD))
                .flatMap(ct -> data.getCongViecs().stream()
                        .filter(cv -> Objects.equals(
                                cv.getMaCV(), ct.getMaCV()))
                        .flatMap(cv -> data.getThos().stream()
                                .filter(tho -> Objects.equals(
                                        tho.getMaTho(), ct.getMaTho()))
                                .map(tho -> new KetQuaCongViecTho(
                                        ct, cv, tho))))
                .sorted(Comparator.comparing(
                        kq -> kq.congViec().getMaCV()))
                .toList();
    }

    // Câu 25: Lọc chi tiết chưa thực hiện hoặc đang thực hiện.
    public static List<ChiTietHopDong> layChiTietChuaHoacDangThucHien(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getChiTietHopDongs().stream()
                .filter(ct ->
                        ct.getTrangThaiCV() == TrangThaiCongViec.CHUA_THUC_HIEN
                        || ct.getTrangThaiCV() == TrangThaiCongViec.DANG_THUC_HIEN)
                .sorted(Comparator.comparing(ChiTietHopDong::getSoHD)
                        .thenComparing(ChiTietHopDong::getMaCV))
                .toList();
    }

    // Câu 26: Đếm hạng mục đã hoàn thành hoặc đang thực hiện của một thợ.
    public static long demHangMucThoDaHoacDangThucHien(
            DataSeeder.DuLieuMau data, String maTho) {

        Objects.requireNonNull(data);

        if (maTho == null || maTho.isBlank()) {
            throw new IllegalArgumentException("Mã thợ không được để trống.");
        }

        return data.getChiTietHopDongs().stream()
                .filter(ct -> Objects.equals(ct.getMaTho(), maTho))
                .filter(ct ->
                        ct.getTrangThaiCV() == TrangThaiCongViec.HOAN_THANH
                        || ct.getTrangThaiCV() == TrangThaiCongViec.DANG_THUC_HIEN)
                .count();
    }

    // Câu 27: Tìm phiếu thu theo số hợp đồng.
    public static List<PhieuThu> timPhieuThuTheoHopDong(
            DataSeeder.DuLieuMau data, String soHD) {

        Objects.requireNonNull(data);

        if (soHD == null || soHD.isBlank()) {
            throw new IllegalArgumentException("Số hợp đồng không được để trống.");
        }

        return data.getPhieuThus().stream()
                .filter(pt -> Objects.equals(pt.getSoHD(), soHD))
                .sorted(Comparator.comparing(PhieuThu::getNgayTT)
                        .thenComparing(PhieuThu::getMaPhieuThu))
                .toList();
    }

    // Câu 28: Lấy các phiếu thu thanh toán bằng chuyển khoản.
    public static List<PhieuThu> layPhieuThuChuyenKhoan(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getPhieuThus().stream()
                .filter(pt -> pt.getPhuongThucTT()
                        == PhuongThucThanhToan.CHUYEN_KHOAN)
                .sorted(Comparator.comparing(PhieuThu::getNgayTT)
                        .thenComparing(PhieuThu::getMaPhieuThu))
                .toList();
    }

    // Câu 29: Tính doanh thu trong khoảng ngày, tính cả hai ngày đầu và cuối.
    public static double tinhDoanhThuTheoKhoangNgay(
            DataSeeder.DuLieuMau data,
            LocalDate tuNgay, LocalDate denNgay) {

        Objects.requireNonNull(data);
        Objects.requireNonNull(tuNgay);
        Objects.requireNonNull(denNgay);

        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException(
                    "Ngày bắt đầu không được sau ngày kết thúc.");
        }

        return data.getPhieuThus().stream()
                .filter(pt -> !pt.getNgayTT().isBefore(tuNgay)
                        && !pt.getNgayTT().isAfter(denNgay))
                .mapToDouble(PhieuThu::getSoTienTT)
                .sum();
    }

    // Câu 30: Thống kê tổng tiền theo từng phương thức thanh toán.
    public static List<TongTienTheoPhuongThuc> tinhTongTienTheoPhuongThuc(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        Map<PhuongThucThanhToan, Double> tongTien =
                data.getPhieuThus().stream()
                        .collect(Collectors.groupingBy(
                                PhieuThu::getPhuongThucTT,
                                () -> new TreeMap<>(),
                                Collectors.summingDouble(
                                        PhieuThu::getSoTienTT)));

        return tongTien.entrySet().stream()
                .map(entry -> new TongTienTheoPhuongThuc(
                        entry.getKey(), entry.getValue()))
                .toList();
    }
}
