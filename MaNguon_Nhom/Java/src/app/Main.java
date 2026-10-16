package app;

import dao.InMemoryDAO;
import model.entity.NhomTho;

public class Main {
    public static void main(String[] args) {
        InMemoryDAO<NhomTho> dao = new InMemoryDAO<>(NhomTho::getMaNhomTho);

        NhomTho nhom = new NhomTho("NT01", "Nhom sua chua", "T01");
        dao.add(nhom);

        System.out.println("So nhom tho: " + dao.getAll().size());
        System.out.println("Ten nhom: " + dao.findById("NT01").getTenNhom());
        System.out.println("Xoa thanh cong: " + dao.removeById("NT01"));
        System.out.println("So luong sau xoa: " + dao.getAll().size());
    }
}