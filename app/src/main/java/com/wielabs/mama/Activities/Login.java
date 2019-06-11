package com.wielabs.mama.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.goodiebag.pinview.Pinview;
import com.sinch.android.rtc.SinchError;
import com.wielabs.mama.BaseActivity;
import com.wielabs.mama.R;
import com.wielabs.mama.SharedPrefManager;
import com.wielabs.mama.Signup;
import com.wielabs.mama.SinchService;

public class Login extends BaseActivity implements SinchService.StartFailedListener {

    private Button login;
    private TextView signup, forgotpassword;
    private Pinview mobno;
    private String userName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Home.class));
            return;
        }

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

        if (userName.equals("")) {
            Toast.makeText(this, "Please enter a Mobile number", Toast.LENGTH_LONG).show();
            return;
        }

        if (!userName.equals(getSinchServiceInterface().getUserName())) {
            getSinchServiceInterface().stopClient();
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient(userName);
            openHome();
        }
    }

    public void openHome(){
        //SharedPrefManager.getInstance(Login.this).userLogin(new User(userName,"email","M"));
        startActivity(new Intent(this, Home.class));
    }

    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }

//    private void userLogin() {
//        //first getting the values
//        final String username = editTextUsername.getText().toString();
//        final String password = editTextPassword.getText().toString();
//
//        //validating inputs
//        if (TextUtils.isEmpty(username)) {
//            editTextUsername.setError("Please enter your username");
//            editTextUsername.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            editTextPassword.setError("Please enter your password");
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        //if everything is fine
//
//        class UserLogin extends AsyncTask<Void, Void, String> {
//
//            ProgressBar progressBar;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);
//
//
//                try {
//                    //converting response to json object
//                    JSONObject obj = new JSONObject(s);
//
//                    //if no error in response
//                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                        //getting the user from the response
//                        JSONObject userJson = obj.getJSONObject("user");
//
//                        //creating a new user object
//                        User user = new User(
//                                userJson.getString("username"),
//                                userJson.getString("email"),
//                                userJson.getString("gender")
//                        );
//
//                        //storing the user in shared preferences
//                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                        //starting the profile activity
//                        finish();
//                        startActivity(new Intent(getApplicationContext(), Home.class));
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("password", password);
//
//                //returing the response
//                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
//            }
//        }
//
//        UserLogin ul = new UserLogin();
//        ul.execute();
//    }
}
