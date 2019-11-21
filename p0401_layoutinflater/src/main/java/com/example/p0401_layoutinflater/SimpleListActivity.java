package com.example.p0401_layoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleListActivity extends AppCompatActivity {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simplelist);

        // находим список
        ListView lvMain = findViewById(R.id.lvMain);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.my_list_item, names);
 //               android.R.layout.simple_list_item_1, names); // - Использование системного layout-ресурса

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
    }
}
