package com.example.project_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Project_App.R;

public class AuthorizationActivity extends AppCompatActivity {

    private Button _confirmUserName;
    private EditText _inputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        _inputField = findViewById(R.id.inputField);
        _confirmUserName = findViewById(R.id.confirmUserName);
        _confirmUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = _inputField.getText().toString().trim();
                // если пользователь не ввел текст
                if (user_name.equals("")) {
                    Toast.makeText(AuthorizationActivity.this, R.string.inputUrNameHint, Toast.LENGTH_LONG).show();
                }
                else {
                    // сохраним имя пользователя
                    LocalDataBase.setUserName(user_name);


                    Intent intent = new Intent(AuthorizationActivity.this, ChooseDateActivity.class);
                    startActivity(intent);
                }
            }
        });

        // установим в текстовое поле имя пользователя, если оно было введено ранее
        _inputField.setText(LocalDataBase.getUserName());
    }

    public void jumpToMainPage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}