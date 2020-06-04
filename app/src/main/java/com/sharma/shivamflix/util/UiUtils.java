package com.sharma.shivamflix.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

public class UiUtils {

    private static Dialog loadingDialog;

    public static void showShortToast(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLoadingDialog(Context context) {
        hideLoadingDialog();
        loadingDialog = new Dialog(context);
        loadingDialog.setCancelable(false);
//        loadingDialog.setContentView(R.layout.api);
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    private static void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing())
            try {
                loadingDialog.dismiss();
            }catch (Exception e) {
                e.printStackTrace();
            }
    }
}
