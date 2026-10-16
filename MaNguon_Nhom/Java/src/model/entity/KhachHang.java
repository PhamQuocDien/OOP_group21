package model.entity;
import java.time.LocalDate;
public class KhachHang {
    private String maKH;
    private String hoten;
    private String phai;
    private LocalDate ngaysinh;
    private String diachi;
    private String sdt;
    public KhachHang() {
    }
    public KhachHang(String maKH, String hoten, String phai, LocalDate ngaysinh, String diachi, String sdt) {
        this.maKH = maKH;
        this.hoten = hoten;
        this.phai = phai;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.sdt = sdt;
    }
    public String getMaKH() {
        return maKH;
    }
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
    public String getHoten() {
        return hoten;
    }
    public void setHoten(String hoten) {
        this.hoten = hoten;
    }
    public String getPhai() {
        return phai;
    }
    public void setPhai(String phai) {
        this.phai = phai;
    }
    public LocalDate getNgaysinh() {
        return ngaysinh;
    }
    public void setNgaysinh(LocalDate ngaysinh) {
        this.ngaysinh = ngaysinh;
    }
    public String getDiachi() {
        return diachi;
    }
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    public String getSdt() {
        return sdt;
    }
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}