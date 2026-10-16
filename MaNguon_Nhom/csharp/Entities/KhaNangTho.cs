using CSharp.Enums;
namespace CSharp.Entities
{
    public class KhaNangTho
    {
        public string MaTho { get; set; } = string.Empty;
        public string MaCV { get; set; } = string.Empty;
        public CapDo Capdo { get; set; }
        public KhaNangTho()
        {
        }
        public KhaNangTho(string maTho, string maCV, CapDo capdo)
        {
            MaTho = maTho;
            MaCV = maCV;
            Capdo = capdo;
        }
    }
}