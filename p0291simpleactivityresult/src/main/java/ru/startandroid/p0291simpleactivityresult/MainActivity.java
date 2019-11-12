package ru.startandroid.p0291simpleactivityresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvName;
    Button btnName;
    Button btnColor;
    Button btnAlign;
    Button btnWeb;
    Button btnMap;
    Button btnCall;

    final int REQUEST_CODE_NAME = 0;
    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGN = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tvName = findViewById(R.id.tvName);
        btnName = findViewById(R.id.btnName);
        btnColor = findViewById(R.id.btnColor);
        btnAlign = findViewById(R.id.btnAlign);
        btnWeb = findViewById(R.id.btnWeb);
        btnMap = findViewById(R.id.btnMap);
        btnCall = findViewById(R.id.btnCall);

        btnName.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnAlign.setOnClickListener(this);
        btnWeb.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnName:
                intent = new Intent(this, NameActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NAME);
                break;
            case R.id.btnColor:
                intent = new Intent(this, ColorActivity.class);
                startActivityForResult(intent, REQUEST_CODE_COLOR);
                break;
            case R.id.btnAlign:
                intent = new Intent(this, AlignActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ALIGN);
                break;
            case R.id.btnWeb:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com"));
                startActivity(intent);
                break;
            case R.id.btnMap:
                intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:55.754283,37.62002"));
                startActivity(intent);
                break;
            case R.id.btnCall:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:12345"));
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;}
        else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NAME:
                    String name = data.getStringExtra("name");
                    tvName.setText("Your name is:\n" + name);
                case REQUEST_CODE_COLOR:
                    int color = data.getIntExtra("color", tvName.getTextColors().getDefaultColor());
                    tvName.setTextColor(color);
                case REQUEST_CODE_ALIGN:
                    int align = data.getIntExtra("alignment", tvName.getGravity());
                    tvName.setGravity(align);
                    break;
            }
        }
        else {
            Toast.makeText(this, "Нет результата", Toast.LENGTH_SHORT).show();
        }
    }
}
