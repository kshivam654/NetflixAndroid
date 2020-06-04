package com.sharma.shivamflix.util;

import android.text.TextUtils;
import android.util.Patterns;

public class AppUtils {


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
