package model.entity;
public class ThietBi {
    private String maThietBi;
    private String loaiTB;
    private String hangSX;
    private String model;
    private String tinhtrang;
    private String maKH;
    public ThietBi() {
    }
    public ThietBi(String maThietBi, String loaiTB, String hangSX, String model, String tinhtrang, String maKH) {
        this.maThietBi = maThietBi;
        this.loaiTB = loaiTB;
        this.hangSX = hangSX;
        this.model = model;
        this.tinhtrang = tinhtrang;
        this.maKH = maKH;
    }
    public String getMaThietBi() {
        return maThietBi;
    }
    public void setMaThietBi(String maThietBi) {
        this.maThietBi = maThietBi;
    }
    public String getLoaiTB() {
        return loaiTB;
    }
    public void setLoaiTB(String loaiTB) {
        this.loaiTB = loaiTB;
    }
    public String getHangSX() {
        return hangSX;
    }
    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getTinhtrang() {
        return tinhtrang;
    }
    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }
    public String getMaKH() {
        return maKH;
    }
    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}