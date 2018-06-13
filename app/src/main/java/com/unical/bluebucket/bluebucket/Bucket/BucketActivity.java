package com.unical.bluebucket.bluebucket.Bucket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.unical.bluebucket.bluebucket.Account.AccountActivity;
import com.unical.bluebucket.bluebucket.Account.Model.AccountDetails;
import com.unical.bluebucket.bluebucket.Bucket.Model.BucketBottles;
import com.unical.bluebucket.bluebucket.Bucket.RecyclerAdapter.BucketBottlesAdapter;
import com.unical.bluebucket.bluebucket.Home.MainActivity;
import com.unical.bluebucket.bluebucket.Home.RecyclerAdapter.BarAdapter;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;
import com.unical.bluebucket.bluebucket.Utils.BottomNavViewHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BucketActivity extends AppCompatActivity {

    private static final String TAG = "BucketActivity";
    private static final int ACTIVITY_NUM = 2;

    private ApiInterface apiInterface;

    String sessionPhoneNumber,sessionCustomerId,sessionAddress;
    ArrayList<BucketBottles> allBottles;
    RecyclerView recyclerBucketBottles ;
    BucketBottlesAdapter mAdapter;
    Context mContext= BucketActivity.this;
    protected RecyclerView.LayoutManager mLayoutManager;
 //   BarAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sessionPhoneNumber = preferences.getString("@string/SessionPhoneNumber","");
        sessionCustomerId = preferences.getString("@string/SessionCustomerId","");
        sessionAddress = preferences.getString("@string/SessionAddress","");
        Log.d(TAG, "onCreate: started with custId - "+sessionCustomerId);

        recyclerBucketBottles = findViewById(R.id.recycler_bucket_bottles);
        mLayoutManager = new LinearLayoutManager(mContext);

        RecyclerView.ItemDecoration itemDecorationvrb =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerBucketBottles.addItemDecoration(itemDecorationvrb);
        recyclerBucketBottles.setLayoutManager(mLayoutManager);

        setupBottonNavigationView();
        getAllBucketBottles();

    }
    private void setupBottonNavigationView() {

        Log.d(TAG, "setupBottonNavigationView: Setting up Bottom Nav View");
        BottomNavViewHelper setUp = new BottomNavViewHelper();
        BottomNavigationViewEx bottom_nav_menus = findViewById(R.id.bottom_navigation_view_ex_bar);
        setUp.setupNav(bottom_nav_menus);
        setUp.enableNavigation(this, bottom_nav_menus);

        Menu menu = bottom_nav_menus.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    private void getAllBucketBottles(){


        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<BucketBottles>> call = apiInterface.getBucketBottles(sessionCustomerId);

        Log.d(TAG,"Function Called");

        call.enqueue(new Callback<ArrayList<BucketBottles>>() {

            @Override
            public void onResponse(Call<ArrayList<BucketBottles>> call, Response<ArrayList<BucketBottles>> response) {
                Log.d(TAG,"Succeeded");

                allBottles = response.body();

                if(allBottles.size()!=0){

                    mAdapter = new BucketBottlesAdapter(allBottles,mContext);
                    recyclerBucketBottles.setAdapter(mAdapter);


                }
                else {
                    Toast.makeText(mContext, "Server Down - Error in Fetching Bucket Bottles",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<BucketBottles>> call, Throwable t) {

                Log.d(TAG,"failed");
                Toast.makeText(mContext, "Check Internet Connection - "+t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }



    @Override
    public void onBackPressed() {
        Intent intentMain = new Intent(this, MainActivity.class);
        this.startActivity(intentMain);
    }
}
