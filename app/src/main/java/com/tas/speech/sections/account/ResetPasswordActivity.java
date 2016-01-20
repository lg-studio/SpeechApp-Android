package com.tas.speech.sections.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.SpeechApp;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.form.Form;
import com.tas.speech.form.FormElementModel;
import com.tas.speech.form.elements.FormElement;
import com.tas.speech.sections.account.client.AccountApi;
import com.tas.speech.sections.account.models.ResetPasswordResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedString;

public class ResetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ResetPasswordActivity";

    private final String KEY_EMAIL = "Email";

    private ProgressBar pbLoading;
    private LinearLayout llFormContainer;
    private Button btnResetPassword;
    private TextView tvTitle;

    private Form resetPasswordForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        tvTitle = (TextView)findViewById(R.id.toolbar_title);
        tvTitle.setText(getString(R.string.reset_password_title));

        pbLoading = (ProgressBar)findViewById(R.id.pb_loading);

        llFormContainer = (LinearLayout)findViewById(R.id.ll_form_container);
        btnResetPassword = (Button)findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validForm = resetPasswordForm.triggerValidations();
                Log.d(TAG, "Valid form: " + validForm);
                if(!validForm) {

                }
                else {
                    Callback<ResetPasswordResponse> resetPasswordCallback = new Callback<ResetPasswordResponse>() {
                        @Override
                        public void success(ResetPasswordResponse resetPasswordResponse, Response response) {
                            hideProgress();

                            Log.d(TAG, "callback Success");
                            Log.d(TAG, "callback resetPasswordResponse status code: " + resetPasswordResponse.getStatusCode());
                            Log.d(TAG, "callback resetPasswordResponse message: " + resetPasswordResponse.getMessage());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            hideProgress();
                            Log.d(TAG, "callback Error: " + error.getMessage());
                        }
                    };

                    MultipartTypedOutput resetPasswordData = new MultipartTypedOutput();
                    resetPasswordData.addPart("Email",
                            new TypedString(resetPasswordForm.getFormElementsMap().get(KEY_EMAIL).getValue()));

                    showProgress();
                    ((AccountApi) SpeechApp.getApiOfType(ApiTypes.ACCOUNT_API)).passwordReset(resetPasswordData,
                            resetPasswordCallback);
                }
            }
        });

        FormElementModel email = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.SIMPLE, new FormElement.Validation[]{FormElement.Validation.MANDATORY,
                FormElement.Validation.EMAIL}, new FormElement.Property[]{},
                R.string.email_label, R.string.email_hint, KEY_EMAIL);

        resetPasswordForm = new Form(this, llFormContainer, new FormElementModel[]{email});
        resetPasswordForm.render();
    }

    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }
}
