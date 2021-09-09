package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SouthLoading extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_loading);

        //남쪽 메인으로 가는 버튼
        Button goNorth=(Button)findViewById(R.id.goSouthMain);
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),SouthMain.class);
                startActivity(intent);
            }
        });


    }
}
