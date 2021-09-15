package com.example.ssu_everything_contest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //북쪽 로딩으로 가는 버튼
        ImageButton goNorth= (ImageButton) findViewById(R.id.northButton);
        goNorth.bringToFront();
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                startActivity(intent);
            }
        });

        //남쪽 로딩으로 가는 버튼
        ImageButton goSouth=(ImageButton)findViewById(R.id.southButton);
        goSouth.bringToFront();
        goSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SouthLoading.class);
                startActivity(intent);
            }
        });

    }
}