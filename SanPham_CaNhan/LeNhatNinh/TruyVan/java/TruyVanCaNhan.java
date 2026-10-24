package SanPham_CaNhan.LeNhatNinh.TruyVan.java;

import data.DataSeeder;
import model.entity.CongViec;
import model.entity.NhomCongViec;
import model.entity.HopDong;
import model.entity.ChiTietHopDong;
import model.entity.NhanVien;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TruyVanCaNhan {

    private TruyVanCaNhan() {
    }

    // Câu 11: Lấy danh sách tất cả công việc.
    public static List<CongViec> layTatCaCongViec(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getCongViecs().stream()
                .sorted(Comparator.comparing(CongViec::getMaCV))
                .toList();
    }

    // Câu 12: Tìm công việc theo mã nhóm công việc.
    public static List<CongViec> layCongViecTheoNhom(
            DataSeeder.DuLieuMau data, String maNhomCV) {

        Objects.requireNonNull(data);

        if (maNhomCV == null || maNhomCV.isBlank()) {
            throw new IllegalArgumentException(
                    "Mã nhóm công việc không được để trống.");
        }

        return data.getCongViecs().stream()
                .filter(cv -> Objects.equals(
                        cv.getMaNhomCV(), maNhomCV))
                .sorted(Comparator.comparing(CongViec::getMaCV))
                .toList();
    }

    // Câu 13: Tìm công việc có đơn giá lớn hơn mức giá cho trước.
    public static List<CongViec> layCongViecTheoDonGia(
            DataSeeder.DuLieuMau data, double donGiaToiThieu) {

        Objects.requireNonNull(data);

        return data.getCongViecs().stream()
                .filter(cv -> cv.getDongia() > donGiaToiThieu)
                .sorted(Comparator.comparingDouble(CongViec::getDongia)
                        .reversed()
                        .thenComparing(CongViec::getMaCV))
                .toList();
    }

    // Câu 14: Tìm công việc có đơn giá cao nhất.
    public static List<CongViec> timCongViecDonGiaCaoNhat(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        double max = data.getCongViecs().stream()
                .mapToDouble(CongViec::getDongia)
                .max()
                .orElse(0);

        return data.getCongViecs().stream()
                .filter(cv -> cv.getDongia() == max)
                .sorted(Comparator.comparing(CongViec::getMaCV))
                .toList();
    }

    // Câu 15: Đếm số công việc thuộc từng nhóm công việc.
    public static Map<String, Long> demCongViecTheoNhom(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getCongViecs().stream()
                .collect(Collectors.groupingBy(
                        CongViec::getMaNhomCV,
                        Collectors.counting()));
    }

    // Câu 16: Tìm các nhóm công việc có trong hệ thống.
    public static List<NhomCongViec> layTatCaNhomCongViec(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getNhomCongViecs().stream()
                .sorted(Comparator.comparing(
                        NhomCongViec::getMaNhomCV))
                .toList();
    }

    // Câu 17: Tìm nhân viên có chức vụ tư vấn.
    public static List<NhanVien> layNhanVienTuVan(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getNhanViens().stream()
                .filter(nv -> nv.getChucVu()
                        == model.enums.ChucVu.TU_VAN)
                .sorted(Comparator.comparing(NhanVien::getMaNV))
                .toList();
    }

    // Câu 18: Tìm công việc có tổng giá trị cao nhất
    // dựa trên đơn giá của công việc trong các hợp đồng.
    public record KetQuaDoanhThuCongViec(
            CongViec congViec, double tongGiaTri) {
    }

    public static List<KetQuaDoanhThuCongViec>
            timCongViecCoTongGiaTriCaoNhat(
                    DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        Map<String, Double> tongGiaTriTheoCongViec =
                data.getChiTietHopDongs().stream()
                        .collect(Collectors.groupingBy(
                                ChiTietHopDong::getMaCV,
                                Collectors.summingDouble(ct ->
                                        data.getCongViecs().stream()
                                                .filter(cv -> Objects.equals(
                                                        cv.getMaCV(),
                                                        ct.getMaCV()))
                                                .mapToDouble(
                                                        CongViec::getDongia)
                                                .findFirst()
                                                .orElse(0))));

        double max = tongGiaTriTheoCongViec.values().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0);

        return tongGiaTriTheoCongViec.entrySet().stream()
                .filter(entry -> Double.compare(
                        entry.getValue(), max) == 0)
                .flatMap(entry -> data.getCongViecs().stream()
                        .filter(cv -> Objects.equals(
                                cv.getMaCV(), entry.getKey()))
                        .map(cv -> new KetQuaDoanhThuCongViec(
                                cv, entry.getValue())))
                .sorted(Comparator.comparing(
                        kq -> kq.congViec().getMaCV()))
                .toList();
    }

    // Câu 19: Tìm các hợp đồng có tổng tiền lớn hơn mức cho trước.
    public static List<HopDong> layHopDongTheoTongTien(
            DataSeeder.DuLieuMau data, double tongTienToiThieu) {

        Objects.requireNonNull(data);

        return data.getHopDongs().stream()
                .filter(hd -> hd.getTongtien() > tongTienToiThieu)
                .sorted(Comparator.comparingDouble(HopDong::getTongtien)
                        .reversed()
                        .thenComparing(HopDong::getSoHD))
                .toList();
    }

    // Câu 20: Đếm số hợp đồng do từng nhân viên lập.
    public record KetQuaDemHopDong(
            NhanVien nhanVien, long soHopDong) {
    }

    public static List<KetQuaDemHopDong> demHopDongTheoNhanVien(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data);

        return data.getNhanViens().stream()
                .map(nv -> new KetQuaDemHopDong(
                        nv,
                        data.getHopDongs().stream()
                                .filter(hd -> Objects.equals(
                                        hd.getMaNV(), nv.getMaNV()))
                                .count()))
                .sorted(Comparator.comparing(
                        kq -> kq.nhanVien().getMaNV()))
                .toList();
    }
}
