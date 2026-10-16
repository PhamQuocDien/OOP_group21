namespace CSharp.Entities
{
    public class NhomCongViec
    {
        public string MaNhomCV { get; set; } = string.Empty;
        public string TenNhom { get; set; } = string.Empty;
        public string Noidung { get; set; } = string.Empty;

        public NhomCongViec()
        {
        }

        public NhomCongViec(string maNhomCV, string tenNhom, string noidung)
        {
            MaNhomCV = maNhomCV;
            TenNhom = tenNhom;
            Noidung = noidung;
        }
    }
}