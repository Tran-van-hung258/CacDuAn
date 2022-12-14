package fpt.edu.duannnhom.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fpt.edu.duannnhom.Adapter.HoaDonAdapter;
import fpt.edu.duannnhom.Adapter.NhanVienAdapter;
import fpt.edu.duannnhom.Adapter.NhanVienSpinerAdapter;
import fpt.edu.duannnhom.Adapter.SanPhamSpinerAdapter;
import fpt.edu.duannnhom.R;
import fpt.edu.duannnhom.dao.HoaDonDao;
import fpt.edu.duannnhom.dao.NhanVienDAO;
import fpt.edu.duannnhom.dao.SanPhamDAO;
import fpt.edu.duannnhom.model.HoaDon;
import fpt.edu.duannnhom.model.NhanVien;
import fpt.edu.duannnhom.model.SanPham;


public class HoaDonFragment extends Fragment {
    ListView lvHoaDon;
    ArrayList<HoaDon> list;
    static HoaDonDao dao;
    HoaDonAdapter adapter;
    HoaDon item;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaHD,edDiachi;
    Spinner spNV, spSanPham;
    TextView tvNgay,tvTienThue,tvTongHangBan,tvNgaySinh;
    Button btnSave,btnCancel,btnMuaHang;

    NhanVienSpinerAdapter nhanViepSpinnerAdapter;
    ArrayList<NhanVien> listNhanVien;
    NhanVienDAO nhanVienDAO;
    NhanVien nhanVien;
    int maNhanVien;

    SanPhamSpinerAdapter sanPhamSpinnerAdapter;
    ArrayList<SanPham> listSanPham;
    SanPhamDAO sanPhamDAO;
    SanPham sanPham;
    int maSanPham, tienThue;
    String ngaySinh;
    int positionNV,positionSanPham;
    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_hoa_don, container, false);
        tvTongHangBan = v.findViewById(R.id.tvTongHangBan);
        lvHoaDon = v.findViewById(R.id.lvHoaDon);
        dao = new HoaDonDao(getActivity());
       fab = v.findViewById(R.id.fab);
        tvTienThue = v.findViewById(R.id.tvTienThue);
       tvNgaySinh = v.findViewById(R.id.tvNguoiGiaoHang);
        fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openDialog(getActivity(),0);
    }
});
        lvHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(), 1);//update
                return false;
            }
        });
capNhapLv();

return  v;
    }
    void capNhapLv(){
        tongHangBan();
        list = (ArrayList<HoaDon>) dao.getAll();
        adapter= new HoaDonAdapter(getActivity(),this,list);
        lvHoaDon.setAdapter(adapter);
    }
    protected void  openDialog(final Context context, final  int type) {
dialog = new Dialog(context);
dialog.setContentView(R.layout.hoa_don_dialog);

        edMaHD = dialog.findViewById(R.id.edMaHD);
        spNV = dialog.findViewById(R.id.spMaNV);
        spSanPham = dialog.findViewById(R.id.spMaSanPham);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvNgaySinh = dialog.findViewById(R.id.tvNguoiGiaoHang);
        tvTienThue = dialog.findViewById(R.id.tvTienThue);
        btnCancel = dialog.findViewById(R.id.btnCancelHD);
        btnSave = dialog.findViewById(R.id.btnSaveHD);

        tvNgay.setText("Ng??y b??n: "+sdf.format(new Date()));
        edMaHD.setEnabled(false);
        nhanVienDAO = new NhanVienDAO(context);
        listNhanVien = new ArrayList<NhanVien>();
listNhanVien = (ArrayList<NhanVien>) nhanVienDAO.getAll();
nhanViepSpinnerAdapter = new NhanVienSpinerAdapter(context,listNhanVien);
spNV.setAdapter(nhanViepSpinnerAdapter);
spNV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
maNhanVien = listNhanVien.get(position).getMaNV();
ngaySinh = listNhanVien.get(position).getNgaySinh();
        tvNgaySinh.setText("Ng?????i giao h??ng: " + ngaySinh);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
sanPhamDAO = new SanPhamDAO(context);
listSanPham = new ArrayList<SanPham>();
listSanPham = (ArrayList<SanPham>) sanPhamDAO.getAll();
        sanPhamSpinnerAdapter = new SanPhamSpinerAdapter(context,listSanPham);
        spSanPham.setAdapter(sanPhamSpinnerAdapter);
spSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        maSanPham =listSanPham.get(position).getMaSanPham();
        tienThue = Integer.parseInt(listSanPham.get(position).getGia());
        tvTienThue.setText("Gi?? s???n ph???m: " + tienThue + " VND");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
//sua
        if(type !=0){
            edMaHD.setText(String.valueOf(item.getMaHoaDon()));

            for(int i =0; i < listNhanVien.size();i++)
                if(item.getMaTV() == (listNhanVien.get(i).getMaNV())){
                    positionNV = i;
                }
            spNV.setSelection(positionNV);
            for (int i =0; i<listSanPham.size();i++)
                if(item.getMaSanPham()==(listSanPham.get(i).getMaSanPham())){

                    positionNV = i;
                    tvNgaySinh.setText("Ng?????i giao h??ng"+item.getNgaySinnh());
                }
            spSanPham.setSelection(positionSanPham);
            tvNgay.setText("Ng??y B??n: "+sdf.format(item.getNgay()));
            tvTienThue.setText("Ti???n thu??: "+item.getTienThue());


        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new HoaDon();
                item.setMaSanPham(maSanPham);
                item.setMaTV(maNhanVien);
                item.setNgay(new Date());
                item.setTienThue(tienThue);
                item.setNgaySinh(ngaySinh);
                if(type == 0){
                    if(dao.insert(item) > 0){
                        Toast.makeText(context,"th??m th??nh c??ng",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,"th??m kh??n th??nh c??ng",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    item.setMaHoaDon(Integer.parseInt(edMaHD.getText().toString()));
                    if(dao.update(item)>0){
                        Toast.makeText(context,"s???a th??nh c??ng",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context,"S???a kh??n th??nh c??ng",Toast.LENGTH_SHORT).show();
                    }
                }
                capNhapLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("delete");
        builder.setMessage("B???n c?? mu???n x??a kh??ng?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "C??",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dao.delete(Id);
                        capNhapLv();
                        dialog.cancel();
                    }
                }
        );

        builder.setNegativeButton(
                "kh??ng",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }
        );

        AlertDialog alert = builder.create();
        builder.show();
    }
    void tongHangBan(){
        dao = new HoaDonDao(getActivity());
        tvTongHangBan.setText("T???ng h??a ????n: " + dao.getTongHoaDon() + " VND");
    }
}