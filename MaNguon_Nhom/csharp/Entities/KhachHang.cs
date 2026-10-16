namespace CSharp.Entities
{
    public class KhachHang
    {
        public string MaKH { get; set; } = string.Empty;
        public string Hoten { get; set; } = string.Empty;
        public string Phai { get; set; } = string.Empty;
        public DateOnly Ngaysinh { get; set; }
        public string Diachi { get; set; } = string.Empty;
        public string Sdt { get; set; } = string.Empty;
        public KhachHang()
        {
        }
        public KhachHang(string maKH, string hoten, string phai, DateOnly ngaysinh, string diachi, string sdt)
        {
            MaKH = maKH;
            Hoten = hoten;
            Phai = phai;
            Ngaysinh = ngaysinh;
            Diachi = diachi;
            Sdt = sdt;
        }
    }
}