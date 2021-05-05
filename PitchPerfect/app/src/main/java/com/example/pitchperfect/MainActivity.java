package com.example.pitchperfect;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String TAG = "PitchPerfect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Entered onCreate()");

        try {
            Log.d(TAG, "calling onCreate()");
            super.onCreate(savedInstanceState);

            Log.d(TAG, "setting content view");
            setContentView(R.layout.activity_main);
        } catch (Exception e) {
            Log.e(TAG, "onCreate", e);
            throw e;
        }

        Log.d(TAG, "Exited onCreate()");
    }

    public void openFakeWebsite(View view){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://letmegooglethat.com/?q=Pitch+Perfect+Baseball")));
    }

    public void openPlayActivity(View view){
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }

    public void openSettingsActivity(View view){
        startActivity(new Intent(MainActivity.this, ThirdActivity.class));
    }
}