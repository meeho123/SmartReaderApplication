package com.example.lenovo.smartreaderapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A login screen that offers login via email/password.
 */
@SuppressLint("Registered")
public class RegisterActivity extends BaseActivity implements AsyncResponse{



    // Message for MainActivity
    //public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    // Auth related stuff
    private static final String AUTH_TOKEN_URL = "http://192.168.1.10:8000/uusapp/register/";
    private static final String SUCCESS_MESSAGE =  "Successful result";
    private static final String FAILURES_MESSAGE =  "Something went wrong please try later";
    AwesomeValidation awesomeValidation;
    private UserRegisterTask mAuthTask = null;
    EditText username,Fname,Lname,Email,phone,password,confirmPassword;

    // UI references.
    private static final String L_TAG = Login.class.getSimpleName();
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etFirstname;
    private EditText etLastname;
    private EditText etPhoneNo;
    private EditText etconfirmedPassword;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvTest;
    private Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Set up the login form.
        etUsername = (EditText) findViewById(R.id.username);
        etPassword = (EditText) findViewById(R.id.password);
        etEmail = (EditText) findViewById(R.id.email);
        etconfirmedPassword = (EditText) findViewById(R.id.confiemPassword);
        etFirstname = (EditText) findViewById(R.id.FirstName);
        etLastname = (EditText) findViewById(R.id.LastName);
        etPhoneNo=(EditText)findViewById(R.id.phone);
        etconfirmedPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    startRegister();
                    return true;
                }
                return false;
            }
        });

        bRegister = (Button) findViewById(R.id.done);
        bRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });


        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvTest = (TextView) findViewById(R.id.returned_token);

    }



    private void startRegister() {
        // If user is not very patient
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        etUsername.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String firstname = etFirstname.getText().toString();
        String lastname = etLastname.getText().toString();
        String confirmpass = etconfirmedPassword.getText().toString();
        String phoneNo = etPhoneNo.getText().toString();




        boolean cancel = false;
        View focusView1 = null;
        View focusView2 = null;
        View focusView3 = null;
        View focusView4 = null;
        View focusView5 = null;
        View focusView6 = null;
        View focusView7 = null;
        View focusView8 = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
             focusView1 = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            etUsername.setError(getString(R.string.error_field_required));
             focusView2 = etUsername;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
             focusView3 = etEmail;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
             focusView4 = etPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(confirmpass)) {
            etconfirmedPassword.setError(getString(R.string.error_field_required));
             focusView5 = etconfirmedPassword;
            cancel = true;
        }
        if (TextUtils.isEmpty(firstname)) {
            etFirstname.setError(getString(R.string.error_field_required));
             focusView6 = etFirstname;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastname)) {
            etLastname.setError(getString(R.string.error_field_required));
             focusView7 = etLastname;
            cancel = true;
        }
        if (TextUtils.isEmpty(phoneNo)) {
            etPhoneNo.setError(getString(R.string.error_field_required));
             focusView8 = etPhoneNo;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if(focusView1!=null)      focusView1.requestFocus();
            if(focusView2!=null) focusView2.requestFocus();
            if(focusView3!=null) focusView3.requestFocus();
            if(focusView4!=null) focusView4.requestFocus();
            if(focusView5!=null) focusView5.requestFocus();
            if(focusView6!=null) focusView6.requestFocus();
            if(focusView7!=null) focusView7.requestFocus();
            if(focusView8!=null) focusView8.requestFocus();

        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserRegisterTask(username, password,email,firstname,lastname ,this);
            mAuthTask.execute((Void) null);
        }
    }

    @Override
    public void processFinish(String response) {
        if (response == SUCCESS_MESSAGE) {

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }else {
            tvTest.setText(FAILURES_MESSAGE);
        }
    }
    private boolean isPasswordValid(String password) {
       return password.length()>4;
    }



    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    @SuppressLint("StaticFieldLeak")
    public class UserRegisterTask extends AsyncTask<Void, Void, String> {

        private final String sUserName;
        private final String sPassWord;
        private final String email ;
        private final String firstname;
        private final String lastname ;
        private Boolean success = false;
        public AsyncResponse delegate = null;



        UserRegisterTask(String sUserName, String sPassword, String email,
                         String firstname, String lastname,AsyncResponse delegate) {
            this.sUserName = sUserName;
            this.sPassWord = sPassword;
            this.email=email;
            this.firstname = firstname;
            this.lastname = lastname;
            this.delegate = delegate;
        }


        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            try {
                return getToken(this.sUserName, this.sPassWord);

            } catch (Exception e) {
                return "Caught some freaking exception";
            }
        }

        protected String getToken(String username, String password) {
            JSONfunctions parser = new JSONfunctions();
            JSONObject login = parser.getLoginObject(username, password);
            String message = login.toString();
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;
            try {
                URL url = new URL(AUTH_TOKEN_URL); //AUTH_TOKEN_URL=http://192.168.1.10:8000/uusapp/register/
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d(L_TAG, "url.openConnection");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Log.d(L_TAG, "Set up data unrelated headers");
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //header crap
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                // conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //Setup sen
                OutputStream os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //
                os.flush();
                os.close();
                //connect
                conn.connect();


                Log.d(L_TAG, "data is sent");


                // do something with response
                is = conn.getInputStream();
                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
                if (is != null) {
                    is.close();
                }
                String serverResponseMessage = conn.getResponseMessage();
                System.out.println("---------------------"+serverResponseMessage+"-------------------------------");
                int serverResponseCode =  conn.getResponseCode();
                if(serverResponseCode == 201){
                    this.success = true;
                }else {
                    Log.d(L_TAG, serverResponseMessage + " " + serverResponseCode);
                }

                Log.d(L_TAG, contentAsString);
                return contentAsString;

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(L_TAG,"Exeption");
                return "";}


        }




        public String readIt(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }




        @Override
        protected void onPostExecute( String response )
        {
            mAuthTask = null;
            showProgress(false);
            if (this.success) {
                delegate.processFinish(SUCCESS_MESSAGE);
            } else {
                Log.d(L_TAG, response);
                delegate.processFinish(FAILURES_MESSAGE);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

