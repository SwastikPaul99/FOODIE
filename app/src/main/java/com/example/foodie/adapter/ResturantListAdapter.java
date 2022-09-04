package com.example.foodie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.R;
import com.example.foodie.model.ResturantModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ResturantListAdapter extends RecyclerView.Adapter<ResturantListAdapter.MyViewHolder> {

    private List<ResturantModel> resturantModelList;
    private ResturantListClickListener clickListener;

    public ResturantListAdapter(List<ResturantModel> resturantModelList, ResturantListClickListener clickListener) {
        this.resturantModelList = resturantModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<ResturantModel> resturantModelList) {
        this.resturantModelList = resturantModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResturantListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResturantListAdapter.MyViewHolder holder,int position) {
        holder.resturantName.setText(resturantModelList.get(holder.getAdapterPosition()).getName());
        holder.resturantAddress.setText("Address: "+resturantModelList.get(holder.getAdapterPosition()).getAddress());
        holder.resturantHours.setText("Today's hours: " + resturantModelList.get(holder.getAdapterPosition()).getHours().getTodayHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(resturantModelList.get(holder.getAdapterPosition()));
            }
        });
        Glide.with(holder.thumbImage)
                .load(resturantModelList.get(holder.getAdapterPosition()).getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return resturantModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  resturantName;
        TextView  resturantAddress;
        TextView  resturantHours;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            resturantName = view.findViewById(R.id.resturantName);
            resturantAddress = view.findViewById(R.id.resturantAddress);
            resturantHours = view.findViewById(R.id.resturantHours);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface ResturantListClickListener {
        public void onItemClick(ResturantModel resturantModel);
    }
}