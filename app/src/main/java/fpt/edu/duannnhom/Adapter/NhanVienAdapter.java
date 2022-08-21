package fpt.edu.duannnhom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.duannnhom.R;
import fpt.edu.duannnhom.dao.LoaiSanPhamDAO;
import fpt.edu.duannnhom.dao.NhanVienDAO;
import fpt.edu.duannnhom.fragment.NhanVienFragment;
import fpt.edu.duannnhom.model.LoaiSanPham;
import fpt.edu.duannnhom.model.NhanVien;
import fpt.edu.duannnhom.model.SanPham;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    NhanVienFragment fragment;
    private ArrayList<NhanVien> lists;
    TextView tvMaNV, tvTenNV, tvNgaySinh, tvSdt, tvLuong;
    ImageView imgDelNV;

    public NhanVienAdapter(@NonNull Context context, NhanVienFragment fragment, ArrayList<NhanVien> lists) {
        super(context, 0,lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.nhan_vien_item,null);
        }
        final NhanVien item = lists.get(position);
        if (item != null) {


            tvMaNV = v.findViewById(R.id.tvMaNV);
            tvMaNV.setText("Mã :  "+item.getMaNV());
            tvTenNV = v.findViewById(R.id.tvTenNV);
            tvTenNV.setText("Chi nhánh:  "+item.getTenNV());
            tvNgaySinh = v.findViewById(R.id.tvNgaySinh);
            tvNgaySinh.setText("Nhân viên quản lý:  "+item.getNgaySinh());
            tvSdt = v.findViewById(R.id.tvSdt);
            tvSdt.setText("SĐT:  "+item.getSdt());
            tvLuong = v.findViewById(R.id.tvLuong);
            tvLuong.setText("Lương:  "+item.getLuong());

            imgDelNV = v.findViewById(R.id.imgDelNV);
        }
        imgDelNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xóa
                fragment.xoa(String.valueOf(item.getMaNV()));
            }
        });
        return v;



    }
}
