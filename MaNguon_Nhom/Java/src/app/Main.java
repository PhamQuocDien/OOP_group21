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
                "\n========== KIEM THU 10 CAU TRUY VAN CA NHAN (JAVA STREAM) ==========");

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
