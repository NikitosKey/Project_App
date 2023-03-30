package com.example.project_app;

import static com.example.project_app.Firebase.FB_Read_CS;
import static com.example.project_app.LocalDataBase.addBunkerListCustomer;
import static com.example.project_app.LocalDataBase.getSizeBunkerArr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.Project_App.R;

import java.util.Calendar;

public class StatisticActivity extends AppCompatActivity {

    private TextView _userName;
    private TextView _tableDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staticstic);

        initTable();
        dataBaseInDataTable();

        // установим в текстовое поле имя пользователя
        _userName = findViewById(R.id.user_name);
        _userName.setText(LocalDataBase.getUserName());

        // установим в текстовое поле выбранную ранее дату
        _tableDate = findViewById(R.id.table_date);
        String text_date = _tableDate.getText().toString() + " " + LocalDataBase.getStringDate(LocalDataBase.getDateCurrent());
        _tableDate.setText(text_date);
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
            TextView obj_fact = new TextView(this);
            TextView obj_driver = new TextView(this);

            String t2 = FeedBrand.getString(bunker_arr.getList().get(i).getFeedBrand());
            String t3 = String.valueOf(bunker_arr.getList().get(i).getPlan());
            t3 = getRemoveZeroFloatStr(t3);
            String t4 = String.valueOf(bunker_arr.getList().get(i).getPlan());
            t4 = getRemoveZeroFloatStr(t4);
            String t5 = bunker_arr.getList().get(i).getNameDriver();
            if (t5.equals(""))
                t5 = "-";

            // изменение свойств объектов
            ViewProc.forTable(obj_index, String.valueOf(i + 1), typeface, 0.1f);
            ViewProc.forTable(obj_brand, t2, typeface, 0.2f);
            ViewProc.forTable(obj_plan, t3, typeface, 0.17f);
            ViewProc.forTable(obj_fact, t4, typeface, 0.17f);
            ViewProc.forTable(obj_driver, t5, typeface, 0.4f);

            // добавление элементов в строку таблицы
            table_row.addView(obj_index);
            table_row.addView(obj_brand);
            table_row.addView(obj_plan);
            table_row.addView(obj_fact);
            table_row.addView(obj_driver);

            // добавление строки в саму таблицу
            table.addView(table_row);
        }
    }
    private void dataBaseInDataTable(){
        FB_Read_CS(getSizeBunkerArr(), new Firebase.OnDataLoadedListener()  {
            @Override
            public void onDataLoaded(BunkerList bunkerList) {
                // Здесь получаем значение bunkerList и можем использовать его дальше
                //System.out.println(bunkerList.bunkerArrayList);
                addBunkerListCustomer(bunkerList);
                //System.out.println(bunkerList);
                FromAnyDataBaseToDataTable(bunkerList);
            }});
    }

    // чтение даннных из базы данных в таблицу
    private void FromAnyDataBaseToDataTable(BunkerList bunkerList) {
        //BunkerList bunkerList = new BunkerList(LocalDataBase.getSizeBunkerArr());
       // bunkerList.setDate(date);

       // // если нету списка бункеров по данной дате
       // if (!LocalDataBase.getBunkerListForDate(bunkerList))
       //     return;

        float total_plan = 0.0f;
        float total_fact = 0.0f;

        TableLayout tableMain = findViewById(R.id.table_main);
        // проход по каждой строке таблицы
        for (int i = 0; i < LocalDataBase.getSizeBunkerArr(); i++) {
            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);

            TextView obj_brand = (TextView) tableRow.getChildAt(1);
            TextView obj_plan = (TextView) tableRow.getChildAt(2);
            TextView obj_fact = (TextView) tableRow.getChildAt(3);
            TextView obj_driver = (TextView) tableRow.getChildAt(4);

            // установить в объекты новые значения
            String text_brand = FeedBrand.getString(bunkerList.getList().get(i).getFeedBrand());
            obj_brand.setText(text_brand);
            String text_plan = String.valueOf(bunkerList.getList().get(i).getPlan());
            obj_plan.setText( getRemoveZeroFloatStr(text_plan) );
            String text_fact = String.valueOf(bunkerList.getList().get(i).getFact());
            obj_fact.setText( getRemoveZeroFloatStr(text_fact) );

            String text_driver = bunkerList.getList().get(i).getNameDriver();
            if (text_driver.equals(""))
                text_driver = "-";
            obj_driver.setText(text_driver);

            total_plan += Float.parseFloat( obj_plan.getText().toString() );
            total_fact += Float.parseFloat( obj_fact.getText().toString() );
        }

        TextView obj_plan_text = findViewById(R.id.column_plan_total);
        TextView obj_fact_text = findViewById(R.id.column_fact_total);

        obj_plan_text.setText( getRemoveZeroFloatStr(String.valueOf(total_plan)) );
        obj_fact_text.setText( getRemoveZeroFloatStr(String.valueOf(total_fact)) );
    }

    // чтение даннных из FireBase в таблицу
    private void FireBaseInDataTable(Calendar date) {
        BunkerList bunkerList = new BunkerList(LocalDataBase.getSizeBunkerArr());
        bunkerList.setDate(date);

        // если нету списка бункеров по данной дате
        if (!LocalDataBase.getBunkerListForDate(bunkerList))
            return;

        float total_plan = 0.0f;
        float total_fact = 0.0f;

        TableLayout tableMain = findViewById(R.id.table_main);
        // проход по каждой строке таблицы
        for (int i = 0; i < LocalDataBase.getSizeBunkerArr(); i++) {
            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);

            TextView obj_brand = (TextView) tableRow.getChildAt(1);
            TextView obj_plan = (TextView) tableRow.getChildAt(2);
            TextView obj_fact = (TextView) tableRow.getChildAt(3);
            TextView obj_driver = (TextView) tableRow.getChildAt(4);

            // установить в объекты новые значения
            String text_brand = FeedBrand.getString(bunkerList.getList().get(i).getFeedBrand());
            obj_brand.setText(text_brand);
            String text_plan = String.valueOf(bunkerList.getList().get(i).getPlan());
            obj_plan.setText( getRemoveZeroFloatStr(text_plan) );
            String text_fact = String.valueOf(bunkerList.getList().get(i).getFact());
            obj_fact.setText( getRemoveZeroFloatStr(text_fact) );

            String text_driver = bunkerList.getList().get(i).getNameDriver();
            if (text_driver.equals(""))
                text_driver = "-";
            obj_driver.setText(text_driver);

            total_plan += Float.parseFloat( obj_plan.getText().toString() );
            total_fact += Float.parseFloat( obj_fact.getText().toString() );
        }

        TextView obj_plan_text = findViewById(R.id.column_plan_total);
        TextView obj_fact_text = findViewById(R.id.column_fact_total);

        obj_plan_text.setText( getRemoveZeroFloatStr(String.valueOf(total_plan)) );
        obj_fact_text.setText( getRemoveZeroFloatStr(String.valueOf(total_fact)) );
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
