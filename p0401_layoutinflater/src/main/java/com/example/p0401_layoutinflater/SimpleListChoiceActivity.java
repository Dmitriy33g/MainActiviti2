package com.example.p0401_layoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SimpleListChoiceActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    ListView lvMain;
    String[] names;
    Button btnChecked, btnCheckedMultiple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simplelistchoice);

        lvMain = findViewById(R.id.lvMain);
        // устанавливаем режим выбора пунктов списка
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // Создаем адаптер, используя массив из файла ресурсов
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.names, android.R.layout.simple_list_item_single_choice);
        lvMain.setAdapter(adapter);

        btnChecked = findViewById(R.id.btnChecked);
        btnChecked.setOnClickListener(this);

        btnCheckedMultiple = findViewById(R.id.btnCheckedMultiple);
        btnCheckedMultiple.setOnClickListener(this);

        // получаем массив из файла ресурсов
        names = getResources().getStringArray(R.array.names);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnChecked:
                // пишем в лог выделенный элемент
                int itemPos = lvMain.getCheckedItemPosition();

                Log.d(LOG_TAG, "getCheckedItemPosition : " + itemPos);
                Log.d(LOG_TAG, "getCheckedItemCount : " + lvMain.getCheckedItemCount());

                if (itemPos > -1) {
                    Log.d(LOG_TAG, "checked: " + names[itemPos]);
                }
                break;
            case R.id.btnCheckedMultiple:
                lvMain = findViewById(R.id.lvMain);
                // устанавливаем режим выбора пунктов списка
                lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                // Создаем адаптер, используя массив из файла ресурсов
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.names, android.R.layout.simple_list_item_multiple_choice);
                lvMain.setAdapter(adapter);
         }
    }
}
