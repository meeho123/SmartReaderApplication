package com.example.lenovo.smartreaderapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
Button button;
EditText username,Fname,Lname,Email,phone,password,confirmPassword;
AwesomeValidation awesomeValidation;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updateUI();

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (awesomeValidation.validate()) {
                Toast.makeText(Signup.this , "Data Received  Successfully" ,Toast.LENGTH_SHORT);
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(Signup.this,"Error", Toast.LENGTH_SHORT);

            }



        }
    });




    }
    private void updateUI()
    {
        Fname =(EditText) findViewById(R.id.FirstName);
        Lname =(EditText) findViewById(R.id.LastName);
        password =(EditText) findViewById(R.id.password);
        confirmPassword =(EditText) findViewById(R.id.confiemPassword);
        phone =(EditText) findViewById(R.id.phone);
        Email =(EditText) findViewById(R.id.email);
        username =(EditText) findViewById(R.id.username);
        button = (Button) findViewById(R.id.done);
        String hello ="HELLO";


        String regexPassword = "(?=.*[a-z])(?=.*[A-Z](?=.*[\\d)(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?])";
        String regexUsernme ="(?=.*[a-z])(?=.*[A-Z](?=.*[\\d)(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?])";
        awesomeValidation.addValidation(Signup.this,R.id.FirstName,"[a-zA-Z\\s]+",R.string.Vfname);
        awesomeValidation.addValidation(Signup.this,R.id.LastName,"[a-zA-Z\\s]+",R.string.Vlname);
        awesomeValidation.addValidation(Signup.this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.Vemail);
        awesomeValidation.addValidation(Signup.this,R.id.phone, RegexTemplate.TELEPHONE,R.string.Vphone);
        awesomeValidation.addValidation(Signup.this,R.id.password,regexPassword,R.string.Vpassword);
        awesomeValidation.addValidation(Signup.this,R.id.confiemPassword,regexPassword,R.string.VCpassword);
        awesomeValidation.addValidation(Signup.this,R.id.username,regexUsernme,R.string.VUname);






    }}
