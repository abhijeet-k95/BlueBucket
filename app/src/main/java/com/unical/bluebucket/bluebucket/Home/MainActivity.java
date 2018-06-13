package com.unical.bluebucket.bluebucket.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.unical.bluebucket.bluebucket.Home.Fragments.BarsFragment;
import com.unical.bluebucket.bluebucket.Home.Fragments.DrinksFragment;
import com.unical.bluebucket.bluebucket.Login.LogInActivity;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.BottomNavViewHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 0;
    private static final int ERROR_DIALOG_REPORT = 9001;
    BottomNavViewHelper setUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String phone = preferences.getString("@string/SessionPhoneNumber","");

        Log.d(TAG, "onCreate: "+phone);

        if(phone.equals("")){

            Intent intent =new Intent(MainActivity.this,LogInActivity.class);
            startActivity(intent);

        }

        setupBottonNavigationView();
        setUpViewPager();

    }

    private void setupBottonNavigationView() {

        Log.d(TAG, "setupBottonNavigationView: Setting up Bottom Nav View");
        setUp = new BottomNavViewHelper();
        BottomNavigationViewEx bottom_nav_menus = findViewById(R.id.bottom_navigation_view_ex_bar);
        setUp.setupNav(bottom_nav_menus);
        setUp.enableNavigation(this, bottom_nav_menus);

        Menu menu = bottom_nav_menus.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }

    public  void setUpViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BarsFragment());
        adapter.addFrag(new DrinksFragment());
        ViewPager viewPager =  findViewById(R.id.container);
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUp.ACTIVITY_NUM = ACTIVITY_NUM;
    }
}
