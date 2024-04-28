package com.example.smd_assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmEditText;

    MyDatabaseHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myHelper = new MyDatabaseHelper(this);
        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        confirmEditText=findViewById(R.id.etConfirmPass);

        Button registerButton = findViewById(R.id.btnregister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confrimPass = confirmEditText.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty() || confrimPass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confrimPass)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    long registered = myHelper.addUser(username, password);
                    if (registered>0) {
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}