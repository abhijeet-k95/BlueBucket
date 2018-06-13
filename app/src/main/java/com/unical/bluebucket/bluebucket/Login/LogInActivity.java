package com.unical.bluebucket.bluebucket.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.unical.bluebucket.bluebucket.Home.MainActivity;
import com.unical.bluebucket.bluebucket.Login.Model.LogInData;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";

    private ApiInterface apiInterface;

    EditText mPhoneNumber,mPasword;
    Button mLogIn;
    ProgressBar mProgressBar;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPhoneNumber = findViewById(R.id.logInPhoneNumber);
        mPasword = findViewById(R.id.logInPassword);
        mLogIn= findViewById(R.id.btn_login);
        mProgressBar = findViewById(R.id.logInRequestProgressBar);
        mProgressBar.setVisibility(View.GONE);
        mContext = this;

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Attempting Login");

                String phonenumber = mPhoneNumber.getText().toString();
                String password = mPasword.getText().toString();
                if(!phonenumber.equals("")&& !password.equals("")){
                    mProgressBar.setVisibility(View.VISIBLE);

                    login(phonenumber,password);
                }


            }
        });
    }

    private void login(final String phonenumber, final String password) {


        // getting API Client and make request/call to server
        apiInterface= ApiInterface.ApiClient.getApiClient().create(ApiInterface.class);

        Call<LogInData> call = apiInterface.checkLogin(phonenumber,password);

        Log.d(TAG,"Function Called"+call.request().url());

        call.enqueue(new Callback<LogInData>() {

            @Override
            public void onResponse(Call<LogInData> call, Response<LogInData> response) {
                Log.d(TAG," Function Succeeded");

                if(response.body().isSuccess()){

                    Log.d(TAG," Log In Successful ");
                    Toast.makeText(LogInActivity.this, "Log In Successful!",
                            Toast.LENGTH_LONG).show();
                    setSessionParams(phonenumber,response.body().getCustId(),response.body().getAddress());
                    mProgressBar.setVisibility(View.GONE);
                    //getSessioParams();
                    Intent intent =new Intent(LogInActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else {

                    Log.d(TAG,"Log in unsuccessful check password and phone number ");
                    mProgressBar.setVisibility(View.GONE);
                    Toast.makeText(LogInActivity.this, "Log In usnuccessful! - " +response.body().getErrorMessage(),
                            Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<LogInData> call, Throwable t) {

                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG,"failed - " + t.getMessage());
                Toast.makeText(mContext, "Log In Failed!",
                        Toast.LENGTH_LONG).show();


            }
        });




    }
    private void setSessionParams(String phonenumber, String custId, String address){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LogInActivity.this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("@string/SessionPhoneNumber",phonenumber);
        editor.commit();
        editor.putString("@string/SessionCustomerId",custId);
        editor.commit();
        editor.putString("@string/SessionAddress",address);
        editor.commit();
    }

    private  void getSessioParams(Context ctx){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        String sessionPhoneNumber = preferences.getString("@string/SessionPhoneNumber","");
        String sessionCustomerId = preferences.getString("@string/SessionCustomerId","");
        String sessionAddress = preferences.getString("@string/SessionAddress","");

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
