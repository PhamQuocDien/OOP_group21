namespace CSharp.Entities
{
    public class ThietBi
    {
        public string MaThietBi { get; set; } = string.Empty;
        public string LoaiTB { get; set; } = string.Empty;
        public string HangSX { get; set; } = string.Empty;
        public string Model { get; set; } = string.Empty;
        public string Tinhtrang { get; set; } = string.Empty;
        public string MaKH { get; set; } = string.Empty;
        public ThietBi()
        {
        }
        public ThietBi(string maThietBi, string loaiTB, string hangSX, string model, string tinhtrang, string maKH)
        {
            MaThietBi = maThietBi;
            LoaiTB = loaiTB;
            HangSX = hangSX;
            Model = model;
            Tinhtrang = tinhtrang;
            MaKH = maKH;
        }
    }
}