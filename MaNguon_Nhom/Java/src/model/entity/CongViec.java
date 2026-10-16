package model.entity;

public class CongViec {
    private String maCV;
    private double dongia;
    private String tenCV;
    private String noidungCV;
    private String maNhomCV;
    public CongViec() {
    }
    public CongViec(String maCV, double dongia, String tenCV, String noidungCV, String maNhomCV) {
        this.maCV = maCV;
        this.dongia = dongia;
        this.tenCV = tenCV;
        this.noidungCV = noidungCV;
        this.maNhomCV = maNhomCV;
    }
    public String getMaCV() {
        return maCV;
    }
    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }
    public double getDongia() {
        return dongia;
    }
    public void setDongia(double dongia) {
        this.dongia = dongia;
    }
    public String getTenCV() {
        return tenCV;
    }
    public void setTenCV(String tenCV) {
        this.tenCV = tenCV;
    }
    public String getNoidungCV() {
        return noidungCV;
    }
    public void setNoidungCV(String noidungCV) {
        this.noidungCV = noidungCV;
    }
    public String getMaNhomCV() {
        return maNhomCV;
    }
    public void setMaNhomCV(String maNhomCV) {
        this.maNhomCV = maNhomCV;
    }
}