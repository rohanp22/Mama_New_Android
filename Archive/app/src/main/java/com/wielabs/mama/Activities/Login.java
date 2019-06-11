package com.wielabs.mama.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.sinch.android.rtc.SinchError;
import com.wielabs.mama.BaseActivity;
import com.wielabs.mama.R;
import com.wielabs.mama.Signup;
import com.wielabs.mama.SinchService;

public class Login extends BaseActivity implements SinchService.StartFailedListener {

    private Button login;
    private TextView signup, forgotpassword;
    private Pinview mobno;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobno = (Pinview) findViewById(R.id.pinView);
        login = (Button) findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });

        mobno.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                userName = pinview.getValue();
            }
        });

        signup = (TextView) findViewById(R.id.txt_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });

        forgotpassword = (TextView) findViewById(R.id.txt_fp);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void loginClicked() {

        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a Mobile number", Toast.LENGTH_LONG).show();
            return;
        }

        if (!userName.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);
        } else {
            openHome();
        }
    }

    public void openHome(){
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }
}
