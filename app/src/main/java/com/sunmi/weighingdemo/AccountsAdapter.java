package com.sunmi.weighingdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.MyHolder> {

    private List<AccountsBean> list;
    private Context context;

    public AccountsAdapter(List<AccountsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override

    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_accounts, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());
        holder.tvPrice.setText(list.get(position).getPrice() + "€/kg");
        if (list.get(position).isWeigh()) {
            holder.tvWeigh.setText(list.get(position).getWeigh() + "kg");
        } else {
            holder.tvWeigh.setText(list.get(position).getWeigh() + "pcs");
        }
        holder.tvTotal.setText(list.get(position).getTotal() + "€");

        holder.ivDelete.setOnClickListener(view -> listener.delete(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setData(List<AccountsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPrice;
        private TextView tvWeigh;
        private TextView tvTotal;
        private ImageView ivDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvWeigh = itemView.findViewById(R.id.tv_weight);
            tvTotal = itemView.findViewById(R.id.tv_total);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

    private OnClickListener listener;

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    interface OnClickListener {
        void delete(int position);
    }
}
