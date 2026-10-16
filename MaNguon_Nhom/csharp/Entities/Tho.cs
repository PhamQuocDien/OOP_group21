
using System;

namespace CSharp.Entities
{
    public class Tho
    {
        public string MaTho { get; set; } = string.Empty;
        public string TenTho { get; set; } = string.Empty;
        public string Phai { get; set; } = string.Empty;
        public DateOnly Ngaysinh { get; set; }
        public string Sdt { get; set; } = string.Empty;
        public string MaNhomTho { get; set; } = string.Empty;

        public Tho()
        {
        }

        public Tho(
            string maTho,
            string tenTho,
            string phai,
            DateOnly ngaysinh,
            string sdt,
            string maNhomTho)
        {
            MaTho = maTho;
            TenTho = tenTho;
            Phai = phai;
            Ngaysinh = ngaysinh;
            Sdt = sdt;
            MaNhomTho = maNhomTho;
        }
    }
}
