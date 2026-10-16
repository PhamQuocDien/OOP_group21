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
 * Khởi tạo cùng bộ dữ liệu logic với DataSeeder.cs.
 * Không thay đổi Entity, DAO hoặc Main.java.
 */
public final class DataSeeder {
    private DataSeeder() {
    }

    /** Chứa 11 danh sách dữ liệu mẫu dùng chung cho các truy vấn. */
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

    // Mỗi hợp đồng thuộc một nhóm dịch vụ: 1..5 tương ứng NCV01..NCV05.
    private static final int[] NHOM_THEO_HOP_DONG = {
        1, 2, 3, 4, 3, 4,
        1, 1, 5, 5, 5, 1,
        2, 2, 4, 5, 5, 3,
        5, 5, 3, 2, 2, 3,
        3, 3, 4, 4, 4, 5,
        2, 4, 1, 2, 1, 1
    };

    // KH01..KH04 chỉ có hợp đồng chưa thanh toán; KH05..KH15 có hợp đồng đã thanh toán.
    private static final String[] KHACH_HANG_THEO_HOP_DONG = {
        "KH01", "KH02", "KH03", "KH04", "KH03", "KH04",
        "KH05", "KH05", "KH05", "KH06", "KH06", "KH06",
        "KH07", "KH07", "KH07", "KH08", "KH08", "KH08",
        "KH09", "KH09", "KH09", "KH10", "KH10", "KH10",
        "KH11", "KH11", "KH11", "KH12", "KH12", "KH12",
        "KH13", "KH13", "KH14", "KH14", "KH15", "KH15"
    };

    private static final int[][] NHAN_VIEN_TU_VAN_THEO_THANG = {
        // Tháng 7: NV01 lập 4 hợp đồng, nhiều nhất trong tháng.
        { 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5 },
        // Tháng 8: NV02 lập 4 hợp đồng, nhiều nhất trong tháng.
        { 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 1 },
        // Tháng 9: NV03 lập 4 hợp đồng, nhiều nhất trong tháng.
        { 3, 3, 3, 3, 4, 4, 4, 5, 5, 1, 1, 2 }
    };

    /** Tạo dữ liệu mẫu và tự động kiểm tra tính toàn vẹn trước khi trả về. */
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
        data.khachHangs.add(new KhachHang("KH01", "Nguyễn Hoàng Anh", "Nam", LocalDate.of(1992, 4, 12), "Quận 1, TP. Hồ Chí Minh", "0901000001"));
        data.khachHangs.add(new KhachHang("KH02", "Trần Minh Châu", "Nữ", LocalDate.of(1989, 7, 23), "Quận 3, TP. Hồ Chí Minh", "0901000002"));
        data.khachHangs.add(new KhachHang("KH03", "Lê Quốc Bảo", "Nam", LocalDate.of(1995, 2, 15), "Quận 5, TP. Hồ Chí Minh", "0901000003"));
        data.khachHangs.add(new KhachHang("KH04", "Phạm Ngọc Mai", "Nữ", LocalDate.of(1990, 11, 6), "Quận 10, TP. Hồ Chí Minh", "0901000004"));
        data.khachHangs.add(new KhachHang("KH05", "Võ Thành Đạt", "Nam", LocalDate.of(1987, 9, 19), "Bình Thạnh, TP. Hồ Chí Minh", "0901000005"));
        data.khachHangs.add(new KhachHang("KH06", "Đặng Thu Hà", "Nữ", LocalDate.of(1994, 1, 30), "Gò Vấp, TP. Hồ Chí Minh", "0901000006"));
        data.khachHangs.add(new KhachHang("KH07", "Bùi Gia Huy", "Nam", LocalDate.of(1998, 6, 21), "Tân Bình, TP. Hồ Chí Minh", "0901000007"));
        data.khachHangs.add(new KhachHang("KH08", "Đỗ Khánh Linh", "Nữ", LocalDate.of(1991, 12, 8), "Phú Nhuận, TP. Hồ Chí Minh", "0901000008"));
        data.khachHangs.add(new KhachHang("KH09", "Hồ Minh Quân", "Nam", LocalDate.of(1993, 3, 4), "Thủ Đức, TP. Hồ Chí Minh", "0901000009"));
        data.khachHangs.add(new KhachHang("KH10", "Ngô Bảo Trâm", "Nữ", LocalDate.of(1988, 5, 27), "Quận 7, TP. Hồ Chí Minh", "0901000010"));
        data.khachHangs.add(new KhachHang("KH11", "Dương Quốc Việt", "Nam", LocalDate.of(1997, 8, 13), "Quận 8, TP. Hồ Chí Minh", "0901000011"));
        data.khachHangs.add(new KhachHang("KH12", "Lý Thảo Nguyên", "Nữ", LocalDate.of(1990, 10, 1), "Bình Tân, TP. Hồ Chí Minh", "0901000012"));
        data.khachHangs.add(new KhachHang("KH13", "Nguyễn Đức Phúc", "Nam", LocalDate.of(1986, 2, 20), "Tân Phú, TP. Hồ Chí Minh", "0901000013"));
        data.khachHangs.add(new KhachHang("KH14", "Trần Mỹ Duyên", "Nữ", LocalDate.of(1996, 4, 17), "Hóc Môn, TP. Hồ Chí Minh", "0901000014"));
        data.khachHangs.add(new KhachHang("KH15", "Phan Tuấn Kiệt", "Nam", LocalDate.of(1992, 12, 29), "Nhà Bè, TP. Hồ Chí Minh", "0901000015"));
    }

    private static void taoNhanVien(DuLieuMau data) {
        data.nhanViens.add(new NhanVien("NV01", "Nguyễn Văn Hưng", "Nam", LocalDate.of(1988, 3, 10), "Quận 1, TP. Hồ Chí Minh", "0912000001", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV02", "Trần Thanh Tùng", "Nam", LocalDate.of(1990, 5, 22), "Quận 3, TP. Hồ Chí Minh", "0912000002", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV03", "Lê Thị Thu", "Nữ", LocalDate.of(1992, 8, 14), "Quận 5, TP. Hồ Chí Minh", "0912000003", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV04", "Phạm Nhật Nam", "Nam", LocalDate.of(1987, 11, 2), "Quận 10, TP. Hồ Chí Minh", "0912000004", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV05", "Võ Ngọc Yến", "Nữ", LocalDate.of(1993, 6, 18), "Bình Thạnh, TP. Hồ Chí Minh", "0912000005", ChucVu.TU_VAN));
        data.nhanViens.add(new NhanVien("NV06", "Nguyễn Thị Loan", "Nữ", LocalDate.of(1989, 1, 9), "Gò Vấp, TP. Hồ Chí Minh", "0912000006", ChucVu.THU_NGAN));
        data.nhanViens.add(new NhanVien("NV07", "Trương Minh Anh", "Nam", LocalDate.of(1991, 4, 25), "Tân Bình, TP. Hồ Chí Minh", "0912000007", ChucVu.THU_NGAN));
        data.nhanViens.add(new NhanVien("NV08", "Đỗ Thanh Phương", "Nữ", LocalDate.of(1994, 9, 7), "Phú Nhuận, TP. Hồ Chí Minh", "0912000008", ChucVu.THU_NGAN));
        data.nhanViens.add(new NhanVien("NV09", "Bùi Thị Hạnh", "Nữ", LocalDate.of(1990, 12, 11), "Thủ Đức, TP. Hồ Chí Minh", "0912000009", ChucVu.THU_NGAN));
    }

    private static void taoNhomCongViec(DuLieuMau data) {
        data.nhomCongViecs.add(new NhomCongViec("NCV01", "Sửa chữa điện thoại và máy tính bảng", "Kiểm tra, bảo dưỡng và xử lý lỗi thiết bị di động."));
        data.nhomCongViecs.add(new NhomCongViec("NCV02", "Sửa chữa máy tính xách tay", "Bảo dưỡng và thay thế linh kiện laptop."));
        data.nhomCongViecs.add(new NhomCongViec("NCV03", "Sửa chữa máy tính để bàn", "Kiểm tra, nâng cấp và sửa chữa máy tính để bàn."));
        data.nhomCongViecs.add(new NhomCongViec("NCV04", "Sửa chữa thiết bị hiển thị", "Sửa chữa tivi và màn hình."));
        data.nhomCongViecs.add(new NhomCongViec("NCV05", "Thiết bị mạng và bo mạch chuyên dụng", "Cấu hình, sửa chữa thiết bị mạng và bo mạch."));
    }

    private static void taoCongViec(DuLieuMau data) {
        data.congViecs.add(new CongViec("CV01", 350_000, "Kiểm tra và chẩn đoán thiết bị di động", "Kiểm tra tổng quát và xác định lỗi thiết bị di động.", "NCV01"));
        data.congViecs.add(new CongViec("CV02", 450_000, "Cài đặt và khôi phục phần mềm thiết bị di động", "Cài đặt hoặc khôi phục phần mềm theo yêu cầu.", "NCV01"));
        data.congViecs.add(new CongViec("CV03", 500_000, "Vệ sinh và bảo dưỡng thiết bị di động", "Vệ sinh thiết bị và kiểm tra hoạt động.", "NCV01"));
        data.congViecs.add(new CongViec("CV04", 600_000, "Sửa loa hoặc micro thiết bị di động", "Kiểm tra và xử lý lỗi loa hoặc micro.", "NCV01"));

        data.congViecs.add(new CongViec("CV05", 450_000, "Vệ sinh laptop", "Vệ sinh quạt, khe tản nhiệt và kiểm tra nhiệt độ.", "NCV02"));
        data.congViecs.add(new CongViec("CV06", 500_000, "Sửa cổng sạc laptop", "Kiểm tra và sửa lỗi cổng sạc.", "NCV02"));
        data.congViecs.add(new CongViec("CV07", 700_000, "Nâng cấp RAM laptop", "Lắp đặt và kiểm tra bộ nhớ RAM tương thích.", "NCV02"));
        data.congViecs.add(new CongViec("CV08", 900_000, "Thay ổ SSD laptop", "Thay ổ SSD và kiểm tra nhận diện ổ đĩa.", "NCV02"));

        data.congViecs.add(new CongViec("CV09", 350_000, "Kiểm tra và vệ sinh máy tính để bàn", "Vệ sinh máy và kiểm tra các linh kiện cơ bản.", "NCV03"));
        data.congViecs.add(new CongViec("CV10", 600_000, "Nâng cấp RAM máy tính để bàn", "Lắp đặt và kiểm tra bộ nhớ RAM.", "NCV03"));
        data.congViecs.add(new CongViec("CV11", 800_000, "Thay ổ SSD máy tính để bàn", "Thay ổ lưu trữ và kiểm tra hoạt động.", "NCV03"));
        data.congViecs.add(new CongViec("CV12", 1_200_000, "Sửa bo mạch chủ máy tính để bàn", "Kiểm tra và sửa lỗi bo mạch chủ.", "NCV03"));

        data.congViecs.add(new CongViec("CV13", 800_000, "Kiểm tra bộ nguồn thiết bị hiển thị", "Kiểm tra và sửa bộ nguồn thiết bị hiển thị.", "NCV04"));
        data.congViecs.add(new CongViec("CV14", 1_000_000, "Sửa lỗi hình ảnh thiết bị hiển thị", "Kiểm tra tín hiệu và xử lý lỗi hiển thị.", "NCV04"));
        data.congViecs.add(new CongViec("CV15", 1_500_000, "Sửa bo mạch thiết bị hiển thị", "Kiểm tra và sửa bo mạch điều khiển thiết bị hiển thị.", "NCV04"));
        data.congViecs.add(new CongViec("CV16", 1_800_000, "Thay tấm nền hiển thị", "Thay tấm nền tương thích và kiểm tra hiển thị.", "NCV04"));

        data.congViecs.add(new CongViec("CV17", 300_000, "Cấu hình router Wi-Fi", "Thiết lập mạng không dây và bảo mật cơ bản.", "NCV05"));
        data.congViecs.add(new CongViec("CV18", 450_000, "Sửa bộ nguồn modem hoặc router", "Kiểm tra và xử lý lỗi nguồn thiết bị mạng.", "NCV05"));
        data.congViecs.add(new CongViec("CV19", 850_000, "Thay cổng WAN hoặc LAN", "Thay cổng kết nối và kiểm tra truyền dữ liệu.", "NCV05"));
        data.congViecs.add(new CongViec("CV20", 4_200_000, "Thay bo mạch thiết bị mạng cao cấp", "Thay bo mạch chính cho thiết bị mạng hiệu năng cao.", "NCV05"));
    }

    private static void taoThoVaNhomTho(DuLieuMau data) {
        data.thos.add(new Tho("T01", "Nguyễn Quốc Bảo", "Nam", LocalDate.of(1988, 1, 15), "0933000001", "NT01"));
        data.thos.add(new Tho("T02", "Trần Minh Khang", "Nam", LocalDate.of(1991, 3, 21), "0933000002", "NT01"));
        data.thos.add(new Tho("T03", "Lê Hoàng Phúc", "Nam", LocalDate.of(1994, 7, 12), "0933000003", "NT01"));
        data.thos.add(new Tho("T04", "Phạm Anh Tuấn", "Nam", LocalDate.of(1987, 10, 9), "0933000004", "NT02"));
        data.thos.add(new Tho("T05", "Võ Thành Long", "Nam", LocalDate.of(1992, 5, 17), "0933000005", "NT02"));
        data.thos.add(new Tho("T06", "Đặng Nhật Minh", "Nam", LocalDate.of(1995, 12, 4), "0933000006", "NT02"));
        data.thos.add(new Tho("T07", "Bùi Đức Thịnh", "Nam", LocalDate.of(1989, 6, 26), "0933000007", "NT03"));
        data.thos.add(new Tho("T08", "Đỗ Gia Huy", "Nam", LocalDate.of(1993, 8, 2), "0933000008", "NT03"));
        data.thos.add(new Tho("T09", "Hồ Thanh Phong", "Nam", LocalDate.of(1996, 2, 18), "0933000009", "NT03"));
        data.thos.add(new Tho("T10", "Ngô Minh Trí", "Nam", LocalDate.of(1986, 9, 13), "0933000010", "NT04"));
        data.thos.add(new Tho("T11", "Dương Quốc Khánh", "Nam", LocalDate.of(1990, 4, 7), "0933000011", "NT04"));
        data.thos.add(new Tho("T12", "Lý Hoàng Nam", "Nam", LocalDate.of(1997, 11, 29), "0933000012", "NT04"));

        // Thêm nhóm sau danh sách thợ vì maTruongNhom phải trỏ đến thợ tồn tại.
        data.nhomThos.add(new NhomTho("NT01", "Tổ kỹ thuật thiết bị di động", "T01"));
        data.nhomThos.add(new NhomTho("NT02", "Tổ kỹ thuật máy tính", "T04"));
        data.nhomThos.add(new NhomTho("NT03", "Tổ sửa thiết bị hiển thị", "T07"));
        data.nhomThos.add(new NhomTho("NT04", "Tổ thiết bị mạng và bo mạch", "T10"));
    }

    private static void taoKhaNangTho(DuLieuMau data) {
        CapDo[] capDos = { CapDo.CO_BAN, CapDo.KHA, CapDo.TOT, CapDo.CHUYEN_GIA };
        // Mỗi thợ có 5 kỹ năng khác nhau; 60 cặp (thợ, công việc) không trùng nhau.
        for (int i = 0; i < data.thos.size(); i++) {
            int batDau = (i * 7) % data.congViecs.size();
            for (int k = 0; k < 5; k++) {
                int chiSoCongViec = (batDau + k) % data.congViecs.size();
                String maCV = data.congViecs.get(chiSoCongViec).getMaCV();
                data.khaNangThos.add(new KhaNangTho(
                    data.thos.get(i).getMaTho(), maCV, capDos[(i + k) % capDos.length]));
            }
        }
    }

    private static Map<String, String> taoThietBi(DuLieuMau data) {
        String[][] mau = {
            {"KH01", "1", "Điện thoại", "Samsung", "Galaxy S22", "Nứt kính màn hình"},
            {"KH02", "2", "Laptop", "Dell", "Inspiron 14", "Không khởi động ổn định"},
            {"KH03", "3", "Máy tính để bàn", "Dell", "OptiPlex 7090", "Máy chạy chậm"},
            {"KH04", "4", "Tivi", "Samsung", "Crystal UHD 43 inch", "Không lên hình"},
            {"KH05", "1", "Điện thoại", "Apple", "iPhone 13", "Pin chai, sạc chậm"},
            {"KH05", "5", "Router Wi-Fi", "TP-Link", "Archer AX23", "Mất kết nối không dây"},
            {"KH06", "5", "Router Wi-Fi", "ASUS", "RT-AX55", "Tín hiệu Wi-Fi không ổn định"},
            {"KH06", "1", "Điện thoại", "Apple", "iPhone 12", "Loa thoại bị rè"},
            {"KH07", "2", "Laptop", "Lenovo", "ThinkPad E14", "Quạt tản nhiệt kêu lớn"},
            {"KH07", "4", "Màn hình máy tính", "LG", "27MP400", "Màn hình nhấp nháy"},
            {"KH08", "5", "Router Wi-Fi", "TP-Link", "Deco M4", "Không cấp địa chỉ mạng ổn định"},
            {"KH08", "3", "Máy tính để bàn", "HP", "Pavilion Desktop", "Lỗi bộ nhớ"},
            {"KH09", "5", "Router Wi-Fi", "Huawei", "WiFi AX3", "Cổng LAN không hoạt động"},
            {"KH09", "3", "Máy tính để bàn", "Lenovo", "ThinkCentre M70", "Ổ lưu trữ báo lỗi"},
            {"KH10", "2", "Laptop", "HP", "ProBook 440", "Cổng sạc tiếp xúc kém"},
            {"KH10", "3", "Máy tính để bàn", "ASUS", "ExpertCenter D5", "Máy quá nhiệt"},
            {"KH11", "3", "Máy tính để bàn", "Acer", "Veriton X", "Lỗi khởi động"},
            {"KH11", "4", "Màn hình máy tính", "Samsung", "Essential 24 inch", "Mất tín hiệu hình ảnh"},
            {"KH12", "4", "Tivi", "Sony", "Bravia 50 inch", "Có tiếng nhưng không có hình"},
            {"KH12", "5", "Modem mạng", "TP-Link", "Archer VR400", "Kết nối mạng chập chờn"},
            {"KH13", "2", "Laptop", "Acer", "Aspire 5", "Máy chạy chậm"},
            {"KH13", "4", "Tivi", "LG", "UHD AI 43 inch", "Lỗi màu sắc"},
            {"KH14", "1", "Máy tính bảng", "Apple", "iPad Air (5th Gen)", "Không nhận sạc ổn định"},
            {"KH14", "2", "Laptop", "ASUS", "Vivobook 15", "Không nhận ổ SSD"},
            {"KH15", "1", "Điện thoại", "Xiaomi", "Redmi Note 12", "Lỗi loa ngoài"}
        };

        Map<String, String> traCuu = new HashMap<>();
        for (int i = 0; i < mau.length; i++) {
            String maKH = mau[i][0];
            int nhom = Integer.parseInt(mau[i][1]);
            String maThietBi = String.format("TB%02d", i + 1);
            data.thietBis.add(new ThietBi(maThietBi, mau[i][2], mau[i][3], mau[i][4], mau[i][5], maKH));
            traCuu.put(khoaThietBi(maKH, nhom), maThietBi);
        }
        return traCuu;
    }

    private static void taoHopDongVaChiTiet(DuLieuMau data, Map<String, String> thietBiTheoKhachVaNhom) {
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
            String maThietBi = thietBiTheoKhachVaNhom.get(khoaThietBi(maKH, nhomDichVu));
            if (maThietBi == null) {
                throw new IllegalStateException("Không tìm thấy thiết bị phù hợp cho " + maKH
                    + ", nhóm dịch vụ " + nhomDichVu + ".");
            }

            int viTriTrongNhom = soLanTheoNhom[nhomDichVu - 1]++;
            int[] mauCombo = nhomDichVu == 5 ? mauCombo8 : mauCombo7;
            int viTriCombo = mauCombo[viTriTrongNhom];
            int soCongViecDauTien = (nhomDichVu - 1) * 4 + 1;
            int[] soThuTuCongViec = new int[3];
            for (int j = 0; j < 3; j++) {
                soThuTuCongViec[j] = soCongViecDauTien + comboTheoViTriLoaiTru[viTriCombo][j];
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
            double tongTien = congViecHopDong.stream().mapToDouble(CongViec::getDongia).sum();

            data.hopDongs.add(new HopDong(soHD, ngayLap, ngayHenTra, trangThaiHD,
                tongTien, maKH, maNV, maThietBi));

            for (int j = 0; j < congViecHopDong.size(); j++) {
                CongViec congViec = congViecHopDong.get(j);
                final int contractIndex = i;
                final int taskIndex = j;
                Tho thoPhuHop = data.thos.stream()
                    .filter(t -> data.khaNangThos.stream().anyMatch(kn ->
                        kn.getMaTho().equals(t.getMaTho()) && kn.getMaCV().equals(congViec.getMaCV())))
                    .min(Comparator
                        .comparingInt((Tho t) -> soLanPhanCong.get(t.getMaTho()))
                        .thenComparingInt(t -> (Integer.parseInt(t.getMaTho().substring(1))
                            + contractIndex + taskIndex) % data.thos.size()))
                    .orElseThrow(() -> new IllegalStateException("Không có thợ đủ kỹ năng cho " + congViec.getMaCV() + "."));

                soLanPhanCong.put(thoPhuHop.getMaTho(), soLanPhanCong.get(thoPhuHop.getMaTho()) + 1);
                TrangThaiCongViec trangThaiCV = xacDinhTrangThaiCongViec(trangThaiHD, i, j);
                data.chiTietHopDongs.add(new ChiTietHopDong(soHD, congViec.getMaCV(),
                    thoPhuHop.getMaTho(), trangThaiCV));
            }
        }
    }

    private static TrangThaiHopDong xacDinhTrangThaiHopDong(int chiSoHopDong) {
        if (chiSoHopDong <= 2 || (chiSoHopDong >= 6 && chiSoHopDong <= 10)) {
            return TrangThaiHopDong.MOI_TIEP_NHAN;
        }
        if ((chiSoHopDong >= 3 && chiSoHopDong <= 5) || (chiSoHopDong >= 11 && chiSoHopDong <= 17)) {
            return TrangThaiHopDong.DANG_SUA_CHUA;
        }
        return chiSoHopDong <= 25 ? TrangThaiHopDong.HOAN_THANH : TrangThaiHopDong.DA_TRA;
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

    private static void themPhieuThu(DuLieuMau data, HopDong hopDong, double soTien, LocalDate ngayThanhToan) {
        int soThuTuPhieu = data.phieuThus.size() + 1;
        String maPhieuThu = String.format("PT%03d", soThuTuPhieu);
        String maNVThuNgan = String.format("NV%02d", 6 + ((soThuTuPhieu - 1) % 4));
        PhuongThucThanhToan phuongThuc = soThuTuPhieu % 2 == 1
            ? PhuongThucThanhToan.TIEN_MAT
            : PhuongThucThanhToan.CHUYEN_KHOAN;
        data.phieuThus.add(new PhieuThu(maPhieuThu, soTien, ngayThanhToan,
            phuongThuc, hopDong.getSoHD(), maNVThuNgan));
    }

    private static void kiemTraToanVen(DuLieuMau data, Map<String, String> thietBiTheoKhachVaNhom) {
        kiemTraSoLuong(data.nhomThos.size() == 4, "NHOM_THO phải có 4 bản ghi.");
        kiemTraSoLuong(data.thos.size() == 12, "THO phải có 12 bản ghi.");
        kiemTraSoLuong(data.nhomCongViecs.size() == 5, "NHOM_CONG_VIEC phải có 5 bản ghi.");
        kiemTraSoLuong(data.congViecs.size() == 20, "CONG_VIEC phải có 20 bản ghi.");
        kiemTraSoLuong(data.khachHangs.size() == 15, "KHACH_HANG phải có 15 bản ghi.");
        kiemTraSoLuong(data.thietBis.size() == 25, "THIET_BI phải có 25 bản ghi.");
        kiemTraSoLuong(data.nhanViens.size() == 9, "NHAN_VIEN phải có 9 bản ghi.");
        kiemTraSoLuong(data.hopDongs.size() == 36, "HOP_DONG phải có 36 bản ghi.");
        kiemTraSoLuong(data.chiTietHopDongs.size() == 108, "CHI_TIET_HOP_DONG phải có 108 bản ghi.");
        kiemTraSoLuong(data.phieuThus.size() == 48, "PHIEU_THU phải có 48 bản ghi.");
        kiemTraSoLuong(data.khaNangThos.size() == 60, "KHA_NANG_THO phải có 60 bản ghi.");

        kiemTraDuyNhat(data.khachHangs, KhachHang::getMaKH, "Mã khách hàng bị trùng.");
        kiemTraDuyNhat(data.thietBis, ThietBi::getMaThietBi, "Mã thiết bị bị trùng.");
        kiemTraDuyNhat(data.nhanViens, NhanVien::getMaNV, "Mã nhân viên bị trùng.");
        kiemTraDuyNhat(data.thos, Tho::getMaTho, "Mã thợ bị trùng.");
        kiemTraDuyNhat(data.nhomThos, NhomTho::getMaNhomTho, "Mã nhóm thợ bị trùng.");
        kiemTraDuyNhat(data.nhomCongViecs, NhomCongViec::getMaNhomCV, "Mã nhóm công việc bị trùng.");
        kiemTraDuyNhat(data.congViecs, CongViec::getMaCV, "Mã công việc bị trùng.");
        kiemTraDuyNhat(data.hopDongs, HopDong::getSoHD, "Số hợp đồng bị trùng.");
        kiemTraDuyNhat(data.phieuThus, PhieuThu::getMaPhieuThu, "Mã phiếu thu bị trùng.");
        kiemTraDuyNhat(data.chiTietHopDongs, ct -> ct.getSoHD() + "|" + ct.getMaCV(), "Khóa ghép CHI_TIET_HOP_DONG bị trùng.");
        kiemTraDuyNhat(data.khaNangThos, kn -> kn.getMaTho() + "|" + kn.getMaCV(), "Khóa ghép KHA_NANG_THO bị trùng.");

        for (NhomTho nhom : data.nhomThos) {
            Tho truongNhom = data.thos.stream()
                .filter(t -> t.getMaTho().equals(nhom.getMaTruongNhom())).findFirst().orElse(null);
            kiemTraSoLuong(truongNhom != null && truongNhom.getMaNhomTho().equals(nhom.getMaNhomTho()),
                "Trưởng nhóm " + nhom.getMaTruongNhom() + " không thuộc " + nhom.getMaNhomTho() + ".");
        }

        for (Tho tho : data.thos) {
            kiemTraSoLuong(data.nhomThos.stream().anyMatch(n -> n.getMaNhomTho().equals(tho.getMaNhomTho())),
                "Thợ " + tho.getMaTho() + " tham chiếu nhóm không tồn tại.");
        }
        for (CongViec cv : data.congViecs) {
            kiemTraSoLuong(data.nhomCongViecs.stream().anyMatch(n -> n.getMaNhomCV().equals(cv.getMaNhomCV())),
                "Công việc " + cv.getMaCV() + " tham chiếu nhóm công việc không tồn tại.");
        }
        for (ThietBi tb : data.thietBis) {
            kiemTraSoLuong(data.khachHangs.stream().anyMatch(k -> k.getMaKH().equals(tb.getMaKH())),
                "Thiết bị " + tb.getMaThietBi() + " tham chiếu khách hàng không tồn tại.");
            kiemTraSoLuong(data.hopDongs.stream().anyMatch(h -> h.getMaThietBi().equals(tb.getMaThietBi())),
                "Thiết bị " + tb.getMaThietBi() + " chưa được dùng trong hợp đồng.");
        }

        for (HopDong hd : data.hopDongs) {
            kiemTraSoLuong(data.khachHangs.stream().anyMatch(k -> k.getMaKH().equals(hd.getMaKH())),
                "Hợp đồng " + hd.getSoHD() + " tham chiếu khách hàng không tồn tại.");
            kiemTraSoLuong(data.nhanViens.stream().anyMatch(n -> n.getMaNV().equals(hd.getMaNV()) && n.getChucVu() == ChucVu.TU_VAN),
                "Hợp đồng " + hd.getSoHD() + " phải do nhân viên tư vấn lập.");
            ThietBi tb = timThietBi(data, hd.getMaThietBi());
            kiemTraSoLuong(tb.getMaKH().equals(hd.getMaKH()),
                "Khách hàng và chủ sở hữu thiết bị không khớp trong " + hd.getSoHD() + ".");

            int chiSo = Integer.parseInt(hd.getSoHD().substring(2)) - 1;
            int nhomHopDong = NHOM_THEO_HOP_DONG[chiSo];
            String maTBThamChieu = thietBiTheoKhachVaNhom.get(khoaThietBi(hd.getMaKH(), nhomHopDong));
            kiemTraSoLuong(maTBThamChieu != null && maTBThamChieu.equals(hd.getMaThietBi()),
                "Thiết bị " + hd.getMaThietBi() + " không phù hợp nhóm dịch vụ của " + hd.getSoHD() + ".");

            List<ChiTietHopDong> chiTiet = data.chiTietHopDongs.stream()
                .filter(ct -> ct.getSoHD().equals(hd.getSoHD())).collect(Collectors.toList());
            kiemTraSoLuong(chiTiet.size() == 3, "Hợp đồng " + hd.getSoHD() + " phải có 3 hạng mục.");
            for (ChiTietHopDong ct : chiTiet) {
                CongViec cv = timCongViec(data, ct.getMaCV());
                int nhomCongViec = Integer.parseInt(cv.getMaNhomCV().substring(3));
                kiemTraSoLuong(nhomCongViec == nhomHopDong,
                    "Công việc " + ct.getMaCV() + " không cùng nhóm dịch vụ với " + hd.getSoHD() + ".");
                kiemTraSoLuong(data.thos.stream().anyMatch(t -> t.getMaTho().equals(ct.getMaTho())),
                    "Chi tiết " + ct.getSoHD() + "/" + ct.getMaCV() + " tham chiếu thợ không tồn tại.");
                kiemTraSoLuong(data.khaNangThos.stream().anyMatch(kn -> kn.getMaTho().equals(ct.getMaTho()) && kn.getMaCV().equals(ct.getMaCV())),
                    "Thợ " + ct.getMaTho() + " chưa có kỹ năng " + ct.getMaCV() + ".");
            }
            double tongChiTiet = chiTiet.stream()
                .mapToDouble(ct -> timCongViec(data, ct.getMaCV()).getDongia()).sum();
            kiemTraSoLuong(Math.abs(tongChiTiet - hd.getTongtien()) < 0.01,
                "Tổng tiền " + hd.getSoHD() + " không khớp chi tiết.");
            kiemTraSoLuong(!hd.getNgayHenTra().isBefore(hd.getNgaylap()),
                "Ngày hẹn trả của " + hd.getSoHD() + " không hợp lệ.");
        }

        for (PhieuThu pt : data.phieuThus) {
            kiemTraSoLuong(data.hopDongs.stream().anyMatch(h -> h.getSoHD().equals(pt.getSoHD())),
                "Phiếu thu " + pt.getMaPhieuThu() + " tham chiếu hợp đồng không tồn tại.");
            kiemTraSoLuong(data.nhanViens.stream().anyMatch(n -> n.getMaNV().equals(pt.getMaNV()) && n.getChucVu() == ChucVu.THU_NGAN),
                "Phiếu thu " + pt.getMaPhieuThu() + " phải do thu ngân lập.");
            HopDong hd = timHopDong(data, pt.getSoHD());
            kiemTraSoLuong(pt.getSoTienTT() > 0 && !pt.getNgayTT().isBefore(hd.getNgaylap()),
                "Phiếu thu " + pt.getMaPhieuThu() + " có số tiền hoặc ngày không hợp lệ.");
        }

        for (KhaNangTho kn : data.khaNangThos) {
            kiemTraSoLuong(data.thos.stream().anyMatch(t -> t.getMaTho().equals(kn.getMaTho())),
                "Kỹ năng tham chiếu thợ " + kn.getMaTho() + " không tồn tại.");
            kiemTraSoLuong(data.congViecs.stream().anyMatch(cv -> cv.getMaCV().equals(kn.getMaCV())),
                "Kỹ năng tham chiếu công việc " + kn.getMaCV() + " không tồn tại.");
        }

        for (HopDong hd : data.hopDongs) {
            double daThu = data.phieuThus.stream().filter(pt -> pt.getSoHD().equals(hd.getSoHD()))
                .mapToDouble(PhieuThu::getSoTienTT).sum();
            kiemTraSoLuong(daThu <= hd.getTongtien() + 0.01,
                "Tổng phiếu thu vượt tổng tiền " + hd.getSoHD() + ".");
            if (hd.getTinhtrangHD() == TrangThaiHopDong.DA_TRA) {
                kiemTraSoLuong(Math.abs(daThu - hd.getTongtien()) < 0.01,
                    "Hợp đồng " + hd.getSoHD() + " đã trả nhưng chưa thanh toán đủ.");
            }
        }

        kiemTraSoLuong(data.hopDongs.stream().filter(h -> h.getTinhtrangHD() == TrangThaiHopDong.MOI_TIEP_NHAN).count() == 8,
            "Cần có 8 hợp đồng MOI_TIEP_NHAN.");
        kiemTraSoLuong(data.hopDongs.stream().filter(h -> h.getTinhtrangHD() == TrangThaiHopDong.DANG_SUA_CHUA).count() == 10,
            "Cần có 10 hợp đồng DANG_SUA_CHUA.");
        kiemTraSoLuong(data.hopDongs.stream().filter(h -> h.getTinhtrangHD() == TrangThaiHopDong.HOAN_THANH).count() == 8,
            "Cần có 8 hợp đồng HOAN_THANH.");
        kiemTraSoLuong(data.hopDongs.stream().filter(h -> h.getTinhtrangHD() == TrangThaiHopDong.DA_TRA).count() == 10,
            "Cần có 10 hợp đồng DA_TRA.");
        kiemTraSoLuong(data.phieuThus.stream().filter(p -> p.getPhuongThucTT() == PhuongThucThanhToan.TIEN_MAT).count() == 24,
            "Cần có 24 phiếu thu tiền mặt.");
        kiemTraSoLuong(data.phieuThus.stream().filter(p -> p.getPhuongThucTT() == PhuongThucThanhToan.CHUYEN_KHOAN).count() == 24,
            "Cần có 24 phiếu thu chuyển khoản.");

        Map<String, Long> soPhieuTheoHopDong = data.hopDongs.stream().collect(Collectors.toMap(
            HopDong::getSoHD,
            hd -> data.phieuThus.stream().filter(pt -> pt.getSoHD().equals(hd.getSoHD())).count()));
        kiemTraSoLuong(soPhieuTheoHopDong.values().stream().filter(n -> n == 0).count() == 6,
            "Cần có 6 hợp đồng chưa có phiếu thu.");
        kiemTraSoLuong(soPhieuTheoHopDong.values().stream().filter(n -> n == 1).count() == 12,
            "Cần có 12 hợp đồng có một phiếu thu.");
        kiemTraSoLuong(soPhieuTheoHopDong.values().stream().filter(n -> n == 2).count() == 18,
            "Cần có 18 hợp đồng có hai phiếu thu.");

        long khachHangCoHopDongNhungChuaThanhToan = data.khachHangs.stream().filter(k ->
            data.hopDongs.stream().anyMatch(hd -> hd.getMaKH().equals(k.getMaKH())) &&
            data.hopDongs.stream().filter(hd -> hd.getMaKH().equals(k.getMaKH())).allMatch(hd ->
                data.phieuThus.stream().noneMatch(pt -> pt.getSoHD().equals(hd.getSoHD())))).count();
        kiemTraSoLuong(khachHangCoHopDongNhungChuaThanhToan == 4,
            "Cần có 4 khách hàng có hợp đồng nhưng chưa thanh toán hợp đồng nào.");

        Map<String, Long> tanSuatCongViec = data.chiTietHopDongs.stream().collect(
            Collectors.groupingBy(ChiTietHopDong::getMaCV, Collectors.counting()));
        kiemTraSoLuong(tanSuatCongViec.size() == 20 && tanSuatCongViec.values().stream().allMatch(n -> n == 5 || n == 6),
            "Mỗi công việc phải xuất hiện 5 hoặc 6 lần trong hợp đồng.");

        Map<String, Double> doanhThuTheoCongViec = data.chiTietHopDongs.stream().collect(
            Collectors.groupingBy(ChiTietHopDong::getMaCV,
                Collectors.summingDouble(ct -> timCongViec(data, ct.getMaCV()).getDongia())));
        String congViecDoanhThuCaoNhat = doanhThuTheoCongViec.entrySet().stream()
            .max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
        kiemTraSoLuong(congViecDoanhThuCaoNhat.equals("CV20"),
            "CV20 phải có tổng doanh thu cao nhất để kiểm tra truy vấn 18.");
    }

    private static <T> void kiemTraDuyNhat(List<T> items, Function<T, String> keyExtractor, String message) {
        Set<String> keys = new HashSet<>();
        for (T item : items) {
            kiemTraSoLuong(keys.add(keyExtractor.apply(item)), message);
        }
    }

    private static CongViec timCongViec(DuLieuMau data, String maCV) {
        return data.congViecs.stream().filter(cv -> cv.getMaCV().equals(maCV)).findFirst()
            .orElseThrow(() -> new IllegalStateException("Không tìm thấy công việc " + maCV + "."));
    }

    private static ThietBi timThietBi(DuLieuMau data, String maThietBi) {
        return data.thietBis.stream().filter(tb -> tb.getMaThietBi().equals(maThietBi)).findFirst()
            .orElseThrow(() -> new IllegalStateException("Không tìm thấy thiết bị " + maThietBi + "."));
    }

    private static HopDong timHopDong(DuLieuMau data, String soHD) {
        return data.hopDongs.stream().filter(hd -> hd.getSoHD().equals(soHD)).findFirst()
            .orElseThrow(() -> new IllegalStateException("Không tìm thấy hợp đồng " + soHD + "."));
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
