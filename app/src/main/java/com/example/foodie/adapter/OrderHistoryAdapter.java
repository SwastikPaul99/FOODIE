package com.example.foodie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.model.Order;
import com.example.foodie.model.ResturantModel;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder> {

    private Context context;
    private OrderHistoryListClickListener clickListener;
    private List<Order> OrderList;

    public OrderHistoryAdapter(Context context,List<Order> OrderList,OrderHistoryListClickListener clickListener)
    {
        this.context=context;
        this.OrderList=OrderList;
        this.clickListener=clickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order order=OrderList.get(position);
        holder.resname.setText("Restaurant: "+order.getResturantName());
        holder.resadd.setText("Address: "+order.getResturantAddress());
        holder.odate.setText("Date: "+order.getOrderDate());
        holder.ID.setText("OrderID: "+order.getID());
        holder.type.setText("Delivery Mode: "+order.getOrderType());
        holder.Billingname.setText("Billing Name: "+order.getName());
        holder.orderitems.setText("Ordered Items:\n"+order.getOrderItems());
        Glide.with(holder.image).load(order.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView resname,resadd,odate,ID,type,Billingname,orderitems;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resname=itemView.findViewById(R.id.resName);
            resadd=itemView.findViewById(R.id.resAddress);
            odate=itemView.findViewById(R.id.order_date);
            ID=itemView.findViewById(R.id.order_id);
            type=itemView.findViewById(R.id.order_type);
            image=itemView.findViewById(R.id.orderImage);
            Billingname=itemView.findViewById(R.id.Billing_Name);
            orderitems=itemView.findViewById(R.id.Order_Items);
        }
    }

    public interface OrderHistoryListClickListener {
        public void onItemClick(Order order);
    }
}
