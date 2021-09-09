package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NorthLoading extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_loading);

        //북쪽 메인으로 가는 버튼
        Button goNorth=(Button)findViewById(R.id.goNorthMain);
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),NorthMain.class);
                startActivity(intent);
            }
        });


    }
}
