package com.unical.bluebucket.bluebucket.Account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.unical.bluebucket.bluebucket.Account.Model.AccountDetails;
import com.unical.bluebucket.bluebucket.Home.MainActivity;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;
import com.unical.bluebucket.bluebucket.Utils.BottomNavViewHelper;
import com.unical.bluebucket.bluebucket.Utils.Logout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "AccountActivity";
    private static final int ACTIVITY_NUM = 4;

    private AccountDetails details;
    private ApiInterface apiInterface;

    private TextView name, phone,email,address;
    private Button btnLogout;
    private ImageView testImg;
    private Context mContext = AccountActivity.this;
    String sessionPhoneNumber,sessionCustomerId,sessionAddress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupBottonNavigationView();
        name = findViewById(R.id.tv_name);
        phone = findViewById(R.id.tv_phn);
        email= findViewById(R.id.tv_email);
        address = findViewById(R.id.account_address);
        btnLogout = findViewById(R.id.btn_logout);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        sessionPhoneNumber = preferences.getString("@string/SessionPhoneNumber","");
        sessionCustomerId = preferences.getString("@string/SessionCustomerId","");
        sessionAddress = preferences.getString("@string/SessionAddress","");
        Log.d(TAG, "onCreate: started with custId - "+sessionCustomerId);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout lg = new Logout(mContext);

            }
        });



        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<AccountDetails> call = apiInterface.getAccountDetails(sessionCustomerId);

        Log.d(TAG,"Function Called - "+call.request().url());

        call.enqueue(new Callback<AccountDetails>() {

            @Override
            public void onResponse(Call<AccountDetails> call, Response<AccountDetails> response) {
                Log.d(TAG,"Succeeded");


                details = response.body();

                name.setText(details.getName());
                phone.setText(details.getPhoneNumber());
                email.setText(details.getEmail());
                address.setText(details.getAddress());


            }

            @Override
            public void onFailure(Call<AccountDetails> call, Throwable t) {

                Log.d(TAG,"failed");

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

    @Override
    public void onBackPressed() {
        Intent intentMain = new Intent(this, MainActivity.class);
        this.startActivity(intentMain);
    }
}
