using System;
using CSharp.Enums;
namespace CSharp.Entities
{
    public class PhieuThu
    {
        public string MaPhieuThu { get; set; } = string.Empty;
        public double SoTienTT { get; set; }
        public DateOnly NgayTT { get; set; }
        public PhuongThucThanhToan PhuongThucTT { get; set; }
        public string SoHD { get; set; } = string.Empty;
        public string MaNV { get; set; } = string.Empty;
        public PhieuThu()
        {
        }
        public PhieuThu(string maPhieuThu, double soTienTT, DateOnly ngayTT, PhuongThucThanhToan phuongThucTT, string soHD, string maNV)
        {
            MaPhieuThu = maPhieuThu;
            SoTienTT = soTienTT;
            NgayTT = ngayTT;
            PhuongThucTT = phuongThucTT;
            SoHD = soHD;
            MaNV = maNV;
        }
    }
}