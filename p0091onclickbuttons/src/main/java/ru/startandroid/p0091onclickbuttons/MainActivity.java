package ru.startandroid.p0091onclickbuttons;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvOut;
    Button btnOK;
    Button btnCancel;
    TextView tvBottom;
    Button btnBottom;
    LinearLayout llBottom;

    private static final String TAG = "myLogs"; //описание тега для фильтра лога

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Запишем лог
        Log.d(TAG, "найдем View-элементы");
        // Найдем View-элементы
        tvOut = (TextView) findViewById(R.id.tvOut);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvBottom = (TextView) findViewById(R.id.tvBottom);
        btnBottom = (Button) findViewById(R.id.btnBottom);
        llBottom = (LinearLayout) findViewById(R.id.llBottom);

        // Установим текст
        tvBottom.setText(R.string.tvBottomText);
        btnBottom.setText(R.string.btnBottomText);
        // Установим цвет фона
        llBottom.setBackgroundResource(R.color.llBottomColor);

        Log.d(TAG, "присваиваем обработчик кнопкам"); //запишем лог
        // Создаем обработчик нажатия
        // Первый способ
        OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                tvOut.setText("Нажата кнопка ОК");
            }
        };

        OnClickListener oclBtnCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Меняем текст в TextView (tvOut)
                tvOut.setText("Нажата кнопка Cancel");
            }
        };

        // Присвоим обработчик  кнопке OK (btnOK)
        //btnOK.setOnClickListener(oclBtnOk);
        // Присвоим обработчик  кнопке Cancel (btnCancel)
        //btnCancel.setOnClickListener(oclBtnCancel);

        //Второй способ
        OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "по id определяем кнопку, вызвавшую этот обработчик"); //запишем лог
                // по id определеяем кнопку, вызвавшую этот обработчик
                switch (v.getId()) {
                    case R.id.btnOK:
                        Log.d(TAG, "кнопка ОК"); //запишем лог
                        //кнопка OK
                        tvOut.setText("Нажата кнопка ОК");
                        break;
                    case R.id.btnCancel:
                        Log.d(TAG, "кнопка Cancel"); //запишем лог
                        //кнопка Cancel
                        tvOut.setText("Нажата кнопка Cancel");
                        break;
                }
            }
        };

        btnOK.setOnClickListener(oclBtn);
        btnCancel.setOnClickListener(oclBtn);

        //Получить значение ID ресурса
        getResources().getString(R.id.tvBottom);

    }

    // Третий способ обработки события "onClick"
    public void onClickStart(View v) {
        // Меняем текст в TextView (tvOut)
        tvOut.setText("Нажата кнопка Внимание");
        Log.d(TAG, "выводим вспывающее сообщение"); //запишем лог
        //всплывающие сообщения
        Toast.makeText(this, "Нажата кнопка 'Внимание'", Toast.LENGTH_LONG).show();
    }

}

//public class MainActivity extends Activity implements OnClickListener {
//
//    TextView tvOut;
//    Button btnOk;
//    Button btnCancel;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        // найдем View-элементы
//        tvOut = (TextView) findViewById(R.id.tvOut);
//        btnOk = (Button) findViewById(R.id.btnOk);
//        btnCancel = (Button) findViewById(R.id.btnCancel);
//
//        // присваиваем обработчик кнопкам
//        btnOk.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        // по id определеяем кнопку, вызвавшую этот обработчик
//        switch (v.getId()) {
//            case R.id.btnOk:
//                // кнопка ОК
//                tvOut.setText("Нажата кнопка ОК");
//                break;
//            case R.id.btnCancel:
//                // кнопка Cancel
//                tvOut.setText("Нажата кнопка Cancel");
//                break;
//        }
//    }
//}
