package com.unical.bluebucket.bluebucket.Orders.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unical.bluebucket.bluebucket.Bar.Model.MenuCardDetails;
import com.unical.bluebucket.bluebucket.Bucket.Model.BucketBottles;
import com.unical.bluebucket.bluebucket.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class MoreOrderBucketBottlesAdapter extends RecyclerView.Adapter<MoreOrderBucketBottlesAdapter.MyViewHolder> {

    private static final String TAG = "MoreOrderBucketBottles";

    public static String bottleId[]  = null ;
    public static String quantityPercent[] = null;
    public static String quantityMl[] = null;
    public static String subOrderTotal[] = null;

    ArrayList<BucketBottles> allBottles;
    HashMap<String,String> priceHashMap ;
    private ArrayList<MenuCardDetails> onGoingBarDrinks;
    Context mContext;

    public MoreOrderBucketBottlesAdapter(ArrayList<BucketBottles> allBottles, HashMap<String, String> priceHashMap, ArrayList<MenuCardDetails> onGoingBarDrinks, Context mContext) {
        this.allBottles = allBottles;
        this.priceHashMap = priceHashMap;
        this.onGoingBarDrinks = onGoingBarDrinks;
        this.mContext = mContext;
        filterAllBottles(this.onGoingBarDrinks,this.allBottles);
        bottleId = new String[allBottles.size()];
        quantityPercent = new String[allBottles.size()];
        quantityMl = new String[allBottles.size()];
        subOrderTotal = new String[allBottles.size()];
        for(int i=0;i<allBottles.size();i++){
            bottleId[i] = allBottles.get(i).getBottleId();
            quantityPercent[i] = "00.00";
            quantityMl [i] = "0ml";
            Log.d(TAG, "MoreOrderBucketBottlesAdapter: allbottles - drinkId - " +allBottles.get(i).getDrinkId() +
              " drinkName " +allBottles.get(i).getDrinkName());
        }
        for(int i=0; i<onGoingBarDrinks.size();i++){
            Log.d(TAG, "MoreOrderBucketBottlesAdapter: - ongoingBarDrinks id- "+ onGoingBarDrinks.get(i).getDrinkId() +" drinkname -"+
                    onGoingBarDrinks.get(i).getDrinkName() );
        }
    }

    private void filterAllBottles(ArrayList<MenuCardDetails> onGoingBarDrinks, ArrayList<BucketBottles> allBottles) {

        Log.d(TAG, "filterAllBottles: Filtering bottles");

        List<Integer> removedDrinksId = new ArrayList<>();
        for(int i=0; i<allBottles.size();i++){
            boolean isAvailabel = false;
            for(int j=0;j<onGoingBarDrinks.size();j++){

                Log.d(TAG, "filterAllBottles: checking if loop - allbottle -"+allBottles.get(i).getDrinkId() +
                            "  bar items- " +onGoingBarDrinks.get(j).getDrinkId());
                if(allBottles.get(i).getDrinkId().equals(onGoingBarDrinks.get(j).getDrinkId())){
                    Log.d(TAG, "filterAllBottles:  " + allBottles.get(i).getDrinkName() +" is available at " + j );
                    isAvailabel = true;
                    break;
                }
            }
            if(!isAvailabel){
                Log.d(TAG, "filterAllBottles: removing  " + allBottles.get(i).getDrinkName()  );
                removedDrinksId.add(i);
            }

        }
        Log.d(TAG, "filterAllBottles:  removedDrinksId" + removedDrinksId);

        if(!removedDrinksId.isEmpty()) {
            Collections.sort(removedDrinksId);
            Collections.reverse(removedDrinksId);
            Log.d(TAG, "filterAllBottles:  removedDrinksId sorted" + removedDrinksId);
        }
        else {
            return;
        }


        for(int i=0 ; i<removedDrinksId.size();i++){
            Log.d(TAG, "filterAllBottles: removed - " +removedDrinksId.get(i)+ "  allbotks - "+allBottles.get(i).getDrinkName());
            int k=removedDrinksId.get(i);
            allBottles.remove(k);
        }

        Log.d(TAG, "filterAllBottles: removed Drinkd - "+ removedDrinksId  + " total bottles now = "+ allBottles.size());
    }

    private void filterAllBottles1(ArrayList<MenuCardDetails> onGoingBarDrinks, ArrayList<BucketBottles> allBottles) {

        ListIterator listIterator = allBottles.listIterator();

        while (listIterator.hasNext()){
            listIterator.next();


            for(int j=0;j<onGoingBarDrinks.size();j++){


            }

        }

    }

        @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snippet_order_more_bottle, parent, false);
        return new MoreOrderBucketBottlesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        holder.tvDrinkName.setText(allBottles.get(position).getDrinkName());
        holder.tvDrinktype.setText(allBottles.get(position).getDrinkType());
        holder.tvRemainPercent.setText(allBottles.get(position).getRemainingQuantity());
        final String drinkId= allBottles.get(position).getDrinkId();
        holder.tvTotalQuantPercent.setText("00.00 %");
        holder.tvTotalQuant.setText("0ml");

        final int[] index = {0};

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index[0]<=5) {
                    index[0] = index[0] + 1;
                    String[] quantArray = {"0ml", "30ml", "60ml", "90ml", "120ml", "150ml", "180ml"};
                    String[] quantMlArray = {"0","30","60","90","120","150","180"};

                    int onGoingBarsDrinkIndex = 0;
                    for(int i=0;i<onGoingBarDrinks.size();i++){
                        if(drinkId.equals(onGoingBarDrinks.get(i).getDrinkId())){
                            onGoingBarsDrinkIndex = i;
                            break;
                        }
                    }

                    String priceRS = extractPrice(index[0],onGoingBarDrinks.get(onGoingBarsDrinkIndex).toString());
                    String priceQuantPercent = calculatePercentPrice(priceRS,drinkId);
                    holder.tvTotalQuantPercent.setText(priceQuantPercent +" %");
                    holder.tvTotalQuant.setText(quantArray[index[0]]);

                    changeCart(position,priceQuantPercent,quantMlArray[index[0]],priceRS);
                }

            }

            
        });

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(index[0]>=1) {
                    index[0] = index[0] - 1;
                    int onGoingBarsDrinkIndex = 0;
                    for(int i=0;i<onGoingBarDrinks.size();i++){
                        if(drinkId.equals(onGoingBarDrinks.get(i).getDrinkId())){
                            onGoingBarsDrinkIndex = i;
                            break;
                        }
                    }
                    String[] quantArray = {"0ml", "30ml", "60ml", "90ml", "120ml", "150ml", "180ml"};
                    String priceRS = extractPrice(index[0],onGoingBarDrinks.get(onGoingBarsDrinkIndex).toString());
                    String priceQuant = calculatePercentPrice(priceRS,drinkId);
                    holder.tvTotalQuantPercent.setText(priceQuant +" %");
                    holder.tvTotalQuant.setText(quantArray[index[0]]);

                }

            }
        });




    }

    private void changeCart(int position, String priceQuantPercent, String s,String priceRs) {

        quantityPercent[position] = priceQuantPercent;
        quantityMl[position] = s;
        subOrderTotal[position]=priceRs;

    }



    private String calculatePercentPrice(String priceRS, String drinkId) {


        String str ="00.00";

        if(!priceRS.equals("0")){
            int intRate = Integer.parseInt(priceRS);
            String drinkPrice = priceHashMap.get(drinkId);
            int intDrinkPrice = Integer.parseInt(drinkPrice);

            //Log.d(TAG, "calculatePercentPrice: - "+priceRS +"  di=rinkId - "+drinkId + "drink Price - "+intDrinkPrice);

            double perPrice = intRate*100;
            perPrice = perPrice/intDrinkPrice;
            perPrice = Math.round(perPrice*100.0)/100.0;
            //Log.d(TAG, "calculatePercentrate: rate - "+intRate+" drinkPrice - "+intDrinkPrice + " calculated - "+newKB);
            str = String.valueOf(perPrice);
        }


        return str;
    }

    private String extractPrice(int index, String prices) {
        String retVal="0";
        if(index>0 && index<=6){
            index=index-1;
            String[] arr=prices.split(" ");
            retVal = arr[index];
            int[] allPrices=new int[arr.length];

            for(int i=0;i<arr.length;i++){
                allPrices[i]=Integer.parseInt(arr[i]);
                //Log.d(TAG, "extractPrice: - "+allPrices[i]);
            }

        }
        return retVal;
    }



    @Override
    public int getItemCount() {
        return allBottles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDrinkName,tvDrinktype,tvRemainPercent,tvTotalQuantPercent,tvTotalQuant;
        ImageView imgRemove,imgAdd;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvDrinkName = itemView.findViewById(R.id.order_more_drink_name);
            tvDrinktype = itemView.findViewById(R.id.order_more_drink_type);
            tvRemainPercent = itemView.findViewById(R.id.order_more_remain_percent);
            tvTotalQuantPercent = itemView.findViewById(R.id.order_more_total_qauntity_percent);
            tvTotalQuant = itemView.findViewById(R.id.order_more_total_quant_ml);
            imgRemove = itemView.findViewById(R.id.order_more_img_rmv);
            imgAdd = itemView.findViewById(R.id.order_more_img_add);


        }
    }
}
