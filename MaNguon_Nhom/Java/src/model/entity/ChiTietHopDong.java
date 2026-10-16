package model.entity;
import model.enums.TrangThaiCongViec;
public class ChiTietHopDong {
    private String soHD;
    private String maCV;
    private String maTho;
    private TrangThaiCongViec trangThaiCV;
    public ChiTietHopDong() {
    }
    public ChiTietHopDong(String soHD, String maCV, String maTho, TrangThaiCongViec trangThaiCV) {
        this.soHD = soHD;
        this.maCV = maCV;
        this.maTho = maTho;
        this.trangThaiCV = trangThaiCV;
    }
    public String getSoHD() {
        return soHD;
    }
    public void setSoHD(String soHD) {
        this.soHD = soHD;
    }
    public String getMaCV() {
        return maCV;
    }
    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }
    public String getMaTho() {
        return maTho;
    }
    public void setMaTho(String maTho) {
        this.maTho = maTho;
    }
    public TrangThaiCongViec getTrangThaiCV() {
        return trangThaiCV;
    }
    public void setTrangThaiCV(TrangThaiCongViec trangThaiCV) {
        this.trangThaiCV = trangThaiCV;
    }
}