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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.unical.bluebucket.bluebucket.Home.Model.BarsDetails;
import com.unical.bluebucket.bluebucket.Home.RecyclerAdapter.BarAdapter;
import com.unical.bluebucket.bluebucket.Login.LogInActivity;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BarsFragment extends Fragment {

    private static final String TAG = "BarsFragment";
    Context mContext;
    private ArrayList<BarsDetails> bars = new ArrayList<>();
    RecyclerView rcyclerBar ;
    ProgressBar mProgressBar;
    protected RecyclerView.LayoutManager mLayoutManager;
    BarAdapter mAdapter;
    private ApiInterface apiInterface;




   

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: BarFragment");
        View view = inflater.inflate(R.layout.fragment_bars,container,false);

        mContext = view.getContext();
        rcyclerBar = view.findViewById(R.id.rcycler_bars);
        mProgressBar = view.findViewById(R.id.allBarsRequestProgressBar);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        rcyclerBar.setLayoutManager(mLayoutManager);

        fillBarsData();


        return view;
    }



    private void fillBarsData(){


        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<BarsDetails>> call = apiInterface.getBarDetails();

        Log.d(TAG,"Function Called - "+ call.request().url());

        call.enqueue(new Callback<ArrayList<BarsDetails>>() {

            @Override
            public void onResponse(Call<ArrayList<BarsDetails>> call, Response<ArrayList<BarsDetails>> response) {
                Log.d(TAG,"Succeeded");
                mProgressBar.setVisibility(View.GONE);

                bars = response.body();
//                Log.d(TAG, "onResponse: "+bars.get(0).getBarName());
//                Log.d(TAG, "onResponse: setting BarAdapter" + bars.size());
                if(bars.size()!=0){

                    mAdapter = new BarAdapter(bars,mContext);
                    rcyclerBar.setAdapter(mAdapter);

                }
                else {

                    Toast.makeText(mContext, "Server Down - Error in Fetching Bars",
                            Toast.LENGTH_LONG).show();

                }



            }

            @Override
            public void onFailure(Call<ArrayList<BarsDetails>> call, Throwable t) {

                Log.d(TAG,"failed - " + t.getMessage());
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, "Check Internet Connection - "+t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });


    }


}
