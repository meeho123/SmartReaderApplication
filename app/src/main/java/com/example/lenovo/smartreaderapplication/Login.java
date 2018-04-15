package com.example.lenovo.smartreaderapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Login extends AppCompatActivity {
Button btnLogin,btnSignup;
EditText uname,password;
AwesomeValidation awesomeValidation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updateUI();

        btnLogin = (Button) findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               if(awesomeValidation.validate())

            {
                Toast.makeText(Login.this, "Data Received  Successfully", Toast.LENGTH_SHORT);
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
            else

            {
                Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT);

            }

        }
            });
        btnSignup = (Button) findViewById(R.id.done);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);

            }
        });






    }


    private void updateUI(){
    uname = (EditText) findViewById(R.id.username);
    password=(EditText)findViewById(R.id.password);
        String validatePassword = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        String validateUsernme ="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
        awesomeValidation.addValidation(Login.this,R.id.username,validateUsernme,R.string.VUname);
        awesomeValidation.addValidation(Login.this,R.id.password,validatePassword,R.string.Vpassword);


    }


}