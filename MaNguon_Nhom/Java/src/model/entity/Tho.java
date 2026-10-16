
package model.entity;

import java.time.LocalDate;

public class Tho {
    private String maTho;
    private String tenTho;
    private String phai;
    private LocalDate ngaysinh;
    private String sdt;
    private String maNhomTho;

    public Tho() {
    }

    public Tho(String maTho, String tenTho, String phai,
               LocalDate ngaysinh, String sdt, String maNhomTho) {
        this.maTho = maTho;
        this.tenTho = tenTho;
        this.phai = phai;
        this.ngaysinh = ngaysinh;
        this.sdt = sdt;
        this.maNhomTho = maNhomTho;
    }

    public String getMaTho() {
        return maTho;
    }

    public void setMaTho(String maTho) {
        this.maTho = maTho;
    }

    public String getTenTho() {
        return tenTho;
    }

    public void setTenTho(String tenTho) {
        this.tenTho = tenTho;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getMaNhomTho() {
        return maNhomTho;
    }

    public void setMaNhomTho(String maNhomTho) {
        this.maNhomTho = maNhomTho;
    }
}
