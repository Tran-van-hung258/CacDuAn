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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpt.edu.duannnhom.Adapter.LoaiSanPhamAdapter;
import fpt.edu.duannnhom.Adapter.NhanVienAdapter;
import fpt.edu.duannnhom.R;
import fpt.edu.duannnhom.dao.NhanVienDAO;
import fpt.edu.duannnhom.database.DbHelper;
import fpt.edu.duannnhom.model.LoaiSanPham;
import fpt.edu.duannnhom.model.NhanVien;


public class NhanVienFragment extends Fragment {
TextView tvTongLuong;

    ListView lvNV;
    ArrayList<NhanVien>list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaNV, edTenNV, edNgaySinh, edSdt, edluong;
    Button btnSave, btnCancel;
    static NhanVienDAO dao;
    NhanVienAdapter adapter;
    NhanVien item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nhan_vien, container, false);



        tvTongLuong = v.findViewById(R.id.tvTongLuong);
        lvNV = v.findViewById(R.id.lvNV);
        fab = v.findViewById(R.id.fab);

        capNhatLV();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });
        lvNV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);//1 la se update

                return false;
            }
        });
        return  v;
    }
    public void  openDialog(final Context context, final  int type){
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.nhan_vien_dialog);
            edMaNV = dialog.findViewById(R.id.edMaNV);
            edTenNV = dialog.findViewById(R.id.edTenNV);
            edNgaySinh = dialog.findViewById(R.id.edngaySinh);
            edSdt = dialog.findViewById(R.id.edSdt);

            edluong = dialog.findViewById(R.id.edLuong);
            btnCancel = dialog.findViewById(R.id.btnCancelNV);
            btnSave = dialog.findViewById(R.id.btnSaveNV);
            //kiem tra
            edMaNV.setEnabled(false);
            if (type != 0) {
                edMaNV.setText(String.valueOf(item.getMaNV()));
                edTenNV.setText(item.getTenNV());
                edluong.setText(String.valueOf(item.getLuong()));

                edNgaySinh.setText(item.getNgaySinh());
                edSdt.setText(String.valueOf(item.getSdt()));
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
                item = new NhanVien();
                item.setTenNV(edTenNV.getText().toString());
                item.setLuong((edluong.getText().toString()));
                item.setSdt((edSdt.getText().toString()));
                item.setNgaySinh(edNgaySinh.getText().toString());


                if(validate() >0){

                    if(type ==0){
                        if(dao.insert(item)>0){

                            Toast.makeText(context,"thêm thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"thêm không  thành công",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        item.setMaNV(Integer.parseInt(edMaNV.getText().toString()));
                        if(dao.update(item)>0){
                            Toast.makeText(context,"Sửa thành công",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context,"Sửa không thành công",Toast.LENGTH_SHORT).show();
                        }
                    }

                    capNhatLV();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public void xoa(final String Id){
//alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dao.delete(Id);
                        //cap nhat lv
                        capNhatLV();
                        dialog.cancel();


                    }
                });
        builder.setNegativeButton(
                "không",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        builder.show();
    }
    void capNhatLV(){
        tongLuong();
        list = (ArrayList<NhanVien>)dao.getAll();
     adapter = new NhanVienAdapter(getActivity(),this,list);
        lvNV.setAdapter(adapter);
    }
    public int validate(){
        int check = 1;
        if(edTenNV.getText().length()==0||edSdt.getText().length()==0||edluong.getText().length()==0||edNgaySinh.getText().length()==0) {

            Toast.makeText(getContext(), "Bạn pải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
    void tongLuong(){
        dao = new NhanVienDAO(getActivity());
        tvTongLuong.setText("Tổng lương: " + dao.getSoTienLuong() + " VND");
    }

}