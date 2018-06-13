package com.unical.bluebucket.bluebucket.Bar.MenuCard.RecyclerAdapter;

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

import com.unical.bluebucket.bluebucket.Bar.Model.MenuCardDetails;
import com.unical.bluebucket.bluebucket.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MenuCardDrinkTypeAdapter extends RecyclerView.Adapter<MenuCardDrinkTypeAdapter.MyViewAdapter> {



    private static final String TAG = "MenuCardDrinkTypeAdapte";

    private ArrayList<MenuCardDetails> drinks;
    HashMap<String,String> priceHashMap ;
    final Context mContext;



    MenuCardDrinksAdapter mAdapter;
    String []uniqueDrinkTypes;
    HashMap<String, int[]> drinkOfDrinkTypes = new HashMap<>();

    public MenuCardDrinkTypeAdapter(ArrayList<MenuCardDetails> drinks, HashMap<String, String> priceHashMap, Context mContext) {
        this.drinks = drinks;
        this.priceHashMap = priceHashMap;
        this.mContext = mContext;
        classifyDrinks(drinks);
    }


    //    public MenuCardDrinkTypeAdapter(ArrayList<MenuCardDetails> drinks, Context mContext) {
//        Log.d(TAG, "MenuCardDrinkTypeAdapter: Constructed  - "+drinks.size());
//        this.drinks = drinks;
//        this.mContext = mContext;
//        classifyDrinks(drinks);
//    }

    private void classifyDrinks(ArrayList<MenuCardDetails> drinks) {

        HashSet<String> set = new HashSet<>();
        int[] indexOfDrinkType ;
        ArrayList<MenuCardDetails> drinksFromDrinkType = new ArrayList<>();


        for (int i = 0; i<drinks.size(); i++){
            set.add(drinks.get(i).getDrinkType());
        }

        uniqueDrinkTypes = set.toArray(new String[set.size()]);

        for(int i =0; i<uniqueDrinkTypes.length; i++){

            for(int j=0; j<drinks.size(); j++){

                if(uniqueDrinkTypes[i].equals(drinks.get(j).getDrinkType())){
                    drinksFromDrinkType.add(drinks.get(j));
                }
            }

            indexOfDrinkType= new int[drinksFromDrinkType.size()];
            drinksFromDrinkType.clear();
            int indexTemp =0;
            for(int j=0; j<drinks.size(); j++){

                if(uniqueDrinkTypes[i].equals(drinks.get(j).getDrinkType())){
                    indexOfDrinkType[indexTemp] = j;
                    indexTemp++;
                }
            }
            drinkOfDrinkTypes.put(uniqueDrinkTypes[i],indexOfDrinkType);


        }

    }

    @NonNull
    @Override
    public MyViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snippet_menu_card_drink, parent, false);
        return new MenuCardDrinkTypeAdapter.MyViewAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewAdapter holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");

        final MenuCardDetails menuCard = drinks.get(position);
        holder.menuCardDrinkTypeTitle.setText(uniqueDrinkTypes[position]);

        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(mContext);
        holder.menuCardDrinks.setLayoutManager(mLayoutManager);
        mAdapter = new MenuCardDrinksAdapter(drinks,drinkOfDrinkTypes.get(uniqueDrinkTypes[position]),priceHashMap,mContext);
        holder.menuCardDrinks.setAdapter(mAdapter);


        holder.rlDrinkType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!holder.tgDrinkType.isChecked()){

                    holder.tgDrinkType.setChecked(true);
                    Log.d(TAG, "onClick: toggle buttn is checked ");
                    holder.rlDrinksName.setVisibility(View.VISIBLE);


                }
                else{

                    holder.tgDrinkType.setChecked(false);
                    Log.d(TAG, "onClick: toggle buttn is unchecked ");
                    holder.rlDrinksName.setVisibility(View.GONE);

                }



            }
        });

        holder.tgDrinkType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.tgDrinkType.isChecked()){

                    Log.d(TAG, "onClick: toggle buttn is checked ");
                    holder.rlDrinksName.setVisibility(View.VISIBLE);


                }
                else{

                    Log.d(TAG, "onClick: toggle buttn is unchecked ");
                    holder.rlDrinksName.setVisibility(View.GONE);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return uniqueDrinkTypes.length;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {
        RelativeLayout rlDrinkType,rlDrinksName;
        TextView menuCardDrinkTypeTitle;
        ToggleButton tgDrinkType;
        RecyclerView menuCardDrinks;
        public MyViewAdapter(View itemView) {
            super(itemView);
            rlDrinkType = itemView.findViewById(R.id.rl_menu_card_drink_type);
            menuCardDrinkTypeTitle = itemView.findViewById(R.id.menu_card_drink_type_title);
            tgDrinkType= itemView.findViewById(R.id.toggleButton_drink_type);
            rlDrinksName = itemView.findViewById(R.id.rl_drinks_name);
            menuCardDrinks = itemView.findViewById(R.id.recycler_menu_card_drinks);
        }
    }
}
