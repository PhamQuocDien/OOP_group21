using System;
using System.Collections.Generic;
using System.Linq;
using CSharp.Entities;
using CSharp.Enums;

namespace CSharp.Data
{
    /// <summary>
    /// Gom các danh sách dữ liệu mẫu để các truy vấn LINQ có thể dùng chung.
    /// DataSeeder không sửa Entity, DAO hoặc chương trình chính.
    /// </summary>
    public sealed class DuLieuMau
    {
        // NhomTho trong bản mã nguồn được kiểm tra hiện khai báo ở global namespace.
        public List<NhomTho> NhomThos { get; } = new();
        public List<Tho> Thos { get; } = new();
        public List<NhomCongViec> NhomCongViecs { get; } = new();
        public List<CongViec> CongViecs { get; } = new();
        public List<KhachHang> KhachHangs { get; } = new();
        public List<ThietBi> ThietBis { get; } = new();
        public List<NhanVien> NhanViens { get; } = new();
        public List<HopDong> HopDongs { get; } = new();
        public List<ChiTietHopDong> ChiTietHopDongs { get; } = new();
        public List<PhieuThu> PhieuThus { get; } = new();
        public List<KhaNangTho> KhaNangThos { get; } = new();
    }

    public static class DataSeeder
    {
        // Mỗi hợp đồng thuộc một nhóm dịch vụ. Mã nhóm 1..5 tương ứng NCV01..NCV05.
        private static readonly int[] NhomTheoHopDong =
        {
            1, 2, 3, 4, 3, 4,
            1, 1, 5, 5, 5, 1,
            2, 2, 4, 5, 5, 3,
            5, 5, 3, 2, 2, 3,
            3, 3, 4, 4, 4, 5,
            2, 4, 1, 2, 1, 1
        };

        // KH01..KH04 chỉ có hợp đồng chưa thanh toán; KH05..KH15 có hợp đồng đã thanh toán.
        private static readonly string[] KhachHangTheoHopDong =
        {
            "KH01", "KH02", "KH03", "KH04", "KH03", "KH04",
            "KH05", "KH05", "KH05", "KH06", "KH06", "KH06",
            "KH07", "KH07", "KH07", "KH08", "KH08", "KH08",
            "KH09", "KH09", "KH09", "KH10", "KH10", "KH10",
            "KH11", "KH11", "KH11", "KH12", "KH12", "KH12",
            "KH13", "KH13", "KH14", "KH14", "KH15", "KH15"
        };

        private static readonly int[][] NhanVienTuVanTheoThang =
        {
            // Tháng 7: NV01 lập 4 hợp đồng, nhiều nhất trong tháng.
            new[] { 1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5 },
            // Tháng 8: NV02 lập 4 hợp đồng, nhiều nhất trong tháng.
            new[] { 2, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 1 },
            // Tháng 9: NV03 lập 4 hợp đồng, nhiều nhất trong tháng.
            new[] { 3, 3, 3, 3, 4, 4, 4, 5, 5, 1, 1, 2 }
        };

        public static DuLieuMau TaoDuLieuMau()
        {
            var data = new DuLieuMau();

            TaoKhachHang(data);
            TaoNhanVien(data);
            TaoNhomCongViec(data);
            TaoCongViec(data);
            TaoThoVaNhomTho(data);
            TaoKhaNangTho(data);
            Dictionary<(string MaKH, int NhomDichVu), string> thietBiTheoKhachVaNhom = TaoThietBi(data);
            TaoHopDongVaChiTiet(data, thietBiTheoKhachVaNhom);
            TaoPhieuThu(data);
            KiemTraToanVen(data, thietBiTheoKhachVaNhom);

            return data;
        }

        private static void TaoKhachHang(DuLieuMau data)
        {
            var mau = new (string MaKH, string HoTen, string Phai, DateOnly NgaySinh, string DiaChi, string Sdt)[]
            {
                ("KH01", "Nguyễn Hoàng Anh", "Nam", new DateOnly(1992, 4, 12), "Quận 1, TP. Hồ Chí Minh", "0901000001"),
                ("KH02", "Trần Minh Châu", "Nữ", new DateOnly(1989, 7, 23), "Quận 3, TP. Hồ Chí Minh", "0901000002"),
                ("KH03", "Lê Quốc Bảo", "Nam", new DateOnly(1995, 2, 15), "Quận 5, TP. Hồ Chí Minh", "0901000003"),
                ("KH04", "Phạm Ngọc Mai", "Nữ", new DateOnly(1990, 11, 6), "Quận 10, TP. Hồ Chí Minh", "0901000004"),
                ("KH05", "Võ Thành Đạt", "Nam", new DateOnly(1987, 9, 19), "Bình Thạnh, TP. Hồ Chí Minh", "0901000005"),
                ("KH06", "Đặng Thu Hà", "Nữ", new DateOnly(1994, 1, 30), "Gò Vấp, TP. Hồ Chí Minh", "0901000006"),
                ("KH07", "Bùi Gia Huy", "Nam", new DateOnly(1998, 6, 21), "Tân Bình, TP. Hồ Chí Minh", "0901000007"),
                ("KH08", "Đỗ Khánh Linh", "Nữ", new DateOnly(1991, 12, 8), "Phú Nhuận, TP. Hồ Chí Minh", "0901000008"),
                ("KH09", "Hồ Minh Quân", "Nam", new DateOnly(1993, 3, 4), "Thủ Đức, TP. Hồ Chí Minh", "0901000009"),
                ("KH10", "Ngô Bảo Trâm", "Nữ", new DateOnly(1988, 5, 27), "Quận 7, TP. Hồ Chí Minh", "0901000010"),
                ("KH11", "Dương Quốc Việt", "Nam", new DateOnly(1997, 8, 13), "Quận 8, TP. Hồ Chí Minh", "0901000011"),
                ("KH12", "Lý Thảo Nguyên", "Nữ", new DateOnly(1990, 10, 1), "Bình Tân, TP. Hồ Chí Minh", "0901000012"),
                ("KH13", "Nguyễn Đức Phúc", "Nam", new DateOnly(1986, 2, 20), "Tân Phú, TP. Hồ Chí Minh", "0901000013"),
                ("KH14", "Trần Mỹ Duyên", "Nữ", new DateOnly(1996, 4, 17), "Hóc Môn, TP. Hồ Chí Minh", "0901000014"),
                ("KH15", "Phan Tuấn Kiệt", "Nam", new DateOnly(1992, 12, 29), "Nhà Bè, TP. Hồ Chí Minh", "0901000015")
            };

            foreach (var kh in mau)
            {
                data.KhachHangs.Add(new KhachHang(kh.MaKH, kh.HoTen, kh.Phai, kh.NgaySinh, kh.DiaChi, kh.Sdt));
            }
        }

        private static void TaoNhanVien(DuLieuMau data)
        {
            var mau = new (string MaNV, string HoTen, string Phai, DateOnly NgaySinh, string DiaChi, string Sdt, ChucVu ChucVu)[]
            {
                ("NV01", "Nguyễn Văn Hưng", "Nam", new DateOnly(1988, 3, 10), "Quận 1, TP. Hồ Chí Minh", "0912000001", ChucVu.TU_VAN),
                ("NV02", "Trần Thanh Tùng", "Nam", new DateOnly(1990, 5, 22), "Quận 3, TP. Hồ Chí Minh", "0912000002", ChucVu.TU_VAN),
                ("NV03", "Lê Thị Thu", "Nữ", new DateOnly(1992, 8, 14), "Quận 5, TP. Hồ Chí Minh", "0912000003", ChucVu.TU_VAN),
                ("NV04", "Phạm Nhật Nam", "Nam", new DateOnly(1987, 11, 2), "Quận 10, TP. Hồ Chí Minh", "0912000004", ChucVu.TU_VAN),
                ("NV05", "Võ Ngọc Yến", "Nữ", new DateOnly(1993, 6, 18), "Bình Thạnh, TP. Hồ Chí Minh", "0912000005", ChucVu.TU_VAN),
                ("NV06", "Nguyễn Thị Loan", "Nữ", new DateOnly(1989, 1, 9), "Gò Vấp, TP. Hồ Chí Minh", "0912000006", ChucVu.THU_NGAN),
                ("NV07", "Trương Minh Anh", "Nam", new DateOnly(1991, 4, 25), "Tân Bình, TP. Hồ Chí Minh", "0912000007", ChucVu.THU_NGAN),
                ("NV08", "Đỗ Thanh Phương", "Nữ", new DateOnly(1994, 9, 7), "Phú Nhuận, TP. Hồ Chí Minh", "0912000008", ChucVu.THU_NGAN),
                ("NV09", "Bùi Thị Hạnh", "Nữ", new DateOnly(1990, 12, 11), "Thủ Đức, TP. Hồ Chí Minh", "0912000009", ChucVu.THU_NGAN)
            };

            foreach (var nv in mau)
            {
                data.NhanViens.Add(new NhanVien(nv.MaNV, nv.HoTen, nv.Phai, nv.NgaySinh, nv.DiaChi, nv.Sdt, nv.ChucVu));
            }
        }

        private static void TaoNhomCongViec(DuLieuMau data)
        {
            data.NhomCongViecs.AddRange(new[]
            {
                new NhomCongViec("NCV01", "Sửa chữa điện thoại và máy tính bảng", "Kiểm tra, bảo dưỡng và xử lý lỗi thiết bị di động."),
                new NhomCongViec("NCV02", "Sửa chữa máy tính xách tay", "Bảo dưỡng và thay thế linh kiện laptop."),
                new NhomCongViec("NCV03", "Sửa chữa máy tính để bàn", "Kiểm tra, nâng cấp và sửa chữa máy tính để bàn."),
                new NhomCongViec("NCV04", "Sửa chữa thiết bị hiển thị", "Sửa chữa tivi và màn hình."),
                new NhomCongViec("NCV05", "Thiết bị mạng và bo mạch chuyên dụng", "Cấu hình, sửa chữa thiết bị mạng và bo mạch." )
            });
        }

        private static void TaoCongViec(DuLieuMau data)
        {
            data.CongViecs.AddRange(new[]
            {
                new CongViec("CV01", 350_000, "Kiểm tra và chẩn đoán thiết bị di động", "Kiểm tra tổng quát và xác định lỗi thiết bị di động.", "NCV01"),
                new CongViec("CV02", 450_000, "Cài đặt và khôi phục phần mềm thiết bị di động", "Cài đặt hoặc khôi phục phần mềm theo yêu cầu.", "NCV01"),
                new CongViec("CV03", 500_000, "Vệ sinh và bảo dưỡng thiết bị di động", "Vệ sinh thiết bị và kiểm tra hoạt động.", "NCV01"),
                new CongViec("CV04", 600_000, "Sửa loa hoặc micro thiết bị di động", "Kiểm tra và xử lý lỗi loa hoặc micro.", "NCV01"),

                new CongViec("CV05", 450_000, "Vệ sinh laptop", "Vệ sinh quạt, khe tản nhiệt và kiểm tra nhiệt độ.", "NCV02"),
                new CongViec("CV06", 500_000, "Sửa cổng sạc laptop", "Kiểm tra và sửa lỗi cổng sạc.", "NCV02"),
                new CongViec("CV07", 700_000, "Nâng cấp RAM laptop", "Lắp đặt và kiểm tra bộ nhớ RAM tương thích.", "NCV02"),
                new CongViec("CV08", 900_000, "Thay ổ SSD laptop", "Thay ổ SSD và kiểm tra nhận diện ổ đĩa.", "NCV02"),

                new CongViec("CV09", 350_000, "Kiểm tra và vệ sinh máy tính để bàn", "Vệ sinh máy và kiểm tra các linh kiện cơ bản.", "NCV03"),
                new CongViec("CV10", 600_000, "Nâng cấp RAM máy tính để bàn", "Lắp đặt và kiểm tra bộ nhớ RAM.", "NCV03"),
                new CongViec("CV11", 800_000, "Thay ổ SSD máy tính để bàn", "Thay ổ lưu trữ và kiểm tra hoạt động.", "NCV03"),
                new CongViec("CV12", 1_200_000, "Sửa bo mạch chủ máy tính để bàn", "Kiểm tra và sửa lỗi bo mạch chủ.", "NCV03"),

                new CongViec("CV13", 800_000, "Kiểm tra bộ nguồn thiết bị hiển thị", "Kiểm tra và sửa bộ nguồn thiết bị hiển thị.", "NCV04"),
                new CongViec("CV14", 1_000_000, "Sửa lỗi hình ảnh thiết bị hiển thị", "Kiểm tra tín hiệu và xử lý lỗi hiển thị.", "NCV04"),
                new CongViec("CV15", 1_500_000, "Sửa bo mạch thiết bị hiển thị", "Kiểm tra và sửa bo mạch điều khiển thiết bị hiển thị.", "NCV04"),
                new CongViec("CV16", 1_800_000, "Thay tấm nền hiển thị", "Thay tấm nền tương thích và kiểm tra hiển thị.", "NCV04"),

                new CongViec("CV17", 300_000, "Cấu hình router Wi-Fi", "Thiết lập mạng không dây và bảo mật cơ bản.", "NCV05"),
                new CongViec("CV18", 450_000, "Sửa bộ nguồn modem hoặc router", "Kiểm tra và xử lý lỗi nguồn thiết bị mạng.", "NCV05"),
                new CongViec("CV19", 850_000, "Thay cổng WAN hoặc LAN", "Thay cổng kết nối và kiểm tra truyền dữ liệu.", "NCV05"),
                new CongViec("CV20", 4_200_000, "Thay bo mạch thiết bị mạng cao cấp", "Thay bo mạch chính cho thiết bị mạng hiệu năng cao.", "NCV05")
            });
        }

        private static void TaoThoVaNhomTho(DuLieuMau data)
        {
            var mau = new (string MaTho, string TenTho, string Phai, DateOnly NgaySinh, string Sdt, string MaNhomTho)[]
            {
                ("T01", "Nguyễn Quốc Bảo", "Nam", new DateOnly(1988, 1, 15), "0933000001", "NT01"),
                ("T02", "Trần Minh Khang", "Nam", new DateOnly(1991, 3, 21), "0933000002", "NT01"),
                ("T03", "Lê Hoàng Phúc", "Nam", new DateOnly(1994, 7, 12), "0933000003", "NT01"),
                ("T04", "Phạm Anh Tuấn", "Nam", new DateOnly(1987, 10, 9), "0933000004", "NT02"),
                ("T05", "Võ Thành Long", "Nam", new DateOnly(1992, 5, 17), "0933000005", "NT02"),
                ("T06", "Đặng Nhật Minh", "Nam", new DateOnly(1995, 12, 4), "0933000006", "NT02"),
                ("T07", "Bùi Đức Thịnh", "Nam", new DateOnly(1989, 6, 26), "0933000007", "NT03"),
                ("T08", "Đỗ Gia Huy", "Nam", new DateOnly(1993, 8, 2), "0933000008", "NT03"),
                ("T09", "Hồ Thanh Phong", "Nam", new DateOnly(1996, 2, 18), "0933000009", "NT03"),
                ("T10", "Ngô Minh Trí", "Nam", new DateOnly(1986, 9, 13), "0933000010", "NT04"),
                ("T11", "Dương Quốc Khánh", "Nam", new DateOnly(1990, 4, 7), "0933000011", "NT04"),
                ("T12", "Lý Hoàng Nam", "Nam", new DateOnly(1997, 11, 29), "0933000012", "NT04")
            };

            foreach (var tho in mau)
            {
                data.Thos.Add(new Tho(tho.MaTho, tho.TenTho, tho.Phai, tho.NgaySinh, tho.Sdt, tho.MaNhomTho));
            }

            // Nhóm được thêm sau danh sách thợ vì maTruongNhom phải trỏ đến thợ có thật.
            data.NhomThos.AddRange(new NhomTho[]
            {
                new NhomTho("NT01", "Tổ kỹ thuật thiết bị di động", "T01"),
                new NhomTho("NT02", "Tổ kỹ thuật máy tính", "T04"),
                new NhomTho("NT03", "Tổ sửa thiết bị hiển thị", "T07"),
                new NhomTho("NT04", "Tổ thiết bị mạng và bo mạch", "T10")
            });
        }

        private static void TaoKhaNangTho(DuLieuMau data)
        {
            var capDos = new[] { CapDo.CO_BAN, CapDo.KHA, CapDo.TOT, CapDo.CHUYEN_GIA };

            // Mỗi thợ có 5 kỹ năng khác nhau; các kỹ năng phủ đủ 20 công việc.
            // Cặp (maTho, maCV) được tạo duy nhất để phù hợp khóa chính ghép.
            for (int i = 0; i < data.Thos.Count; i++)
            {
                int batDau = (i * 7) % data.CongViecs.Count;
                for (int k = 0; k < 5; k++)
                {
                    int chiSoCongViec = (batDau + k) % data.CongViecs.Count;
                    string maCV = data.CongViecs[chiSoCongViec].MaCV;
                    data.KhaNangThos.Add(new KhaNangTho(data.Thos[i].MaTho, maCV, capDos[(i + k) % capDos.Length]));
                }
            }
        }

        private static Dictionary<(string MaKH, int NhomDichVu), string> TaoThietBi(DuLieuMau data)
        {
            var mau = new (string MaKH, int Nhom, string Loai, string Hang, string Model, string TinhTrang)[]
            {
                ("KH01", 1, "Điện thoại", "Samsung", "Galaxy S22", "Nứt kính màn hình"),
                ("KH02", 2, "Laptop", "Dell", "Inspiron 14", "Không khởi động ổn định"),
                ("KH03", 3, "Máy tính để bàn", "Dell", "OptiPlex 7090", "Máy chạy chậm"),
                ("KH04", 4, "Tivi", "Samsung", "Crystal UHD 43 inch", "Không lên hình"),

                ("KH05", 1, "Điện thoại", "Apple", "iPhone 13", "Pin chai, sạc chậm"),
                ("KH05", 5, "Router Wi-Fi", "TP-Link", "Archer AX23", "Mất kết nối không dây"),
                ("KH06", 5, "Router Wi-Fi", "ASUS", "RT-AX55", "Tín hiệu Wi-Fi không ổn định"),
                ("KH06", 1, "Điện thoại", "Apple", "iPhone 12", "Loa thoại bị rè"),
                ("KH07", 2, "Laptop", "Lenovo", "ThinkPad E14", "Quạt tản nhiệt kêu lớn"),
                ("KH07", 4, "Màn hình máy tính", "LG", "27MP400", "Màn hình nhấp nháy"),
                ("KH08", 5, "Router Wi-Fi", "TP-Link", "Deco M4", "Không cấp địa chỉ mạng ổn định"),
                ("KH08", 3, "Máy tính để bàn", "HP", "Pavilion Desktop", "Lỗi bộ nhớ"),
                ("KH09", 5, "Router Wi-Fi", "Huawei", "WiFi AX3", "Cổng LAN không hoạt động"),
                ("KH09", 3, "Máy tính để bàn", "Lenovo", "ThinkCentre M70", "Ổ lưu trữ báo lỗi"),
                ("KH10", 2, "Laptop", "HP", "ProBook 440", "Cổng sạc tiếp xúc kém"),
                ("KH10", 3, "Máy tính để bàn", "ASUS", "ExpertCenter D5", "Máy quá nhiệt"),
                ("KH11", 3, "Máy tính để bàn", "Acer", "Veriton X", "Lỗi khởi động"),
                ("KH11", 4, "Màn hình máy tính", "Samsung", "Essential 24 inch", "Mất tín hiệu hình ảnh"),
                ("KH12", 4, "Tivi", "Sony", "Bravia 50 inch", "Có tiếng nhưng không có hình"),
                ("KH12", 5, "Modem mạng", "TP-Link", "Archer VR400", "Kết nối mạng chập chờn"),
                ("KH13", 2, "Laptop", "Acer", "Aspire 5", "Máy chạy chậm"),
                ("KH13", 4, "Tivi", "LG", "UHD AI 43 inch", "Lỗi màu sắc"),
                ("KH14", 1, "Máy tính bảng", "Apple", "iPad Air (5th Gen)", "Không nhận sạc ổn định"),
                ("KH14", 2, "Laptop", "ASUS", "Vivobook 15", "Không nhận ổ SSD"),
                ("KH15", 1, "Điện thoại", "Xiaomi", "Redmi Note 12", "Lỗi loa ngoài")
            };

            var traCuu = new Dictionary<(string MaKH, int NhomDichVu), string>();
            for (int i = 0; i < mau.Length; i++)
            {
                var tb = mau[i];
                string maThietBi = $"TB{i + 1:D2}";
                data.ThietBis.Add(new ThietBi(maThietBi, tb.Loai, tb.Hang, tb.Model, tb.TinhTrang, tb.MaKH));
                traCuu.Add((tb.MaKH, tb.Nhom), maThietBi);
            }

            return traCuu;
        }

        private static void TaoHopDongVaChiTiet(
            DuLieuMau data,
            Dictionary<(string MaKH, int NhomDichVu), string> thietBiTheoKhachVaNhom)
        {
            int[] mauCombo7 = { 0, 1, 2, 3, 1, 2, 3 };
            int[] mauCombo8 = { 0, 1, 2, 3, 0, 1, 2, 3 };
            int[][] comboTheoViTriLoaiTru =
            {
                new[] { 1, 2, 3 }, // bỏ công việc đầu tiên trong nhóm
                new[] { 0, 2, 3 },
                new[] { 0, 1, 3 },
                new[] { 0, 1, 2 }
            };

            var soLanTheoNhom = new int[5];
            var soLanPhanCong = data.Thos.ToDictionary(t => t.MaTho, _ => 0);

            for (int i = 0; i < 36; i++)
            {
                int nhomDichVu = NhomTheoHopDong[i];
                string maKH = KhachHangTheoHopDong[i];
                if (!thietBiTheoKhachVaNhom.TryGetValue((maKH, nhomDichVu), out string? maThietBi))
                {
                    throw new InvalidOperationException($"Không tìm thấy thiết bị phù hợp cho {maKH}, nhóm dịch vụ {nhomDichVu}.");
                }

                int viTriTrongNhom = soLanTheoNhom[nhomDichVu - 1]++;
                int[] mauCombo = nhomDichVu == 5 ? mauCombo8 : mauCombo7;
                int viTriCombo = mauCombo[viTriTrongNhom];
                int soCongViecDauTien = (nhomDichVu - 1) * 4 + 1;
                int[] soThuTuCongViec = comboTheoViTriLoaiTru[viTriCombo]
                    .Select(viTri => soCongViecDauTien + viTri)
                    .ToArray();

                DateOnly ngayLap = new DateOnly(2026, 7 + (i / 12), 2 + (i % 12) * 2);
                DateOnly ngayHenTra = ngayLap.AddDays(7 + (i % 8));
                TrangThaiHopDong trangThaiHD = XacDinhTrangThaiHopDong(i);
                int maNVTuVan = NhanVienTuVanTheoThang[i / 12][i % 12];
                string soHD = $"HD{i + 1:D3}";
                string maNV = $"NV{maNVTuVan:D2}";

                var congViecHopDong = soThuTuCongViec
                    .Select(stt => data.CongViecs.Single(cv => cv.MaCV == $"CV{stt:D2}"))
                    .ToList();
                double tongTien = congViecHopDong.Sum(cv => cv.Dongia);

                data.HopDongs.Add(new HopDong(
                    soHD, ngayLap, ngayHenTra, trangThaiHD, tongTien, maKH, maNV, maThietBi));

                for (int j = 0; j < congViecHopDong.Count; j++)
                {
                    CongViec congViec = congViecHopDong[j];
                    var thoPhuHop = data.Thos
                        .Where(t => data.KhaNangThos.Any(kn => kn.MaTho == t.MaTho && kn.MaCV == congViec.MaCV))
                        .OrderBy(t => soLanPhanCong[t.MaTho])
                        .ThenBy(t => (int.Parse(t.MaTho.Substring(1)) + i + j) % data.Thos.Count)
                        .FirstOrDefault();

                    if (thoPhuHop is null)
                    {
                        throw new InvalidOperationException($"Không có thợ đủ kỹ năng cho {congViec.MaCV}.");
                    }

                    soLanPhanCong[thoPhuHop.MaTho]++;
                    TrangThaiCongViec trangThaiCV = XacDinhTrangThaiCongViec(trangThaiHD, i, j);
                    data.ChiTietHopDongs.Add(new ChiTietHopDong(soHD, congViec.MaCV, thoPhuHop.MaTho, trangThaiCV));
                }
            }
        }

        private static TrangThaiHopDong XacDinhTrangThaiHopDong(int chiSoHopDong)
        {
            if (chiSoHopDong <= 2 || (chiSoHopDong >= 6 && chiSoHopDong <= 10))
            {
                return TrangThaiHopDong.MOI_TIEP_NHAN;
            }

            if ((chiSoHopDong >= 3 && chiSoHopDong <= 5) || (chiSoHopDong >= 11 && chiSoHopDong <= 17))
            {
                return TrangThaiHopDong.DANG_SUA_CHUA;
            }

            return chiSoHopDong <= 25
                ? TrangThaiHopDong.HOAN_THANH
                : TrangThaiHopDong.DA_TRA;
        }

        private static TrangThaiCongViec XacDinhTrangThaiCongViec(TrangThaiHopDong trangThaiHD, int chiSoHopDong, int chiSoCongViec)
        {
            if (trangThaiHD == TrangThaiHopDong.MOI_TIEP_NHAN)
            {
                return TrangThaiCongViec.CHUA_THUC_HIEN;
            }

            if (trangThaiHD == TrangThaiHopDong.DANG_SUA_CHUA)
            {
                return (chiSoHopDong + chiSoCongViec) % 2 == 0
                    ? TrangThaiCongViec.DANG_THUC_HIEN
                    : TrangThaiCongViec.CHUA_THUC_HIEN;
            }

            return TrangThaiCongViec.HOAN_THANH;
        }

        private static void TaoPhieuThu(DuLieuMau data)
        {
            double[] tyLeThanhToanTam = { 0.35, 0.40, 0.45, 0.50, 0.55, 0.60 };

            foreach (HopDong hopDong in data.HopDongs)
            {
                int soThuTuHD = int.Parse(hopDong.SoHD.Substring(2));

                // HD001..HD006 không có phiếu thu.
                if (soThuTuHD <= 6)
                {
                    continue;
                }

                if (soThuTuHD <= 18)
                {
                    // HD007..HD018 có khoản thanh toán tạm ứng một phần.
                    double soTien = Math.Round(
                        hopDong.Tongtien * tyLeThanhToanTam[(soThuTuHD - 7) % tyLeThanhToanTam.Length],
                        0,
                        MidpointRounding.AwayFromZero);
                    ThemPhieuThu(data, hopDong, soTien, hopDong.Ngaylap.AddDays(2 + soThuTuHD % 3));
                    continue;
                }

                // HD019..HD036 có hai lần thanh toán; tổng hai lần bằng đúng tổng hợp đồng.
                double lanDau = Math.Round(hopDong.Tongtien * 0.40, 0, MidpointRounding.AwayFromZero);
                double lanCuoi = hopDong.Tongtien - lanDau;
                ThemPhieuThu(data, hopDong, lanDau, hopDong.Ngaylap.AddDays(2));
                ThemPhieuThu(data, hopDong, lanCuoi, hopDong.Ngaylap.AddDays(5));
            }
        }

        private static void ThemPhieuThu(DuLieuMau data, HopDong hopDong, double soTien, DateOnly ngayThanhToan)
        {
            int soThuTuPhieu = data.PhieuThus.Count + 1;
            string maPhieuThu = $"PT{soThuTuPhieu:D3}";
            string maNVThuNgan = $"NV{6 + ((soThuTuPhieu - 1) % 4):D2}";
            PhuongThucThanhToan phuongThuc = soThuTuPhieu % 2 == 1
                ? PhuongThucThanhToan.TIEN_MAT
                : PhuongThucThanhToan.CHUYEN_KHOAN;

            data.PhieuThus.Add(new PhieuThu(
                maPhieuThu, soTien, ngayThanhToan, phuongThuc, hopDong.SoHD, maNVThuNgan));
        }

        private static void KiemTraToanVen(
            DuLieuMau data,
            Dictionary<(string MaKH, int NhomDichVu), string> thietBiTheoKhachVaNhom)
        {
            KiemTraSoLuong(data.NhomThos.Count == 4, "NHOM_THO phải có 4 bản ghi.");
            KiemTraSoLuong(data.Thos.Count == 12, "THO phải có 12 bản ghi.");
            KiemTraSoLuong(data.NhomCongViecs.Count == 5, "NHOM_CONG_VIEC phải có 5 bản ghi.");
            KiemTraSoLuong(data.CongViecs.Count == 20, "CONG_VIEC phải có 20 bản ghi.");
            KiemTraSoLuong(data.KhachHangs.Count == 15, "KHACH_HANG phải có 15 bản ghi.");
            KiemTraSoLuong(data.ThietBis.Count == 25, "THIET_BI phải có 25 bản ghi.");
            KiemTraSoLuong(data.NhanViens.Count == 9, "NHAN_VIEN phải có 9 bản ghi.");
            KiemTraSoLuong(data.HopDongs.Count == 36, "HOP_DONG phải có 36 bản ghi.");
            KiemTraSoLuong(data.ChiTietHopDongs.Count == 108, "CHI_TIET_HOP_DONG phải có 108 bản ghi.");
            KiemTraSoLuong(data.PhieuThus.Count == 48, "PHIEU_THU phải có 48 bản ghi.");
            KiemTraSoLuong(data.KhaNangThos.Count == 60, "KHA_NANG_THO phải có 60 bản ghi.");

            KiemTraSoLuong(data.KhachHangs.Select(k => k.MaKH).Distinct().Count() == data.KhachHangs.Count, "Mã khách hàng bị trùng.");
            KiemTraSoLuong(data.ThietBis.Select(t => t.MaThietBi).Distinct().Count() == data.ThietBis.Count, "Mã thiết bị bị trùng.");
            KiemTraSoLuong(data.NhanViens.Select(n => n.MaNV).Distinct().Count() == data.NhanViens.Count, "Mã nhân viên bị trùng.");
            KiemTraSoLuong(data.Thos.Select(t => t.MaTho).Distinct().Count() == data.Thos.Count, "Mã thợ bị trùng.");
            KiemTraSoLuong(data.NhomThos.Select(n => n.MaNhomTho).Distinct().Count() == data.NhomThos.Count, "Mã nhóm thợ bị trùng.");
            KiemTraSoLuong(data.NhomCongViecs.Select(n => n.MaNhomCV).Distinct().Count() == data.NhomCongViecs.Count, "Mã nhóm công việc bị trùng.");
            KiemTraSoLuong(data.CongViecs.Select(c => c.MaCV).Distinct().Count() == data.CongViecs.Count, "Mã công việc bị trùng.");
            KiemTraSoLuong(data.HopDongs.Select(h => h.SoHD).Distinct().Count() == data.HopDongs.Count, "Số hợp đồng bị trùng.");
            KiemTraSoLuong(data.PhieuThus.Select(p => p.MaPhieuThu).Distinct().Count() == data.PhieuThus.Count, "Mã phiếu thu bị trùng.");
            KiemTraSoLuong(data.ChiTietHopDongs.Select(c => $"{c.SoHD}|{c.MaCV}").Distinct().Count() == data.ChiTietHopDongs.Count, "Khóa ghép CHI_TIET_HOP_DONG bị trùng.");
            KiemTraSoLuong(data.KhaNangThos.Select(k => $"{k.MaTho}|{k.MaCV}").Distinct().Count() == data.KhaNangThos.Count, "Khóa ghép KHA_NANG_THO bị trùng.");

            foreach (NhomTho nhom in data.NhomThos)
            {
                Tho? truongNhom = data.Thos.SingleOrDefault(t => t.MaTho == nhom.MaTruongNhom);
                KiemTraSoLuong(truongNhom is not null && truongNhom.MaNhomTho == nhom.MaNhomTho,
                    $"Trưởng nhóm {nhom.MaTruongNhom} không thuộc {nhom.MaNhomTho}.");
            }

            foreach (Tho tho in data.Thos)
            {
                KiemTraSoLuong(data.NhomThos.Any(n => n.MaNhomTho == tho.MaNhomTho), $"Thợ {tho.MaTho} tham chiếu nhóm không tồn tại.");
            }

            foreach (CongViec congViec in data.CongViecs)
            {
                KiemTraSoLuong(data.NhomCongViecs.Any(n => n.MaNhomCV == congViec.MaNhomCV), $"Công việc {congViec.MaCV} tham chiếu nhóm công việc không tồn tại.");
            }

            foreach (ThietBi thietBi in data.ThietBis)
            {
                KiemTraSoLuong(data.KhachHangs.Any(k => k.MaKH == thietBi.MaKH), $"Thiết bị {thietBi.MaThietBi} tham chiếu khách hàng không tồn tại.");
                KiemTraSoLuong(data.HopDongs.Any(h => h.MaThietBi == thietBi.MaThietBi), $"Thiết bị {thietBi.MaThietBi} chưa được dùng trong hợp đồng.");
            }

            foreach (HopDong hopDong in data.HopDongs)
            {
                KiemTraSoLuong(data.KhachHangs.Any(k => k.MaKH == hopDong.MaKH), $"Hợp đồng {hopDong.SoHD} tham chiếu khách hàng không tồn tại.");
                KiemTraSoLuong(data.NhanViens.Any(n => n.MaNV == hopDong.MaNV && n.ChucVu == ChucVu.TU_VAN), $"Hợp đồng {hopDong.SoHD} phải do nhân viên tư vấn lập.");
                ThietBi thietBi = data.ThietBis.Single(t => t.MaThietBi == hopDong.MaThietBi);
                KiemTraSoLuong(thietBi.MaKH == hopDong.MaKH, $"Khách hàng và chủ sở hữu thiết bị không khớp trong {hopDong.SoHD}.");

                int chiSo = int.Parse(hopDong.SoHD.Substring(2)) - 1;
                int nhomHopDong = NhomTheoHopDong[chiSo];
                KiemTraSoLuong(thietBiTheoKhachVaNhom.TryGetValue((hopDong.MaKH, nhomHopDong), out string? maTB) && maTB == hopDong.MaThietBi,
                    $"Thiết bị {hopDong.MaThietBi} không phù hợp nhóm dịch vụ của {hopDong.SoHD}.");

                var chiTiet = data.ChiTietHopDongs.Where(c => c.SoHD == hopDong.SoHD).ToList();
                KiemTraSoLuong(chiTiet.Count == 3, $"Hợp đồng {hopDong.SoHD} phải có 3 hạng mục.");
                foreach (ChiTietHopDong ct in chiTiet)
                {
                    CongViec cv = data.CongViecs.Single(c => c.MaCV == ct.MaCV);
                    int nhomCongViec = int.Parse(cv.MaNhomCV.Substring(3));
                    KiemTraSoLuong(nhomCongViec == nhomHopDong, $"Công việc {ct.MaCV} không cùng nhóm dịch vụ với {hopDong.SoHD}.");
                    KiemTraSoLuong(data.Thos.Any(t => t.MaTho == ct.MaTho), $"Chi tiết {ct.SoHD}/{ct.MaCV} tham chiếu thợ không tồn tại.");
                    KiemTraSoLuong(data.KhaNangThos.Any(k => k.MaTho == ct.MaTho && k.MaCV == ct.MaCV), $"Thợ {ct.MaTho} chưa có kỹ năng {ct.MaCV}.");
                }

                double tongChiTiet = chiTiet.Sum(ct => data.CongViecs.Single(cv => cv.MaCV == ct.MaCV).Dongia);
                KiemTraSoLuong(Math.Abs(tongChiTiet - hopDong.Tongtien) < 0.01, $"Tổng tiền {hopDong.SoHD} không khớp chi tiết.");
                KiemTraSoLuong(hopDong.NgayHenTra >= hopDong.Ngaylap, $"Ngày hẹn trả của {hopDong.SoHD} không hợp lệ.");
            }

            foreach (PhieuThu phieuThu in data.PhieuThus)
            {
                KiemTraSoLuong(data.HopDongs.Any(h => h.SoHD == phieuThu.SoHD), $"Phiếu thu {phieuThu.MaPhieuThu} tham chiếu hợp đồng không tồn tại.");
                KiemTraSoLuong(data.NhanViens.Any(n => n.MaNV == phieuThu.MaNV && n.ChucVu == ChucVu.THU_NGAN), $"Phiếu thu {phieuThu.MaPhieuThu} phải do thu ngân lập.");
                HopDong hd = data.HopDongs.Single(h => h.SoHD == phieuThu.SoHD);
                KiemTraSoLuong(phieuThu.SoTienTT > 0 && phieuThu.NgayTT >= hd.Ngaylap, $"Phiếu thu {phieuThu.MaPhieuThu} có số tiền hoặc ngày không hợp lệ.");
            }

            foreach (KhaNangTho khaNang in data.KhaNangThos)
            {
                KiemTraSoLuong(data.Thos.Any(t => t.MaTho == khaNang.MaTho), $"Kỹ năng tham chiếu thợ {khaNang.MaTho} không tồn tại.");
                KiemTraSoLuong(data.CongViecs.Any(c => c.MaCV == khaNang.MaCV), $"Kỹ năng tham chiếu công việc {khaNang.MaCV} không tồn tại.");
            }

            foreach (HopDong hopDong in data.HopDongs)
            {
                double daThu = data.PhieuThus.Where(p => p.SoHD == hopDong.SoHD).Sum(p => p.SoTienTT);
                KiemTraSoLuong(daThu <= hopDong.Tongtien + 0.01, $"Tổng phiếu thu vượt tổng tiền {hopDong.SoHD}.");
                if (hopDong.TinhtrangHD == TrangThaiHopDong.DA_TRA)
                {
                    KiemTraSoLuong(Math.Abs(daThu - hopDong.Tongtien) < 0.01, $"Hợp đồng {hopDong.SoHD} đã trả nhưng chưa thanh toán đủ.");
                }
            }

            KiemTraSoLuong(data.HopDongs.Count(h => h.TinhtrangHD == TrangThaiHopDong.MOI_TIEP_NHAN) == 8, "Cần có 8 hợp đồng MOI_TIEP_NHAN.");
            KiemTraSoLuong(data.HopDongs.Count(h => h.TinhtrangHD == TrangThaiHopDong.DANG_SUA_CHUA) == 10, "Cần có 10 hợp đồng DANG_SUA_CHUA.");
            KiemTraSoLuong(data.HopDongs.Count(h => h.TinhtrangHD == TrangThaiHopDong.HOAN_THANH) == 8, "Cần có 8 hợp đồng HOAN_THANH.");
            KiemTraSoLuong(data.HopDongs.Count(h => h.TinhtrangHD == TrangThaiHopDong.DA_TRA) == 10, "Cần có 10 hợp đồng DA_TRA.");
            KiemTraSoLuong(data.PhieuThus.Count(p => p.PhuongThucTT == PhuongThucThanhToan.TIEN_MAT) == 24, "Cần có 24 phiếu thu tiền mặt.");
            KiemTraSoLuong(data.PhieuThus.Count(p => p.PhuongThucTT == PhuongThucThanhToan.CHUYEN_KHOAN) == 24, "Cần có 24 phiếu thu chuyển khoản.");

            var soPhieuTheoHopDong = data.HopDongs.ToDictionary(h => h.SoHD, h => data.PhieuThus.Count(p => p.SoHD == h.SoHD));
            KiemTraSoLuong(soPhieuTheoHopDong.Count(kv => kv.Value == 0) == 6, "Cần có 6 hợp đồng chưa có phiếu thu.");
            KiemTraSoLuong(soPhieuTheoHopDong.Count(kv => kv.Value == 1) == 12, "Cần có 12 hợp đồng có một phiếu thu.");
            KiemTraSoLuong(soPhieuTheoHopDong.Count(kv => kv.Value == 2) == 18, "Cần có 18 hợp đồng có hai phiếu thu.");

            int khachHangCoHopDongNhungChuaThanhToan = data.KhachHangs.Count(k =>
                data.HopDongs.Any(h => h.MaKH == k.MaKH) &&
                data.HopDongs.Where(h => h.MaKH == k.MaKH).All(h => !data.PhieuThus.Any(p => p.SoHD == h.SoHD)));
            KiemTraSoLuong(khachHangCoHopDongNhungChuaThanhToan == 4, "Cần có 4 khách hàng có hợp đồng nhưng chưa thanh toán hợp đồng nào.");

            var tanSuatCongViec = data.ChiTietHopDongs
                .GroupBy(ct => ct.MaCV)
                .ToDictionary(g => g.Key, g => g.Count());
            KiemTraSoLuong(tanSuatCongViec.Count == 20 && tanSuatCongViec.Values.All(n => n == 5 || n == 6),
                "Mỗi công việc phải xuất hiện 5 hoặc 6 lần trong hợp đồng.");

            var doanhThuTheoCongViec = data.ChiTietHopDongs
                .GroupBy(ct => ct.MaCV)
                .ToDictionary(
                    g => g.Key,
                    g => g.Sum(ct => data.CongViecs.Single(cv => cv.MaCV == ct.MaCV).Dongia));
            string congViecDoanhThuCaoNhat = doanhThuTheoCongViec
                .OrderByDescending(kv => kv.Value)
                .First().Key;
            KiemTraSoLuong(congViecDoanhThuCaoNhat == "CV20", "CV20 phải có tổng doanh thu cao nhất để kiểm tra truy vấn 18.");
        }

        private static void KiemTraSoLuong(bool dieuKien, string thongBao)
        {
            if (!dieuKien)
            {
                throw new InvalidOperationException("DataSeeder: " + thongBao);
            }
        }
    }
}
