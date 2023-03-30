package com.example.project_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent; // для перехода на разные Activity
import android.os.Bundle;
import android.view.View; // для параметра в функции перехода по Activity

import com.example.Project_App.R;


public class MainActivity extends AppCompatActivity {

    private static boolean dataBaseIsInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (!dataBaseIsInit) {
            LocalDataBase.init();
            dataBaseIsInit = true;
        }
    }

    public void jumpToSecondPageForCustomer(View v) {
        LocalDataBase.setTypeUser(TypeUser.CUSTOMER);
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    public void jumpToSecondPageForDriver(View v) {
        LocalDataBase.setTypeUser(TypeUser.DRIVER);
        Intent intent = new Intent(this, AuthorizationActivity.class);
        startActivity(intent);
    }

    public void jumpToStatistic(View v) {
        LocalDataBase.setTypeUser(TypeUser.VISITOR);
        Intent intent = new Intent(this, ChooseDateActivity.class);
        startActivity(intent);
    }
}