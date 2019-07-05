package com.example.netflixclone.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.netflixclone.R;
import com.example.netflixclone.network.APIClient;
import com.example.netflixclone.network.APIInterface;
import com.example.netflixclone.util.AppUtils;
import com.example.netflixclone.util.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.layout_email)
    TextInputLayout layoutEmail;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.layout_password)
    TextInputLayout layputPassword;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @OnClick({R.id.submit_btn, R.id.forgot_pass, R.id.join_now, R.id.help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_google_sign:
                break;
            case R.id.btn_facebook:
                break;
            case R.id.submit_btn:
                if (validateFields()) {
                    doLoginUser();
                }
                break;
        }
    }

    protected void doLoginUser() {
        UiUtils.showLoadingDialog(this);
        Call<String> call = apiInterface.login
    }

    private boolean validateFields() {
        if (edEmail.getText().toString().trim().length() == 0) {
            UiUtils.showShortToast(this, "Email can't be empty");
            return false;
        }
        if (edPassword.getText().toString().trim().length() == 0) {
            UiUtils.showShortToast(this, "Password can't be empty");
            return false;
        }
        if (!AppUtils.isValidEmail(edEmail.getText().toString())) {
            UiUtils.showShortToast(this, "Please enter a valid email");
            return false;
        }
        if (edPassword.getText().toString().length() < 6) {
            UiUtils.showShortToast(this, "Password must be minimum six characters");
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
