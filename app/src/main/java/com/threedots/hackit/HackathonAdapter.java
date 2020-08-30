package com.threedots.hackit;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HackathonAdapter extends RecyclerView.Adapter<HackathonAdapter.MyViewHolder> {

    private ArrayList<Hackathon> hackathonList;
    private Context context;

    public HackathonAdapter(ArrayList<Hackathon> hackathonList, Context context) {
        this.hackathonList = hackathonList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hackathon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Hackathon hackathon = hackathonList.get(position);
        holder.title.setText(hackathon.title);
        holder.date.setText(hackathon.date);
        Picasso.get().load(hackathon.imgUrl).into(holder.imageView);
        Picasso.get().load(hackathon.logoUrl).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return hackathonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView date;
        ImageView logo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.hackathon_imageview);
            title = itemView.findViewById(R.id.card_hackathon_title);
            date = itemView.findViewById(R.id.card_hackathon_time);
            logo = itemView.findViewById(R.id.hackathon_logo);

        }
    }
}
