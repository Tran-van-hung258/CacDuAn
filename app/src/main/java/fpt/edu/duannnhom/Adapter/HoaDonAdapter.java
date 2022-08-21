package fpt.edu.duannnhom.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpt.edu.duannnhom.R;
import fpt.edu.duannnhom.dao.NhanVienDAO;
import fpt.edu.duannnhom.dao.SanPhamDAO;
import fpt.edu.duannnhom.fragment.HoaDonFragment;
import fpt.edu.duannnhom.model.HoaDon;
import fpt.edu.duannnhom.model.NhanVien;
import fpt.edu.duannnhom.model.SanPham;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    private Context context;
    HoaDonFragment fragment;
    private ArrayList<HoaDon> lists;
    TextView tvMaHoaDon,tvTenTV,tvTenSanPham,tvTienThue,tvNgay,tvNgaySinh;
    ImageView imageDel;
    SanPhamDAO sanphamDAO;
    NhanVienDAO nhanVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    LinearLayout lvhd;

    public HoaDonAdapter(@NonNull Context context, HoaDonFragment fragment, ArrayList<HoaDon> lists) {
        super(context, 0,lists);
        this.context= context;
        this.lists=lists;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

                if(v==null){
                    LayoutInflater inflater = (LayoutInflater)
                            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = inflater.inflate(R.layout.hoa_don_item,null);

                }
        final HoaDon item =lists.get(position);

        if(item != null){
            tvMaHoaDon = v.findViewById(R.id.tvMaHoaDon);
            tvMaHoaDon.setText("Mã hóa đơn: "+item.getMaHoaDon());
            sanphamDAO = new SanPhamDAO(context);
            SanPham sanPham = sanphamDAO.getID(String.valueOf(item.getMaSanPham()));
            tvTienThue = v.findViewById(R.id.tvTienThueHD);
            tvTienThue.setText("giá Sản phẩm: "+item.getTienThue());
            tvTenSanPham = v.findViewById(R.id.tvTenSanPhamHD);
            tvTenSanPham.setText("Tên sản phẩm: "+sanPham.getTenSanPham());
            Log.e("//==", "getView: "+sanPham.getTenSanPham());

            nhanVienDAO = new NhanVienDAO(context);
            NhanVien nhanVien = nhanVienDAO.getID(String.valueOf(item.getMaTV()));
            tvTenTV = v.findViewById(R.id.tvTenThanhVienHd);
            tvTenTV.setText("Chi nhánh: "+nhanVien.getTenNV());

            tvNgay = v.findViewById(R.id.tvNgayHD);
            tvNgay.setText("Ngày Bán: "+sdf.format(item.getNgay())) ;
            Log.e("//==", "getView: "+item.getNgay());

tvNgaySinh = v.findViewById(R.id.tvTenNguoiGiaoHang);
tvNgaySinh.setText("Nhân viên: "+item.getNgaySinnh());
        }
        imageDel=v.findViewById(R.id.imgDeletelHD);
        imageDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaHoaDon()));
            }
});
        return v;
    }
}
