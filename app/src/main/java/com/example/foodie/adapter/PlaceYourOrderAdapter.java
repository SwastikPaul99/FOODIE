package com.example.foodie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.foodie.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.model.Menus;

import java.util.List;

public class PlaceYourOrderAdapter extends RecyclerView.Adapter<PlaceYourOrderAdapter.MyViewHolder> {

    private List<Menus> menuList;

    public PlaceYourOrderAdapter(List<Menus> menuList) {
        this.menuList = menuList;
    }

    public void updateData(List<Menus> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public PlaceYourOrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_your_order_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceYourOrderAdapter.MyViewHolder holder, int position) {
       // if(menuList.get(position).getTotalInCart() !=0)
       // {
            holder.menuName.setText(menuList.get(position).getName());
            holder.menuPrice.setText("Price: $"+String.format("%.2f", menuList.get(position).getPrice()*menuList.get(position).getTotalInCart()));
            holder.menuQty.setText("Qty: " + menuList.get(position).getTotalInCart());
            Glide.with(holder.thumbImage)
                    .load(menuList.get(position).getUrl())
                    .into(holder.thumbImage);
       // }
        /*else
        {
            menuList.remove(holder.getAdapterPosition());
        }*/
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView  menuPrice;
        TextView  menuQty;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            menuQty = view.findViewById(R.id.menuQty);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }
}
