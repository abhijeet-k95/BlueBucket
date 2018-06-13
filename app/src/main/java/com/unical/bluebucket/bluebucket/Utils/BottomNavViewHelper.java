package com.unical.bluebucket.bluebucket.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.unical.bluebucket.bluebucket.Account.AccountActivity;
import com.unical.bluebucket.bluebucket.Bucket.BucketActivity;
import com.unical.bluebucket.bluebucket.Home.MainActivity;
import com.unical.bluebucket.bluebucket.Orders.OrdersActivity;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Search.SearchActivity;

public class BottomNavViewHelper {
    private static final String TAG = "BottomNavViewHelper";
    public static int ACTIVITY_NUM = 0;

    public void setupNav(BottomNavigationViewEx btmNavEx) {
        //Log.d(TAG, "setupNav: started");
        Log.d(TAG,"setupNav: Setting up Bottom Nav");
        btmNavEx.enableAnimation(false);
        btmNavEx.enableItemShiftingMode(false);
        btmNavEx.enableShiftingMode(false);
        btmNavEx.setTextVisibility(false);
    }

    public void enableNavigation (final Context ctx, BottomNavigationViewEx view) {

        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.bottom_nav_near_me :{
                        if(ACTIVITY_NUM != 0){
                            ACTIVITY_NUM=0;
                            Intent intentMain = new Intent(ctx, MainActivity.class);
                            ctx.startActivity(intentMain);
                        }
                        break;
                        
                    }
                    case R.id.bottom_nav_search :{
                        if(ACTIVITY_NUM != 1){
                            ACTIVITY_NUM=1;
                            Intent intentSearch = new Intent(ctx, SearchActivity.class);
                            ctx.startActivity(intentSearch);
                        }
                        break;
                    }


                    case R.id.bottom_nav_bucket :{
                        if(ACTIVITY_NUM != 2){
                            ACTIVITY_NUM=2;
                            Intent intentBucket = new Intent(ctx, BucketActivity.class);
                            ctx.startActivity(intentBucket);

                        }
                        break;
                    }

                    case R.id.bottom_nav_wallet :{
                        if(ACTIVITY_NUM != 3){
                            ACTIVITY_NUM=3;
                            Intent intentOrders = new Intent(ctx, OrdersActivity.class);
                            ctx.startActivity(intentOrders);
                        }
                        break;
                    }

                    case R.id.bottom_nav_account :{
                        if(ACTIVITY_NUM != 4){
                            ACTIVITY_NUM=4;
                            Intent intentAccount = new Intent(ctx, AccountActivity.class);
                            ctx.startActivity(intentAccount);
                        }
                        break;
                    }

                }
                return false;
            }
        });

    }
}
