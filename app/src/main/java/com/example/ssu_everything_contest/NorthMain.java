package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NorthMain extends AppCompatActivity {
    NorthGame northGame;
    NorthFail northFail;
    NorthSuccess northSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_main);

        northGame=new NorthGame();
        northFail=new NorthFail();
        northSuccess=new NorthSuccess();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,northGame).commit();

        //실패로 가는 버튼
        Button goSouth=(Button)findViewById(R.id.failButton);
        goSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, northFail).commit();
            }
        });

        //성공으로 가는 버튼
        Button goNorth=(Button)findViewById(R.id.successButton);
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, northSuccess).commit();
            }
        });

    }
}
