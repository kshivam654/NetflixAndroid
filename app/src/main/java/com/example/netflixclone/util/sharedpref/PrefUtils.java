package com.example.netflixclone.util.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
    private static PrefUtils mPrefUtils;
    private Context mContext;
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mSharedPreferencesEditoer;

    public PrefUtils() {
    }

    public PrefUtils(Context mContext) {
        this.mContext = mContext;
        mSharedPreference = mContext.getSharedPreferences("MyPreference",Context.MODE_PRIVATE);
        mSharedPreferencesEditoer = mSharedPreference.edit();
    }

    public static synchronized PrefUtils getInstance(Context context) {
        if (mPrefUtils == null) {
            mPrefUtils = new PrefUtils(context.getApplicationContext());
        }
        return mPrefUtils;
    }

    public int getIntValue(String key, int defaultValue) {
        return mSharedPreference.getInt(key,defaultValue);
    }

    public String getStringValue(String key,String defaultValue) {
        return mSharedPreference.getString(key,defaultValue);
    }

    public boolean getBoolanValue(String keyFlag, boolean defaultValue) {
        return mSharedPreference.getBoolean(keyFlag,defaultValue);
    }

}
