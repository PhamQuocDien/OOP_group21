package app;
import data.DataSeeder;
import dao.InMemoryDAO;
import model.entity.NhomTho;
import java.time.LocalDate;
import jv.TruyVanCaNhan;
public class Main {
    public static void main(String[] args) {
        InMemoryDAO<NhomTho> dao = new InMemoryDAO<>(NhomTho::getMaNhomTho);

        NhomTho nhom = new NhomTho("NT01", "Nhom sua chua", "T01");
        dao.add(nhom);

        System.out.println("So nhom tho: " + dao.getAll().size());
        System.out.println("Ten nhom: " + dao.findById("NT01").getTenNhom());
        System.out.println("Xoa thanh cong: " + dao.removeById("NT01"));
        System.out.println("So luong sau xoa: " + dao.getAll().size());
        System.out.println("\n=== KIEM TRA DATA SEEDER ===");

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
        System.out.println("\n=== KIEM THU 10 TRUY VAN JAVA STREAM ===");

        var q21 = TruyVanCaNhan
                .timNhanVienLapNhieuHopDongNhatTheoThang(duLieuMau, 7, 2026);
        System.out.println("Cau 21: " + q21.size() + " ket qua");

        String maNhomTest = duLieuMau.getNhomThos().get(0).getMaNhomTho();
        var q22 = TruyVanCaNhan.layDanhSachThoTheoNhom(duLieuMau, maNhomTest);
        System.out.println("Cau 22: " + q22.size() + " ket qua");

        var q23 = TruyVanCaNhan.timTruongNhomCuaTatCaNhom(duLieuMau);
        System.out.println("Cau 23: " + q23.size() + " ket qua");

        String soHDTest = duLieuMau.getChiTietHopDongs().get(0).getSoHD();
        var q24 = TruyVanCaNhan.layCongViecVaThoTheoHopDong(duLieuMau, soHDTest);
        System.out.println("Cau 24: " + q24.size() + " ket qua");

        var q25 = TruyVanCaNhan.layChiTietChuaHoacDangThucHien(duLieuMau);
        System.out.println("Cau 25: " + q25.size() + " ket qua");

        String maThoTest = duLieuMau.getThos().get(0).getMaTho();
        var q26 = TruyVanCaNhan
                .demHangMucThoDaHoacDangThucHien(duLieuMau, maThoTest);
        System.out.println("Cau 26: " + q26 + " ket qua");

        String soHDThuTest = duLieuMau.getPhieuThus().get(0).getSoHD();
        var q27 = TruyVanCaNhan.timPhieuThuTheoHopDong(duLieuMau, soHDThuTest);
        System.out.println("Cau 27: " + q27.size() + " ket qua");

        var q28 = TruyVanCaNhan.layPhieuThuChuyenKhoan(duLieuMau);
        System.out.println("Cau 28: " + q28.size() + " ket qua");

        LocalDate tuNgay = duLieuMau.getPhieuThus().stream()
                .map(pt -> pt.getNgayTT())
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate denNgay = duLieuMau.getPhieuThus().stream()
                .map(pt -> pt.getNgayTT())
                .max(LocalDate::compareTo)
                .orElseThrow();

        double q29 = TruyVanCaNhan
                .tinhDoanhThuTheoKhoangNgay(duLieuMau, tuNgay, denNgay);
        System.out.printf("Cau 29: Tong doanh thu = %,.0f%n", q29);

        var q30 = TruyVanCaNhan.tinhTongTienTheoPhuongThuc(duLieuMau);
        System.out.println("Cau 30: " + q30.size() + " nhom thanh toan");

        System.out.println("=== KET THUC KIEM THU JAVA STREAM ===");
    }
}
