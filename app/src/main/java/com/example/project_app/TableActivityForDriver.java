package com.example.project_app;

import static com.example.project_app.Firebase.FB_Read_D;
import static com.example.project_app.LocalDataBase.addBunkerListCustomer;
import static com.example.project_app.LocalDataBase.getDateCurrent;
import static com.example.project_app.LocalDataBase.getSizeBunkerArr;
import static com.example.project_app.LocalDataBase.getStringDateFB;
import static com.example.project_app.LocalDataBase.getUserName;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Project_App.R;

import java.util.Calendar;
import java.util.Date;

public class TableActivityForDriver extends AppCompatActivity {

    private TextView _userName;
    private TextView _tableDate;
    private Button _buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_for_driver);

        initTable();
        dataBaseInDataTable();

        // установим в текстовое поле имя пользователя
        _userName = findViewById(R.id.user_name);
        _userName.setText(LocalDataBase.getUserName());

        // установим в текстовое поле сегодняшнюю дату
        _tableDate = findViewById(R.id.table_date);
        String text_date = _tableDate.getText().toString() + " " + LocalDataBase.getStringDate(Calendar.getInstance());
        _tableDate.setText(text_date);

        // обработка события клика дляподтверждения введенных в таблицу данных
        _buttonOk = findViewById(R.id.button_ok);
        _buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                showAlert("Подтверждение",
                        "Вы уверены, что хотите подтвердить изменение данных в таблице?");
            }
        });
    }

    public void jumpToBack(View v) {
        Intent intent = new Intent(this, ChooseDateActivity.class);
        startActivity(intent);
    }

    // инициализация таблицы
    private void initTable() {
        // создаем объект массив бункеров
        BunkerList bunker_arr = new BunkerList(LocalDataBase.getSizeBunkerArr());
        // получаем с окна объект-таблицу
        TableLayout table = (TableLayout) findViewById(R.id.table_main);
        // инициализация шрифта
        Typeface typeface = ResourcesCompat.getFont(this, R.font.days_one);

        for (int i = 0; i < LocalDataBase.getSizeBunkerArr(); i++) {
            // инициализация строки таблицы
            TableRow table_row = new TableRow(this);
            // инициализация элементов строки таблицы
            TextView obj_index = new TextView(this);
            TextView obj_brand = new TextView(this);
            TextView obj_plan = new TextView(this);
            EditText obj_fact = new EditText(this);

            String t2 = FeedBrand.getString(bunker_arr.getList().get(i).getFeedBrand());
            String t3 = String.valueOf(bunker_arr.getList().get(i).getPlan());
            t3 = getRemoveZeroFloatStr(t3);

            // изменение свойств объектов
            ViewProc.forTable(obj_index, String.valueOf(i + 1), typeface, 0.1f);
            ViewProc.forTable(obj_brand, t2, typeface, 0.4f);
            ViewProc.forTable(obj_plan, t3, typeface, 0.25f);
            ViewProc.forTable(obj_fact, "", "0", typeface, 0.25f);

            // добавление элементов в строку таблицы
            table_row.addView(obj_index);
            table_row.addView(obj_brand);
            table_row.addView(obj_plan);
            table_row.addView(obj_fact);

            // добавление строки в саму таблицу
            table.addView(table_row);
        }
    }

    // запись даннных из таблицы в базу данных
    private void dataTableInDataBase() {
        BunkerList bunkerList = new BunkerList(LocalDataBase.getSizeBunkerArr());
        TableLayout tableMain = findViewById(R.id.table_main);

        // автоматически ставим текущую дату
        bunkerList.setDate(Calendar.getInstance());

        // проход по каждой строке таблицы
        for (int i = 0; i < LocalDataBase.getSizeBunkerArr(); i++) {
            Bunker bunker = new Bunker();

            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);
            EditText obj_fact = (EditText) tableRow.getChildAt(3);

            String text_fact = obj_fact.getText().toString();
            if (!text_fact.equals("")) {
                float fact = Float.parseFloat(text_fact);
                bunker.setFact(fact);
            }

            // добавим бункер в список бункеров
            bunkerList.setElem(i, bunker);
        }

        LocalDataBase.addBunkerListDriver(bunkerList);
        Firebase.FB_Write_Driver(bunkerList, getStringDateFB(getDateCurrent()), getUserName());
    }
    private void dataBaseInDataTable(){
        FB_Read_D(getSizeBunkerArr(), new Firebase.OnDataLoadedListener()  {
            @Override
            public void onDataLoaded(BunkerList bunkerList) {
                // Здесь получаем значение bunkerList и можем использовать его дальше
                System.out.println(bunkerList.bunkerArrayList);
                addBunkerListCustomer(bunkerList);
                //System.out.println(bunkerList);
                FromAnyDataBaseToDataTable(bunkerList);
            }});
    }

    // чтение даннных из базы данных в таблицу
    private void FromAnyDataBaseToDataTable(BunkerList bunkerList) {

        TableLayout tableMain = findViewById(R.id.table_main);
        // проход по каждой строке таблицы
        for (int i = 0; i < LocalDataBase.getSizeBunkerArr(); i++) {
            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);

            TextView obj_brand = (TextView) tableRow.getChildAt(1);
            TextView obj_plan = (TextView) tableRow.getChildAt(2);

            // установить в объекты новые значения
            String text_brand = FeedBrand.getString(bunkerList.getList().get(i).getFeedBrand());
            obj_brand.setText(text_brand);

            float necessary_plan = bunkerList.getList().get(i).getPlan() - bunkerList.getList().get(i).getFact();
            if (necessary_plan <= 0.001f)
                necessary_plan = 0.0f;
            String text_plan = String.valueOf(necessary_plan);
            obj_plan.setText(text_plan);
        }
    }

    // очистить все поля для ввода текста в таблице
    private void clearTableEditText() {
        TableLayout tableMain = findViewById(R.id.table_main);
        // проход по каждой строке таблицы
        for (int i = 0; i < LocalDataBase.getSizeBunkerArr(); i++) {
            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);
            TextView obj_edit = (TextView) tableRow.getChildAt(3);
            obj_edit.setText("");
        }
    }

    // приводит число, хрящееся в строке к пустой строке, если там 0 или к целому, если на конце ".0"
    private String getRemoveZeroFloatStr(String s) {
        if (s.equals("0.0"))
            return "0";
        else {
            String s_last_chars = "" + s.charAt(s.length() - 2) + s.charAt(s.length() - 1);
            if (s_last_chars.equals(".0"))
                return s.substring(0, s.length() - 2);
            else
                return s;
        }
    }

    // показать диалоговое окно с подтверждением
    private void showAlert(String title, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogButtonStyle);

        builder.setTitle(title)
                .setMessage(text)
                .setCancelable(true)
                // подтвердить действие
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // запись даннных из таблицы в базу данных
                        dataTableInDataBase();
                        dataBaseInDataTable();
                        //dataBaseInDataTable();
                        clearTableEditText();
                        Toast.makeText(TableActivityForDriver.this, R.string.dataSaved, Toast.LENGTH_LONG).show();
                    }
                })
                // отменить действие
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
