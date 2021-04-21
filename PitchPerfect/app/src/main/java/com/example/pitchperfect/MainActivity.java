package com.example.pitchperfect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openFakeWebsite(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browserIntent);
    }

    public void openMainPage(){
        Intent main_intent = new Intent(this,MainActivity.class);
        startActivity(main_intent);
    }

    public void openPlayActivity(){
        Intent intent= new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity(){
        Intent intent2 = new Intent(this, ThirdActivity.class);
        startActivity(intent2);
    }
}