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
import model.enums.ChucVu;

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
                "\n========== KIEM THU 10 CAU TRUY VAN ĐÀU TIÊN 1-10 ==========");

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

        
        
        // ==========================================
        // KIEM THU CAC CAU TRUY VAN 11–20
        // ==========================================

        System.out.println(
                "\n========== KIEM THU CAC CAU TRUY VAN 11–20 ==========");

        // Cau 11
        System.out.println(
                "\n[CAU 11] Lay 5 hop dong co tong tien cao nhat.");

        var q11 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan
                .LayTop5HopDongTongTienCaoNhat(duLieuMau);

        System.out.println("So ket qua: " + q11.size());

        for (var hd : q11) {
            System.out.printf(
                    "So hop dong: %s | Ngay lap: %s"
                            + " | Tong tien: %,.0f dong"
                            + " | Trang thai: %s | Ma khach hang: %s%n",
                    hd.getSoHD(),
                    hd.getNgaylap().format(DINH_DANG_NGAY),
                    hd.getTongtien(),
                    hd.getTinhtrangHD(),
                    hd.getMaKH());
        }

        // Cau 12
        System.out.println(
                "\n[CAU 12] Dem so hop dong theo tung trang thai.");

        var q12 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.demHopDongTheoTrangThai(duLieuMau);

        System.out.println("So trang thai: " + q12.size());

        for (var item : q12) {
            System.out.printf(
                    "Trang thai: %s | So hop dong: %d%n",
                    item.trangThai(),
                    item.soLuong());
        }

        // Cau 13
        System.out.println(
                "\n[CAU 13] Lay danh sach tat ca nhom cong viec.");

        var q13 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.layTatCaNhomCongViec(duLieuMau);

        System.out.println("So ket qua: " + q13.size());

        for (var item : q13) {
            System.out.printf(
                    "Ma nhom: %s | Ten nhom: %s | Noi dung: %s%n",
                    item.getMaNhomCV(),
                    item.getTenNhom(),
                    item.getNoidung());
        }

        // Cau 14
        String tenNhomTest14 = duLieuMau.getNhomCongViecs()
                .get(0).getTenNhom();

        System.out.printf(
                "\n[CAU 14] Tim nhom cong viec theo ten '%s'.%n",
                tenNhomTest14);

        var q14 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.timNhomCongViecTheoTen(
                duLieuMau, tenNhomTest14);

        if (q14.isPresent()) {
            var nhom14 = q14.get();

            System.out.printf(
                    "Ma nhom: %s | Ten nhom: %s | Noi dung: %s%n",
                    nhom14.getMaNhomCV(),
                    nhom.getTenNhom(),
                    nhom14.getNoidung());
        } else {
            System.out.println("Khong tim thay nhom cong viec.");
        }

        // Cau 15
        String maNhomTest15 = duLieuMau.getNhomCongViecs()
                .get(0).getMaNhomCV();

        System.out.printf(
                "\n[CAU 15] Lay cong viec theo ma nhom %s.%n",
                maNhomTest15);

        var q15 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.layCongViecTheoMaNhom(
                duLieuMau, maNhomTest15);

        System.out.println("So ket qua: " + q15.size());

        for (var cv : q15) {
            System.out.printf(
                    "Ma cong viec: %s | Ten cong viec: %s"
                            + " | Don gia: %,.0f dong | Ma nhom: %s%n",
                    cv.getMaCV(),
                    cv.getTenCV(),
                    cv.getDongia(),
                    cv.getMaNhomCV());
        }

        // Cau 16
        System.out.println(
                "\n[CAU 16] Lay cong viec co don gia tu 500.000 dong tro len.");

        var q16 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.layCongViecDonGiaTu500000(duLieuMau);

        System.out.println("So ket qua: " + q16.size());

        for (var cv : q16) {
            System.out.printf(
                    "Ma cong viec: %s | Ten cong viec: %s"
                            + " | Don gia: %,.0f dong | Ma nhom: %s%n",
                    cv.getMaCV(),
                    cv.getTenCV(),
                    cv.getDongia(),
                    cv.getMaNhomCV());
        }

        // Cau 17
        System.out.println(
                "\n[CAU 17] Dem so lan moi cong viec xuat hien"
                        + " trong chi tiet hop dong.");

        var q17 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.demSoLanCongViecXuatHien(duLieuMau);

        System.out.println("So ket qua: " + q17.size());

        for (var item : q17) {
            System.out.printf(
                    "Ma cong viec: %s | Ten cong viec: %s"
                            + " | So lan xuat hien: %d%n",
                    item.congViec().getMaCV(),
                    item.congViec().getTenCV(),
                    item.soLanXuatHien());
        }

        // Cau 18
        System.out.println(
                "\n[CAU 18] Tim cong viec co doanh thu du kien cao nhat.");

        var q18 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.timCongViecCoDoanhThuDuKienCaoNhat(duLieuMau);

        if (q18.isPresent()) {
            var item = q18.get();

            System.out.printf(
                    "Ma cong viec: %s | Ten cong viec: %s"
                            + " | Don gia: %,.0f dong"
                            + " | So lan xuat hien: %d"
                            + " | Doanh thu du kien: %,.0f dong%n",
                    item.congViec().getMaCV(),
                    item.congViec().getTenCV(),
                    item.congViec().getDongia(),
                    item.soLanXuatHien(),
                    item.doanhThuDuKien());
        } else {
            System.out.println("Khong co cong viec.");
        }

        // Cau 19
        System.out.println(
                "\n[CAU 19] Lay danh sach nhan vien theo chuc vu.");

        for (ChucVu chucVu : ChucVu.values()) {
            var q19 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.layNhanVienTheoChucVu(
                    duLieuMau, chucVu);

            System.out.println("\nChuc vu: " + chucVu);
            System.out.println("So ket qua: " + q19.size());

            for (var nv : q19) {
                System.out.printf(
                        "Ma nhan vien: %s | Ho ten: %s"
                                + " | Chuc vu: %s%n",
                        nv.getMaNV(),
                        nv.getHoten(),
                        nv.getChucVu());
            }
        }

        // Cau 20
        System.out.println(
                "\n[CAU 20] Dem so hop dong do tung nhan vien lap.");

        var q20 = SanPham_CaNhan.LeNhatNinh.TruyVan.java.TruyVanCaNhan.demHopDongTheoNhanVien(duLieuMau);

        System.out.println("So ket qua: " + q20.size());

        for (var item : q20) {
            System.out.printf(
                    "Ma nhan vien: %s | Ho ten: %s"
                            + " | So hop dong: %d%n",
                    item.nhanVien().getMaNV(),
                    item.nhanVien().getHoten(),
                    item.soHopDong());
        }

        System.out.println(
                "\n========== KET THUC KIEM THU CAU 11–20 ==========");


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
