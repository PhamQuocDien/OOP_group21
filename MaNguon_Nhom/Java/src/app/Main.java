package app;

import data.DataSeeder;
import dao.InMemoryDAO;
import model.entity.ChiTietHopDong;
import model.entity.CongViec;
import model.entity.NhomTho;
import model.entity.PhieuThu;
import model.entity.Tho;
import java.time.LocalDate;
import jv.TruyVanCaNhan;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final DateTimeFormatter DINH_DANG_NGAY =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        System.out.println("========== CHUONG TRINH KIEM THU JAVA ==========");
        System.out.println("========== KIEM TRA IN-MEMORY DAO (JAVA) ==========");

        InMemoryDAO<NhomTho> dao =
                new InMemoryDAO<>(NhomTho::getMaNhomTho);

        NhomTho nhom = new NhomTho("NT01", "Nhom sua chua", "T01");
        dao.add(nhom);

        System.out.println("So nhom tho: " + dao.getAll().size());
        System.out.println("Ten nhom: " + dao.findById("NT01").getTenNhom());
        System.out.println("Xoa thanh cong: " + dao.removeById("NT01"));
        System.out.println("So luong sau xoa: " + dao.getAll().size());

        System.out.println("\n=== KIEM TRA DATA SEEDER (JAVA) ===");

        DataSeeder.DuLieuMau duLieuMau = DataSeeder.taoDuLieuMau();

        System.out.println("Nhom tho: " + duLieuMau.getNhomThos().size());
        System.out.println("Tho: " + duLieuMau.getThos().size());
        System.out.println("Nhom cong viec: " + duLieuMau.getNhomCongViecs().size());
        System.out.println("Cong viec: " + duLieuMau.getCongViecs().size());
        System.out.println("Khach hang: " + duLieuMau.getKhachHangs().size());
        System.out.println("Thiet bi: " + duLieuMau.getThietBis().size());
        System.out.println("Nhan vien: " + duLieuMau.getNhanViens().size());
        System.out.println("Hop dong: " + duLieuMau.getHopDongs().size());
        System.out.println("Chi tiet hop dong: " + duLieuMau.getChiTietHopDongs().size());
        System.out.println("Phieu thu: " + duLieuMau.getPhieuThus().size());
        System.out.println("Kha nang tho: " + duLieuMau.getKhaNangThos().size());

        System.out.println(
                "\n========== KIEM THU 10 CAU TRUY VAN CUA LE NGUYEN MINH THU ==========");

        var khachHangThu = duLieuMau.getKhachHangs().get(0);
        var thietBiThu = duLieuMau.getThietBis().get(0);
        var hopDongThu = duLieuMau.getHopDongs().get(0);
        LocalDate ngayLapDauTienThu = duLieuMau.getHopDongs().stream()
                .map(hd -> hd.getNgaylap())
                .min(LocalDate::compareTo)
                .orElseThrow();
        LocalDate ngayLapCuoiCungThu = duLieuMau.getHopDongs().stream()
                .map(hd -> hd.getNgaylap())
                .max(LocalDate::compareTo)
                .orElseThrow();

        // Cau 1
        System.out.println("\n[CAU 1] Lay danh sach tat ca khach hang.");
        var q1Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .layTatCaKhachHang(duLieuMau);
        System.out.println("So ket qua: " + q1Thu.size());
        for (var item : q1Thu) {
            System.out.printf(
                    "Ma khach hang: %s | Ho ten: %s | SDT: %s | Dia chi: %s%n",
                    item.getMaKH(), item.getHoten(), item.getSdt(), item.getDiachi());
        }

        // Cau 2
        System.out.printf(
                "\n[CAU 2] Tim khach hang theo so dien thoai %s.%n",
                khachHangThu.getSdt());
        var q2Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .timKhachHangTheoSoDienThoai(duLieuMau, khachHangThu.getSdt());
        System.out.println("So ket qua: " + q2Thu.size());
        for (var item : q2Thu) {
            System.out.printf(
                    "Ma khach hang: %s | Ho ten: %s | SDT: %s%n",
                    item.getMaKH(), item.getHoten(), item.getSdt());
        }

        // Cau 3
        System.out.println(
                "\n[CAU 3] Tim khach hang co hop dong chua phat sinh phieu thu.");
        var q3Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .layKhachHangCoHopDongChuaThanhToan(duLieuMau);
        System.out.println("So ket qua: " + q3Thu.size());
        for (var item : q3Thu) {
            System.out.printf(
                    "Ma khach hang: %s | Ho ten: %s | SDT: %s%n",
                    item.getMaKH(), item.getHoten(), item.getSdt());
        }

        // Cau 4
        System.out.println("\n[CAU 4] Dem so hop dong cua tung khach hang.");
        var q4Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .demHopDongTheoKhachHang(duLieuMau);
        System.out.println("So ket qua: " + q4Thu.size());
        for (var item : q4Thu) {
            System.out.printf(
                    "Ma khach hang: %s | Ho ten: %s | So hop dong: %d%n",
                    item.khachHang().getMaKH(),
                    item.khachHang().getHoten(),
                    item.soHopDong());
        }

        // Cau 5
        System.out.printf(
                "\n[CAU 5] Lay danh sach thiet bi cua khach hang %s.%n",
                khachHangThu.getMaKH());
        var q5Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .layThietBiTheoKhachHang(duLieuMau, khachHangThu.getMaKH());
        System.out.println("So ket qua: " + q5Thu.size());
        for (var item : q5Thu) {
            System.out.printf(
                    "Ma thiet bi: %s | Loai: %s | Hang san xuat: %s | Model: %s | Tinh trang: %s%n",
                    item.getMaThietBi(), item.getLoaiTB(), item.getHangSX(),
                    item.getModel(), item.getTinhtrang());
        }

        // Cau 6
        System.out.printf(
                "\n[CAU 6] Loc thiet bi theo hang san xuat %s.%n",
                thietBiThu.getHangSX());
        var q6Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .layThietBiTheoHangSanXuat(duLieuMau, thietBiThu.getHangSX());
        System.out.println("So ket qua: " + q6Thu.size());
        for (var item : q6Thu) {
            System.out.printf(
                    "Ma thiet bi: %s | Loai: %s | Hang san xuat: %s | Model: %s | Ma khach hang: %s%n",
                    item.getMaThietBi(), item.getLoaiTB(), item.getHangSX(),
                    item.getModel(), item.getMaKH());
        }

        // Cau 7
        System.out.println("\n[CAU 7] Thong ke so thiet bi theo tung loai.");
        var q7Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .demThietBiTheoLoai(duLieuMau);
        System.out.println("So loai thiet bi: " + q7Thu.size());
        for (var item : q7Thu) {
            System.out.printf(
                    "Loai thiet bi: %s | So luong: %d%n",
                    item.loaiThietBi(), item.soLuong());
        }

        // Cau 8
        System.out.printf(
                "\n[CAU 8] Tim hop dong theo so hop dong %s.%n",
                hopDongThu.getSoHD());
        var q8Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .timHopDongTheoSoHD(duLieuMau, hopDongThu.getSoHD());
        System.out.println("So ket qua: " + (q8Thu == null ? 0 : 1));
        if (q8Thu != null) {
            System.out.printf(
                    "So hop dong: %s | Ngay lap: %s | Trang thai: %s | Tong tien: %,.0f dong | Ma khach hang: %s%n",
                    q8Thu.getSoHD(),
                    q8Thu.getNgaylap().format(DINH_DANG_NGAY),
                    q8Thu.getTinhtrangHD(),
                    q8Thu.getTongtien(),
                    q8Thu.getMaKH());
        }

        // Cau 9
        System.out.println(
                "\n[CAU 9] Lay hop dong moi tiep nhan hoac dang sua chua.");
        var q9Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .layHopDongDangXuLy(duLieuMau);
        System.out.println("So ket qua: " + q9Thu.size());
        for (var item : q9Thu) {
            System.out.printf(
                    "So hop dong: %s | Ngay lap: %s | Trang thai: %s | Tong tien: %,.0f dong | Ma khach hang: %s%n",
                    item.getSoHD(),
                    item.getNgaylap().format(DINH_DANG_NGAY),
                    item.getTinhtrangHD(),
                    item.getTongtien(),
                    item.getMaKH());
        }

        // Cau 10
        System.out.printf(
                "\n[CAU 10] Lay hop dong tu %s den %s.%n",
                ngayLapDauTienThu.format(DINH_DANG_NGAY),
                ngayLapCuoiCungThu.format(DINH_DANG_NGAY));
        var q10Thu = SanPham_CaNhan.LeNguyenMinhThu.TruyVan.java.TruyVanCaNhan
                .layHopDongTheoKhoangNgay(
                        duLieuMau, ngayLapDauTienThu, ngayLapCuoiCungThu);
        System.out.println("So ket qua: " + q10Thu.size());
        for (var item : q10Thu) {
            System.out.printf(
                    "So hop dong: %s | Ngay lap: %s | Ngay hen tra: %s | Trang thai: %s | Ma khach hang: %s%n",
                    item.getSoHD(),
                    item.getNgaylap().format(DINH_DANG_NGAY),
                    item.getNgayHenTra().format(DINH_DANG_NGAY),
                    item.getTinhtrangHD(),
                    item.getMaKH());
        }

        System.out.println(
                "\n========== KIEM THU 10 CAU TRUY VAN CA NHAN (JAVA STREAM) ==========");


public final class TruyVan11Den20 {

    private TruyVan11Den20() {
    }

    // Ket qua thong ke so hop dong theo trang thai
    public record KetQuaDemHopDongTheoTrangThai(
            Object trangThai, long soLuong) {
    }

    // Ket qua dem so lan cong viec xuat hien trong chi tiet hop dong
    public record KetQuaDemCongViec(
            CongViec congViec, long soLanXuatHien) {
    }

    // Ket qua doanh thu du kien cua cong viec
    public record KetQuaDoanhThuDuKien(
            CongViec congViec,
            long soLanXuatHien,
            double doanhThuDuKien) {
    }

    // Ket qua dem so hop dong cua tung nhan vien
    public record KetQuaDemHopDongNhanVien(
            NhanVien nhanVien, long soHopDong) {
    }

    private static void kiemTraChuoi(String giaTri, String tenThamSo) {
        if (giaTri == null || giaTri.isBlank()) {
            throw new IllegalArgumentException(
                    tenThamSo + " khong duoc de trong.");
        }
    }

    // Cau 11: Lay 5 hop dong co tong tien cao nhat
    public static List<HopDong> layTop5HopDongTongTienCaoNhat(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");

        return data.getHopDongs().stream()
                .sorted(
                        Comparator.comparingDouble(HopDong::getTongtien)
                                .reversed()
                                .thenComparing(HopDong::getSoHD))
                .limit(5)
                .toList();
    }

    // Cau 12: Dem so hop dong theo tung trang thai
    public static List<KetQuaDemHopDongTheoTrangThai>
    demHopDongTheoTrangThai(DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");

        Map<Object, Long> thongKe = data.getHopDongs().stream()
                .collect(Collectors.groupingBy(
                        HopDong::getTinhtrangHD,
                        Collectors.counting()));

        return thongKe.entrySet().stream()
                .map(item -> new KetQuaDemHopDongTheoTrangThai(
                        item.getKey(), item.getValue()))
                .sorted(Comparator.comparing(
                        item -> item.trangThai().toString()))
                .toList();
    }

    // Cau 13: Lay danh sach tat ca nhom cong viec
    public static List<NhomCongViec> layTatCaNhomCongViec(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");

        return data.getNhomCongViecs().stream()
                .sorted(Comparator.comparing(NhomCongViec::getMaNhomCV))
                .toList();
    }

    // Cau 14: Tim nhom cong viec theo ten nhom
    public static Optional<NhomCongViec> timNhomCongViecTheoTen(
            DataSeeder.DuLieuMau data, String tenNhom) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");
        kiemTraChuoi(tenNhom, "Ten nhom");

        return data.getNhomCongViecs().stream()
                .filter(nhom -> nhom.getTenNhom().equalsIgnoreCase(
                        tenNhom.trim()))
                .sorted(Comparator.comparing(NhomCongViec::getMaNhomCV))
                .findFirst();
    }

    // Cau 15: Lay cac cong viec theo ma nhom cong viec
    public static List<CongViec> layCongViecTheoMaNhom(
            DataSeeder.DuLieuMau data, String maNhomCV) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");
        kiemTraChuoi(maNhomCV, "Ma nhom cong viec");

        return data.getCongViecs().stream()
                .filter(cv -> cv.getMaNhomCV().equalsIgnoreCase(
                        maNhomCV.trim()))
                .sorted(Comparator.comparing(CongViec::getMaCV))
                .toList();
    }

    // Cau 16: Lay cong viec co don gia tu 500.000 dong tro len
    public static List<CongViec> layCongViecDonGiaTu500000(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");

        return data.getCongViecs().stream()
                .filter(cv -> cv.getDongia() >= 500000)
                .sorted(
                        Comparator.comparingDouble(CongViec::getDongia)
                                .reversed()
                                .thenComparing(CongViec::getMaCV))
                .toList();
    }

    // Cau 17: Dem so lan moi cong viec xuat hien trong chi tiet hop dong
    // Van hien thi cong viec chua xuat hien, voi so lan bang 0
    public static List<KetQuaDemCongViec> demSoLanCongViecXuatHien(
            DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");

        Map<String, Long> soLanTheoMaCV =
                data.getChiTietHopDongs().stream()
                        .collect(Collectors.groupingBy(
                                ChiTietHopDong::getMaCV,
                                Collectors.counting()));

        return data.getCongViecs().stream()
                .map(cv -> new KetQuaDemCongViec(
                        cv,
                        soLanTheoMaCV.getOrDefault(cv.getMaCV(), 0L)))
                .sorted(Comparator.comparing(
                        item -> item.congViec().getMaCV()))
                .toList();
    }

    // Cau 18: Tim cong viec co doanh thu du kien cao nhat
    // Doanh thu du kien = Don gia * So lan xuat hien
    public static Optional<KetQuaDoanhThuDuKien>
    timCongViecCoDoanhThuDuKienCaoNhat(DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");

        Map<String, Long> soLanTheoMaCV =
                data.getChiTietHopDongs().stream()
                        .collect(Collectors.groupingBy(
                                ChiTietHopDong::getMaCV,
                                Collectors.counting()));

        return data.getCongViecs().stream()
                .map(cv -> {
                    long soLan = soLanTheoMaCV.getOrDefault(
                            cv.getMaCV(), 0L);

                    double doanhThu = cv.getDongia() * soLan;

                    return new KetQuaDoanhThuDuKien(
                            cv, soLan, doanhThu);
                })
                .sorted(
                        Comparator.comparingDouble(
                                        KetQuaDoanhThuDuKien::doanhThuDuKien)
                                .reversed()
                                .thenComparing(
                                        item -> item.congViec().getMaCV()))
                .findFirst();
    }

    // Cau 19: Lay danh sach nhan vien theo chuc vu
    public static List<NhanVien> layNhanVienTheoChucVu(
            DataSeeder.DuLieuMau data, ChucVu chucVu) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");
        Objects.requireNonNull(chucVu, "Chuc vu khong duoc null.");

        return data.getNhanViens().stream()
                .filter(nv -> nv.getChucVu() == chucVu)
                .sorted(Comparator.comparing(NhanVien::getMaNV))
                .toList();
    }

    // Cau 20: Dem so hop dong do tung nhan vien lap
    // Van hien thi nhan vien chua lap hop dong, voi so luong bang 0
    public static List<KetQuaDemHopDongNhanVien>
    demHopDongTheoNhanVien(DataSeeder.DuLieuMau data) {

        Objects.requireNonNull(data, "Du lieu khong duoc null.");

        Map<String, Long> soHopDongTheoMaNV =
                data.getHopDongs().stream()
                        .collect(Collectors.groupingBy(
                                HopDong::getMaNV,
                                Collectors.counting()));

        return data.getNhanViens().stream()
                .map(nv -> new KetQuaDemHopDongNhanVien(
                        nv,
                        soHopDongTheoMaNV.getOrDefault(
                                nv.getMaNV(), 0L)))
                .sorted(Comparator.comparing(
                        item -> item.nhanVien().getMaNV()))
                .toList();
    }
}

        // Cau 21
        System.out.println(
                "\n[CAU 21] Tim nhan vien lap nhieu hop dong nhat trong thang 7/2026.");

        var q21 = TruyVanCaNhan.timNhanVienLapNhieuHopDongNhatTheoThang(
                duLieuMau, 7, 2026);

        System.out.println("So ket qua: " + q21.size());

        for (var item : q21) {
            var nv = item.nhanVien();

            System.out.printf(
                    "Ma nhan vien: %s | Ho ten: %s | Chuc vu: %s | So hop dong: %d%n",
                    nv.getMaNV(), nv.getHoten(), nv.getChucVu(),
                    item.soHopDong());
        }

        // Cau 22
        String maNhomTest =
                duLieuMau.getNhomThos().get(0).getMaNhomTho();

        System.out.printf(
                "\n[CAU 22] Liet ke cac tho thuoc nhom %s.%n",
                maNhomTest);

        var q22 = TruyVanCaNhan.layDanhSachThoTheoNhom(
                duLieuMau, maNhomTest);

        System.out.println("So ket qua: " + q22.size());

        for (Tho tho : q22) {
            System.out.printf(
                    "Ma tho: %s | Ten tho: %s | Gioi tinh: %s | Ngay sinh: %s | SDT: %s | Ma nhom: %s%n",
                    tho.getMaTho(),
                    tho.getTenTho(),
                    tho.getPhai(),
                    tho.getNgaysinh() == null
                            ? "Khong co"
                            : tho.getNgaysinh().format(DINH_DANG_NGAY),
                    tho.getSdt(),
                    tho.getMaNhomTho());
        }

        // Cau 23
        System.out.println(
                "\n[CAU 23] Tim truong nhom cua tat ca cac nhom tho.");

        var q23 = TruyVanCaNhan.timTruongNhomCuaTatCaNhom(duLieuMau);

        System.out.println("So ket qua: " + q23.size());

        for (var item : q23) {
            var nhomTho = item.nhomTho();
            var truongNhom = item.truongNhom();

            System.out.printf(
                    "Ma nhom: %s | Ten nhom: %s | Ma truong nhom: %s | Ten truong nhom: %s | SDT: %s%n",
                    nhomTho.getMaNhomTho(),
                    nhomTho.getTenNhom(),
                    truongNhom.getMaTho(),
                    truongNhom.getTenTho(),
                    truongNhom.getSdt());
        }

        // Cau 24
        String soHDTest =
                duLieuMau.getChiTietHopDongs().get(0).getSoHD();

        System.out.printf(
                "\n[CAU 24] Liet ke cong viec va tho thuc hien theo hop dong %s.%n",
                soHDTest);

        var q24 = TruyVanCaNhan.layCongViecVaThoTheoHopDong(
                duLieuMau, soHDTest);

        System.out.println("So ket qua: " + q24.size());

        for (var item : q24) {
            var ct = item.chiTiet();
            var cv = item.congViec();
            var tho = item.tho();

            System.out.printf(
                    "So hop dong: %s | Ma cong viec: %s | Ten cong viec: %s | Don gia: %,.0f dong | Ma tho: %s | Ten tho: %s | Trang thai: %s%n",
                    ct.getSoHD(),
                    cv.getMaCV(),
                    cv.getTenCV(),
                    cv.getDongia(),
                    tho.getMaTho(),
                    tho.getTenTho(),
                    ct.getTrangThaiCV());
        }

        // Cau 25
        System.out.println(
                "\n[CAU 25] Liet ke cac chi tiet cong viec chua thuc hien hoac dang thuc hien.");

        var q25 = TruyVanCaNhan.layChiTietChuaHoacDangThucHien(duLieuMau);

        System.out.println("So ket qua: " + q25.size());

        for (ChiTietHopDong ct : q25) {
            CongViec cv = duLieuMau.getCongViecs().stream()
                    .filter(item -> item.getMaCV().equals(ct.getMaCV()))
                    .findFirst()
                    .orElse(null);

            Tho tho = duLieuMau.getThos().stream()
                    .filter(item -> item.getMaTho().equals(ct.getMaTho()))
                    .findFirst()
                    .orElse(null);

            System.out.printf(
                    "So hop dong: %s | Ma cong viec: %s | Ten cong viec: %s | Ma tho: %s | Ten tho: %s | Trang thai: %s%n",
                    ct.getSoHD(),
                    ct.getMaCV(),
                    cv == null ? "Khong tim thay" : cv.getTenCV(),
                    ct.getMaTho(),
                    tho == null ? "Khong tim thay" : tho.getTenTho(),
                    ct.getTrangThaiCV());
        }

        // Cau 26
        String maThoTest = duLieuMau.getThos().get(0).getMaTho();

        System.out.printf(
                "\n[CAU 26] Dem so hang muc da hoan thanh hoac dang thuc hien cua tho %s.%n",
                maThoTest);

        var q26 = TruyVanCaNhan.demHangMucThoDaHoacDangThucHien(
                duLieuMau, maThoTest);

        System.out.println("So hang muc: " + q26);

        // Cau 27
        String soHDThuTest =
                duLieuMau.getPhieuThus().get(0).getSoHD();

        System.out.printf(
                "\n[CAU 27] Liet ke cac phieu thu thuoc hop dong %s.%n",
                soHDThuTest);

        var q27 = TruyVanCaNhan.timPhieuThuTheoHopDong(
                duLieuMau, soHDThuTest);

        System.out.println("So ket qua: " + q27.size());

        for (PhieuThu pt : q27) {
            System.out.printf(
                    "Ma phieu thu: %s | So hop dong: %s | Ngay thanh toan: %s | So tien: %,.0f dong | Phuong thuc: %s | Ma nhan vien: %s%n",
                    pt.getMaPhieuThu(),
                    pt.getSoHD(),
                    pt.getNgayTT() == null
                            ? "Khong co"
                            : pt.getNgayTT().format(DINH_DANG_NGAY),
                    pt.getSoTienTT(),
                    pt.getPhuongThucTT(),
                    pt.getMaNV());
        }

        // Cau 28
        System.out.println(
                "\n[CAU 28] Liet ke cac phieu thu thanh toan bang chuyen khoan.");

        var q28 = TruyVanCaNhan.layPhieuThuChuyenKhoan(duLieuMau);

        System.out.println("So ket qua: " + q28.size());

        for (PhieuThu pt : q28) {
            System.out.printf(
                    "Ma phieu thu: %s | So hop dong: %s | Ngay thanh toan: %s | So tien: %,.0f dong | Phuong thuc: %s | Ma nhan vien: %s%n",
                    pt.getMaPhieuThu(),
                    pt.getSoHD(),
                    pt.getNgayTT() == null
                            ? "Khong co"
                            : pt.getNgayTT().format(DINH_DANG_NGAY),
                    pt.getSoTienTT(),
                    pt.getPhuongThucTT(),
                    pt.getMaNV());
        }

        // Cau 29
        LocalDate tuNgay = duLieuMau.getPhieuThus().stream()
                .map(PhieuThu::getNgayTT)
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate denNgay = duLieuMau.getPhieuThus().stream()
                .map(PhieuThu::getNgayTT)
                .max(LocalDate::compareTo)
                .orElseThrow();

        System.out.printf(
                "\n[CAU 29] Tinh tong doanh thu tu %s den %s.%n",
                tuNgay.format(DINH_DANG_NGAY),
                denNgay.format(DINH_DANG_NGAY));

        double q29 = TruyVanCaNhan.tinhDoanhThuTheoKhoangNgay(
                duLieuMau, tuNgay, denNgay);

        System.out.printf("Tong doanh thu: %,.0f dong%n", q29);

        // Cau 30
        System.out.println(
                "\n[CAU 30] Thong ke tong so tien theo tung phuong thuc thanh toan.");

        var q30 = TruyVanCaNhan.tinhTongTienTheoPhuongThuc(duLieuMau);

        System.out.println(
                "So nhom phuong thuc thanh toan: " + q30.size());

        for (var item : q30) {
            System.out.printf(
                    "Phuong thuc: %s | Tong tien: %,.0f dong%n",
                    item.phuongThuc(),
                    item.tongTien());
        }

        System.out.println(
                "\n========== KET THUC KIEM THU JAVA STREAM ==========");
    }
}
