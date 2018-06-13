package com.unical.bluebucket.bluebucket.Orders.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unical.bluebucket.bluebucket.Orders.Model.CompletedOrderDetails;
import com.unical.bluebucket.bluebucket.R;

import java.util.ArrayList;

public class CompletedOrdersAdapter extends RecyclerView.Adapter<CompletedOrdersAdapter.MyViewHolder> {
    private static final String TAG = "CompletedOrdersAdapter";

    ArrayList<CompletedOrderDetails> completedOrders;
    Context mContext;

    public CompletedOrdersAdapter(ArrayList<CompletedOrderDetails> completedOrders, Context mContext) {
        Log.d(TAG, "CompletedOrdersAdapter: Constructed");
        this.completedOrders = completedOrders;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snippet_completed_orders_card_view, parent, false);
        return new CompletedOrdersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvDate.setText(completedOrders.get(position).getOrderPlaceTime());
        holder.tvBarName.setText(completedOrders.get(position).getStoreName());
        holder.tvService.setText(completedOrders.get(position).getServiceType());
        holder.tvStatus.setText(completedOrders.get(position).getOrderStatus());
        holder.tvBill.setText("1200");

    }

    @Override
    public int getItemCount() {
        return completedOrders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvBarName, tvService, tvStatus, tvBill;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.completed_order_date);
            tvBarName = itemView.findViewById(R.id.completed_order_bar_name);
            tvService = itemView.findViewById(R.id.completed_order_service_type);
            tvStatus = itemView.findViewById(R.id.completed_order_status);
            tvBill = itemView.findViewById(R.id.completed_order_bill);
        }
    }
}
