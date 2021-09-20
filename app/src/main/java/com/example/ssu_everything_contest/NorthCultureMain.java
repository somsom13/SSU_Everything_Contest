package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NorthCultureMain extends AppCompatActivity {
    NorthCultureGame northCultureGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_culture_main);

        northCultureGame=new NorthCultureGame();
        getSupportFragmentManager().beginTransaction().replace(R.id.container2,northCultureGame).commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NorthCultureMain.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
