
package model.entity;

public class NhomTho {
    private String maNhomTho;
    private String tenNhom;
    private String maTruongNhom;

    public NhomTho() {
    }

    public NhomTho(String maNhomTho, String tenNhom, String maTruongNhom) {
        this.maNhomTho = maNhomTho;
        this.tenNhom = tenNhom;
        this.maTruongNhom = maTruongNhom;
    }

    public String getMaNhomTho() {
        return maNhomTho;
    }

    public void setMaNhomTho(String maNhomTho) {
        this.maNhomTho = maNhomTho;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getMaTruongNhom() {
        return maTruongNhom;
    }

    public void setMaTruongNhom(String maTruongNhom) {
        this.maTruongNhom = maTruongNhom;
    }
}
