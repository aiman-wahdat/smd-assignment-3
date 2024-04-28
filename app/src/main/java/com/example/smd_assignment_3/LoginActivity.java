package com.example.smd_assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    MyDatabaseHelper myHelper;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login); // Set the layout file
            myHelper= new MyDatabaseHelper(this);

            usernameEditText = findViewById(R.id.etUsername);
            passwordEditText = findViewById(R.id.etPassword);

            Button loginButton = findViewById(R.id.btnlogin);

            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = usernameEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();

                    if(username.isEmpty() || password.isEmpty()){
                        Toast.makeText(LoginActivity.this, "Complete the fields", Toast.LENGTH_SHORT).show();
                    }

                    else {

                        boolean loggedIn = myHelper.checkUser(username, password);
                        if (loggedIn) {
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,password_items.class);
                            startActivity(intent);
                            finish();
                            // Proceed to main activity or password management activity
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

