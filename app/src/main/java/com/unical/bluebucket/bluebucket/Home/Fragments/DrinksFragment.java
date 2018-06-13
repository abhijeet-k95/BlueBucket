package com.unical.bluebucket.bluebucket.Home.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unical.bluebucket.bluebucket.Home.Model.DrinkDetails;
import com.unical.bluebucket.bluebucket.Home.RecyclerAdapter.DrinksTypeAdapter;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DrinksFragment extends Fragment {
    private static final String TAG = "DrinksFragment";

    Context mContext;
    RecyclerView recyclerDrinks ;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<DrinkDetails> drinks = new ArrayList<>();
    DrinksTypeAdapter mAdapter;
    private ApiInterface apiInterface;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Fragmnet");
        View view = inflater.inflate(R.layout.fragment_drinks,container,false);

        mContext = view.getContext();
        recyclerDrinks = view.findViewById(R.id.recycler_drinks_frag_drink);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerDrinks.setLayoutManager(mLayoutManager);
        getDrinks();

        return view;
    }

    private void getDrinks() {

        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<DrinkDetails>> call = apiInterface.getDrinkDetails();

        Log.d(TAG,"Function Called - "+call.request().url());

        call.enqueue(new Callback<ArrayList<DrinkDetails>>() {

            @Override
            public void onResponse(Call<ArrayList<DrinkDetails>> call, Response<ArrayList<DrinkDetails>> response) {
                Log.d(TAG,"Succeeded");

                drinks = response.body();
//                Log.d(TAG, "onResponse: "+bars.get(0).getBarName());
//                Log.d(TAG, "onResponse: setting BarAdapter" + bars.size());

                if(drinks.size()!=0){
                    mAdapter = new DrinksTypeAdapter(drinks,mContext);
                    recyclerDrinks.setAdapter(mAdapter);

                }
                else {
                    Toast.makeText(mContext, "Server Down - Error in Fetching Drinks",
                            Toast.LENGTH_LONG).show();
                }




            }

            @Override
            public void onFailure(Call<ArrayList<DrinkDetails>> call, Throwable t) {

                Log.d(TAG,"failed - " + t.getMessage());
                Toast.makeText(mContext, "Check Intenet - " +t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}
