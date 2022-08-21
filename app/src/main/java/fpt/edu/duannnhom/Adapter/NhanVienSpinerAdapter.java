package fpt.edu.duannnhom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import fpt.edu.duannnhom.R;
import fpt.edu.duannnhom.model.NhanVien;

public class NhanVienSpinerAdapter extends ArrayAdapter<NhanVien> {
    private Context context;
    private ArrayList<NhanVien> lists;
    TextView tvMaNV, tvTenNV;

    public NhanVienSpinerAdapter(@NonNull Context context, ArrayList<NhanVien> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if( v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.thanh_vien_item_spiner,null);
        }
        final NhanVien item = lists.get(position);
        if(item != null){
            tvMaNV = v.findViewById(R.id.tvMaTVSp);
            tvMaNV.setText(item.getMaNV() + ". ");

            tvTenNV = v.findViewById(R.id.tvTenTVSp);
            tvTenNV.setText(item.getTenNV());
        }

        return  v;


    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View  v = convertView;
        if( v == null){
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.thanh_vien_item_spiner,null);
        }
        final NhanVien item = lists.get(position);
        if(item != null) {
            tvMaNV = v.findViewById(R.id.tvMaTVSp);
            tvMaNV.setText(item.getMaNV() + ". ");

            tvTenNV = v.findViewById(R.id.tvTenTVSp);
            tvTenNV.setText(item.getTenNV());
        }
        return  v;
    }
}
