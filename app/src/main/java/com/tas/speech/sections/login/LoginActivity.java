package com.tas.speech.sections.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tas.speech.client.ApiTypes;
import com.tas.speech.SpeechApp;
import com.tas.speech.dialogs.TransparentProgressDialog;
import com.tas.speech.sections.account.ResetPasswordActivity;
import com.tas.speech.sections.login.client.LoginApi;
import com.tas.speech.sections.main.MainActivity;
import com.tas.speech.R;
import com.tas.speech.sections.login.models.LoginResponse;
import com.tas.speech.sections.account.SignUpActivity;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

// The Login Activity - username, password -> server authentication
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    public static final String ARG_USER_INFO = "USER_INFO";

    private Button btnLogin, btnSignup, btnResetPassword;
    private Toolbar toolbar;
    private TextView tvTitle;
    private EditText etUsername, etPassword;
    private TransparentProgressDialog pdLoading;

    private Context ctx;
    private Callback<LoginResponse> loginCallback;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar_login); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tvTitle = (TextView)toolbar.findViewById(R.id.toolbar_title);
        tvTitle.setText(getResources().getText(R.string.login_title));

        etUsername = (EditText)findViewById(R.id.et_username);
        etPassword = (EditText)findViewById(R.id.et_password);

        pdLoading = new TransparentProgressDialog(this, R.drawable.ic_spinner);

        btnSignup = (Button)findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(ctx, SignUpActivity.class);
                startActivity(signupIntent);
            }
        });

        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginApi loginApi = (LoginApi) SpeechApp.getApiOfType(ApiTypes.LOGIN_API);
                pdLoading.show();
                loginApi.login("Basic", etUsername.getText().toString(), etPassword.getText().toString(), "", loginCallback);
            }
        });

        btnResetPassword = (Button)findViewById(R.id.btn_reset_password);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPasswordIntent = new Intent(ctx, ResetPasswordActivity.class);
                startActivity(resetPasswordIntent);
            }
        });

        // login callback method - if ok, go to next screen
        loginCallback = new Callback<LoginResponse>() {

            @Override
            public void success(LoginResponse loginResponse, Response response) {

                List<Header> headers = response.getHeaders();
                for(Header header : headers) {
                    if(header.getName() != null && header.getName().toLowerCase().equals("set-cookie")) {
                        Log.d(TAG, "Set-Cookie=" + header.getValue());
                        String setCookie = header.getValue();
                        String [] cookies = setCookie.split(";");
                        for(String cookie : cookies) {
                            String[] keyValue = cookie.split("=");
                            if(keyValue.length == 2 && keyValue[0].equals(getString(R.string.auth_cookie_name))) {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
                                preferences.edit().putString(ctx.getString(R.string.auth_cookie), keyValue[1]).commit();
                            }
                        }
                    }

                }

                if(loginResponse.getStatusCode() != null) {
                    int statusCode = Integer.parseInt(loginResponse.getStatusCode());
                    if(statusCode == 0) {
                        Intent certificatesIntent = new Intent(ctx, MainActivity.class);
                        certificatesIntent.putExtra(ARG_USER_INFO, loginResponse);
                        etUsername.setText("");
                        etPassword.setText("");
                        etUsername.requestFocus();
                        startActivity(certificatesIntent);
                    }
                    else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                        // set title
                        alertDialogBuilder.setTitle("Authentication Error");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Invalid username or password. Please, try again.")
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                });
                        alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }

                pdLoading.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                pdLoading.dismiss();
                Log.d(TAG, "ERROR");

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
                // set title
                alertDialogBuilder.setTitle("Server Error");

                // set dialog message
                alertDialogBuilder
                        .setMessage(error.getMessage())
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        };
    }



}
