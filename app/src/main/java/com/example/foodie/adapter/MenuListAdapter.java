package com.example.foodie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.model.Menus;
import com.example.foodie.model.ResturantModel;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<Menus> menusList;
    private MenuListListener menuListListener;

    public MenuListAdapter(List<Menus> menusList, MenuListListener menuListListener1) {
        this.menusList = menusList;
        this.menuListListener = menuListListener1;
    }

    public void updateData(List<Menus> menusList) {
        this.menusList = menusList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MyViewHolder holder, int position) {
        holder.menuName.setText(menusList.get(holder.getAdapterPosition()).getName());
        holder.menuPrice.setText("Price: $"+menusList.get(holder.getAdapterPosition()).getPrice());
        holder.addtocartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menus menus=menusList.get(holder.getAdapterPosition());
                menus.setTotalInCart(1);
                menuListListener.onAddtoCartClick(menus);
                holder.addmoreLayout.setVisibility(View.VISIBLE);
                holder.addtocartbutton.setVisibility(View.GONE);
                holder.tvCount.setText(menus.getTotalInCart()+" ");

            }
        });
        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Menus menus = menusList.get(holder.getAdapterPosition());
                  int total=menus.getTotalInCart();
                  total++;
                  if(total<=10)
                  {
                      menus.setTotalInCart(total);
                      menuListListener.onUpdateCartClick(menus);
                      holder.tvCount.setText(total+" ");
                  }
                  else
                  {
                      (Toast.makeText(view.getContext(), "Cannot Add more than 10 Items",Toast.LENGTH_LONG)).show();
                      return;
                  }
              }
        });

        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menus menus = menusList.get(holder.getAdapterPosition());
                int total=menus.getTotalInCart();
                total--;
                if(total>0)
                {
                    menus.setTotalInCart(total);
                    menuListListener.onUpdateCartClick(menus);
                    holder.tvCount.setText(total+" ");
                }
                else
                {
                    holder.addmoreLayout.setVisibility(View.GONE);
                    holder.addtocartbutton.setVisibility(View.VISIBLE);
                    menus.setTotalInCart(total);
                    menuListListener.onRemoveFromCartClick(menus);
                }
            }
        });


        Glide.with(holder.thumbImage)
                .load(menusList.get(holder.getAdapterPosition()).getUrl())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return menusList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  menuName;
        TextView  menuPrice;
        TextView  addtocartbutton;
        ImageView thumbImage;
        ImageView imageAddOne;
        ImageView imageMinus;
        TextView tvCount;
        LinearLayout addmoreLayout;

        public MyViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            addtocartbutton = view.findViewById(R.id.AddToCart);
            thumbImage = view.findViewById(R.id.thumbImage);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            imageMinus = view.findViewById(R.id.imageMinus);
            tvCount = view.findViewById(R.id.tvCount);
            addmoreLayout=view.findViewById(R.id.addmoreLayout);


        }
    }

    public interface MenuListListener {
        public void onAddtoCartClick(Menus menus);
        public void onUpdateCartClick(Menus menus);
        public void onRemoveFromCartClick(Menus menus);
    }
}