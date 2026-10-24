package SanPham_CaNhan.LeNhatNinh.TruyVan.java;

import data.DataSeeder;
import model.entity.CongViec;
import model.entity.NhomCongViec;
import model.entity.HopDong;
import model.entity.ChiTietHopDong;
import model.entity.NhanVien;
import model.enums.ChucVu;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TruyVanCaNhan {

    private TruyVanCaNhan() {
    }

    // Record kết quả cho các câu truy vấn thống kê/gom nhóm
    public record KetQuaDemTrangThai(String trangThai, long soLuong) {}
    public record KetQuaSoLanXuatHien(CongViec congViec, long soLanXuatHien) {}
    public record KetQuaDoanhThuCongViec(CongViec congViec, long soLanXuatHien, double doanhThuDuKien) {}
    public record KetQuaDemHopDongNhanVien(NhanVien nhanVien, long soHopDong) {}

    // Câu 11: Lấy 5 hợp đồng có tổng tiền cao nhất
    public static List<HopDong> LayTop5HopDongTongTienCaoNhat(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data);
        return data.getHopDongs().stream()
                .sorted(Comparator.comparingDouble(HopDong::getTongtien).reversed())
                .limit(5)
                .toList();
    }

    // Câu 12: Đếm số hợp đồng theo từng trạng thái
    public static List<KetQuaDemTrangThai> demHopDongTheoTrangThai(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data);
        
        Map<String, Long> map = data.getHopDongs().stream()
                .collect(Collectors.groupingBy(hd -> hd.getTinhtrangHD().toString(), Collectors.counting()));
        
        return map.entrySet().stream()
                .map(entry -> new KetQuaDemTrangThai(entry.getKey(), entry.getValue()))
                .toList();
    }

    // Câu 13: Lấy danh sách tất cả nhóm công việc
    public static List<NhomCongViec> layTatCaNhomCongViec(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data);
        return data.getNhomCongViecs().stream()
                .sorted(Comparator.comparing(NhomCongViec::getMaNhomCV))
                .toList();
    }

    // Câu 14: Tìm nhóm công việc theo tên
    public static Optional<NhomCongViec> timNhomCongViecTheoTen(DataSeeder.DuLieuMau data, String tenNhom) {
        Objects.requireNonNull(data);
        if (tenNhom == null) return Optional.empty();
        return data.getNhomCongViecs().stream()
                .filter(nhom -> nhom.getTenNhom().equalsIgnoreCase(tenNhom))
                .findFirst();
    }

    // Câu 15: Lấy công việc theo mã nhóm
    public static List<CongViec> layCongViecTheoMaNhom(DataSeeder.DuLieuMau data, String maNhomCV) {
        Objects.requireNonNull(data);
        return data.getCongViecs().stream()
                .filter(cv -> Objects.equals(cv.getMaNhomCV(), maNhomCV))
                .toList();
    }

    // Câu 16: Lấy công việc có đơn giá từ 500.000 đồng trở lên
    public static List<CongViec> layCongViecDonGiaTu500000(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data);
        return data.getCongViecs().stream()
                .filter(cv -> cv.getDongia() >= 500000.0)
                .toList();
    }

    // Câu 17: Đếm số lần mỗi công việc xuất hiện trong chi tiết hợp đồng
    public static List<KetQuaSoLanXuatHien> demSoLanCongViecXuatHien(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data);
        Map<String, Long> countMap = data.getChiTietHopDongs().stream()
                .collect(Collectors.groupingBy(ChiTietHopDong::getMaCV, Collectors.counting()));

        return countMap.entrySet().stream()
                .map(entry -> {
                    CongViec cv = data.getCongViecs().stream()
                            .filter(c -> c.getMaCV().equals(entry.getKey()))
                            .findFirst()
                            .orElse(null);
                    return new KetQuaSoLanXuatHien(cv, entry.getValue());
                })
                .filter(item -> item.congViec() != null)
                .toList();
    }

    // Câu 18: Tìm công việc có doanh thu dự kiến cao nhất
    public static Optional<KetQuaDoanhThuCongViec> timCongViecCoDoanhThuDuKienCaoNhat(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data);
        List<KetQuaSoLanXuatHien> dsXuatHien = demSoLanCongViecXuatHien(data);

        return dsXuatHien.stream()
                .map(item -> {
                    double doanhThu = item.soLanXuatHien() * item.congViec().getDongia();
                    return new KetQuaDoanhThuCongViec(item.congViec(), item.soLanXuatHien(), doanhThu);
                })
                .max(Comparator.comparingDouble(KetQuaDoanhThuCongViec::doanhThuDuKien));
    }

    // Câu 19: Lấy danh sách nhân viên theo chức vụ
    public static List<NhanVien> layNhanVienTheoChucVu(DataSeeder.DuLieuMau data, ChucVu chucVu) {
        Objects.requireNonNull(data);
        return data.getNhanViens().stream()
                .filter(nv -> nv.getChucVu() == chucVu)
                .toList();
    }

    // Câu 20: Đếm số hợp đồng do từng nhân viên lập
    public static List<KetQuaDemHopDongNhanVien> demHopDongTheoNhanVien(DataSeeder.DuLieuMau data) {
        Objects.requireNonNull(data);
        return data.getNhanViens().stream()
                .map(nv -> {
                    long count = data.getHopDongs().stream()
                            .filter(hd -> Objects.equals(hd.getMaNV(), nv.getMaNV()))
                            .count();
                    return new KetQuaDemHopDongNhanVien(nv, count);
                })
                .toList();
    }
}
