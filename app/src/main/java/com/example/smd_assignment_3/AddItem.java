package com.example.smd_assignment_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity {
    EditText etName, etPassword, etUrl;
    Button btnAdd, btnCancel;
    RecyclerView rvPasswordItems;
    FloatingActionButton fbtnAddItem;
    RecyclerView RvPasswordItems;
    LinearLayoutManager manager;
    passwordManagerAdapter adapter;
    ArrayList<Password> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etName= findViewById(R.id.etEditName);
        etPassword= findViewById(R.id.etEditPassword);
        etUrl=findViewById(R.id.etEditUrl);
        btnAdd= findViewById(R.id.btnAdd);
        btnCancel= findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();

                startActivity(new Intent(AddItem.this, password_items.class));
                finish();
            }
        });
    }

    private void addItem()
    {
        if(etName != null && etPassword!= null && etUrl!= null){

            String name = etName.getText().toString().trim();
            String password= etPassword.getText().toString().trim() ;
            String url = etUrl.getText().toString().trim();

            MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(AddItem.this);

            myDatabaseHelper.addEntry(name, password,url);

            myDatabaseHelper.close();

        }

    }
}