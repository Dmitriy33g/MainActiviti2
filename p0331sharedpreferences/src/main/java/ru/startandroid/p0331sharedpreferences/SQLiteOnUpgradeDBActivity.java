package ru.startandroid.p0331sharedpreferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

public class SQLiteOnUpgradeDBActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    final String DB_NAME = "staff"; // имя БД
    final int DB_VERSION = 2; // версия БД

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, " --- onCreate database --- ");

            String[] people_name = { "Иван", "Марья", "Петр", "Антон", "Даша",
                    "Борис", "Костя", "Игорь" };
            int[] people_posid = { 2, 3, 2, 2, 3, 1, 2, 4 };

            // данные для таблицы должностей
            String[] people_positions = { "Программер", "Бухгалтер",
                    "Программер", "Программер", "Бухгалтер", "Директор",
                    "Программер", "Охранник" };
            int[] position_id = { 1, 2, 3, 4 };
            String[] position_name = { "Директор", "Программер", "Бухгалтер",
                    "Охранник" };
            int[] position_salary = { 15000, 13000, 10000, 8000 };

            ContentValues cv = new ContentValues();
            String sql;

            if (db.getVersion() == 2) {
                sql = "create table people ("
                        + "id integer primary key autoincrement,"
                        + "name text,"
                        + "posid integer);";
                // создаем таблицу должностей
                db.execSQL("create table position ("
                        + "id integer primary key,"
                        + "name text, salary integer);");

                // заполняем ее
                for (int i = 0; i < position_id.length; i++) {
                    cv.clear();
                    cv.put("id", position_id[i]);
                    cv.put("name", position_name[i]);
                    cv.put("salary", position_salary[i]);
                    db.insert("position", null, cv);
                }
            } else {
                sql = "create table people ("
                        + "id integer primary key autoincrement,"
                        + "name text,"
                        + "position text);";
            }

            // создаем таблицу людей
            db.execSQL(sql);

            // заполняем ее
            for (int i = 0; i < people_name.length; i++) {
                cv.clear();
                cv.put("name", people_name[i]);
                if (db.getVersion() == 2) {
                    cv.put("posid", people_posid[i]);
                } else
                    cv.put("position", people_positions[i]);
                db.insert("people", null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(LOG_TAG, " --- onUpgrade database from " + oldVersion
                    + " to " + newVersion + " version --- ");

            if (oldVersion == 1 && newVersion == 2) {

                ContentValues cv = new ContentValues();

                // данные для таблицы должностей
                int[] position_id = { 1, 2, 3, 4 };
                String[] position_name = { "Директор", "Программер", "Бухгалтер", "Охранник" };
                int[] position_salary = { 15000, 13000, 10000, 8000 };

                db.beginTransaction();
                try {
                    // создаем таблицу должностей
                    db.execSQL("create table position ("
                            + "id integer primary key,"
                            + "name text,"
                            + "salary integer);");

                    // заполняем ее
                    for (int i = 0; i < position_id.length; i++) {
                        cv.clear();
                        cv.put("id", position_id[i]);
                        cv.put("name", position_name[i]);
                        cv.put("salary", position_salary[i]);
                        db.insert("position", null, cv);
                    }

                    db.execSQL("alter table people add column posid integer;");

                    for (int i = 0; i < position_id.length; i++) {
                        cv.clear();
                        cv.put("posid", position_id[i]);
                        db.update("people", cv, "position = ?",
                                new String[]{position_name[i]});
                    }

                    db.execSQL("create temporary table people_tmp (" +
                            "id integer, name text, position text, posid integer);");

                    db.execSQL("insert into people_tmp select id, name, position, posid from people;");
                    db.execSQL("drop table people");

                    db.execSQL("create table people (" +
                            "id integer primary key autoincrement," +
                            "name text," +
                            "posid integer);");

                    db.execSQL("insert into people select id, name, posid from people_tmp;");
                    db.execSQL("drop table people_tmp;");

                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqliteonupgradedb);

        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        Log.d(LOG_TAG, " --- Staff db v." + db.getVersion() + " --- ");
        writeStaff(db);
        dbh.close();
    }

    // запрос данных и вывод в лог
    private void writeStaff(SQLiteDatabase db) {
        Cursor c = db.rawQuery("select * from people", null);
        logCursor(c, "Table people");
        c.close();

        if (db.getVersion() == 2) {
            c = db.rawQuery("select * from position", null);
            logCursor(c, "Table position");
            c.close();

            String sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary "
                    + "from people as PL "
                    + "inner join position as PS "
                    + "on PL.posid = PS.id ";
            c = db.rawQuery(sqlQuery, null);
            logCursor(c, "inner join");
            c.close();
        }

    }

    // вывод в лог данных из курсора
    void logCursor(Cursor c, String title) {
        if (c != null) {
            if (c.moveToFirst()) {
                Log.d(LOG_TAG, title + ". " + c.getCount() + " rows");
                StringBuilder sb = new StringBuilder();
                do {
                    sb.setLength(0);
                    for (String cn : c.getColumnNames()) {
                        sb.append(cn + " = " + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d(LOG_TAG, sb.toString());
                } while (c.moveToNext());
            } else
                Log.d(LOG_TAG, title + ". Cursor is null");
        }
    }
}
