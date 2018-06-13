package com.unical.bluebucket.bluebucket.Home.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.unical.bluebucket.bluebucket.Bar.BarActivity;
import com.unical.bluebucket.bluebucket.Home.Model.BarsDetails;
import com.unical.bluebucket.bluebucket.R;

import java.util.ArrayList;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.MyViewHolder> {
    private static final String TAG = "BarAdapter";

    private ArrayList<BarsDetails> bars;
    private Context mContext;

    public BarAdapter(ArrayList<BarsDetails> bars, Context mContext) {
        this.bars = bars;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_home_bar_card_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final BarsDetails bar = bars.get(position);
        holder.barName.setText(bar.getBarName());
        holder.barAddress.setText(bar.getBarAddress());


        holder.barContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: bar selected " + bar.getBarName());
                Intent intent = new Intent(mContext,BarActivity.class);

                intent.putExtra("@string/bar_id",bar.getBarID());
                intent.putExtra("@string/bar_name",bar.getBarName());
                intent.putExtra("@string/bar_address",bar.getBarAddress());

                mContext.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return bars.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout barContainer;
        TextView barName;
        TextView barAddress;

        public MyViewHolder(View itemView) {
            super(itemView);
            barContainer = itemView.findViewById(R.id.home_bars_cardview);
            barName = itemView.findViewById(R.id.bar_name);
            barAddress = itemView.findViewById(R.id.bar_address);
        }
    }
}
