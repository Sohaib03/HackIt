package com.threedots.hackit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContestAdapter extends RecyclerView.Adapter<ContestAdapter.MyViewHolder> {

    ArrayList <Contest> contestList;
    Context context;

    public ContestAdapter(Context context, ArrayList<Contest> contestList) {
        this.context = context;
        this.contestList = contestList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contest, parent, false);
        return new ContestAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contest contest = contestList.get(position);
        holder.titleText.setText(contest.title);
        holder.dateText.setText(contest.date);
        holder.imageView.setImageResource(R.drawable.ic_cf_logo_01);
        //Picasso.get().load(R.drawable.ic_cf_logo_01).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return contestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView dateText;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.contest_title_text);
            dateText = itemView.findViewById(R.id.contest_time_text);
            imageView = itemView.findViewById(R.id.contest_logo);
        }
    }
}
