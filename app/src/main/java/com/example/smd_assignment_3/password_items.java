package com.example.smd_assignment_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class password_items extends AppCompatActivity {
    RecyclerView rvPasswordItems;
    FloatingActionButton fbtnAddItem;
    FloatingActionButton ftnRecycleBin;
    RecyclerView RvPasswordItems;
    LinearLayoutManager manager;
    passwordManagerAdapter adapter;
    ArrayList<Password> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_items);
        init();

        fbtnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(password_items.this, AddItem.class));
                finish();
            }
        });

        ftnRecycleBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(password_items.this,recyclerBin.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void init()
    {
        fbtnAddItem = findViewById(R.id.fbtnAddItem);
        ftnRecycleBin=findViewById(R.id.ftnRecycleBin);
        RvPasswordItems = findViewById(R.id.rvPasswordItems);
        RvPasswordItems.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        RvPasswordItems.setLayoutManager(manager);

        MyDatabaseHelper database = new MyDatabaseHelper(this);
        contacts = database.readAllEntries();
        database.close();

        adapter = new passwordManagerAdapter(this, contacts);
        RvPasswordItems.setAdapter(adapter);
    }
}