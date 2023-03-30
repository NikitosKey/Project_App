package com.example.project_app;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

// обработка элементов
public class ViewProc {
    private static final int tableFontSize =        15;             // размер шрифта
    private static final int tableTextColor =       Color.BLACK;    // цвет текста
    private static final int tableTextColorHint =   Color.GRAY;    // цвет подсказки
    private static final int tableGravity =         Gravity.CENTER; // выравнивание
    private static final int tableHeight =          120;             // вертикальный отступ в каждой строке таблицы

    public static void forTable(TextView obj, String text, Typeface typeface, float weight) {
        obj.setTypeface(typeface);
        obj.setText(text);
        obj.setTextSize(tableFontSize);
        obj.setTextColor(tableTextColor);

        obj.setMinimumHeight(tableHeight);
        obj.setGravity(tableGravity);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                weight);
        obj.setLayoutParams(params);
    }

    public static void forTable(EditText obj, String text, String hint, Typeface typeface, float weight) {
        obj.setTypeface(typeface);
        obj.setText(text);
        obj.setTextSize(tableFontSize);
        obj.setTextColor(tableTextColor);
        obj.setMaxWidth(20);

        obj.setHint(hint);
        obj.setHintTextColor(tableTextColorHint);
        obj.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        obj.setBackgroundTintMode(PorterDuff.Mode.LIGHTEN);

        obj.setMinimumHeight(tableHeight);
        obj.setGravity(tableGravity);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                weight);
        obj.setLayoutParams(params);
    }

    public static void forTable(Spinner obj, ArrayAdapter<String> adapter, Typeface typeface, float weight) {
        obj.setMinimumHeight(tableHeight);
        obj.setGravity(tableGravity);
        obj.setBackgroundColor(Color.WHITE);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        obj.setAdapter(adapter);

        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                weight);
        obj.setLayoutParams(params);
    }

}
