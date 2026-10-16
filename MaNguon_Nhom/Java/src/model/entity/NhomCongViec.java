package model.entity;

public class NhomCongViec {
    private String maNhomCV;
    private String tenNhom;
    private String noidung;

    public NhomCongViec() {
    }

    public NhomCongViec(String maNhomCV, String tenNhom, String noidung) {
        this.maNhomCV = maNhomCV;
        this.tenNhom = tenNhom;
        this.noidung = noidung;
    }

    public String getMaNhomCV() {
        return maNhomCV;
    }

    public void setMaNhomCV(String maNhomCV) {
        this.maNhomCV = maNhomCV;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}