package model.entity;
import java.time.LocalDate;
import model.enums.PhuongThucThanhToan;
public class PhieuThu {
    private String maPhieuThu;
    private double soTienTT;
    private LocalDate ngayTT;
    private PhuongThucThanhToan phuongThucTT;
    private String soHD;
    private String maNV;
    public PhieuThu() {
    }
    public PhieuThu(String maPhieuThu, double soTienTT, LocalDate ngayTT, PhuongThucThanhToan phuongThucTT, String soHD, String maNV) {
        this.maPhieuThu = maPhieuThu;
        this.soTienTT = soTienTT;
        this.ngayTT = ngayTT;
        this.phuongThucTT = phuongThucTT;
        this.soHD = soHD;
        this.maNV = maNV;
    }
    public String getMaPhieuThu() {
        return maPhieuThu;
    }
    public void setMaPhieuThu(String maPhieuThu) {
        this.maPhieuThu = maPhieuThu;
    }
    public double getSoTienTT() {
        return soTienTT;
    }
    public void setSoTienTT(double soTienTT) {
        this.soTienTT = soTienTT;
    }
    public LocalDate getNgayTT() {
        return ngayTT;
    }
    public void setNgayTT(LocalDate ngayTT) {
        this.ngayTT = ngayTT;
    }
    public PhuongThucThanhToan getPhuongThucTT() {
        return phuongThucTT;
    }
    public void setPhuongThucTT(PhuongThucThanhToan phuongThucTT) {
        this.phuongThucTT = phuongThucTT;
    }
    public String getSoHD() {
        return soHD;
    }
    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }
    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}