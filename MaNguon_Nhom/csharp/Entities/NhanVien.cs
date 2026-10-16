using System;
using CSharp.Enums;
namespace CSharp.Entities
{
    public class NhanVien
    {
        public string MaNV { get; set; } = string.Empty;
        public string Hoten { get; set; } = string.Empty;
        public string Phai { get; set; } = string.Empty;
        public DateOnly Ngaysinh { get; set; }
        public string Diachi { get; set; } = string.Empty;
        public string Sdt { get; set; } = string.Empty;
        public ChucVu ChucVu { get; set; }
        public NhanVien()
        {
        }
        public NhanVien(string maNV, string hoten, string phai, DateOnly ngaysinh, string diachi, string sdt, ChucVu chucVu)
        {
            MaNV = maNV;
            Hoten = hoten;
            Phai = phai;
            Ngaysinh = ngaysinh;
            Diachi = diachi;
            Sdt = sdt;
            ChucVu = chucVu;
        }
    }
}