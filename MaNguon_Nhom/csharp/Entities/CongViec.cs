namespace CSharp.Entities
{
    public class CongViec
    {
        public string MaCV { get; set; } = string.Empty;
        public double Dongia { get; set; }
        public string TenCV { get; set; } = string.Empty;
        public string NoidungCV { get; set; } = string.Empty;
        public string MaNhomCV { get; set; } = string.Empty;
        public CongViec()
        {
        }
        public CongViec(string maCV, double dongia, string tenCV, string noidungCV, string maNhomCV)
        {
            MaCV = maCV;
            Dongia = dongia;
            TenCV = tenCV;
            NoidungCV = noidungCV;
            MaNhomCV = maNhomCV;
        }
    }
}