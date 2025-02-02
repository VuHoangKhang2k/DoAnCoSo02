package com.rin1903.bookstoremanager.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.rin1903.bookstoremanager.MainActivity;
import com.rin1903.bookstoremanager.R;
import com.rin1903.bookstoremanager.SQLite.PHIEUNHAP;
import com.rin1903.bookstoremanager.SQLite.SACH;
import com.rin1903.bookstoremanager.fragment.Fragment_Sach;
import com.rin1903.bookstoremanager.fragment.Fragment_TaoPhieuNhap;

import java.util.ArrayList;

public class PhieuNhapAdapter extends RecyclerView.Adapter<PhieuNhapAdapter.ViewHolder> implements Filterable {
    private ArrayList<PHIEUNHAP> phieunhapArrayList;
    private ArrayList<PHIEUNHAP> phieunhapArrayList_old;
    private Context context;

    private ViewBinderHelper viewBinderHelper= new ViewBinderHelper();

    public PhieuNhapAdapter(ArrayList<PHIEUNHAP> phieunhapArrayList, Context context) {
        this.phieunhapArrayList = phieunhapArrayList;
        this.context = context;
        this.phieunhapArrayList_old= phieunhapArrayList;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.item_hienthi_khongcohinh_hdpn,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_tieude.setText("Mã Phiếu:"+phieunhapArrayList.get(position).getMAPHIEUNHAP());
        holder.tv_mota1.setText("Thành Tiền: "+phieunhapArrayList.get(position).getTHANHTIEN_PN());
        holder.tv_mota2.setText("Ngày Lập:"+phieunhapArrayList.get(position).getNGAY_PN());
        PHIEUNHAP phieunhap= phieunhapArrayList.get(position);
        String maphieunhap= phieunhapArrayList.get(position).getMAPHIEUNHAP();
        viewBinderHelper.bind(holder.swipeRevealLayout,phieunhapArrayList.get(position).getMAPHIEUNHAP());



        holder.item_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_TaoPhieuNhap fragment= new Fragment_TaoPhieuNhap();
                Bundle bundle= new Bundle();
                bundle.putString("guidulieu","chinhsua_phieunhap_"+maphieunhap);
                fragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_content, fragment).addToBackStack(context.getClass().getName())
                        .commit();
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_TaoPhieuNhap fragment= new Fragment_TaoPhieuNhap();
                Bundle bundle= new Bundle();
                bundle.putString("guidulieu","xem_phieunhap_"+maphieunhap);
                fragment.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_content, fragment).addToBackStack(context.getClass().getName())
                        .commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return phieunhapArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BootstrapLabel tv_tieude,tv_mota1,tv_mota2;
        SwipeRevealLayout swipeRevealLayout;
        CardView linearLayout;
        ImageView item_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tieude= itemView.findViewById(R.id.tv_item_khongcohinh_hienthi_hdpn);
            tv_mota1=itemView.findViewById(R.id.tv_item_mota1_khongcohinh_hienthi_hdpn);
            swipeRevealLayout = itemView.findViewById(R.id.swipelayout_item_khongcohinh_hdpn);
            linearLayout = itemView.findViewById(R.id.cardview_item_khongcohinh_hdpn);
            item_edit= itemView.findViewById(R.id.tv_edit_item_khongcohinh_hdpn);
            tv_mota2= itemView.findViewById(R.id.tv_item_mota2_khongcohinh_hienthi_hdpn);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String stringsearch= constraint.toString();
                if(stringsearch.isEmpty()){
                    phieunhapArrayList= phieunhapArrayList_old;
                }
                else {
                    ArrayList<PHIEUNHAP> list= new ArrayList<>();
                    for(PHIEUNHAP phieunhap:phieunhapArrayList_old){
                        if(phieunhap.getMAPHIEUNHAP().toLowerCase().contains(stringsearch.toLowerCase())){
                            list.add(phieunhap);
                        }
                    }
                    phieunhapArrayList= list;
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values= phieunhapArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                phieunhapArrayList = (ArrayList<PHIEUNHAP>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
