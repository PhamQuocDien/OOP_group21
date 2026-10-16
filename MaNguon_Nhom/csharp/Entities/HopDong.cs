using System;
using CSharp.Enums;
namespace CSharp.Entities
{
    public class HopDong
    {
        public string SoHD { get; set; } = string.Empty;
        public DateOnly Ngaylap { get; set; }
        public DateOnly NgayHenTra { get; set; }
        public TrangThaiHopDong TinhtrangHD { get; set; }
        public double Tongtien { get; set; }
        public string MaKH { get; set; } = string.Empty;
        public string MaNV { get; set; } = string.Empty;
        public string MaThietBi { get; set; } = string.Empty;
        public HopDong()
        {
        }
        public HopDong(string soHD, DateOnly ngaylap, DateOnly ngayHenTra, TrangThaiHopDong tinhtrangHD, double tongtien, string maKH, string maNV, string maThietBi)
        {
            SoHD = soHD;
            Ngaylap = ngaylap;
            NgayHenTra = ngayHenTra;
            TinhtrangHD = tinhtrangHD;
            Tongtien = tongtien;
            MaKH = maKH;
            MaNV = maNV;
            MaThietBi = maThietBi;
        }
    }
}