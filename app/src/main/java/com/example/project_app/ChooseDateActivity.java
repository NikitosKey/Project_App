package com.example.project_app;

import static com.example.project_app.LocalDataBase.setDateCurrent;
import static com.example.project_app.LocalDataBase.setSizeBunkerArr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.Project_App.R;

import java.util.Calendar;

public class ChooseDateActivity extends AppCompatActivity {

    private TextView _userName;
    private TextView _tableDate;
    private TextView _countBunker;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateListener;
    private String textTableDateInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        calendar = LocalDataBase.getDateCurrent();
        _tableDate = findViewById(R.id.table_date);
        textTableDateInit = _tableDate.getText().toString() + " ";
        setTableDate();

        // установим в текстовое поле имя пользователя
        _userName = findViewById(R.id.user_name);
        _userName.setText(LocalDataBase.getUserName());

        // установим количество бункеров
        _countBunker = findViewById(R.id.count_bunker);
        String text_count_bunker = _countBunker.getText().toString() + " " + LocalDataBase.getSizeBunkerArr();
        _countBunker.setText(text_count_bunker);

        // установка обработчика выбора даты
        dateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                setTableDate();
            }
        };
    }

    public void jumpToMainPage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void jumpToTablePage(View v) {
        Intent intent;
        TypeUser typeUser = LocalDataBase.getTypeUser();

        switch (typeUser) {
            case CUSTOMER:
                intent = new Intent(this, TableActivityForCustomer.class);
                break;

            case DRIVER:
                // если дата совпадает с датой сегодняшнего дня
                if (LocalDataBase.dateIsEqual( LocalDataBase.getDateCurrent(), Calendar.getInstance() ))
                    intent = new Intent(this, TableActivityForDriver.class);
                else
                    intent = new Intent(this, TableActivityForDriverReview.class);
                break;

            default:
                intent = new Intent(this, StatisticActivity.class);
                break;
        }
        startActivity(intent);
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(ChooseDateActivity.this, dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // запись в текстовое поле даты
    private void setTableDate() {
        setDateCurrent(calendar);


        String text = textTableDateInit + LocalDataBase.getStringDate(calendar);
        _tableDate.setText(text);
    }

}
