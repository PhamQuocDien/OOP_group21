package data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import model.entity.ChiTietHopDong;
import model.entity.CongViec;
import model.entity.HopDong;
import model.entity.KhaNangTho;
import model.entity.KhachHang;
import model.entity.NhanVien;
import model.entity.NhomCongViec;
import model.entity.NhomTho;
import model.entity.PhieuThu;
import model.entity.ThietBi;
import model.entity.Tho;
import model.enums.CapDo;
import model.enums.ChucVu;
import model.enums.PhuongThucThanhToan;
import model.enums.TrangThaiCongViec;
import model.enums.TrangThaiHopDong;

/**
 * Khoi tao cung bo du lieu logic voi DataSeeder.cs.
 * Khong thay doi Entity, DAO hoac Main.java.
 */
public final class DataSeeder {
    private DataSeeder() {
    }

    /** Chua 11 danh sach du lieu mau dung chung cho cac truy van. */
    public static final class DuLieuMau {
        private final List<NhomTho> nhomThos = new ArrayList<>();
        private final List<Tho> thos = new ArrayList<>();
        private final List<NhomCongViec> nhomCongViecs = new ArrayList<>();
        private final List<CongViec> congViecs = new ArrayList<>();
        private final List<KhachHang> khachHangs = new ArrayList<>();
        private final List<ThietBi> thietBis = new ArrayList<>();
        private final List<NhanVien> nhanViens = new ArrayList<>();
        private final List<HopDong> hopDongs = new ArrayList<>();
        private final List<ChiTietHopDong> chiTietHopDongs = new ArrayList<>();
        private final List<PhieuThu> phieuThus = new ArrayList<>();
        private final List<KhaNangTho> khaNangThos = new ArrayList<>();

        public List<NhomTho> getNhomThos() { return nhomThos; }
        public List<Tho> getThos() { return thos; }
        public List<NhomCongViec> getNhomCongViecs() { return nhomCongViecs; }
        public List<CongViec> getCongViecs() { return congViecs; }
        public List<KhachHang> getKhachHangs() { return khachHangs; }
        public List<ThietBi> getThietBis() { return thietBis; }
        public List<NhanVien> getNhanViens() { return nhanViens; }
        public List<HopDong> getHopDongs() { return hopDongs; }
        public List<ChiTietHopDong> getChiTietHopDongs() { return chiTietHopDongs; }
        public List<PhieuThu> getPhieuThus() { return phieuThus; }
        public List<KhaNangTho> getKhaNangThos() { return khaNangThos; }
    }

    // Moi hop dong thuoc mot nhom dich vu: 1..5 tuong ung NCV01..NCV05.
    private static final int[] NHOM_THEO_HOP_DONG = {
        1, 2, 3, 4, 3, 4,
        1, 1, 5, 5, 5, 1,
        2, 2, 4, 5, 5, 3,
        5, 5, 3, 2, 2, 3,
        3, 3, 4, 4, 4, 5,
        2, 4, 1, 2, 1, 1
    };

    // KH01..KH04 chi co hop dong chua thanh toan; KH05..KH15 co hop dong da thanh toan.
    private static final String[] KHACH_HANG_THEO_HOP_DONG = {
        "KH01", "KH02", "KH03", "KH04", "KH03", "KH04",
        "KH05", "KH05", "KH05", "KH06", "KH06", "KH06",
        "KH07", "KH07", "KH07", "KH08", "KH08", "KH08",
        "KH09", "KH09", "KH09", "KH10", "KH10", "KH10",
        "KH11", "KH11", "KH11", "KH12", "KH12", "KH12",
        "KH13", "KH13", "KH14", "KH14", "KH15", "KH15"
    };

    private static final int[][] NHAN_VIEN_TU_VAN_THEO_THANG = {
        // Thang 7: NV01 lap 4 hop dong, nhieu nhat trong thang.
        { 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5 },
        // Thang 8: NV02 lap 4 hop dong, nhieu nhat trong thang.
        { 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 1 },
        // Thang 9: NV03 lap 4 hop dong, nhieu nhat trong thang.
        { 3, 3, 3, 3, 4, 4, 4, 5, 5, 1, 1, 2 }
    };

    /** Tao du lieu mau va tu dong kiem tra tinh toan ven truoc khi tra ve. */
    public static DuLieuMau taoDuLieuMau() {
        DuLieuMau data = new DuLieuMau();

        taoKhachHang(data);
        taoNhanVien(data);
        taoNhomCongViec(data);
        taoCongViec(data);
        taoThoVaNhomTho(data);
        taoKhaNangTho(data);
        Map<String, String> thietBiTheoKhachVaNhom = taoThietBi(data);
        taoHopDongVaChiTiet(data, thietBiTheoKhachVaNhom);
        taoPhieuThu(data);
        kiemTraToanVen(data, thietBiTheoKhachVaNhom);

        return data;
    }

    private static void taoKhachHang(DuLieuMau data) {
        data.khachHangs.add(new KhachHang("KH01", "Nguyen Hoang Anh", "Nam", LocalDate.of(1992, 4, 12), "Quan 1, TP. Ho Chi Minh", "0901000001"));
        data.khachHangs.add(new KhachHang("KH02", "Tran Minh Chau", "Nu", LocalDate.of(1989, 7, 23), "Quan 3, TP. Ho Chi Minh", "0901000002"));
        data.khachHangs.add(new KhachHang("KH03", "Le Quoc Bao", "Nam", LocalDate.of(1995, 2, 15), "Quan 5, TP. Ho Chi Minh", "0901000003"));
        data.khachHangs.add(new KhachHang("KH04", "Pham Ngoc Mai", "Nu", LocalDate.of(1990, 11, 6), "Quan 10, TP. Ho Chi Minh", "0901000004"));
        data.khachHangs.add(new KhachHang("KH05", "Vo Thanh Dat", "Nam", LocalDate.of(1987, 9, 19), "Binh Thanh, TP. Ho Chi Minh", "0901000005"));
        data.khachHangs.add(new KhachHang("KH06", "Dang Thu Ha", "Nu", LocalDate.of(1994, 1, 30), "Go Vap, TP. Ho Chi Minh", "0901000006"));
        data.khachHangs.add(new KhachHang("KH07", "Bui Gia Huy", "Nam", LocalDate.of(1998, 6, 21), "Tan Binh, TP. Ho Chi Minh", "0901000007"));
        data.khachHangs.add(new KhachHang("KH08", "Do Khanh Linh", "Nu", LocalDate.of(1991, 12, 8), "Phu Nhuan, TP. Ho Chi Minh", "0901000008"));
        data.khachHangs.add(new KhachHang("KH09", "Ho Minh Quan", "Nam", LocalDate.of(1993, 3, 4), "Thu Duc, TP. Ho Chi Minh", "0901000009"));
        data.khachHangs.add(new KhachHang("KH10", "Ngo Bao Tram", "Nu", LocalDate.of(1988, 5, 27), "Quan 7, TP. Ho Chi Minh", "0901000010"));
        data.khachHangs.add(new KhachHang("KH11", "Duong Quoc Viet", "Nam", LocalDate.of(1997, 8, 13), "Quan 8, TP. Ho Chi Minh", "0901000011"));
        data.khachHangs.add(new KhachHang("KH12", "Ly Thao Nguyen", "Nu", LocalDate.of(1990, 10, 1), "Binh Tan, TP. Ho Chi Minh", "0901000012"));
        data.khachHangs.add(new KhachHang("KH13", "Nguyen Duc Phuc", "Nam", LocalDate.of(1986, 2, 20), "Tan Phu, TP. Ho Chi Minh", "0901000013"));
        data.khachHangs.add(new KhachHang("KH14", "Tran My Duyen", "Nu", LocalDate.of(1996, 4, 17), "Hoc Mon, TP. Ho Chi Minh", "0901000014"));
        data.khachHangs.add(new KhachHang("KH15", "Phan Tuan Kiet", "Nam", LocalDate.of(1992, 12, 29), "Nha Be, TP. Ho Chi Minh", "0901000015"));
    }

    private static void taoNhanVien(DuLieuMau data) {
        data.nhanViens.add(new NhanVien("NV01", "Nguyen Van Hung", "Nam", LocalDate.of(1988, 3, 10), "Quan 1, TP. Ho Chi Minh", "0912000001", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV02", "Tran Thanh Tung", "Nam", LocalDate.of(1990, 5, 22), "Quan 3, TP. Ho Chi Minh", "0912000002", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV03", "Le Thi Thu", "Nu", LocalDate.of(1992, 8, 14), "Quan 5, TP. Ho Chi Minh", "0912000003", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV04", "Pham Nhat Nam", "Nam", LocalDate.of(1987, 11, 2), "Quan 10, TP. Ho Chi Minh", "0912000004", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV05", "Vo Ngoc Yen", "Nu", LocalDate.of(1993, 6, 18), "Binh Thanh, TP. Ho Chi Minh", "0912000005", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV06", "Nguyen Thi Loan", "Nu", LocalDate.of(1989, 1, 9), "Go Vap, TP. Ho Chi Minh", "0912000006", ChucVu.THU_NGAN));
        data.nhanViens.add(new NhanVien("NV07", "Truong Minh Anh", "Nam", LocalDate.of(1991, 4, 25), "Tan Binh, TP. Ho Chi Minh", "0912000007", ChucVu.THU_NGAN));
        data.nhanViens.add(new NhanVien("NV08", "Do Thanh Phuong", "Nu", LocalDate.of(1994, 9, 7), "Phu Nhuan, TP. Ho Chi Minh", "0912000008", ChucVu.THU_NGAN));
        data.nhanViens.add(new NhanVien("NV09", "Bui Thi Hanh", "Nu", LocalDate.of(1990, 12, 11), "Thu Duc, TP. Ho Chi Minh", "0912000009", ChucVu.THU_NGAN));
    }

    private static void taoNhomCongViec(DuLieuMau data) {
        data.nhomCongViecs.add(new NhomCongViec("NCV01", "Sua chua dien thoai va may tinh bang", "Kiem tra, bao duong va xu ly loi thiet bi di dong."));
        data.nhomCongViecs.add(new NhomCongViec("NCV02", "Sua chua may tinh xach tay", "Bao duong va thay the linh kien laptop."));
        data.nhomCongViecs.add(new NhomCongViec("NCV03", "Sua chua may tinh de ban", "Kiem tra, nang cap va sua chua may tinh de ban."));
        data.nhomCongViecs.add(new NhomCongViec("NCV04", "Sua chua thiet bi hien thi", "Sua chua tivi va man hinh."));
        data.nhomCongViecs.add(new NhomCongViec("NCV05", "Thiet bi mang va bo mach chuyen dung", "Cau hinh, sua chua thiet bi mang va bo mach."));
    }

    private static void taoCongViec(DuLieuMau data) {
        data.congViecs.add(new CongViec("CV01", 350_000, "Kiem tra va chan doan thiet bi di dong", "Kiem tra tong quat va xac dinh loi thiet bi di dong.", "NCV01"));
        data.congViecs.add(new CongViec("CV02", 450_000, "Cai dat va khoi phuc phan mem thiet bi di dong", "Cai dat hoac khoi phuc phan mem theo yeu cau.", "NCV01"));
        data.congViecs.add(new CongViec("CV03", 500_000, "Ve sinh va bao duong thiet bi di dong", "Ve sinh thiet bi va kiem tra hoat dong.", "NCV01"));
        data.congViecs.add(new CongViec("CV04", 600_000, "Sua loa hoac micro thiet bi di dong", "Kiem tra va xu ly loi loa hoac micro.", "NCV01"));

        data.congViecs.add(new CongViec("CV05", 450_000, "Ve sinh laptop", "Ve sinh quat, khe tan nhiet va kiem tra nhiet do.", "NCV02"));
        data.congViecs.add(new CongViec("CV06", 500_000, "Sua cong sac laptop", "Kiem tra va sua loi cong sac.", "NCV02"));
        data.congViecs.add(new CongViec("CV07", 700_000, "Nang cap RAM laptop", "Lap dat va kiem tra bo nho RAM tuong thich.", "NCV02"));
        data.congViecs.add(new CongViec("CV08", 900_000, "Thay o SSD laptop", "Thay o SSD va kiem tra nhan dien o dia.", "NCV02"));

        data.congViecs.add(new CongViec("CV09", 350_000, "Kiem tra va ve sinh may tinh de ban", "Ve sinh may va kiem tra cac linh kien co ban.", "NCV03"));
        data.congViecs.add(new CongViec("CV10", 600_000, "Nang cap RAM may tinh de ban", "Lap dat va kiem tra bo nho RAM.", "NCV03"));
        data.congViecs.add(new CongViec("CV11", 800_000, "Thay o SSD may tinh de ban", "Thay o luu tru va kiem tra hoat dong.", "NCV03"));
        data.congViecs.add(new CongViec("CV12", 1_200_000, "Sua bo mach chu may tinh de ban", "Kiem tra va sua loi bo mach chu.", "NCV03"));

        data.congViecs.add(new CongViec("CV13", 800_000, "Kiem tra bo nguon thiet bi hien thi", "Kiem tra va sua bo nguon thiet bi hien thi.", "NCV04"));
        data.congViecs.add(new CongViec("CV14", 1_000_000, "Sua loi hinh anh thiet bi hien thi", "Kiem tra tin hieu va xu ly loi hien thi.", "NCV04"));
        data.congViecs.add(new CongViec("CV15", 1_500_000, "Sua bo mach thiet bi hien thi", "Kiem tra va sua bo mach dieu khien thiet bi hien thi.", "NCV04"));
        data.congViecs.add(new CongViec("CV16", 1_800_000, "Thay tam nen hien thi", "Thay tam nen tuong thich va kiem tra hien thi.", "NCV04"));

        data.congViecs.add(new CongViec("CV17", 300_000, "Cau hinh router Wi-Fi", "Thiet lap mang khong day va bao mat co ban.", "NCV05"));
        data.congViecs.add(new CongViec("CV18", 450_000, "Sua bo nguon modem hoac router", "Kiem tra va xu ly loi nguon thiet bi mang.", "NCV05"));
        data.congViecs.add(new CongViec("CV19", 850_000, "Thay cong WAN hoac LAN", "Thay cong ket noi va kiem tra truyen du lieu.", "NCV05"));
        data.congViecs.add(new CongViec("CV20", 4_200_000, "Thay bo mach thiet bi mang cao cap", "Thay bo mach chinh cho thiet bi mang hieu nang cao.", "NCV05"));
    }

    private static void taoThoVaNhomTho(DuLieuMau data) {
        data.thos.add(new Tho("T01", "Nguyen Quoc Bao", "Nam", LocalDate.of(1988, 1, 15), "0933000001", "NT01"));
        data.thos.add(new Tho("T02", "Tran Minh Khang", "Nam", LocalDate.of(1991, 3, 21), "0933000002", "NT01"));
        data.thos.add(new Tho("T03", "Le Hoang Phuc", "Nam", LocalDate.of(1994, 7, 12), "0933000003", "NT01"));
        data.thos.add(new Tho("T04", "Pham Anh Tuan", "Nam", LocalDate.of(1987, 10, 9), "0933000004", "NT02"));
        data.thos.add(new Tho("T05", "Vo Thanh Long", "Nam", LocalDate.of(1992, 5, 17), "0933000005", "NT02"));
        data.thos.add(new Tho("T06", "Dang Nhat Minh", "Nam", LocalDate.of(1995, 12, 4), "0933000006", "NT02"));
        data.thos.add(new Tho("T07", "Bui Duc Thinh", "Nam", LocalDate.of(1989, 6, 26), "0933000007", "NT03"));
        data.thos.add(new Tho("T08", "Do Gia Huy", "Nam", LocalDate.of(1993, 8, 2), "0933000008", "NT03"));
        data.thos.add(new Tho("T09", "Ho Thanh Phong", "Nam", LocalDate.of(1996, 2, 18), "0933000009", "NT03"));
        data.thos.add(new Tho("T10", "Ngo Minh Tri", "Nam", LocalDate.of(1986, 9, 13), "0933000010", "NT04"));
        data.thos.add(new Tho("T11", "Duong Quoc Khanh", "Nam", LocalDate.of(1990, 4, 7), "0933000011", "NT04"));
        data.thos.add(new Tho("T12", "Ly Hoang Nam", "Nam", LocalDate.of(1997, 11, 29), "0933000012", "NT04"));

        // Them nhom sau danh sach tho vi maTruongNhom phai tro den tho ton tai.
        data.nhomThos.add(new NhomTho("NT01", "To ky thuat thiet bi di dong", "T01"));
        data.nhomThos.add(new NhomTho("NT02", "To ky thuat may tinh", "T04"));
        data.nhomThos.add(new NhomTho("NT03", "To sua thiet bi hien thi", "T07"));
        data.nhomThos.add(new NhomTho("NT04", "To thiet bi mang va bo mach", "T10"));
    }

    private static void taoKhaNangTho(DuLieuMau data) {
        CapDo[] capDos = { CapDo.CO_BAN, CapDo.KHA, CapDo.TOT, CapDo.CHUYEN_GIA };

        // Moi tho co 5 ky nang khac nhau; 60 cap (tho, cong viec) khong trung nhau.
        for (int i = 0; i < data.thos.size(); i++) {
            int batDau = (i * 7) % data.congViecs.size();

            for (int k = 0; k < 5; k++) {
                int chiSoCongViec = (batDau + k) % data.congViecs.size();
                String maCV = data.congViecs.get(chiSoCongViec).getMaCV();

                data.khaNangThos.add(new KhaNangTho(
                        data.thos.get(i).getMaTho(),
                        maCV,
                        capDos[(i + k) % capDos.length]));
            }
        }
    }

    private static Map<String, String> taoThietBi(DuLieuMau data) {
        String[][] mau = {
            {"KH01", "1", "Dien thoai", "Samsung", "Galaxy S22", "Nut kinh man hinh"},
            {"KH02", "2", "Laptop", "Dell", "Inspiron 14", "Khong khoi dong on dinh"},
            {"KH03", "3", "May tinh de ban", "Dell", "OptiPlex 7090", "May chay cham"},
            {"KH04", "4", "Tivi", "Samsung", "Crystal UHD 43 inch", "Khong len hinh"},
            {"KH05", "1", "Dien thoai", "Apple", "iPhone 13", "Pin chai, sac cham"},
            {"KH05", "5", "Router Wi-Fi", "TP-Link", "Archer AX23", "Mat ket noi khong day"},
            {"KH06", "5", "Router Wi-Fi", "ASUS", "RT-AX55", "Tin hieu Wi-Fi khong on dinh"},
            {"KH06", "1", "Dien thoai", "Apple", "iPhone 12", "Loa thoai bi re"},
            {"KH07", "2", "Laptop", "Lenovo", "ThinkPad E14", "Quat tan nhiet keu lon"},
            {"KH07", "4", "Man hinh may tinh", "LG", "27MP400", "Man hinh nhap nhay"},
            {"KH08", "5", "Router Wi-Fi", "TP-Link", "Deco M4", "Khong cap dia chi mang on dinh"},
            {"KH08", "3", "May tinh de ban", "HP", "Pavilion Desktop", "Loi bo nho"},
            {"KH09", "5", "Router Wi-Fi", "Huawei", "WiFi AX3", "Cong LAN khong hoat dong"},
            {"KH09", "3", "May tinh de ban", "Lenovo", "ThinkCentre M70", "O luu tru bao loi"},
            {"KH10", "2", "Laptop", "HP", "ProBook 440", "Cong sac tiep xuc kem"},
            {"KH10", "3", "May tinh de ban", "ASUS", "ExpertCenter D5", "May qua nhiet"},
            {"KH11", "3", "May tinh de ban", "Acer", "Veriton X", "Loi khoi dong"},
            {"KH11", "4", "Man hinh may tinh", "Samsung", "Essential 24 inch", "Mat tin hieu hinh anh"},
            {"KH12", "4", "Tivi", "Sony", "Bravia 50 inch", "Co tieng nhung khong co hinh"},
            {"KH12", "5", "Modem mang", "TP-Link", "Archer VR400", "Ket noi mang chap chon"},
            {"KH13", "2", "Laptop", "Acer", "Aspire 5", "May chay cham"},
            {"KH13", "4", "Tivi", "LG", "UHD AI 43 inch", "Loi mau sac"},
            {"KH14", "1", "May tinh bang", "Apple", "iPad Air (5th Gen)", "Khong nhan sac on dinh"},
            {"KH14", "2", "Laptop", "ASUS", "Vivobook 15", "Khong nhan o SSD"},
            {"KH15", "1", "Dien thoai", "Xiaomi", "Redmi Note 12", "Loi loa ngoai"}
        };

        Map<String, String> traCuu = new HashMap<>();

        for (int i = 0; i < mau.length; i++) {
            String maKH = mau[i][0];
            int nhom = Integer.parseInt(mau[i][1]);
            String maThietBi = String.format("TB%02d", i + 1);

            data.thietBis.add(new ThietBi(
                    maThietBi, mau[i][2], mau[i][3], mau[i][4], mau[i][5], maKH));

            traCuu.put(khoaThietBi(maKH, nhom), maThietBi);
        }

        return traCuu;
    }

    private static void taoHopDongVaChiTiet(
            DuLieuMau data, Map<String, String> thietBiTheoKhachVaNhom) {

        int[] mauCombo7 = { 0, 1, 2, 3, 1, 2, 3 };
        int[] mauCombo8 = { 0, 1, 2, 3, 0, 1, 2, 3 };

        int[][] comboTheoViTriLoaiTru = {
            { 1, 2, 3 },
            { 0, 2, 3 },
            { 0, 1, 3 },
            { 0, 1, 2 }
        };

        int[] soLanTheoNhom = new int[5];
        Map<String, Integer> soLanPhanCong = new HashMap<>();

        for (Tho tho : data.thos) {
            soLanPhanCong.put(tho.getMaTho(), 0);
        }

        for (int i = 0; i < 36; i++) {
            int nhomDichVu = NHOM_THEO_HOP_DONG[i];
            String maKH = KHACH_HANG_THEO_HOP_DONG[i];
            String maThietBi = thietBiTheoKhachVaNhom.get(
                    khoaThietBi(maKH, nhomDichVu));

            if (maThietBi == null) {
                throw new IllegalStateException(
                        "Khong tim thay thiet bi phu hop cho " + maKH
                                + ", nhom dich vu " + nhomDichVu + ".");
            }

            int viTriTrongNhom = soLanTheoNhom[nhomDichVu - 1]++;
            int[] mauCombo = nhomDichVu == 5 ? mauCombo8 : mauCombo7;
            int viTriCombo = mauCombo[viTriTrongNhom];
            int soCongViecDauTien = (nhomDichVu - 1) * 4 + 1;
            int[] soThuTuCongViec = new int[3];

            for (int j = 0; j < 3; j++) {
                soThuTuCongViec[j] =
                        soCongViecDauTien + comboTheoViTriLoaiTru[viTriCombo][j];
            }

            LocalDate ngayLap = LocalDate.of(2026, 7 + (i / 12), 2 + (i % 12) * 2);
            LocalDate ngayHenTra = ngayLap.plusDays(7 + (i % 8));
            TrangThaiHopDong trangThaiHD = xacDinhTrangThaiHopDong(i);
            int maNVTuVan = NHAN_VIEN_TU_VAN_THEO_THANG[i / 12][i % 12];
            String soHD = String.format("HD%03d", i + 1);
            String maNV = String.format("NV%02d", maNVTuVan);

            List<CongViec> congViecHopDong = new ArrayList<>();

            for (int stt : soThuTuCongViec) {
                String maCV = String.format("CV%02d", stt);
                congViecHopDong.add(timCongViec(data, maCV));
            }

            double tongTien = congViecHopDong.stream()
                    .mapToDouble(CongViec::getDongia)
                    .sum();

            data.hopDongs.add(new HopDong(
                    soHD, ngayLap, ngayHenTra, trangThaiHD,
                    tongTien, maKH, maNV, maThietBi));

            for (int j = 0; j < congViecHopDong.size(); j++) {
                CongViec congViec = congViecHopDong.get(j);
                final int contractIndex = i;
                final int taskIndex = j;

                Tho thoPhuHop = data.thos.stream()
                        .filter(t -> data.khaNangThos.stream().anyMatch(kn ->
                                kn.getMaTho().equals(t.getMaTho())
                                        && kn.getMaCV().equals(congViec.getMaCV())))
                        .min(Comparator
                                .comparingInt((Tho t) ->
                                        soLanPhanCong.get(t.getMaTho()))
                                .thenComparingInt(t ->
                                        (Integer.parseInt(t.getMaTho().substring(1))
                                                + contractIndex + taskIndex) % data.thos.size()))
                        .orElseThrow(() -> new IllegalStateException(
                                "Khong co tho du ky nang cho "
                                        + congViec.getMaCV() + "."));

                soLanPhanCong.put(
                        thoPhuHop.getMaTho(),
                        soLanPhanCong.get(thoPhuHop.getMaTho()) + 1);

                TrangThaiCongViec trangThaiCV =
                        xacDinhTrangThaiCongViec(trangThaiHD, i, j);

                data.chiTietHopDongs.add(new ChiTietHopDong(
                        soHD, congViec.getMaCV(),
                        thoPhuHop.getMaTho(), trangThaiCV));
            }
        }
    }

    private static TrangThaiHopDong xacDinhTrangThaiHopDong(int chiSoHopDong) {
        if (chiSoHopDong <= 2
                || (chiSoHopDong >= 6 && chiSoHopDong <= 10)) {
            return TrangThaiHopDong.MOI_TIEP_NHAN;
        }

        if ((chiSoHopDong >= 3 && chiSoHopDong <= 5)
                || (chiSoHopDong >= 11 && chiSoHopDong <= 17)) {
            return TrangThaiHopDong.DANG_SUA_CHUA;
        }

        return chiSoHopDong <= 25
                ? TrangThaiHopDong.HOAN_THANH
                : TrangThaiHopDong.DA_TRA;
    }

    private static TrangThaiCongViec xacDinhTrangThaiCongViec(
            TrangThaiHopDong trangThaiHD, int chiSoHopDong, int chiSoCongViec) {

        if (trangThaiHD == TrangThaiHopDong.MOI_TIEP_NHAN) {
            return TrangThaiCongViec.CHUA_THUC_HIEN;
        }

        if (trangThaiHD == TrangThaiHopDong.DANG_SUA_CHUA) {
            return (chiSoHopDong + chiSoCongViec) % 2 == 0
                    ? TrangThaiCongViec.DANG_THUC_HIEN
                    : TrangThaiCongViec.CHUA_THUC_HIEN;
        }

        return TrangThaiCongViec.HOAN_THANH;
    }

    private static void taoPhieuThu(DuLieuMau data) {
        double[] tyLeThanhToanTam = { 0.35, 0.40, 0.45, 0.50, 0.55, 0.60 };

        for (HopDong hopDong : data.hopDongs) {
            int soThuTuHD = Integer.parseInt(hopDong.getSoHD().substring(2));

            if (soThuTuHD <= 6) {
                continue;
            }

            if (soThuTuHD <= 18) {
                double soTien = Math.round(hopDong.getTongtien()
                        * tyLeThanhToanTam[(soThuTuHD - 7) % tyLeThanhToanTam.length]);

                themPhieuThu(data, hopDong, soTien,
                        hopDong.getNgaylap().plusDays(2 + soThuTuHD % 3));
                continue;
            }

            double lanDau = Math.round(hopDong.getTongtien() * 0.40);
            double lanCuoi = hopDong.getTongtien() - lanDau;

            themPhieuThu(data, hopDong, lanDau, hopDong.getNgaylap().plusDays(2));
            themPhieuThu(data, hopDong, lanCuoi, hopDong.getNgaylap().plusDays(5));
        }
    }

    private static void themPhieuThu(
            DuLieuMau data, HopDong hopDong,
            double soTien, LocalDate ngayThanhToan) {

        int soThuTuPhieu = data.phieuThus.size() + 1;
        String maPhieuThu = String.format("PT%03d", soThuTuPhieu);
        String maNVThuNgan = String.format(
                "NV%02d", 6 + ((soThuTuPhieu - 1) % 4));

        PhuongThucThanhToan phuongThuc =
                soThuTuPhieu % 2 == 1
                        ? PhuongThucThanhToan.TIEN_MAT
                        : PhuongThucThanhToan.CHUYEN_KHOAN;

        data.phieuThus.add(new PhieuThu(
                maPhieuThu, soTien, ngayThanhToan,
                phuongThuc, hopDong.getSoHD(), maNVThuNgan));
    }

    private static void kiemTraToanVen(
            DuLieuMau data, Map<String, String> thietBiTheoKhachVaNhom) {

        kiemTraSoLuong(data.nhomThos.size() == 4, "NHOM_THO phai co 4 ban ghi.");
        kiemTraSoLuong(data.thos.size() == 12, "THO phai co 12 ban ghi.");
        kiemTraSoLuong(data.nhomCongViecs.size() == 5, "NHOM_CONG_VIEC phai co 5 ban ghi.");
        kiemTraSoLuong(data.congViecs.size() == 20, "CONG_VIEC phai co 20 ban ghi.");
        kiemTraSoLuong(data.khachHangs.size() == 15, "KHACH_HANG phai co 15 ban ghi.");
        kiemTraSoLuong(data.thietBis.size() == 25, "THIET_BI phai co 25 ban ghi.");
        kiemTraSoLuong(data.nhanViens.size() == 9, "NHAN_VIEN phai co 9 ban ghi.");
        kiemTraSoLuong(data.hopDongs.size() == 36, "HOP_DONG phai co 36 ban ghi.");
        kiemTraSoLuong(data.chiTietHopDongs.size() == 108, "CHI_TIET_HOP_DONG phai co 108 ban ghi.");
        kiemTraSoLuong(data.phieuThus.size() == 48, "PHIEU_THU phai co 48 ban ghi.");
        kiemTraSoLuong(data.khaNangThos.size() == 60, "KHA_NANG_THO phai co 60 ban ghi.");

        kiemTraDuyNhat(data.khachHangs, KhachHang::getMaKH, "Ma khach hang bi trung.");
        kiemTraDuyNhat(data.thietBis, ThietBi::getMaThietBi, "Ma thiet bi bi trung.");
        kiemTraDuyNhat(data.nhanViens, NhanVien::getMaNV, "Ma nhan vien bi trung.");
        kiemTraDuyNhat(data.thos, Tho::getMaTho, "Ma tho bi trung.");
        kiemTraDuyNhat(data.nhomThos, NhomTho::getMaNhomTho, "Ma nhom tho bi trung.");
        kiemTraDuyNhat(data.nhomCongViecs, NhomCongViec::getMaNhomCV, "Ma nhom cong viec bi trung.");
        kiemTraDuyNhat(data.congViecs, CongViec::getMaCV, "Ma cong viec bi trung.");
        kiemTraDuyNhat(data.hopDongs, HopDong::getSoHD, "So hop dong bi trung.");
        kiemTraDuyNhat(data.phieuThus, PhieuThu::getMaPhieuThu, "Ma phieu thu bi trung.");

        kiemTraDuyNhat(
                data.chiTietHopDongs,
                ct -> ct.getSoHD() + "|" + ct.getMaCV(),
                "Khoa ghep CHI_TIET_HOP_DONG bi trung.");

        kiemTraDuyNhat(
                data.khaNangThos,
                kn -> kn.getMaTho() + "|" + kn.getMaCV(),
                "Khoa ghep KHA_NANG_THO bi trung.");

        for (NhomTho nhom : data.nhomThos) {
            Tho truongNhom = data.thos.stream()
                    .filter(t -> t.getMaTho().equals(nhom.getMaTruongNhom()))
                    .findFirst()
                    .orElse(null);

            kiemTraSoLuong(
                    truongNhom != null
                            && truongNhom.getMaNhomTho().equals(nhom.getMaNhomTho()),
                    "Truong nhom " + nhom.getMaTruongNhom()
                            + " khong thuoc " + nhom.getMaNhomTho() + ".");
        }

        for (Tho tho : data.thos) {
            kiemTraSoLuong(
                    data.nhomThos.stream().anyMatch(
                            n -> n.getMaNhomTho().equals(tho.getMaNhomTho())),
                    "Tho " + tho.getMaTho() + " tham chieu nhom khong ton tai.");
        }

        for (CongViec cv : data.congViecs) {
            kiemTraSoLuong(
                    data.nhomCongViecs.stream().anyMatch(
                            n -> n.getMaNhomCV().equals(cv.getMaNhomCV())),
                    "Cong viec " + cv.getMaCV()
                            + " tham chieu nhom cong viec khong ton tai.");
        }

        for (ThietBi tb : data.thietBis) {
            kiemTraSoLuong(
                    data.khachHangs.stream().anyMatch(
                            k -> k.getMaKH().equals(tb.getMaKH())),
                    "Thiet bi " + tb.getMaThietBi()
                            + " tham chieu khach hang khong ton tai.");

            kiemTraSoLuong(
                    data.hopDongs.stream().anyMatch(
                            h -> h.getMaThietBi().equals(tb.getMaThietBi())),
                    "Thiet bi " + tb.getMaThietBi()
                            + " chua duoc dung trong hop dong.");
        }

        for (HopDong hd : data.hopDongs) {
            kiemTraSoLuong(
                    data.khachHangs.stream().anyMatch(
                            k -> k.getMaKH().equals(hd.getMaKH())),
                    "Hop dong " + hd.getSoHD()
                            + " tham chieu khach hang khong ton tai.");

            kiemTraSoLuong(
                    data.nhanViens.stream().anyMatch(
                            n -> n.getMaNV().equals(hd.getMaNV())
                                    && n.getChucVu() == ChucVu.TU_VAN),
                    "Hop dong " + hd.getSoHD()
                            + " phai do nhan vien tu van lap.");

            ThietBi tb = timThietBi(data, hd.getMaThietBi());

            kiemTraSoLuong(
                    tb.getMaKH().equals(hd.getMaKH()),
                    "Khach hang va chu so huu thiet bi khong khop trong "
                            + hd.getSoHD() + ".");

            int chiSo = Integer.parseInt(hd.getSoHD().substring(2)) - 1;
            int nhomHopDong = NHOM_THEO_HOP_DONG[chiSo];

            String maTBThamChieu = thietBiTheoKhachVaNhom.get(
                    khoaThietBi(hd.getMaKH(), nhomHopDong));

            kiemTraSoLuong(
                    maTBThamChieu != null
                            && maTBThamChieu.equals(hd.getMaThietBi()),
                    "Thiet bi " + hd.getMaThietBi()
                            + " khong phu hop nhom dich vu cua " + hd.getSoHD() + ".");

            List<ChiTietHopDong> chiTiet = data.chiTietHopDongs.stream()
                    .filter(ct -> ct.getSoHD().equals(hd.getSoHD()))
                    .collect(Collectors.toList());

            kiemTraSoLuong(
                    chiTiet.size() == 3,
                    "Hop dong " + hd.getSoHD() + " phai co 3 hang muc.");

            for (ChiTietHopDong ct : chiTiet) {
                CongViec cv = timCongViec(data, ct.getMaCV());
                int nhomCongViec = Integer.parseInt(cv.getMaNhomCV().substring(3));

                kiemTraSoLuong(
                        nhomCongViec == nhomHopDong,
                        "Cong viec " + ct.getMaCV()
                                + " khong cung nhom dich vu voi " + hd.getSoHD() + ".");

                kiemTraSoLuong(
                        data.thos.stream().anyMatch(
                                t -> t.getMaTho().equals(ct.getMaTho())),
                        "Chi tiet " + ct.getSoHD() + "/" + ct.getMaCV()
                                + " tham chieu tho khong ton tai.");

                kiemTraSoLuong(
                        data.khaNangThos.stream().anyMatch(
                                kn -> kn.getMaTho().equals(ct.getMaTho())
                                        && kn.getMaCV().equals(ct.getMaCV())),
                        "Tho " + ct.getMaTho()
                                + " chua co ky nang " + ct.getMaCV() + ".");
            }

            double tongChiTiet = chiTiet.stream()
                    .mapToDouble(ct -> timCongViec(data, ct.getMaCV()).getDongia())
                    .sum();

            kiemTraSoLuong(
                    Math.abs(tongChiTiet - hd.getTongtien()) < 0.01,
                    "Tong tien " + hd.getSoHD() + " khong khop chi tiet.");

            kiemTraSoLuong(
                    !hd.getNgayHenTra().isBefore(hd.getNgaylap()),
                    "Ngay hen tra cua " + hd.getSoHD() + " khong hop le.");
        }

        for (PhieuThu pt : data.phieuThus) {
            kiemTraSoLuong(
                    data.hopDongs.stream().anyMatch(
                            h -> h.getSoHD().equals(pt.getSoHD())),
                    "Phieu thu " + pt.getMaPhieuThu()
                            + " tham chieu hop dong khong ton tai.");

            kiemTraSoLuong(
                    data.nhanViens.stream().anyMatch(
                            n -> n.getMaNV().equals(pt.getMaNV())
                                    && n.getChucVu() == ChucVu.THU_NGAN),
                    "Phieu thu " + pt.getMaPhieuThu()
                            + " phai do thu ngan lap.");

            HopDong hd = timHopDong(data, pt.getSoHD());

            kiemTraSoLuong(
                    pt.getSoTienTT() > 0
                            && !pt.getNgayTT().isBefore(hd.getNgaylap()),
                    "Phieu thu " + pt.getMaPhieuThu()
                            + " co so tien hoac ngay khong hop le.");
        }

        for (KhaNangTho kn : data.khaNangThos) {
            kiemTraSoLuong(
                    data.thos.stream().anyMatch(
                            t -> t.getMaTho().equals(kn.getMaTho())),
                    "Ky nang tham chieu tho " + kn.getMaTho()
                            + " khong ton tai.");

            kiemTraSoLuong(
                    data.congViecs.stream().anyMatch(
                            cv -> cv.getMaCV().equals(kn.getMaCV())),
                    "Ky nang tham chieu cong viec " + kn.getMaCV()
                            + " khong ton tai.");
        }

        for (HopDong hd : data.hopDongs) {
            double daThu = data.phieuThus.stream()
                    .filter(pt -> pt.getSoHD().equals(hd.getSoHD()))
                    .mapToDouble(PhieuThu::getSoTienTT)
                    .sum();

            kiemTraSoLuong(
                    daThu <= hd.getTongtien() + 0.01,
                    "Tong phieu thu vuot tong tien " + hd.getSoHD() + ".");

            if (hd.getTinhtrangHD() == TrangThaiHopDong.DA_TRA) {
                kiemTraSoLuong(
                        Math.abs(daThu - hd.getTongtien()) < 0.01,
                        "Hop dong " + hd.getSoHD()
                                + " da tra nhung chua thanh toan du.");
            }
        }

        kiemTraSoLuong(
                data.hopDongs.stream()
                        .filter(h -> h.getTinhtrangHD()
                                == TrangThaiHopDong.MOI_TIEP_NHAN)
                        .count() == 8,
                "Can co 8 hop dong MOI_TIEP_NHAN.");

        kiemTraSoLuong(
                data.hopDongs.stream()
                        .filter(h -> h.getTinhtrangHD()
                                == TrangThaiHopDong.DANG_SUA_CHUA)
                        .count() == 10,
                "Can co 10 hop dong DANG_SUA_CHUA.");

        kiemTraSoLuong(
                data.hopDongs.stream()
                        .filter(h -> h.getTinhtrangHD()
                                == TrangThaiHopDong.HOAN_THANH)
                        .count() == 8,
                "Can co 8 hop dong HOAN_THANH.");

        kiemTraSoLuong(
                data.hopDongs.stream()
                        .filter(h -> h.getTinhtrangHD()
                                == TrangThaiHopDong.DA_TRA)
                        .count() == 10,
                "Can co 10 hop dong DA_TRA.");

        kiemTraSoLuong(
                data.phieuThus.stream()
                        .filter(p -> p.getPhuongThucTT()
                                == PhuongThucThanhToan.TIEN_MAT)
                        .count() == 24,
                "Can co 24 phieu thu tien mat.");

        kiemTraSoLuong(
                data.phieuThus.stream()
                        .filter(p -> p.getPhuongThucTT()
                                == PhuongThucThanhToan.CHUYEN_KHOAN)
                        .count() == 24,
                "Can co 24 phieu thu chuyen khoan.");

        Map<String, Long> soPhieuTheoHopDong = data.hopDongs.stream()
                .collect(Collectors.toMap(
                        HopDong::getSoHD,
                        hd -> data.phieuThus.stream()
                                .filter(pt -> pt.getSoHD().equals(hd.getSoHD()))
                                .count()));

        kiemTraSoLuong(
                soPhieuTheoHopDong.values().stream().filter(n -> n == 0).count() == 6,
                "Can co 6 hop dong chua co phieu thu.");

        kiemTraSoLuong(
                soPhieuTheoHopDong.values().stream().filter(n -> n == 1).count() == 12,
                "Can co 12 hop dong co mot phieu thu.");

        kiemTraSoLuong(
                soPhieuTheoHopDong.values().stream().filter(n -> n == 2).count() == 18,
                "Can co 18 hop dong co hai phieu thu.");

        long khachHangCoHopDongNhungChuaThanhToan = data.khachHangs.stream()
                .filter(k ->
                        data.hopDongs.stream().anyMatch(
                                hd -> hd.getMaKH().equals(k.getMaKH()))
                                && data.hopDongs.stream()
                                        .filter(hd -> hd.getMaKH().equals(k.getMaKH()))
                                        .allMatch(hd -> data.phieuThus.stream()
                                                .noneMatch(pt -> pt.getSoHD()
                                                        .equals(hd.getSoHD()))))
                .count();

        kiemTraSoLuong(
                khachHangCoHopDongNhungChuaThanhToan == 4,
                "Can co 4 khach hang co hop dong nhung chua thanh toan hop dong nao.");

        Map<String, Long> tanSuatCongViec = data.chiTietHopDongs.stream()
                .collect(Collectors.groupingBy(
                        ChiTietHopDong::getMaCV,
                        Collectors.counting()));

        kiemTraSoLuong(
                tanSuatCongViec.size() == 20
                        && tanSuatCongViec.values().stream().allMatch(n -> n == 5 || n == 6),
                "Moi cong viec phai xuat hien 5 hoac 6 lan trong hop dong.");

        Map<String, Double> doanhThuTheoCongViec = data.chiTietHopDongs.stream()
                .collect(Collectors.groupingBy(
                        ChiTietHopDong::getMaCV,
                        Collectors.summingDouble(
                                ct -> timCongViec(data, ct.getMaCV()).getDongia())));

        String congViecDoanhThuCaoNhat = doanhThuTheoCongViec.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        kiemTraSoLuong(
                congViecDoanhThuCaoNhat.equals("CV20"),
                "CV20 phai co tong doanh thu cao nhat de kiem tra truy van 18.");
    }

    private static <T> void kiemTraDuyNhat(
            List<T> items, Function<T, String> keyExtractor, String message) {

        Set<String> keys = new HashSet<>();

        for (T item : items) {
            kiemTraSoLuong(keys.add(keyExtractor.apply(item)), message);
        }
    }

    private static CongViec timCongViec(DuLieuMau data, String maCV) {
        return data.congViecs.stream()
                .filter(cv -> cv.getMaCV().equals(maCV))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Khong tim thay cong viec " + maCV + "."));
    }

    private static ThietBi timThietBi(DuLieuMau data, String maThietBi) {
        return data.thietBis.stream()
                .filter(tb -> tb.getMaThietBi().equals(maThietBi))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Khong tim thay thiet bi " + maThietBi + "."));
    }

    private static HopDong timHopDong(DuLieuMau data, String soHD) {
        return data.hopDongs.stream()
                .filter(hd -> hd.getSoHD().equals(soHD))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Khong tim thay hop dong " + soHD + "."));
    }

    private static String khoaThietBi(String maKH, int nhomDichVu) {
        return maKH + "|" + nhomDichVu;
    }

    private static void kiemTraSoLuong(boolean dieuKien, String thongBao) {
        if (!dieuKien) {
            throw new IllegalStateException("DataSeeder: " + thongBao);
        }
    }
}
