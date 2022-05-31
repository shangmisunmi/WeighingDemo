package com.sunmi.weighingdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private List<FruitBean> data;
    private Context mContext;
    private int clickPosition;
    private boolean isClick = false;


    public MyAdapter(List<FruitBean> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(data.get(position).getName());
        holder.price.setText(mContext.getString(R.string.price, data.get(position).getPrice()));
        Glide.with(mContext)
                .load(data.get(position).getIcon())
                .into(holder.icon);

        if (clickPosition == position && isClick){
            holder.itemView.setBackgroundColor(Color.parseColor("#E8E8E9"));
        }else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setPosition(int position,boolean isClick){
        this.clickPosition = position;
        this.isClick = isClick;
        notifyDataSetChanged();
    }

    public void setData(List<FruitBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        private TextView price;
        private ImageView icon;
        private TextView name;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.tv_price);
            icon = itemView.findViewById(R.id.iv_icon);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    interface OnItemClickListener {
        void onClick(int position);
    }
}
