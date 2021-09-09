package com.example.ssu_everything_contest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //북쪽 로딩으로 가는 버튼
        Button goNorth=(Button)findViewById(R.id.northButton);
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                startActivity(intent);
            }
        });

        //남쪽 로딩으로 가는 버튼
        Button goSouth=(Button)findViewById(R.id.southButton);
        goSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SouthLoading.class);
                startActivity(intent);
            }
        });

    }
}