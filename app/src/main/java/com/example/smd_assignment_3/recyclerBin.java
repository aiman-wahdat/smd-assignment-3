package com.example.smd_assignment_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class recyclerBin extends AppCompatActivity {
    RecyclerView rvRecycleBin;

    LinearLayoutManager manager;
    recycleBinAdapter adapter;
    ArrayList<Password> bin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_bin);
        init();

    }
    private void init()
    {
        rvRecycleBin = findViewById(R.id.rvRecycleBin);
        rvRecycleBin.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvRecycleBin.setLayoutManager(manager);

        MyDatabaseHelper database = new MyDatabaseHelper(this);
        bin = database.readAllReycleBin();
        database.close();

        adapter = new recycleBinAdapter(this, bin);
        rvRecycleBin.setAdapter(adapter);
    }

}