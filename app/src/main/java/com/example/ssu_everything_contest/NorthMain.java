package com.example.ssu_everything_contest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NorthMain extends AppCompatActivity {
    NorthGame northGame;
    NorthFail northFail;
    NorthSuccess northSuccess;
    private static int progressCount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_main);

        northGame=new NorthGame();
        northFail=new NorthFail();
        northSuccess=new NorthSuccess();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,northGame).commit();
        
        //무사히 끝나면, 성공화면!
        //만약 중간에 호감도가 50보다 떨어진다면, 실패화면




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NorthMain.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
