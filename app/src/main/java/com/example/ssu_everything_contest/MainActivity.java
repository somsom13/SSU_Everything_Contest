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
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    /**
     * 외부 class에서도 접근 가능한 목록들. ex) MainActivity.mDb 
     */
    public static SQLiteDatabase mDb;
    public static List<WordGameList> wordGameListList=new ArrayList<>(); //게임용 리스트
    public static List<WordDictionary> wordDictionaryList=new ArrayList<>();//단어사전 리스트
    public static int favoriteGage=100;
    public final static int lowFavorite=50;//실패하는 점수 (기준점수)
    //lowFavorite까지 가지 않고 무사히 게임을 다 통과한다면 결혼!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * DB연결
         */
        DBHelper mDBHelper = new DBHelper(getApplicationContext());
        try {
            mDBHelper.CreateDatabase();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        mDBHelper.OpenDatabase();
        mDBHelper.close();
        mDb=mDBHelper.getReadableDatabase();


        /**
         * 단어사전, 게임리스트 생성 및 데이터 추가
         * 
         */
        insertDataToGame();
        insertDataToDictionary();
        Log.v("checkDictionary", String.valueOf(wordDictionaryList.size()));


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


    private void insertDataToGame(){
        String sql="SELECT * FROM NSdictionary WHERE question not null";
        Cursor cur = mDb.rawQuery(sql, null);

        if (cur != null) {
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                String sword = cur.getString(1);
                String nword = cur.getString(2);
                String q = cur.getString(3);
                if (q == null)
                    break;
                String an1 = cur.getString(4);
                String an2 = cur.getString(5);
                String an3 = cur.getString(6);
                String an4 = cur.getString(7);
                String check = cur.getString(8);
                int foundResult = cur.getInt(9);
                //Log.v("checkWordGame","add new value, id: "+id);
                wordGameListList.add(new WordGameList(id,sword,nword,q,an1,an2,an3,an4,foundResult));

            }
        }
    }

    private void insertDataToDictionary(){
        String sql="SELECT * FROM NSdictionary";
        Cursor cur = mDb.rawQuery(sql, null);

        if (cur != null) {
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                String sword = cur.getString(1);
                String nword = cur.getString(2);
                //Log.v("checkWordGame","add new value, id: "+id);
                wordDictionaryList.add(new WordDictionary(id,sword,nword));
            }
        }
    }



}

