package com.example.ssu_everything_contest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    /**
     * 외부 class에서도 접근 가능한 목록들. ex) MainActivity.mDb 
     */
    public static SQLiteDatabase mDb;
    public static List<WordGameList> wordGameListList=new ArrayList<>(); //게임용 리스트
    public static ArrayList<NorthWordSouthWordData> nsWordList=new ArrayList<>();//단어사전 리스트
    private int favoriteGage;
    SharedPreferences test;
    SharedPreferences.Editor editor;
    private TextView favoriteText;
    TextView southText,northText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        southText=(TextView) findViewById(R.id.southSoongsilText);
        northText=(TextView) findViewById(R.id.northSoongsilText);

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

        updateDB();

        /**
         * 단어사전, 게임리스트 생성 및 데이터 추가
         * 
         */
        insertDataToGame();
        insertDataToDictionary();
        //Log.v("checkDictionary", String.valueOf(wordDictionaryList.size()));

        favoriteText=(TextView) findViewById(R.id.fav);
        test = getSharedPreferences("test", MODE_PRIVATE);
        editor = test.edit();

        if(!test.contains("favoriteGage"))
            editor.putInt("favoriteGage", 0);
        if(!test.contains("progressCount"))
            editor.putInt("progressCount",0);
        editor.commit(); //완료한다.

        //favoriteGage=test.getInt("favoriteGage",100);
        favoriteGage=test.getInt("favoriteGage",100);
        favoriteText.setText(String.valueOf(favoriteGage));
        Log.v("checkFavorite","from main activity, get favoriteGage: "+favoriteGage);


        //북쪽 로딩으로 가는 버튼
        ImageButton goNorth= (ImageButton) findViewById(R.id.northButton);
        goNorth.bringToFront();
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //favoriteGage 따라 전환 설정
                if(northText.getVisibility()==View.GONE)
                    northText.setVisibility(View.VISIBLE);
                else
                    northText.setVisibility(View.GONE);
                if(southText.getVisibility()==View.VISIBLE)
                    southText.setVisibility(View.GONE);
                /*if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                    startActivity(intent);
                    finish();
                }else{
                    makeDialog();
                }*/

            }
        });

        /*favoriteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDb.beginTransaction();
                String sql="UPDATE WordDictionaryData SET ANSWER=1 WHERE _ID="+15;
                String sql2="UPDATE WordDictionaryData SET A1='헉 낚시하러 가시나봐!' WHERE _ID="+35;
                try{
                    MainActivity.mDb.execSQL(sql);
                    MainActivity.mDb.execSQL(sql2);
                }catch (SQLException e){
                    Log.v("SQLcheck","update error");
                }
                Log.v("SQLcheck","update ok");
                mDb.setTransactionSuccessful();
                mDb.endTransaction();
            }
        });*/

        //남쪽 로딩으로 가는 버튼
        ImageButton goSouth=(ImageButton)findViewById(R.id.southButton);
        goSouth.bringToFront();
        goSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(southText.getVisibility()==View.GONE)
                    southText.setVisibility(View.VISIBLE);
                else
                    southText.setVisibility(View.GONE);
                if(northText.getVisibility()==View.VISIBLE)
                    northText.setVisibility(View.GONE);
                /*southText.setVisibility(View.VISIBLE);
                Intent intent=new Intent(getApplicationContext(),SouthLoading.class);
                startActivity(intent);
                finish();*/
            }
        });


        southText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SouthLoading.class);
                startActivity(intent);
                finish();
            }
        });

        northText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                    startActivity(intent);
                    finish();
                }else{
                    makeDialog();
                }
            }
        });


        favoriteText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                easterEgg();
                return false;
            }
        });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void initializeFavoriteGage() {
        favoriteGage=test.getInt("favoriteGage",100);
        favoriteText.setText(String.valueOf(favoriteGage));
    }



    private void insertDataToGame(){
        String sql="SELECT * FROM WordDictionaryData WHERE question not null";
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

    public static void insertDataToDictionary(){
        String sql="SELECT * FROM WordDictionaryData";
        Cursor cur = mDb.rawQuery(sql, null);

        if (cur != null) {
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                String sword = cur.getString(1);
                String nword = cur.getString(2);
                String check=cur.getString(8);
                //Log.v("checkWordGame","add new value, id: "+id);
                nsWordList.add(new NorthWordSouthWordData(id,sword,nword,check.equals("X")?false:true));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void makeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("호감도가 낮아서 평양숭실에 방문할 수 없습니다. \n호감도를 100이상으로 높여주세요!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateDB(){

        mDb.beginTransaction();
        String sql="UPDATE WordDictionaryData SET ANSWER=1 WHERE _ID="+15;
        String sql2="UPDATE WordDictionaryData SET A1='헉 낚시하러 가시나봐!' WHERE _ID="+35;
        try{
            MainActivity.mDb.execSQL(sql);
            MainActivity.mDb.execSQL(sql2);
        }catch (SQLException e){
            Log.v("SQLcheck","update error");
        }
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
    }

    private void easterEgg(){
        editor.putInt("favoriteGage",90);
        editor.apply();
        favoriteGage=test.getInt("favoriteGage",90);
        favoriteText.setText(String.valueOf(favoriteGage));

        //test base64 to bitmap and show
        /*byte[] decodedString = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAB0AAABrCAMAAAHLMUl3AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAACEUExURQAAAJ+fnwAAAAAAAKOjowAAADAwMAAAAH9/fwwMDAAAAAAAAAAAAIODgwAAAKurqwAAAK+vrwAAABgYGP///wAAAAAAAI+PjwAAAAAAAAAAAAAAAJOTkwAAAAAAALu7uwAAAAAAAHBwcCQkJL+/vwAAAAAAAAAAAHR0dMPDwwAAAAQEBBbKthAAAAArdFJOUwD/wwj/0/8Y///jjyj/8/84/7f//wQM/88U13D/JOf/gCz///88/6v//7sko8INAAAACXBIWXMAABcRAAAXEQHKJvM/AAABc0lEQVQ4T+2U21KEMBBEI14QxcVLWBQV19VtRP7//+xMZkO2pCzlyYecKiozaQghzLRZQs0LNjemsh3DXiaXkNvRmM5WDJcv8jvWwJpDWVhS7BhmV00rUgD/e4SVAPwABm4IgQr7G/0gxLEj5Yf8nNcAXLV7WEPurMHKJyVG+QXWjiiZd3jXfMBK7mjRMKswldknzpBp7HnUcR4cbielMSmN+UvaA5Hf9thup6rM8GFtg3uf+RK2R76GW1QuczVN4+9w4zNf8a8Qb3UUoMGG/iikPUwu3cNmeXaZdtO+mQhz79EKe+9YQ6GeGjFis9loNAdX1GiOpMYkNSapMUmNWa7Wl1T7WSMzNV0Zb7xuIxNU1hnnt3TPc47ZoZ47TVyaNIzbYKkmb4HxSTVHNQIXYsem7KipnwdeCqCjvlsBxaCTE8MdsKK3zz08uEevZWl98Ykq/rVu2UB+GjbdUIu27NEPdp+TPehcjBzWt6OYoB4fszFf1a5PkMTHU5YAAAAASUVORK5CYII=", Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ImageView imageview=(ImageView) findViewById(R.id.imageView);
        imageview.setImageBitmap(decodedByte);*/
    }


}

