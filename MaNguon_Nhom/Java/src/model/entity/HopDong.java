package model.entity;
import java.time.LocalDate;
import model.enums.TrangThaiHopDong;
public class HopDong {
    private String soHD;
    private LocalDate ngaylap;
    private LocalDate ngayHenTra;
    private TrangThaiHopDong tinhtrangHD;
    private double tongtien;
    private String maKH;
    private String maNV;
    private String maThietBi;
    public HopDong() {
    }
    public HopDong(String soHD, LocalDate ngaylap, LocalDate ngayHenTra, TrangThaiHopDong tinhtrangHD, double tongtien, String maKH, String maNV, String maThietBi) {
        this.soHD = soHD;
        this.ngaylap = ngaylap;
        this.ngayHenTra = ngayHenTra;
        this.tinhtrangHD = tinhtrangHD;
        this.tongtien = tongtien;
        this.maKH = maKH;
        this.maNV = maNV;
        this.maThietBi = maThietBi;
    }
    public String getSoHD() {
        return soHD;
    }
    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }
    public LocalDate getNgaylap() {
        return ngaylap;
    }
    public void setNgaylap(LocalDate ngaylap) {
        this.ngaylap = ngaylap;
    }
    public LocalDate getNgayHenTra() {
        return ngayHenTra;
    }
    public void setNgayHenTra(LocalDate ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }
    public TrangThaiHopDong getTinhtrangHD() {
        return tinhtrangHD;
    }
    public void setTinhtrangHD(TrangThaiHopDong tinhtrangHD) {
        this.tinhtrangHD = tinhtrangHD;
    }
    public double getTongtien() {
        return tongtien;
    }
    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }
    public String getMaKH() {
        return maKH;
    }
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
    public String getMaThietBi() {
        return maThietBi;
    }
    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }
}