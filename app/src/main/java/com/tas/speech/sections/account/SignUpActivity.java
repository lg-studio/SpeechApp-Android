package com.tas.speech.sections.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tas.speech.R;
import com.tas.speech.SpeechApp;
import com.tas.speech.client.ApiTypes;
import com.tas.speech.form.Form;
import com.tas.speech.form.FormElementModel;
import com.tas.speech.form.elements.FormElement;
import com.tas.speech.sections.account.client.AccountApi;
import com.tas.speech.sections.account.models.AccountData;
import com.tas.speech.sections.account.models.SingUpResponse;
import com.tas.speech.utils.ImageFilePath;
import com.tas.speech.utils.ScalingUtilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private static final int SELECT_PICTURE = 1;

    private final String KEY_FIRST_NAME = "FirstName";
    private final String KEY_LAST_NAME = "LastName";
    private final String KEY_YEAR_BORN = "YearBorn";
    private final String KEY_GENDER = "Gender";
    private final String KEY_COUNTRY = "HomeCountry";
    private final String KEY_LANGUAGE = "NativeLanguage";
    private final String KEY_EMAIL = "Email";
    private final String KEY_PASSWORD = "Password";
    private final String KEY_CONFIRM_PASSWORD = "ConfirmPassword";

    private ProgressBar pbLoading;
    private ScrollView svFormContainer;
    private LinearLayout llFormContainer;
    private Button btnCreateAccount;
    private TextView tvTitle;
    private ImageView ivProfileAdd, ivProfileDefault;

    String mCurrentPhotoPath;

    private Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mCurrentPhotoPath = "";

        pbLoading = (ProgressBar)findViewById(R.id.pb_loading);

        svFormContainer = (ScrollView)findViewById(R.id.sv_form_container);
        svFormContainer.setSmoothScrollingEnabled(true);
        llFormContainer = (LinearLayout)findViewById(R.id.ll_form_container);

        btnCreateAccount = (Button)findViewById(R.id.btn_create_account);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validForm = form.triggerValidations();
                Log.d(TAG, "Valid form: " + validForm);
                if(!validForm) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Scroll to: " + ((TextView) ((ViewGroup) form.firstInvalidElement).getChildAt(0)).getText());
                            svFormContainer.smoothScrollTo(0, form.firstInvalidElement.getTop());
                        }
                    });
                }
                else {
                    MultipartTypedOutput signUpData = new MultipartTypedOutput();
                    ArrayList<TypedFile> typedFiles = new ArrayList<>();

                    if(mCurrentPhotoPath != null && !mCurrentPhotoPath.equals("")) {
                        typedFiles.add(new TypedFile("multipart/form-data", new File(mCurrentPhotoPath)));
                    }

                    // Convert AccountData into JSON using Gson library
                    AccountData accountDataPojo = getAccountData();
                    Gson gson = new Gson();
                    String accountDataJson = gson.toJson(accountDataPojo);
                    TypedString accountData = new TypedString(accountDataJson);
                    signUpData.addPart("AccountData", accountData);

                    for(TypedFile typedFile : typedFiles) {
                        signUpData.addPart("Files", typedFile);
                    }

                    Callback<SingUpResponse> signUpCallback = new Callback<SingUpResponse>() {
                        @Override
                        public void success(SingUpResponse singUpResponse, Response response) {
                            hideProgress();

                            Log.d(TAG, "callback Success");
                            Log.d(TAG, "callback SignUpResponse status code: " + singUpResponse.getStatusCode());
                            Log.d(TAG, "callback SignUpResponse message: " + singUpResponse.getMessage());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            hideProgress();
                            Log.d(TAG, "callback Error: " + error.getMessage());
                        }
                    };

                    showProgress();
                    ((AccountApi) SpeechApp.getApiOfType(ApiTypes.ACCOUNT_API)).signUp(signUpData, signUpCallback);
                }
            }
        });

        tvTitle = (TextView)findViewById(R.id.toolbar_title);
        tvTitle.setText(getString(R.string.sign_up_title));

        ivProfileDefault = (ImageView)findViewById(R.id.iv_profile_default);
        ivProfileAdd = (ImageView)findViewById(R.id.iv_profile_add);
        ivProfileAdd.setAlpha(90);
        ivProfileAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent();
                pickIntent.setType("image/*");
                pickIntent.setAction(Intent.ACTION_GET_CONTENT);

                Intent takePhotoIntent = createTakePictureIntent();
                if(takePhotoIntent != null) {
                    String pickTitle = "Select or take a new Picture"; // Or get from strings.xml
                    Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
                    chooserIntent.putExtra
                            (
                                    Intent.EXTRA_INITIAL_INTENTS,
                                    new Intent[]{takePhotoIntent}
                            );
                    startActivityForResult(chooserIntent, 1);
                }
            }
        });

        FormElementModel firstNameModel = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.SIMPLE, new FormElement.Validation[]{FormElement.Validation.MANDATORY},
                new FormElement.Property[]{FormElement.Property.START_WITH_CAP},
                R.string.first_name_label, R.string.first_name_hint, KEY_FIRST_NAME);

        FormElementModel lastNameModel = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.SIMPLE,new FormElement.Validation[]{FormElement.Validation.MANDATORY},
                new FormElement.Property[]{FormElement.Property.START_WITH_CAP},
                R.string.last_name_label, R.string.last_name_hint, KEY_LAST_NAME);

        String []yearsArr = getYearsArray();
        FormElementModel yearBorn = new FormElementModel(FormElement.DataType.NUMBER,
                FormElement.InputType.LOV, null, new FormElement.Property[]{}, yearsArr, -1,
                R.string.year_born_label, R.string.year_born_hint, KEY_YEAR_BORN, R.string.year_select_title);

        String []genderArr =  getResources().getStringArray(R.array.gender_array);
        FormElementModel gender = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.LOV, null, new FormElement.Property[]{}, genderArr, -1,
                R.string.gender_label, R.string.gender_hint, KEY_GENDER, R.string.gender_select_title);


        HashSet<String> countries = getCountries();
        String []countriesArr = countries.toArray(new String[countries.size()]);
        Arrays.sort(countriesArr);
        FormElementModel country = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.LOV, null, new FormElement.Property[]{}, countriesArr, -1,
                R.string.country_label, R.string.country_hint, KEY_COUNTRY, R.string.country_select_title);

        HashSet<String> languages = getLanguages();
        String []languagesArr = languages.toArray(new String[languages.size()]);
        Arrays.sort(languagesArr);
        FormElementModel language = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.LOV, null, new FormElement.Property[]{}, languagesArr, -1,
                R.string.language_label, R.string.language_hint, KEY_LANGUAGE, R.string.language_select_title);

        FormElementModel email = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.SIMPLE, new FormElement.Validation[]{FormElement.Validation.MANDATORY,
                FormElement.Validation.EMAIL}, new FormElement.Property[]{},
                R.string.email_label, R.string.email_hint, KEY_EMAIL);

        FormElementModel password = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.PASSWORD, new FormElement.Validation[]{FormElement.Validation.MANDATORY},
                new FormElement.Property[]{},
                R.string.password_label, R.string.password_hint, KEY_PASSWORD, KEY_CONFIRM_PASSWORD);

        FormElementModel confirmPassword = new FormElementModel(FormElement.DataType.TEXT,
                FormElement.InputType.PASSWORD, new FormElement.Validation[]{FormElement.Validation.MANDATORY},
                new FormElement.Property[]{},
                R.string.confirm_password_label, R.string.confirm_password_hint, KEY_CONFIRM_PASSWORD, KEY_PASSWORD);

        form = new Form(this, llFormContainer, new FormElementModel[]{confirmPassword, password,
                email, language, country, gender, yearBorn, lastNameModel, firstNameModel});
        form.render();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            // New photo taken
            if(data.getData() == null) {

            }
            // ImageProcessor chosen from the gallery
            else {
                mCurrentPhotoPath = ImageFilePath.getPath(getApplicationContext(), data.getData());
            }

            String path = ScalingUtilities.decodeFile(mCurrentPhotoPath,
                    ivProfileDefault.getLayoutParams().width, ivProfileDefault.getLayoutParams().height);

            Bitmap photo = BitmapFactory.decodeFile(path);

            ivProfileDefault.setImageBitmap(photo);

            mCurrentPhotoPath = path;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private Intent createTakePictureIntent() {
        Log.d(TAG, "createTakePictureIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                return takePictureIntent;
            }
        }

        return null;
    }

    public HashSet<String> getLanguages() {
        HashSet<String> languages = new HashSet<>();
        Locale locales[] = Locale.getAvailableLocales();

        for(Locale locale : locales) {
            if(locale != null) {
                languages.add(locale.getDisplayLanguage());
            }
        }

        return languages;
    }

    public HashSet<String> getCountries() {
        HashSet<String> countries = new HashSet<>();
        Locale locales[] = Locale.getAvailableLocales();

        for(Locale locale : locales) {
            if(locale != null) {
                String country = locale.getDisplayCountry();
                if(country != null && !country.trim().equals("")) {
                    countries.add(country);
                }
            }
        }

        return countries;
    }

    public String[] getYearsArray() {
        ArrayList<String> years = new ArrayList<>();

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int firstYear = year - 100;
        for(int i = firstYear; i <= year;  ++i) {
            years.add(String.valueOf(i));
        }
        String []yearsArr = years.toArray(new String[years.size()]);
        return yearsArr;

    }

    private AccountData getAccountData() {
        LinkedHashMap<String, FormElement> formElementsMap = form.getFormElementsMap();
        AccountData accountData = new AccountData();

        String yearBornStr = formElementsMap.get(KEY_YEAR_BORN).getValue();
        if(yearBornStr != null && !yearBornStr.equals("")) {
            accountData.setBornYear(Integer.parseInt(yearBornStr));
        }

        String email = formElementsMap.get(KEY_EMAIL).getValue();
        if(email != null && !email.equals("")) {
            accountData.setEmail(email);
        }

        String firstName = formElementsMap.get(KEY_FIRST_NAME).getValue();
        if(firstName != null && !firstName.equals("")) {
            accountData.setFirstName(firstName);
        }

        String lastName = formElementsMap.get(KEY_LAST_NAME).getValue();
        if(lastName != null && !lastName.equals("")) {
            accountData.setLastName(lastName);
        }

        String gender = formElementsMap.get(KEY_GENDER).getValue();
        if(gender != null && !gender.equals("")) {
            accountData.setGender(gender);
        }

        String homeCountry = formElementsMap.get(KEY_COUNTRY).getValue();
        if(homeCountry != null && !homeCountry.equals("")) {
            accountData.setHomeCountry(homeCountry);
        }

        String language = formElementsMap.get(KEY_LANGUAGE).getValue();
        if(language != null && !language.equals("")) {
            accountData.setNativeLanguage(language);
        }

        String password = formElementsMap.get(KEY_PASSWORD).getValue();
        if(password != null && !password.equals("")) {
            accountData.setPassword(password);
        }

        return accountData;
    }

    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
    }
}
