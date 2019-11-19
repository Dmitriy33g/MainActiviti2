package ru.startandroid.p0331sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class SQLiteQueryActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";

    String name[] = { "Китай", "США", "Бразилия", "Россия", "Япония",
            "Германия", "Египет", "Италия", "Франция", "Канада" };
    int peopple[] = { 1400, 311, 195, 142, 128, 82, 80, 60, 66, 35 };
    String region[] = { "Азия", "Америка", "Америка", "Европа", "Азия",
            "Европа", "Африка", "Европа", "Европа", "Америка" };

    Button btnAll, btnFunc, btnPeople, btnSort, btnGroup, btnHaving, btnLimit, btnDistinct;
    EditText etFunc, etPeople, etRegionPeople, etLimit;
    RadioGroup rgSort;

    MainActivity.DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlitequery);

        btnAll = findViewById(R.id.btnAll);
        btnAll.setOnClickListener(this);

        btnFunc = findViewById(R.id.btnFunc);
        btnFunc.setOnClickListener(this);

        btnPeople = findViewById(R.id.btnPeople);
        btnPeople.setOnClickListener(this);

        btnSort = findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnGroup = findViewById(R.id.btnGroup);
        btnGroup.setOnClickListener(this);

        btnHaving = findViewById(R.id.btnHaving);
        btnHaving.setOnClickListener(this);

        btnLimit = findViewById(R.id.btnLimit);
        btnLimit.setOnClickListener(this);

        btnDistinct = findViewById(R.id.btnDistinct);
        btnDistinct.setOnClickListener(this);

        etFunc = findViewById(R.id.etFunc);
        etPeople = findViewById(R.id.etPeople);
        etRegionPeople = findViewById(R.id.etRegionPeople);
        etLimit = findViewById(R.id.etLimit);

        rgSort = findViewById(R.id.rgSort);

        dbHelper = new MainActivity.DBHelper(this);
        // подключаемся к базе
        db = dbHelper.getWritableDatabase();

        // проверка существования записей
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        if (c.getCount() == 0) {
            ContentValues cv = new ContentValues();
            // заполним таблицу
            for (int i = 0; i < 10; i++) {
                cv.put("name", name[i]);
                cv.put("people", peopple[i]);
                cv.put("region", region[i]);
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "id = " + rowID);
            }
        }
        c.close();
        dbHelper.close();
        // эмулируем нажатие кнопки btnAll
        onClick(btnAll);
    }

    @Override
    public void onClick(View v) {
        // подключаемся к БД
        db = dbHelper.getWritableDatabase();
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // данные с экрана
        String sFunc = etFunc.getText().toString();
        String sPeople = etPeople.getText().toString();
        String sRegionPeople = etRegionPeople.getText().toString();

        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        // курсор
        Cursor c = null;

        switch (v.getId()) {
            case R.id.btnAll:
                // Все записи
                Log.d(LOG_TAG, "--- Все записи ---");
                c = db.query("mytable", null, null, null, null, null, null);
                break;
            case R.id.btnFunc:
                // Функция
                Log.d(LOG_TAG, "--- Функция " + sFunc + " ---");
                columns = new String[] { sFunc };
                c = db.query("mytable", columns, null, null, null, null, null);
                break;
            case R.id.btnPeople:
                // Население больше, чем
                Log.d(LOG_TAG, "--- Население больше " + sPeople + " ---");
                selection = "people > ?";
                selectionArgs = new String[] { sPeople };
                c = db.query("mytable", null, selection, selectionArgs, null, null, null);
                break;
            case R.id.btnGroup:
                // Население по региону
                Log.d(LOG_TAG, "--- Население по региону ---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                c = db.query("mytable", columns, null, null, groupBy, null, null);
                break;
            case R.id.btnHaving:
                Log.d(LOG_TAG, "--- Регионы с населением больше " + sRegionPeople + " ---");
                columns = new String[] { "region", "sum(people) as people" };
                groupBy = "region";
                having = "sum(people) > " + sRegionPeople;
                c = db.query("mytable", columns, null, null, groupBy, having, null);
                break;
            case R.id.btnSort:
                // Сортировка
                switch (rgSort.getCheckedRadioButtonId()) {
                    // сортировка по
                    case R.id.rName:
                        // наименование
                        Log.d(LOG_TAG, "--- Сортировка по наименованию ---");
                        orderBy = "name";
                        break;
                    case R.id.rPeople:
                        // население
                        Log.d(LOG_TAG, "--- Сортировка по населению ---");
                        orderBy = "people";
                        break;
                    case R.id.rRegion:
                        // регион
                        Log.d(LOG_TAG, "--- Сортировка по региону ---");
                        orderBy = "region";
                        break;
                }
                c = db.query("mytable", null, null, null, null, null, orderBy);
                break;
            case R.id.btnLimit:
                Log.d(LOG_TAG, "--- Получить первые записи ---");
                limit = etLimit.getText().toString();
                c = db.query("mytable", null, null, null, null, null, null, limit);
                break;
            case R.id.btnDistinct:
                Log.d(LOG_TAG, "--- Удаление дубликатов ---");
                c = db.query(true, "mytable", null, null, null, null, null, null, null);
                break;
        }

        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, str);
                } while (c.moveToNext());
            }
            c.close();
        } else
            Log.d(LOG_TAG, "Cursor is null");

        // закрываем подключение к БД
        dbHelper.close();
    }
}
