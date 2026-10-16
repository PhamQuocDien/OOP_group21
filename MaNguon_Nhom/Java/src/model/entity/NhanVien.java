package model.entity;
import java.time.LocalDate;
import model.enums.ChucVu;
public class NhanVien {
    private String maNV;
    private String hoten;
    private String phai;
    private LocalDate ngaysinh;
    private String diachi;
    private String sdt;
    private ChucVu chucVu;
    public NhanVien() {
    }
    public NhanVien(String maNV, String hoten, String phai, LocalDate ngaysinh, String diachi, String sdt, ChucVu chucVu) {
        this.maNV = maNV;
        this.hoten = hoten;
        this.phai = phai;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.sdt = sdt;
        this.chucVu = chucVu;
    }
    public String getMaNV() {
        return maNV;
    }
    public void setMaNV(String maNV) {
        this.maNV = maNV;
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
    public ChucVu getChucVu() {
        return chucVu;
    }
    public void setChucVu(ChucVu chucVu) {
        this.chucVu = chucVu;
    }
}