package com.example.perfectpitchapp;

import androidx.appcompat.app.AppCompatActivity;
import java.util.*;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

    boolean success = DatabaseHelper.addOne();

}