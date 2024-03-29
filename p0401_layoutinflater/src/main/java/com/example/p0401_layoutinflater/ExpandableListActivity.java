package com.example.p0401_layoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpandableListActivity extends AppCompatActivity {

    // названия компаний (групп)
    String[] groups = new String[] {"HTC", "Samsung", "LG"};

    // названия телефонов (элементов)
    String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    // коллекция для групп
    ArrayList<Map<String, String>> groupData;

    // коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список атрибутов группы или элемента
    Map<String, String> m;

    ExpandableListView elvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandablelist);

        // заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<>();
        for (String group : groups) {
            // заполняем список атрибутов для каждой группы
            m = new HashMap<>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        // список атрибутов групп для чтения
        String[] groupFrom = new String[]{"groupName"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int[] groupTo = new int[]{android.R.id.text1};

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<>();

        // создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<>();
        // заполняем список атрибутов для каждого элемента
        for (String phone : phonesHTC) {
            m = new HashMap<>();
            m.put("phoneName", phone); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<>();
        for (String phone : phonesSams) {
            m = new HashMap<>();
            m.put("phoneName", phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // создаем коллекцию элементов для третьей группы
        childDataItem = new ArrayList<>();
        for (String phone : phonesLG) {
            m = new HashMap<>();
            m.put("phoneName", phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // список атрибутов элементов для чтения
        String[] childFrom = new String[]{"phoneName"};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int[] childTo = new int[]{android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,
                groupData, android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo,
                childData, android.R.layout.simple_list_item_1, childFrom, childTo);

        elvMain = findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
    }
}
