package com.example.project_app;


import static com.example.project_app.Firebase.FB_Read_CS;
import static com.example.project_app.LocalDataBase.addBunkerListCustomer;
import static com.example.project_app.LocalDataBase.getDateCurrent;
import static com.example.project_app.LocalDataBase.getSizeBunkerArr;

import static com.example.project_app.LocalDataBase.getStringDateFB;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.Project_App.R;

import java.util.Calendar;
import java.util.Date;


public class TableActivityForCustomer extends AppCompatActivity {

    private TextView _userName;
    private TextView _tableDate;
    private Button _buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_for_customer);

        initTable();
        dataBaseInDataTable();

        // установим в текстовое поле имя пользователя
        _userName = findViewById(R.id.user_name);
        _userName.setText(LocalDataBase.getUserName());

        // установим в текстовое поле выбранную ранее дату
        _tableDate = findViewById(R.id.table_date);
        String text_date = _tableDate.getText().toString() + " " + LocalDataBase.getStringDate(LocalDataBase.getDateCurrent());
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
        // объект: массив бункеров
        BunkerList bunker_arr = new BunkerList(LocalDataBase.getSizeBunkerArr());
        TableLayout table = (TableLayout) findViewById(R.id.table_main);

        // шрифт
        Typeface typeface = ResourcesCompat.getFont(this, R.font.days_one);

        for (int i = 0; i < LocalDataBase.getSizeBunkerArr(); i++) {
            // инициализация строки таблицы
            TableRow table_row = new TableRow(this);
            // инициализация элементов строки таблицы
            TextView obj_index = new TextView(this);
            Spinner text_brand = new Spinner(this);
            EditText obj_plan = new EditText(this);

            ArrayAdapter<String> adapter_brand = createAdapterBrand();
            String t3 = String.valueOf(bunker_arr.getList().get(i).getPlan());
            t3 = getRemoveZeroFloatStr(t3);
            // изменение свойств объектов
            ViewProc.forTable(obj_index, String.valueOf(i + 1), typeface, 0.2f);
            ViewProc.forTable(text_brand, adapter_brand, typeface, 0.45f);
            ViewProc.forTable(obj_plan, t3, "0", typeface, 0.35f);

            // добавление элементов в строку таблицы
            table_row.addView(obj_index);
            table_row.addView(text_brand);
            table_row.addView(obj_plan);

            // добавление строки в саму таблицу
            table.addView(table_row);
        }
    }

    private ArrayAdapter<String> createAdapterBrand() {
        int count_brand = 8;
        String[] array_text_brand = new String[count_brand];
        for (int i = 0; i < count_brand; i++)
            array_text_brand[i] = "СК-" + (i + 1);

        ArrayAdapter<String> adapter_brand = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array_text_brand);

        return adapter_brand;
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
                        Toast.makeText(TableActivityForCustomer.this, R.string.dataSaved, Toast.LENGTH_LONG).show();
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


    // запись даннных из таблицы в базу данных
    private void dataTableInDataBase() {
        BunkerList bunkerList = new BunkerList(getSizeBunkerArr());
        TableLayout tableMain = findViewById(R.id.table_main);

        // выбраная дата
        bunkerList.setDate( (Calendar) LocalDataBase.getDateCurrent().clone() );

        // проход по каждой строке таблицы
        for (int i = 0; i < getSizeBunkerArr(); i++) {
            Bunker bunker = new Bunker();

            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);
            Spinner obj_brand = (Spinner) tableRow.getChildAt(1);
            EditText obj_plan = (EditText) tableRow.getChildAt(2);

            // выпадающий список
            String text_brand = obj_brand.getSelectedItem().toString();
            bunker.setFeedBrand( FeedBrand.getFeedBrand(text_brand) );

            // текстовое поле
            String text_plan = obj_plan.getText().toString();
            if (!text_plan.equals(""))
                bunker.setPlan( Float.parseFloat(text_plan) );

            // добавим бункер в список бункеров
            bunkerList.setElem(i, bunker);
        }

        addBunkerListCustomer(bunkerList);
        Firebase.FB_Write_Customer(bunkerList, getStringDateFB(getDateCurrent()));
    }
    private void dataBaseInDataTableLocal(Calendar Date) {
        BunkerList bunkerList = new BunkerList(getSizeBunkerArr());
        bunkerList.setDate(Date);
        // если нету списка бункеров по данной дате
        //if (!LocalDataBase.getBunkerListForDate(bunkerList))
        //return;

        TableLayout tableMain = findViewById(R.id.table_main);
        // проход по каждой строке таблицы
        for (int i = 0; i < getSizeBunkerArr(); i++) {
            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);
            Spinner obj_brand = (Spinner) tableRow.getChildAt(1);
            EditText obj_plan = (EditText) tableRow.getChildAt(2);

            // установить в объекты новые значения
            int index_brand = FeedBrand.getIndex(bunkerList.getList().get(i).getFeedBrand());
            obj_brand.setSelection(index_brand);

            String text_plan = String.valueOf(bunkerList.getList().get(i).getPlan());
            text_plan = getRemoveZeroFloatStr(text_plan);
            obj_plan.setText(text_plan);
        }
    }
    private void FromAnyDataBaseToDataTable(BunkerList bunkerList) {
        //bunkerList.setDate(Date);
        // если нету списка бункеров по данной дате
        //if (!LocalDataBase.getBunkerListForDate(bunkerList))
        //return;

        TableLayout tableMain = findViewById(R.id.table_main);
        // проход по каждой строке таблицы
        for (int i = 0; i < bunkerList.getBunkerArrayListSize(); i++) {
            // получим объекты со строки таблицы
            TableRow tableRow = (TableRow) tableMain.getChildAt(i);
            Spinner obj_brand = (Spinner) tableRow.getChildAt(1);
            EditText obj_plan = (EditText) tableRow.getChildAt(2);

            // установить в объекты новые значения
            int index_brand = FeedBrand.getIndex(bunkerList.getList().get(i).getFeedBrand());
            obj_brand.setSelection(index_brand);

            String text_plan = String.valueOf(bunkerList.getList().get(i).getPlan());
            text_plan = getRemoveZeroFloatStr(text_plan);
            obj_plan.setText(text_plan);
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

    // приводит число, хрящееся в строке к пустой строке, если там 0 или к целому, если на конце ".0"
    private String getRemoveZeroFloatStr(String s) {
        if (s.equals("0.0") || s.equals("0"))
            return "";
        else {
            String s_last_chars = "" + s.charAt(s.length() - 2) + s.charAt(s.length() - 1);
            if (s_last_chars.equals(".0"))
                return s.substring(0, s.length() - 2);
            else
                return s;
        }
    }
}
