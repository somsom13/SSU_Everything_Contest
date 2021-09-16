package com.example.ssu_everything_contest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

        /*String sql= "SELECT * from NSdictionary";
        Cursor cur=MainActivity.mDb.rawQuery(sql,null);
        if(cur!=null){
            while(cur.moveToNext()) {
                int id=cur.getInt(0);
                String sword=cur.getString(1);
                String nword=cur.getString(2);
                String question=cur.getString(3);
                String a1=cur.getString(4);
                String a2=cur.getString(5);
                String a3=cur.getString(6);
                String a4=cur.getString(7);
                String check=cur.getString(8);
                int answerNum=cur.getInt(9);

                int success=valueToFragment();
                if(success==1){
                    //alert 띄우기 (호감도 up!)
                }else{
                    //호감도 down alert
                }
                
                //다음 db로 이동

            }
        }*/
        
        //무사히 끝나면, 성공화면!
        //만약 중간에 호감도가 50보다 떨어진다면, 실패화면


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
