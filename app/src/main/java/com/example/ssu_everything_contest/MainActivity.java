package com.example.ssu_everything_contest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper mDBHelper = new DBHelper(getApplicationContext());
        try {
            mDBHelper.CreateDatabase();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        mDBHelper.OpenDatabase();
        mDBHelper.close();
        mDb=mDBHelper.getWritableDatabase();


        /**
         * Database Connection Test! (check number)
         */
        /*String sql="SELECT * FROM NSdictionary";

        Cursor cur=mDb.rawQuery(sql,null);
        int count=0;
        if(cur!=null){
            Log.d("checkDB", cur.getColumnNames().toString());
            int tupleNbr = 1;
            while(cur.moveToNext()) {
                Log.v("checkDB", "check column1: "+Integer.toString(tupleNbr));
                for(int i = 0; i < cur.getColumnCount()-1; i++){
                    Log.v("checkDB", "check column in for: "+cur.getString(i));
                }
                count++;
            }
        }
        Log.v("checkDB","count: "+count);*/



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

