package model.entity;
import model.enums.CapDo;
public class KhaNangTho {
    private String maTho;
    private String maCV;
    private CapDo capdo;
    public KhaNangTho() {
    }
    public KhaNangTho(String maTho, String maCV, CapDo capdo) {
        this.maTho = maTho;
        this.maCV = maCV;
        this.capdo = capdo;
    }
    public String getMaTho() {
        return maTho;
    }
    public void setMaTho(String maTho) {
        this.maTho = maTho;
    }
    public String getMaCV() {
        return maCV;
    }
    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }
    public CapDo getCapdo() {
        return capdo;
    }
    public void setCapdo(CapDo capdo) {
        this.capdo = capdo;
    }
}