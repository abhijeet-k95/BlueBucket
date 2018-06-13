package com.unical.bluebucket.bluebucket.Home.RecyclerAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unical.bluebucket.bluebucket.Home.Model.BarsDetails;
import com.unical.bluebucket.bluebucket.Home.Model.DrinkDetails;
import com.unical.bluebucket.bluebucket.Home.Model.NewBottleInsert;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.MyViewHolder> {
    private static final String TAG = "DrinksAdapter";
    String paymentStringDrinkName,paymentStringDrinkType,paymentStringDrinkQuantity,paymentStringDrinkPrice,paymentStringDrinkId;
    TextView paymentDrinkName,paymentDrinkType,paymentDrinkQuantity,paymentDrinkPrice;
    EditText paymentEntered;
    Button pay;
    private ApiInterface apiInterface;

    String sessionPhoneNumber,sessionCustomerId,sessionAddress;

    private ArrayList<DrinkDetails> drinks;
    private Context mContext;

    public DrinksAdapter(ArrayList<DrinkDetails> drinks, Context mContext) {
        this.drinks = drinks;
        this.mContext = mContext;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        this.sessionPhoneNumber = preferences.getString("@string/SessionPhoneNumber","");
        this.sessionCustomerId = preferences.getString("@string/SessionCustomerId","");
        this.sessionAddress = preferences.getString("@string/SessionAddress","");
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.snippet_drinks_name_from_drink_frag, parent, false);
        return new DrinksAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.drinkName.setText(drinks.get(position).getDrinkName());
        holder.drinkQuantity.setText(drinks.get(position).getDrinkQuantity());
        holder.drinkPrice.setText(drinks.get(position).getDrinkPrice());
        holder.drinkAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymentStringDrinkName = drinks.get(position).getDrinkName();
                paymentStringDrinkType = drinks.get(position).getDrinkType();
                paymentStringDrinkQuantity = drinks.get(position).getDrinkQuantity();
                paymentStringDrinkPrice = drinks.get(position).getDrinkPrice();
                paymentStringDrinkId = drinks.get(position).getDrinkId();

//                Toast.makeText(mContext, "Payment proceed!",
//                        Toast.LENGTH_LONG).show();

                View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_payment_proceed, null);
                paymentDrinkName = mView.findViewById(R.id.payment_drink_name);
                paymentDrinkType = mView.findViewById(R.id.payment_drink_type);
                paymentDrinkQuantity = mView.findViewById(R.id.payment_drink_quantity);
                paymentDrinkPrice = mView.findViewById(R.id.payment_drink_price);

                paymentEntered = mView.findViewById(R.id.payment_amount_enter);
                pay = mView.findViewById(R.id.btn_payment_pay);


                paymentDrinkName.setText(paymentStringDrinkName);
                paymentDrinkType.setText(paymentStringDrinkType);
                paymentDrinkQuantity.setText(paymentStringDrinkQuantity);
                paymentDrinkPrice.setText(paymentStringDrinkPrice);





                final Dialog dialog = new Dialog(mContext);
                dialog.setTitle(R.string.app_name);
                dialog.setContentView(mView);
                int width =(int)(mContext.getResources().getDisplayMetrics().widthPixels*0.90);
                int height = (int)(mContext.getResources().getDisplayMetrics().heightPixels*0.7);
                dialog.getWindow().setLayout(width,height);

                pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!paymentEntered.getText().toString().equals("")){
                            if(Integer.parseInt(paymentEntered.getText().toString())==Integer.parseInt(paymentStringDrinkPrice)){



                                // getting API Client and make request/call to server
                                apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

                                Call<NewBottleInsert> call = apiInterface.insertNewDrink(sessionCustomerId,paymentStringDrinkId);

                                Log.d(TAG,"Function Called");

                                call.enqueue(new Callback<NewBottleInsert>() {

                                    @Override
                                    public void onResponse(Call<NewBottleInsert> call, Response<NewBottleInsert> response) {
                                        Log.d(TAG,"Succeeded");


                                    }

                                    @Override
                                    public void onFailure(Call<NewBottleInsert> call, Throwable t) {

                                        Log.d(TAG,"failed - " + t.getMessage());

                                    }
                                });










                                Toast.makeText(mContext, "Payment Successful!",
                                        Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }
                        else {
                            Toast.makeText(mContext, "Enter Amount",
                                    Toast.LENGTH_LONG).show();

                        }

                    }
                });
                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //  tv_drink_name_drink_frag  drink_quantity_tag  drink_price_tag  drink_add_to_cart

        TextView drinkName;
        TextView drinkQuantity;
        TextView drinkPrice;
        ImageView drinkAddToCart;
        public MyViewHolder(View itemView) {
            super(itemView);
            drinkName = itemView.findViewById(R.id.tv_drink_name_drink_frag);
            drinkQuantity = itemView.findViewById(R.id.drink_quantity_tag);
            drinkPrice = itemView.findViewById(R.id.drink_price_tag);
            drinkAddToCart = itemView.findViewById(R.id.drink_add_to_cart);
        }
    }
}
