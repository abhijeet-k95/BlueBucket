package com.unical.bluebucket.bluebucket.Bucket.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unical.bluebucket.bluebucket.Bucket.Model.BucketBottles;
import com.unical.bluebucket.bluebucket.R;

import java.util.ArrayList;

public class BucketBottlesAdapter extends RecyclerView.Adapter<BucketBottlesAdapter.MyViewHolder> {

    ArrayList<BucketBottles> allBottles;
    Context mContext;

    public BucketBottlesAdapter(ArrayList<BucketBottles> allBottles, Context mContext) {
        this.allBottles = allBottles;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snippet_bucket_bottle, parent, false);
        return new BucketBottlesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvDrinkName.setText(allBottles.get(position).getDrinkName());
        holder.tvDrinkType.setText(allBottles.get(position).getDrinkType());
        holder.tvPurchedOn.setText(allBottles.get(position).getPurchedOn());
        holder.tvRemainingQuantity.setText(allBottles.get(position).getRemainingQuantity());

    }

    @Override
    public int getItemCount() {
        return allBottles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDrinkName;
        TextView tvDrinkType;
        TextView tvPurchedOn,tvPurchedOnStatic;
        TextView tvRemainingQuantity;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvDrinkName = itemView.findViewById(R.id.bucket_bottle_drink_name);
            tvDrinkType = itemView.findViewById(R.id.bucket_bottle_drink_type);
            tvPurchedOn = itemView.findViewById(R.id.bucket_bottle_purched_on);
            tvPurchedOnStatic = itemView.findViewById(R.id.bucket_bottle_purched_on_static);

            tvRemainingQuantity = itemView.findViewById(R.id.bucket_bottle_remaining_quantity);
        }
    }
}
