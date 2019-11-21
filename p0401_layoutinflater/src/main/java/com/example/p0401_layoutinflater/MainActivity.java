package com.example.p0401_layoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    Button btnList, btnSimpleList, btnSimpleListChoice, btnSimpleListEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LayoutInflater ltInfalter = getLayoutInflater();

        LinearLayout linLayout = findViewById(R.id.linLayout);
        View view1 = ltInfalter.inflate(R.layout.text, linLayout, true);
        LayoutParams lp1 = view1.getLayoutParams();

        Log.d(LOG_TAG, "Class of view1: " + view1.getClass().toString());
        Log.d(LOG_TAG, "Class of layoutParams of view1: " + lp1.getClass().toString());
        //Log.d(LOG_TAG, "Text of view1: " + ((TextView) view1).getText());

        //linLayout.addView(view);

//        RelativeLayout  relLayout = findViewById(R.id.relLayout);
//        View view2 = ltInfalter.inflate(R.layout.text, relLayout, true);
//        LayoutParams lp2 = view2.getLayoutParams();
//
//        Log.d(LOG_TAG, "Class of view2: " + view2.getClass().toString());
//        Log.d(LOG_TAG, "Class of layoutParams of view2: " + lp2.getClass().toString());
//        //Log.d(LOG_TAG, "Text of view2: " + ((TextView) view2).getText());

        btnList = findViewById(R.id.btnList);
        btnList.setOnClickListener(this);

        btnSimpleList = findViewById(R.id.btnSimpleList);
        btnSimpleList.setOnClickListener(this);

        btnSimpleListChoice = findViewById(R.id.btnSimpleListChoice);
        btnSimpleListChoice.setOnClickListener(this);

        btnSimpleListEvents = findViewById(R.id.btnSimpleListEvents);
        btnSimpleListEvents.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            case R.id.btnList:
                intent = new Intent(this, LayoutInflaterListActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSimpleList:
                intent = new Intent(this, SimpleListActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSimpleListChoice:
                intent = new Intent(this, SimpleListChoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSimpleListEvents:
                intent = new Intent(this, SimpleListEventsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
