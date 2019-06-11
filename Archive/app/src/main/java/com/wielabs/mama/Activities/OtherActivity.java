package com.wielabs.mama.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wielabs.mama.R;

public class OtherActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    String[] listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.array_technology);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value=adapter.getItem(position);
                switch (value){
                    case "Profile" : startActivity(new Intent(OtherActivity.this, Profile.class));
                    break;
                    case "Contact us" : startActivity(new Intent(OtherActivity.this, ContactUs.class));
                        break;
                    case "Help" : startActivity(new Intent(OtherActivity.this, Help.class));
                        break;
                    case "How it works" : startActivity(new Intent(OtherActivity.this, HowItWorks.class));
                        break;
                    case "Feedback" : startActivity(new Intent(OtherActivity.this, Feedback.class));
                        break;
                }
            }
        });

        findViewById(R.id.back_other).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
