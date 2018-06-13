package com.unical.bluebucket.bluebucket.Home.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.unical.bluebucket.bluebucket.Bar.MenuCard.RecyclerAdapter.MenuCardDrinksAdapter;
import com.unical.bluebucket.bluebucket.Bar.Model.MenuCardDetails;
import com.unical.bluebucket.bluebucket.Home.Model.DrinkDetails;
import com.unical.bluebucket.bluebucket.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DrinksTypeAdapter extends RecyclerView.Adapter<DrinksTypeAdapter.MyViewHolder> {

    private static final String TAG = "DrinksTypeAdapter";

    private ArrayList<DrinkDetails> drinks ;
    Context mContext;

    DrinksAdapter mAdapter;

    String []uniqueDrinkTypes;
    HashMap<String, ArrayList<DrinkDetails>> drinkOfDrinkTypes = new HashMap<>();

    public DrinksTypeAdapter(ArrayList<DrinkDetails> drinks, Context mContext) {
        this.drinks = drinks;
        this.mContext = mContext;
        classifyDrinks1(drinks);
    }


    private void classifyDrinks1(ArrayList<DrinkDetails> drinks) {

        HashSet<String> set = new HashSet<>();


        for (int i = 0; i<drinks.size(); i++){
            set.add(drinks.get(i).getDrinkType());
        }

        uniqueDrinkTypes = set.toArray(new String[set.size()]);

        Log.d(TAG, "classifyDrinks1: length of un - "+uniqueDrinkTypes.length);
        for(int i=0; i<uniqueDrinkTypes.length;i++){
            drinkOfDrinkTypes.put(uniqueDrinkTypes[i],new ArrayList<DrinkDetails>());
        }

        for(int i=0; i<drinks.size();i++){
            drinkOfDrinkTypes.get(drinks.get(i).getDrinkType()).add(drinks.get(i));
        }

    }

//    private void classifyDrinks(ArrayList<DrinkPriceDetails> drinks) {
//
//        HashSet<String> set = new HashSet<>();
//        int[] indexOfDrinkType ;
//        ArrayList<DrinkPriceDetails> drinksFromDrinkType = new ArrayList<>();
//
//
//        for (int i = 0; i<drinks.size(); i++){
//            set.add(drinks.get(i).getDrinkType());
//        }
//
//        uniqueDrinkTypes = set.toArray(new String[set.size()]);
//
//        for(int i =0; i<uniqueDrinkTypes.length; i++){
//
//            for(int j=0; j<drinks.size(); j++){
//
//                if(uniqueDrinkTypes[i].equals(drinks.get(j).getDrinkType())){
//                    drinksFromDrinkType.add(drinks.get(j));
//                }
//            }
//
//            indexOfDrinkType= new int[drinksFromDrinkType.size()];
//            drinksFromDrinkType.clear();
//            int indexTemp =0;
//            for(int j=0; j<drinks.size(); j++){
//
//                if(uniqueDrinkTypes[i].equals(drinks.get(j).getDrinkType())){
//                    indexOfDrinkType[indexTemp] = j;
//                    indexTemp++;
//                }
//            }
//            drinkOfDrinkTypes.put(uniqueDrinkTypes[i],indexOfDrinkType);
//            //Log.d(TAG, "classifyDrinks: xxcy  added - "+uniqueDrinkTypes[i] +"  "+ indexOfDrinkType +" numbers "+indexOfDrinkType.length);
//
//
//        }
//        Log.d(TAG, "classifyDrinks: xxcy  added - "+ drinkOfDrinkTypes);
//        Log.d(TAG, "classifyDrinks: xxcy  added - "+ drinkOfDrinkTypes.get(uniqueDrinkTypes[0]).length);
//
//
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snippet_drink_type_from_drinks_frag, parent, false);
        return new DrinksTypeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.tvDrinkTypeDrinkFrag.setText(uniqueDrinkTypes[position]);

        final DrinkDetails menuCard = drinks.get(position);

        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(mContext);
        holder.rcyclerDrinks.setLayoutManager(mLayoutManager);
       // mAdapter = new DrinksAdapter(drinks,drinkOfDrinkTypes.get(uniqueDrinkTypes[position]),mContext);
        mAdapter = new DrinksAdapter(drinkOfDrinkTypes.get(uniqueDrinkTypes[position]),mContext);
        holder.rcyclerDrinks.setAdapter(mAdapter);



        holder.rlDrinkTypeDrinksFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!holder.tgDrinktypeDrinkFrag.isChecked()){

                    holder.tgDrinktypeDrinkFrag.setChecked(true);
                    Log.d(TAG, "onClick: toggle buttn is checked ");
                    holder.rlDrinksNameDrinkFrag.setVisibility(View.VISIBLE);


                }
                else{

                    holder.tgDrinktypeDrinkFrag.setChecked(false);
                    Log.d(TAG, "onClick: toggle buttn is unchecked ");
                    holder.rlDrinksNameDrinkFrag.setVisibility(View.GONE);

                }



            }
        });
        holder.tgDrinktypeDrinkFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.tgDrinktypeDrinkFrag.isChecked()){

                    Log.d(TAG, "onClick: toggle buttn is checked ");
                    holder.rlDrinksNameDrinkFrag.setVisibility(View.VISIBLE);


                }
                else{

                    Log.d(TAG, "onClick: toggle buttn is unchecked ");
                    holder.rlDrinksNameDrinkFrag.setVisibility(View.GONE);

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return uniqueDrinkTypes.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlDrinkTypeDrinksFrag;
        TextView tvDrinkTypeDrinkFrag;
        ToggleButton tgDrinktypeDrinkFrag;
        RelativeLayout rlDrinksNameDrinkFrag;
        RecyclerView rcyclerDrinks;

        public MyViewHolder(View itemView) {
            super(itemView);

            rlDrinkTypeDrinksFrag = itemView.findViewById(R.id.rl_drink_type_drink_frag);
            tvDrinkTypeDrinkFrag = itemView.findViewById(R.id.drink_frag_drink_type);
            tgDrinktypeDrinkFrag = itemView.findViewById(R.id.toggleButton_drink_type_drink_frag);
            rlDrinksNameDrinkFrag = itemView.findViewById(R.id.rl_drinks_name_drink_frag);
            rcyclerDrinks = itemView.findViewById(R.id.recycler_drink_frag_drinks);



        }
    }
}
