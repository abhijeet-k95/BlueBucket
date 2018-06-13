package com.unical.bluebucket.bluebucket.Bar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unical.bluebucket.bluebucket.Account.Model.AccountDetails;
import com.unical.bluebucket.bluebucket.Bar.MenuCard.RecyclerAdapter.MenuCardDrinkTypeAdapter;
import com.unical.bluebucket.bluebucket.Bar.Model.DrinkPriceDetails;
import com.unical.bluebucket.bluebucket.Bar.Model.MenuCardDetails;
import com.unical.bluebucket.bluebucket.Bar.Model.PlaceNewOrder;
import com.unical.bluebucket.bluebucket.Home.MainActivity;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarActivity extends AppCompatActivity {
    private static final String TAG = "BarActivity";
    String sessionPhoneNumber,sessionCustomerId,sessionAddress;


    RecyclerView rcyclerDrinkType ;
    TextView tvBarName, tvBarAddress;
    Button btnPlaceOrder;

    //--------------------------------------------

    TextView dialogBarName, dialogBarRating, dialogCloseTime, dialogAcService;
    Button dialogCancel, dialofConfirmOrder;
    RadioGroup ac_service;

    //--------------------------------------------------

    String barName,barAddress,barID;
    private ApiInterface apiInterface;
    protected RecyclerView.LayoutManager mLayoutManager;
    MenuCardDrinkTypeAdapter mAdapter;
    private ArrayList<MenuCardDetails> drinks = new ArrayList<>();
    Context mContext = this;

    private ArrayList<DrinkPriceDetails> drinksPrice = new ArrayList<>();
    HashMap<String,String> priceHashMap ;

    ImageView backArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        Log.d(TAG, "onCreate: ");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sessionPhoneNumber = preferences.getString("@string/SessionPhoneNumber","");
        sessionCustomerId = preferences.getString("@string/SessionCustomerId","");
        sessionAddress = preferences.getString("@string/SessionAddress","");
        Log.d(TAG, "onCreate: started with custId - "+sessionCustomerId);


        Intent incoming =getIntent();
        backArrow = findViewById(R.id.bar_back_arrow);
        tvBarName = findViewById(R.id.bar_ac_bar_name);
        tvBarAddress= findViewById(R.id.bar_ac_bar_address);
        barName =incoming.getStringExtra("@string/bar_name");
        barAddress =incoming.getStringExtra("@string/bar_address");
        barID =incoming.getStringExtra("@string/bar_id");
        tvBarName.setText(barName);
        tvBarAddress.setText(barAddress);
        btnPlaceOrder = findViewById(R.id.btn_place_order);



        mContext =this;
        rcyclerDrinkType = findViewById(R.id.recycler_menu_card_drink_type);
        mLayoutManager = new LinearLayoutManager(this);
        rcyclerDrinkType.setLayoutManager(mLayoutManager);

        getDrinksPrice();



        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Confirming Order");


                View mView = getLayoutInflater().inflate(R.layout.snippet_dialog_confirm_new_order,null);
                dialogBarName = mView.findViewById(R.id.dialog_bar_name);
                dialogBarRating = mView.findViewById(R.id.dialog_bar_rating);
                dialogCloseTime = mView.findViewById(R.id.dialog_bar_close_time);
                dialogAcService = mView.findViewById(R.id.dialog_bar_name);
                dialogCancel = mView.findViewById(R.id.btn_place_order_cancel);
                dialofConfirmOrder = mView.findViewById(R.id.btn_place_order_confirm);
                ac_service = mView.findViewById(R.id.rg_ac_service);




                final Dialog dialog = new Dialog(BarActivity.this);
                dialog.setTitle("Dialog");
                dialog.setContentView(mView);
                int width =(int)(getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.3);
                dialog.getWindow().setLayout(width,height);

                dialogCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialofConfirmOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ac_service_string ="" ;
                        int selected = 0;
                        selected= ac_service.getCheckedRadioButtonId();

                        if(selected== -1){
                            Toast.makeText(mContext, "Please Select Service!",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            if(selected == R.id.radio_ac){
                                ac_service_string="1";
                            }
                            else if(selected == R.id.radio_non_ac){
                                ac_service_string="0";
                            }

                            Log.d(TAG, "onClick: Confriming Order with selected radio id - "+ac_service_string);

                            // getting API Client and make request/call to server
                            apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

                            Call<PlaceNewOrder> call = apiInterface.placeNewOrder(sessionCustomerId,barID,ac_service_string);

                            Log.d(TAG,"Function Called");

                            call.enqueue(new Callback<PlaceNewOrder>() {

                                @Override
                                public void onResponse(Call<PlaceNewOrder> call, Response<PlaceNewOrder> response) {
                                    Log.d(TAG,"Succeeded");
                                    if(response.body().isSuccess()){

                                        Toast.makeText(mContext, "Order Placed!",
                                                Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(mContext, "Order does not Placed! - "+response.body().getErrorMessage(),
                                                Toast.LENGTH_LONG).show();
                                    }


                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure(Call<PlaceNewOrder> call, Throwable t) {

                                    Toast.makeText(mContext, "Order does not Placed! - " +t.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Log.e(TAG,"failed");

                                }
                            });

                        }


                    }
                });
                dialog.show();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

    }

    private void getMenuCard(){


        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<MenuCardDetails>> call = apiInterface.getMenuCard(barID);

        Log.d(TAG,"Function Called with barID - "+barID);

        call.enqueue(new Callback<ArrayList<MenuCardDetails>>() {

            @Override
            public void onResponse(Call<ArrayList<MenuCardDetails>> call, Response<ArrayList<MenuCardDetails>> response) {
                Log.d(TAG,"Succeeded");

                drinks=response.body();
                if(drinks.size()!= 0){

                    mAdapter = new MenuCardDrinkTypeAdapter(drinks,priceHashMap,mContext);
                    rcyclerDrinkType.setAdapter(mAdapter);

                }
                else {
                    Toast.makeText(mContext, "Server Down - Unable to Fetch Menu Card",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<MenuCardDetails>> call, Throwable t) {

                Log.d(TAG,"failed");
                Toast.makeText(mContext, "Check Internet Connection - "+t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }
        });


    }

    private void getDrinksPrice() {


        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<DrinkPriceDetails>> call = apiInterface.getDrinkPriceDetails();

        Log.d(TAG,"Function Called - "+call.request().url());

        call.enqueue(new Callback<ArrayList<DrinkPriceDetails>>() {

            @Override
            public void onResponse(Call<ArrayList<DrinkPriceDetails>> call, Response<ArrayList<DrinkPriceDetails>> response) {
                Log.d(TAG,"Succeeded");

                drinksPrice = response.body();
                if (drinksPrice.size()>0){
                    Log.d(TAG, "onResponse: total fetched drink price - " + drinksPrice.size());
                    cretaeHashMap(drinksPrice);
                    getMenuCard();
                }
                else {
                    Toast.makeText(mContext, "Drinks Price not fetched",
                            Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<DrinkPriceDetails>> call, Throwable t) {

                Log.d(TAG,"failed - " + t.getMessage());
                Toast.makeText(mContext, "Check Intenet - " +t.getMessage(),
                        Toast.LENGTH_LONG).show();

            }

        });
    }

    private void cretaeHashMap(ArrayList<DrinkPriceDetails> drinksPrice) {
        priceHashMap = new HashMap<>();
        int sz= drinksPrice.size();
        for(int i = 0; i<sz;i++ ){
            priceHashMap.put(drinksPrice.get(i).getDrinkId(),drinksPrice.get(i).getDrinkPrice());
        }
    }

//    public void onBackPressed() {
//        Intent intentMain = new Intent(this, MainActivity.class);
//        this.startActivity(intentMain);
//    }
}
