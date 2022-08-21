package fpt.edu.duannnhom.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.duannnhom.database.DbHelper;
import fpt.edu.duannnhom.model.NhanVien;

public class NhanVienDAO {
    private SQLiteDatabase db;
    private Context context;
    public NhanVienDAO(Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(NhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("tenNV", obj.getTenNV());
        values.put("ngaySinh", obj.getNgaySinh());
        values.put("sdt", obj.getSdt());
        values.put("luong", obj.getLuong());
        return db.insert("NhanVien", null, values);
    }

    public int update(NhanVien obj) {
        ContentValues values = new ContentValues();
        values.put("tenNV", obj.getTenNV());
        values.put("ngaySinh", obj.getNgaySinh());
        values.put("sdt", obj.getSdt());
        values.put("luong", obj.getLuong());
        return db.update("NhanVien", values, "maNV=?", new String[]{String.valueOf(obj.getMaNV())});
    }

    public int delete(String id) {
        return db.delete("NhanVien", "maNV=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<NhanVien> getData(String sql, String... selectionArgs) {
        List<NhanVien> list = new ArrayList<NhanVien>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            NhanVien obj = new NhanVien();
            obj.setMaNV(Integer.parseInt(c.getString(c.getColumnIndex("maNV"))));
            obj.setTenNV(c.getString(c.getColumnIndex("tenNV")));
            obj.setNgaySinh(c.getString(c.getColumnIndex("ngaySinh")));
            obj.setSdt((c.getString(c.getColumnIndex("sdt"))));
            obj.setLuong((c.getString(c.getColumnIndex("luong"))));
            list.add(obj);
        }
        return list;
    }

    //get tat ca data
    public List<NhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien";
        return getData(sql);
    }

    ///theo id
    public NhanVien getID(String id) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = getData(sql, id);
        return list.get(0);
    }

    public int getSoTienLuong() {
        String s = "SELECT SUM(luong) FROM NhanVien ";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(s, null);

        while (c.moveToNext()) {
            try{
                list.add(Integer.parseInt(c.getString(0)));
            }catch (Exception e){
                list.add(0);
            }
        }

        return list.get(0);
    }

    }

