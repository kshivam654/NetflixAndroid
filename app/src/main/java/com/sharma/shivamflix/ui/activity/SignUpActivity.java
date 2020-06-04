package com.sharma.shivamflix.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.sharma.shivamflix.R;
import com.sharma.shivamflix.network.APIClient;
import com.sharma.shivamflix.network.APIInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.ed_email2)
    EditText edEmail;
    @BindView(R.id.layout_email)
    TextInputLayout layoutEmail;
    @BindView(R.id.ed_password2)
    EditText edPassword;
    @BindView(R.id.layout_password)
    TextInputLayout layputPassword;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);

    }

    @OnClick({R.id.submit_btn2, R.id.login_now, R.id.help2, R.id.privacy2})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.help2:
                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.submit_btn2:
//                if (validateFields()) {
//                    doLoginUser();
//                }
                Toast.makeText(this, "Submit button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login_now:
                Intent signUp = new Intent(this, LoginActivity.class);
                startActivity(signUp);
                finish();
                break;
            case R.id.privacy2:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com"));
                startActivity(intent);
                finish();
                break;


        }
    }

    @Override
    public void onClick(View v) {
    }

}