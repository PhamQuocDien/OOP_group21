
using CSharp.DAO;
using CSharp.Entities;

var dao = new InMemoryDAO<NhomTho>(nhom => nhom.MaNhomTho);

var nhom = new NhomTho("NT01", "Nhom sua chua", "T01");
dao.Add(nhom);

Console.WriteLine("So nhom tho: " + dao.GetAll().Count);
Console.WriteLine("Ten nhom: " + dao.FindById("NT01")?.TenNhom);
Console.WriteLine("Xoa thanh cong: " + dao.RemoveById("NT01"));
Console.WriteLine("So luong sau xoa: " + dao.GetAll().Count);
