package com.wielabs.mama;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.wielabs.mama.Activities.CallScreenActivity;
import com.wielabs.mama.Activities.Home;

import java.util.HashMap;

import androidx.core.app.ActivityCompat;

public class  PlaceCallActivity extends BaseActivity implements SinchService.StartFailedListener{

    private Button mCallButton;
    private EditText query;
    private AutoCompleteTextView actv;
    private String[] keywords ={"Cooking","Programming","Cricket","Badminton","Studying"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.back_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

        query = findViewById(R.id.query);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,keywords);
        //Getting the instance of AutoCompleteTextView
//        actv =  (AutoCompleteTextView)findViewById(R.id.keyword);
//        actv.setThreshold(1);//will start working from first character
//        actv.setAdapter(adapter);
//        actv.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {}
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if(actv.getText().toString().equals("asdf"))
//                    mCallButton.setEnabled(false);
//                else
//                    mCallButton.setEnabled(true);
//            }
//        });
        mCallButton = (Button) findViewById(R.id.callButton);
        mCallButton.setEnabled(false);
        mCallButton.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onServiceConnected() {
        getSinchServiceInterface().setStartListener(this);
//        if(!getSinchServiceInterface().isStarted())
//        getSinchServiceInterface().startClient(getSinchServiceInterface().getUserName());
        mCallButton.setEnabled(true);
    }

    private void stopButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().stopClient();
        }
        finish();
    }

    private void callButtonClicked() {

        String key="";
        int i;

        RequestHandler requestHandler = new RequestHandler();

        for(i = 0 ; i < keywords.length ; i++){
            if(query.getText().toString().contains(keywords[i])) {
                key = keywords[i];
                break;
            }
        }
        if(i == keywords.length) {
            Toast.makeText(PlaceCallActivity.this, "Cannot process request", Toast.LENGTH_SHORT);
            return;
        }

        //creating request parameters
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", key);

        //returing the response
        String uname = requestHandler.sendPostRequest(URLs.URL_REGISTER, params);

        String userName = "1234";
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Call call = getSinchServiceInterface().callUser(userName);
            if (call == null) {
                // Service failed for some reason, show a Toast and abort
                Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before "
                        + "placing a call.", Toast.LENGTH_LONG).show();
                return;
            }
            String callId = call.getCallId();
            Intent callScreen = new Intent(this, CallScreenActivity.class);
            callScreen.putExtra(SinchService.CALL_ID, callId);
            startActivity(callScreen);
        } catch (MissingPermissionException e) {
            ActivityCompat.requestPermissions(this, new String[]{e.getRequiredPermission()}, 0);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You may now place a call", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "This application needs permission to use your microphone to function properly.", Toast
                    .LENGTH_LONG).show();
        }
    }

    private OnClickListener buttonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.callButton:
                    callButtonClicked();
                    break;
            }
        }
    };

    @Override
    public void onStartFailed(SinchError error) {

    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onBackPressed() {

    }
}
