package com.example.project_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.Project_App.R;

import java.util.Calendar;

public class TableActivityForDriverReview extends AppCompatActivity {

    private TextView _userName;
    private TextView _tableDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_for_driver_review);

        initTable();
        dataBaseInDataTable(LocalDataBase.getDateCurrent());

        // установим в текстовое поле имя пользователя
        _userName = findViewById(R.id.user_name);
        _userName.setText(LocalDataBase.getUserName());

        // установим в текстовое поле сегодняшнюю дату
        _tableDate = findViewById(R.id.table_date);
        String text_date = _tableDate.getText().toString() + " " + LocalDataBase.getStringDate( LocalDataBase.getDateCurrent() );
        _tableDate.setText(text_date);
    }

    public void jumpToBack(View v) {
        Intent intent = new Intent(this, MainActivity.class);
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

            String t2 = FeedBrand.getString(bunker_arr.getList().get(i).getFeedBrand());
            String t3 = String.valueOf(bunker_arr.getList().get(i).getPlan());
            t3 = getRemoveZeroFloatStr(t3);

            // изменение свойств объектов
            ViewProc.forTable(obj_index, String.valueOf(i + 1), typeface, 0.1f);
            ViewProc.forTable(obj_brand, t2, typeface, 0.4f);
            ViewProc.forTable(obj_plan, t3, typeface, 0.25f);

            // добавление элементов в строку таблицы
            table_row.addView(obj_index);
            table_row.addView(obj_brand);
            table_row.addView(obj_plan);

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
        //Firebase.FB_Write(bunkerList, getStringDateFB(getDateCurrent()));
    }

    // чтение даннных из базы данных в таблицу
    private void dataBaseInDataTable(Calendar date) {
        BunkerList bunkerList = new BunkerList(LocalDataBase.getSizeBunkerArr());
        bunkerList.setDate(date);

        // если нету списка бункеров по данной дате
        if (!LocalDataBase.getBunkerListForDate(bunkerList))
            return;

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
}
