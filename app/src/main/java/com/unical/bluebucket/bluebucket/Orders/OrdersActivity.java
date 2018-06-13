package com.unical.bluebucket.bluebucket.Orders;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.mancj.slideup.SlideUp;
import com.unical.bluebucket.bluebucket.Bar.Model.DrinkPriceDetails;
import com.unical.bluebucket.bluebucket.Bar.Model.MenuCardDetails;
import com.unical.bluebucket.bluebucket.Bucket.Model.BucketBottles;
import com.unical.bluebucket.bluebucket.Home.MainActivity;
import com.unical.bluebucket.bluebucket.Orders.Model.CancelOrder;
import com.unical.bluebucket.bluebucket.Orders.Model.CompletedOrderDetails;
import com.unical.bluebucket.bluebucket.Orders.Model.FinishOrder;
import com.unical.bluebucket.bluebucket.Orders.Model.OnGoingOrderDetails;
import com.unical.bluebucket.bluebucket.Orders.Model.UpdateCustomerOrder;
import com.unical.bluebucket.bluebucket.Orders.Model.UpdateCustomerOrderResult;
import com.unical.bluebucket.bluebucket.Orders.RecyclerAdapter.CompletedOrdersAdapter;
import com.unical.bluebucket.bluebucket.Orders.RecyclerAdapter.MoreOrderBucketBottlesAdapter;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;
import com.unical.bluebucket.bluebucket.Utils.BottomNavViewHelper;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {
    private static final String TAG = "OrdersActivity";
    private static final int ACTIVITY_NUM = 3;
    private Context mContext = OrdersActivity.this;
    private ApiInterface apiInterface;

    RelativeLayout rlOnGoingOrder;
    LinearLayout linBtnFinishOrderMore, linBtnCancelOrder;
    String sessionPhoneNumber,sessionCustomerId,sessionAddress;

    CompletedOrdersAdapter mCompletedOrdersAdapter;
    MoreOrderBucketBottlesAdapter  mAllBOttlesAdapter;
    ArrayList<CompletedOrderDetails> allCompletedOrders;
    OnGoingOrderDetails onGoingOrder;


    ArrayList<BucketBottles> allBottles;

    RecyclerView recyclerCompletedOrders,recyclerBucketBottles ;

    private SlideUp slideUp;
    private View dim,slideView;
    FloatingActionButton fab;
    RelativeLayout rlMiddlePager;

    TextView tvOngoingDate,tvOngoingBarName,tvOngoingServiceType,tvOngoingBill;
    Button btnOngoingFinish, btnOngoingOrderMore, btnOngoingCancel;
    ProgressBar onGoingOrdersProgressBar;



    //---------------------------------------------------------------------
    private RecyclerView.LayoutManager mLayoutManager,mLayoutManager1;

    String onGoingBarId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);


        setupBottonNavigationView();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sessionPhoneNumber = preferences.getString("@string/SessionPhoneNumber","");
        sessionCustomerId = preferences.getString("@string/SessionCustomerId","");
        sessionAddress = preferences.getString("@string/SessionAddress","");
        Log.d(TAG, "onCreate: started with custId - "+sessionCustomerId);

        rlOnGoingOrder = findViewById(R.id.rl_ongoing_order);
        onGoingOrdersProgressBar = findViewById(R.id.ongoingOrderProgressBar);

//        getOnGoingWaitingOrder = findViewById(R.id.rl_ongoing_order_not_accepted);


        linBtnFinishOrderMore = findViewById(R.id.lin_btn_order_more_finish);
        linBtnCancelOrder = findViewById(R.id.lin_btn_cancel_order);

        tvOngoingDate = findViewById(R.id.ongoing_date);
        tvOngoingBarName = findViewById(R.id.ongoing_bar_name);
        tvOngoingServiceType = findViewById(R.id.ongoing_service_type);
        tvOngoingBill = findViewById(R.id.ongoing_bill);
        btnOngoingFinish = findViewById(R.id.btn_new_order_finish);
        btnOngoingOrderMore = findViewById(R.id.btn_new_order_more);
        btnOngoingCancel = findViewById(R.id.btn_new_order_cancel);

        rlMiddlePager = findViewById(R.id.rl_order_activity_middle_page);
        slideView = findViewById(R.id.slider_ongoing_orders);
        dim = findViewById(R.id.frame_lay_ongoing_orders);

        slideUp = new SlideUp(slideView);
        slideUp.hideImmediately();

        fab = findViewById(R.id.fab_ongoing_orders);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                getOngoingOrders();

                slideUp.animateIn();
                rlMiddlePager.setVisibility(View.GONE);

            }
        });

        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlideDown(float v) {
                dim.setAlpha(1-(v/100));
            }

            @Override
            public void onVisibilityChanged(int i) {

                if (i==View.GONE){
                    rlMiddlePager.setVisibility(View.VISIBLE);
                }
            }
        });




        recyclerCompletedOrders =  findViewById(R.id.recycler_completed_orders);
        //recyclerCompletedOrders.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerCompletedOrders.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration itemDecorationvrb =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerCompletedOrders.addItemDecoration(itemDecorationvrb);
        getOrders();
        getAllBucketBottles();
        getDrinksPrice();

        //----------------------------------------------------------------------


    }

    private void getOrders() {

        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<CompletedOrderDetails>> call = apiInterface.getCompletedOrders(sessionCustomerId);

        Log.d(TAG,"Function Called  -  "+ call.request().url());

        call.enqueue(new Callback<ArrayList<CompletedOrderDetails>>() {

            @Override
            public void onResponse(Call<ArrayList<CompletedOrderDetails>> call, Response<ArrayList<CompletedOrderDetails>> response) {
                Log.d(TAG," Function Succeeded");
                allCompletedOrders = response.body();

                Log.d(TAG, "onResponse: completed orders - "+ allCompletedOrders.size());

                mCompletedOrdersAdapter = new CompletedOrdersAdapter(allCompletedOrders,mContext);
                recyclerCompletedOrders.setAdapter(mCompletedOrdersAdapter);


            }

            @Override
            public void onFailure(Call<ArrayList<CompletedOrderDetails>> call, Throwable t) {



            }
        });

    }

    private void getOngoingOrders() {

        rlOnGoingOrder.setVisibility(View.GONE);
        linBtnCancelOrder.setVisibility(View.GONE);
        linBtnFinishOrderMore.setVisibility(View.GONE);
        onGoingOrdersProgressBar.setVisibility(View.VISIBLE);

        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<OnGoingOrderDetails> call = apiInterface.getOngoingOrders(sessionCustomerId);

        Log.d(TAG,"Function Called  -  "+ call.request().url());

        call.enqueue(new Callback<OnGoingOrderDetails>() {

            @Override
            public void onResponse(Call<OnGoingOrderDetails> call, Response<OnGoingOrderDetails> response) {

                Log.d(TAG," Function Succeeded");
                onGoingOrder = response.body();

                onGoingBarId = onGoingOrder.getStoreId();
                getMenuCard();

                if(onGoingOrder.isSuccess()){

                    onGoingOrdersProgressBar.setVisibility(View.GONE);

                    tvOngoingBill.setText(onGoingOrder.getOrderPlaceTime());
                    tvOngoingBarName.setText(onGoingOrder.getStoreName());
                    tvOngoingServiceType.setText(onGoingOrder.getServiceType());
                    tvOngoingBill.setText("0");

                    if(onGoingOrder.isAccepted()){

                        rlOnGoingOrder.setVisibility(View.VISIBLE);
                        linBtnFinishOrderMore.setVisibility(View.VISIBLE);
                        setOrdersAcceptedBtnListener(onGoingOrder.getOrderId());

                    }
                    else{

                        rlOnGoingOrder.setVisibility(View.VISIBLE);
                        linBtnCancelOrder.setVisibility(View.VISIBLE);
                        cancelOrder(onGoingOrder.getOrderId());

                    }

                }
                else {

                    Toast.makeText(mContext, "Server Down - Error in Fetching Ongoing Order",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OnGoingOrderDetails> call, Throwable t) {

                Log.e(TAG, "onFailure: "+ t.getMessage());

            }
        });

    }

    private void setOrdersAcceptedBtnListener(final String orderId) {

        btnOngoingFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);

                // Set a title for alert dialog
                builder.setTitle("Finish Order.");

                // Ask the final question
                builder.setMessage("Are you sure to Finish the order?");

                builder.setPositiveButton("Finish Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Yes Button Clicked",Toast.LENGTH_SHORT).show();
                        finishOrder(orderId);
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked
                        Toast.makeText(getApplicationContext(),
                                "No Button Clicked",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                //Display the alert dialog on interface
                dialog.show();

            }
        });




        btnOngoingOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showMoreOrderDialog();

            }
        });



    }


    Button orderMore,orderMoreCancel;
    private void showMoreOrderDialog() {
        if(doesMenuCardFetched  && doesDrinkPriceFetched){

            View mView = getLayoutInflater().inflate(R.layout.dialog_order_more,null);
            recyclerBucketBottles = mView.findViewById(R.id.recycler_order_more_bottles);
            orderMore = mView.findViewById(R.id.btn_order_more_confirm);
            mLayoutManager1 = new LinearLayoutManager(mContext);
            recyclerBucketBottles.setLayoutManager(mLayoutManager1);
            RecyclerView.ItemDecoration itemDecorationvrb =
                    new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
            recyclerBucketBottles.addItemDecoration(itemDecorationvrb);
            mAllBOttlesAdapter = new MoreOrderBucketBottlesAdapter(allBottles,priceHashMap,onGoingBarDrinks,mContext);

            recyclerBucketBottles.setAdapter(mAllBOttlesAdapter);

            final Dialog dialog = new Dialog(OrdersActivity.this);
            dialog.setTitle("Dialog");
            dialog.setContentView(mView);
            int width =(int)(getResources().getDisplayMetrics().widthPixels*0.90);
            int height = (int)(getResources().getDisplayMetrics().heightPixels*0.8);
            dialog.getWindow().setLayout(width,height);
            orderMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    orderMoreCall(MoreOrderBucketBottlesAdapter.bottleId,MoreOrderBucketBottlesAdapter.quantityPercent,MoreOrderBucketBottlesAdapter.quantityMl);


                }
            });
            dialog.show();

        }
        else{
            Toast.makeText(mContext, "Network is slow please try again",Toast.LENGTH_SHORT).show();
            getMenuCard();
            getDrinksPrice();
        }


    }

    int subOrderCount =1;
    private void orderMoreCall(String[] bottleId, String[] quantityPercent, String[] quantityMl) {

        for(int i=0; i<MoreOrderBucketBottlesAdapter.bottleId.length;i++){

            Log.d(TAG, "onClick: bottleId - " +MoreOrderBucketBottlesAdapter.bottleId[i]+" quantity - "+MoreOrderBucketBottlesAdapter.quantityPercent[i]
                    +"  - "+MoreOrderBucketBottlesAdapter.quantityMl[i] +"  -  "+MoreOrderBucketBottlesAdapter.subOrderTotal[i]);


        }

        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        UpdateCustomerOrder updateCustomerOrder = new UpdateCustomerOrder(onGoingOrder.getOrderId(),
                subOrderCount,
                bottleId,
                quantityMl,
                quantityPercent,
                MoreOrderBucketBottlesAdapter.subOrderTotal);
//
//        Call<UpdateCustomerOrderResult> call = apiInterface.updateCustomerOrder(
//                onGoingOrder.getOrderId(),
//                subOrderCount,
//                bottleId,
//                quantityMl,
//                quantityPercent,
//                MoreOrderBucketBottlesAdapter.subOrderTotal);

        Log.d(TAG, "orderMoreCall: "+ updateCustomerOrder.toString());

        Call<UpdateCustomerOrderResult> call = apiInterface.updateCustomerOrder(updateCustomerOrder.toString());

        Log.d(TAG,"Function Called  -  "+ call.request().url() );

        call.enqueue(new Callback<UpdateCustomerOrderResult>() {

            @Override
            public void onResponse(Call<UpdateCustomerOrderResult> call, Response<UpdateCustomerOrderResult> response) {

                if(response.body().isSuccess()){

                    Toast.makeText(mContext, "Order updated !  - "+response.body().getErrorMessage(),
                            Toast.LENGTH_LONG).show();

                    Log.d(TAG, "onResponse: -- Order updated !  - "+response.body().getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call<UpdateCustomerOrderResult> call, Throwable t) {

                Log.e(TAG, "onFailure: "+ t.getMessage());


            }
        });





    }

    private void finishOrder(String orderId) {

        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<FinishOrder> call = apiInterface.finishOrder(orderId);

        Log.d(TAG,"Function Called  -  "+ call.request().url());

        call.enqueue(new Callback<FinishOrder>() {
            @Override
            public void onResponse(Call<FinishOrder> call, Response<FinishOrder> response) {

                if(response.body().isSuccess()){

                    rlOnGoingOrder.setVisibility(View.GONE);

                    Toast.makeText(mContext, "Order Finished !",
                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<FinishOrder> call, Throwable t) {

                Log.e(TAG, "onFailure: "+ t.getMessage());

                Toast.makeText(mContext, "Network Issue !" +t.getMessage(),
                        Toast.LENGTH_LONG).show();


            }
        });



    }


    private void cancelOrder(final String orderId) {


        btnOngoingCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // getting API Client and make request/call to server
                apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

                Call<CancelOrder> call = apiInterface.cancelOrder(orderId);

                Log.d(TAG,"Function Called  -  "+ call.request().url());

                call.enqueue(new Callback<CancelOrder>() {

                    @Override
                    public void onResponse(Call<CancelOrder> call, Response<CancelOrder> response) {

                        if(response.body().isSuccess()){

                            rlOnGoingOrder.setVisibility(View.GONE);

                            Toast.makeText(mContext, "Order Cancelled !",
                                    Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<CancelOrder> call, Throwable t) {

                        Log.e(TAG, "onFailure: "+ t.getMessage());


                    }
                });

            }
        });
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

                if(allBottles.size()>0){



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
        if(rlMiddlePager.getVisibility()==View.GONE){
            Snackbar snackbar = Snackbar
                    .make(slideView, "Slide it Down", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
        else{
            Intent intentMain = new Intent(this, MainActivity.class);
            this.startActivity(intentMain);
        }

    }
    private ArrayList<DrinkPriceDetails> drinksPrice = new ArrayList<>();
    HashMap<String,String> priceHashMap ;

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

                }
                else {
                    Toast.makeText(mContext, "Drinks Price not fetched",
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<DrinkPriceDetails>> call, Throwable t) {

                Log.d(TAG,"failed - " + t.getMessage());
                Toast.makeText(mContext, "Check Intenet - " +t.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }

        });
    }


    boolean doesDrinkPriceFetched = false;
    private void cretaeHashMap(ArrayList<DrinkPriceDetails> drinksPrice) {
        priceHashMap = new HashMap<>();
        int sz= drinksPrice.size();
        for(int i = 0; i<sz;i++ ){
            priceHashMap.put(drinksPrice.get(i).getDrinkId(),drinksPrice.get(i).getDrinkPrice());
        }
        doesDrinkPriceFetched = true;
    }


    private ArrayList<MenuCardDetails> onGoingBarDrinks = new ArrayList<>();
    boolean doesMenuCardFetched= false;

    private void getMenuCard(){


        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<ArrayList<MenuCardDetails>> call = apiInterface.getMenuCard(onGoingBarId);

        Log.d(TAG,"Function Called with barID - "+onGoingBarId);

        call.enqueue(new Callback<ArrayList<MenuCardDetails>>() {

            @Override
            public void onResponse(Call<ArrayList<MenuCardDetails>> call, Response<ArrayList<MenuCardDetails>> response) {
                Log.d(TAG,"Succeeded");
                onGoingBarDrinks=response.body();
                doesMenuCardFetched=true;
            }

            @Override
            public void onFailure(Call<ArrayList<MenuCardDetails>> call, Throwable t) {

                Log.d(TAG,"failed");
                Toast.makeText(mContext, "Check Internet Connection - "+t.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });


    }
}
