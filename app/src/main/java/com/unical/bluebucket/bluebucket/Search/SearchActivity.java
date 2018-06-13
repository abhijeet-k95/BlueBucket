package com.unical.bluebucket.bluebucket.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.unical.bluebucket.bluebucket.Home.MainActivity;
import com.unical.bluebucket.bluebucket.R;
import com.unical.bluebucket.bluebucket.Utils.BottomNavViewHelper;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupBottonNavigationView();
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
