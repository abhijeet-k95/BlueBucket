package com.unical.bluebucket.bluebucket.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.unical.bluebucket.bluebucket.Login.LogInActivity;

public  class Logout {
    private static final String TAG = "Logout";

    Context ctx;


    public Logout(Context ctx) {

        Log.d(TAG, "Logout: clearing sessions");
        this.ctx = ctx;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

        Intent intent =new Intent(ctx,LogInActivity.class);
        ctx.startActivity(intent);
    }
}
