using CSharp.Enums;
namespace CSharp.Entities
{
    public class ChiTietHopDong
    {
        public string SoHD { get; set; } = string.Empty;
        public string MaCV { get; set; } = string.Empty;
        public string MaTho { get; set; } = string.Empty;
        public TrangThaiCongViec TrangThaiCV { get; set; }
        public ChiTietHopDong()
        {
        }
        public ChiTietHopDong(string soHD, string maCV, string maTho, TrangThaiCongViec trangThaiCV)
        {
            SoHD = soHD;
            MaCV = maCV;
            MaTho = maTho;
            TrangThaiCV = trangThaiCV;
        }
    }
}