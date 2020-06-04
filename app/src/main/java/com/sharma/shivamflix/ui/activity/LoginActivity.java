package com.sharma.shivamflix.ui.activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sharma.shivamflix.R;
import com.sharma.shivamflix.network.APIClient;
import com.sharma.shivamflix.network.APIInterface;
import com.sharma.shivamflix.util.AppUtils;
import com.sharma.shivamflix.util.UiUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.layout_email)
    TextInputLayout layoutEmail;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.layout_password)
    TextInputLayout layoutPassword;

    APIInterface apiInterface;
    private String TAG = "Error";

    private static final String TAG2 = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        ButterKnife.bind(this);

        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @OnClick({R.id.submit_btn, R.id.forgot_pass, R.id.join_now, R.id.help, R.id.privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.help:
                String url = "http://www.google.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            case R.id.forgot_pass:
                Toast.makeText(this, "forgot button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.submit_btn:
//                if (validateFields()) {
//                    doLoginUser();
//                }
                Toast.makeText(this, "Submit button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.join_now:
                Toast.makeText(this, "sign in button pressed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.privacy:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com"));
                startActivity(intent);
                break;

        }

    }

    private void facebookSignIn() {
        //do something to do facebook signin
        LoginButton FBloginButton = findViewById(R.id.btn_facebook_sign);
        //Setting the permission that we need to read
        FBloginButton.setPermissions("public_profile","email", "user_birthday", "user_friends");
        //Registering callback!
        CallbackManager mCallbackManager = CallbackManager.Factory.create();
        FBloginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Sign in completed
                Log.i(TAG, "onSuccess: logged in successfully");

                //Getting the user information
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        Log.i(TAG, "onCompleted: response: " + response.toString());
                        try {
                            String email = object.getString("email");
                            String birthday = object.getString("birthday");

                            Log.i(TAG, "onCompleted: Email: " + email);
                            Log.i(TAG, "onCompleted: Birthday: " + birthday);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "onCompleted: JSON exception");
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
    }

    private void googleSignIn() {



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG2, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG2, "Google sign in failed", e);
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG2, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG2, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]



    protected void doLoginUser() {
        UiUtils.showLoadingDialog(this);
//        Call<String> call = apiInterface.login;
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
        if( v.getId() == R.id.btn_google_sign){
            signIn();
        }
        if (v.getId() == R.id.btn_facebook_sign){
            facebookSignIn();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
