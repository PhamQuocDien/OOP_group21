public class NhomTho
{    public string MaNhomTho { get; set; } = string.Empty;
    public string TenNhom { get; set; } = string.Empty;
    public string MaTruongNhom { get; set; } = string.Empty;

    public NhomTho()
    {
    }

    public NhomTho(
        string maNhomTho,
        string tenNhom,
        string maTruongNhom)
    {
        MaNhomTho = maNhomTho;
        TenNhom = tenNhom;
        MaTruongNhom = maTruongNhom;
    }
}
