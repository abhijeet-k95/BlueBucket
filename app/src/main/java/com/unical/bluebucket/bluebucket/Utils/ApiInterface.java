package com.unical.bluebucket.bluebucket.Utils;

import com.unical.bluebucket.bluebucket.Bar.Model.DrinkPriceDetails;
import com.unical.bluebucket.bluebucket.Bar.Model.MenuCardDetails;
import com.unical.bluebucket.bluebucket.Account.Model.AccountDetails;
import com.unical.bluebucket.bluebucket.Bar.Model.PlaceNewOrder;
import com.unical.bluebucket.bluebucket.Bucket.Model.BucketBottles;
import com.unical.bluebucket.bluebucket.Home.Model.BarsDetails;
import com.unical.bluebucket.bluebucket.Home.Model.DrinkDetails;
import com.unical.bluebucket.bluebucket.Home.Model.NewBottleInsert;
import com.unical.bluebucket.bluebucket.Login.Model.LogInData;
import com.unical.bluebucket.bluebucket.Orders.Model.CancelOrder;
import com.unical.bluebucket.bluebucket.Orders.Model.CompletedOrderDetails;
import com.unical.bluebucket.bluebucket.Orders.Model.FinishOrder;
import com.unical.bluebucket.bluebucket.Orders.Model.OnGoingOrderDetails;
import com.unical.bluebucket.bluebucket.Orders.Model.UpdateCustomerOrder;
import com.unical.bluebucket.bluebucket.Orders.Model.UpdateCustomerOrderResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by admin on 11/10/2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("account_details")
    Call<AccountDetails> getAccountDetails(@Field("customer_id") String custId);

    @GET("home_page_bars")
    Call<ArrayList<BarsDetails>> getBarDetails();

    @FormUrlEncoded
    @POST("get_menu_card")
    Call<ArrayList<MenuCardDetails>> getMenuCard(@Field("store_id") String storeId);


    @FormUrlEncoded
    @POST("check_customer_login")
    Call<LogInData> checkLogin(@Field("phone_number") String phoneNumber, @Field("password") String password);


    @GET("get_all_drinks")
    Call<ArrayList<DrinkDetails>> getDrinkDetails();

    @GET("get_all_drinks")
    Call<ArrayList<DrinkPriceDetails>> getDrinkPriceDetails();

    @FormUrlEncoded
    @POST("insert_new_bottle")
    Call<NewBottleInsert> insertNewDrink(@Field("customer_id") String custId, @Field("drink_id") String drinkId);

    @FormUrlEncoded
    @POST("get_bucket_bottles")
    Call<ArrayList<BucketBottles>> getBucketBottles(@Field("customer_id") String custId);

    @FormUrlEncoded
    @POST("place_new_order")
    Call<PlaceNewOrder> placeNewOrder(@Field("customer_id") String custId, @Field("store_id") String storeId, @Field("ac_service") String acService);


    @GET("get_customers_completed_order/{customer_id}")
    Call<ArrayList<CompletedOrderDetails>> getCompletedOrders(@Path("customer_id") String customer_id);

    @GET("get_customers_ongoing_order/{customer_id}")
    Call<OnGoingOrderDetails> getOngoingOrders(@Path("customer_id") String customer_id);

    @GET("cancel_order/{order_id}")
    Call<CancelOrder> cancelOrder(@Path("order_id") String order_id);

    @GET("finish_order/{order_id}")
    Call<FinishOrder> finishOrder(@Path("order_id") String order_id);

    @FormUrlEncoded
    @POST("update_customers_ongoing_order")
    Call<UpdateCustomerOrderResult> updateCustomerOrder(@Field("json_data") String updateCustomerOrder);



    public class ApiClient {

        public static  final String BASE_URL="http://192.168.1.103/bluebucket/android/public/";

        public static Retrofit retrofit =null;

        public  static Retrofit getApiClient(){
            if(retrofit==null){
                retrofit = new Retrofit.Builder().
                        baseUrl(BASE_URL).
                        addConverterFactory(GsonConverterFactory.create()).
                        build();
            }
            return retrofit;
        }

    }



}

