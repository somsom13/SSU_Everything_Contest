package com.example.ssu_everything_contest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
    public static ArrayList<CultureData> cultureList=new ArrayList<>();//문화 리스트
    private int favoriteGage;
    private int setImg=0;
    SharedPreferences test;
    SharedPreferences.Editor editor;
    private TextView favoriteText;
    TextView southText,northText,foodText, cultureText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        southText=findViewById(R.id.southSoongsilText);
        northText= findViewById(R.id.northSoongsilText);
        foodText=findViewById(R.id.foodText);
        cultureText=findViewById(R.id.cultureText);

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

        insertDataToGame();
        insertDataToDictionary();
        insertDataToCulture();
        sendImgRequest("cultureData");
        //sendImgRequest("foodData");
        sendImgRequest("tourData");


        favoriteText=findViewById(R.id.tour_favorite);
        test = getSharedPreferences("test", MODE_PRIVATE);
        editor = test.edit();

        if(!test.contains("favoriteGage"))
            editor.putInt("favoriteGage", 0);
        if(!test.contains("progressCount"))
            editor.putInt("progressCount",0);
        if(!test.contains("tourProgress"))
            editor.putInt("tourProgress",0);
        editor.commit(); //완료한다.

        //favoriteGage=test.getInt("favoriteGage",100);
        favoriteGage=test.getInt("favoriteGage",100);
        Log.v("favoriteGage","get favorite gage in main oncreate");
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
                if(foodText.getVisibility()==View.VISIBLE)
                    foodText.setVisibility(View.GONE);
                if(cultureText.getVisibility()==View.VISIBLE)
                    cultureText.setVisibility(View.GONE);
                /*if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                    startActivity(intent);
                    finish();
                }else{
                    makeDialog();
                }*/

            }
        });

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
                if(foodText.getVisibility()==View.VISIBLE)
                    foodText.setVisibility(View.GONE);
                if(cultureText.getVisibility()==View.VISIBLE)
                    cultureText.setVisibility(View.GONE);
                /*southText.setVisibility(View.VISIBLE);
                Intent intent=new Intent(getApplicationContext(),SouthLoading.class);
                startActivity(intent);
                finish();*/
            }
        });

        //푸드로 가는 버튼
        ImageButton foodButton=(ImageButton)findViewById(R.id.foodButton);
        foodButton.bringToFront();
        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(foodText.getVisibility()==View.GONE)
                    foodText.setVisibility(View.VISIBLE);
                else
                    foodText.setVisibility(View.GONE);
                if(northText.getVisibility()==View.VISIBLE)
                    northText.setVisibility(View.GONE);
                if(southText.getVisibility()==View.VISIBLE)
                    southText.setVisibility(View.GONE);
                if(cultureText.getVisibility()==View.VISIBLE)
                    cultureText.setVisibility(View.GONE);
            }
        });

        //문화로 가는 버튼
        ImageButton cultureButton=(ImageButton)findViewById(R.id.cultureButton);
        cultureButton.bringToFront();
        cultureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cultureText.getVisibility()==View.GONE)
                    cultureText.setVisibility(View.VISIBLE);
                else
                    cultureText.setVisibility(View.GONE);
                if(northText.getVisibility()==View.VISIBLE)
                    northText.setVisibility(View.GONE);
                if(southText.getVisibility()==View.VISIBLE)
                    southText.setVisibility(View.GONE);
                if(foodText.getVisibility()==View.VISIBLE)
                    foodText.setVisibility(View.GONE);
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

        foodText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthFoodMain.class);
                    startActivity(intent);
                    finish();
                }else{
                    makeDialog();
                }
            }
        });

        cultureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthCultureMain.class);
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
        Log.v("favoriteGage","enter resume");
        favoriteText.setText(String.valueOf(favoriteGage));
    }



    private void insertDataToGame(){
        String sql="SELECT * FROM WordDictionaryDatas WHERE question not null";
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
        String sql="SELECT * FROM WordDictionaryDatas";
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

    private void insertDataToCulture(){
        String sql="SELECT * FROM CultureData";
        Cursor cur = mDb.rawQuery(sql, null);

        if (cur != null) {
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                String name = cur.getString(1);
                String title = cur.getString(2);
                String detail=cur.getString(3);
                String question=cur.getString(4);
                String answer=cur.getString(5);
                String check=cur.getString(6);
                //Log.v("checkWordGame","add new value, id: "+id);
                if(id==2){
                    String[] res=question.split("평양");
                    question=res[0]+"\n평양 "+res[1];
                }
                if(id==4)
                    question="만수대는 평양시에 위치한 OO지대야.";
                if(id==8){
                    String[] res=question.split("생전");
                    question=res[0]+"\n생전 "+res[1];
                }
                if(id==9){
                    String[] res=question.split("노동자,");
                    question=res[0]+"노동자,\n"+res[1];
                }
                if(name.equals("금수산기념궁전")) {
                    name = "금수산태양궁전";
                    title="금수산태양궁전";
                }
                cultureList.add(new CultureData(id,name,title,detail,question,answer,check));
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

    private void easterEgg(){
        editor.putInt("favoriteGage",90);
        editor.apply();
        favoriteGage=test.getInt("favoriteGage",90);
        favoriteText.setText(String.valueOf(favoriteGage));
    }

    private void sendImgRequest(String neededIn){
        Intent intent = new Intent(MainActivity.this, setViewImg.class);
        intent.putExtra("sender",neededIn);
        if(neededIn.equals("foodData"))
            ;
        else
            intent.putExtra("num", 10);    //값 전달
        startActivity(intent);
        //finish();
    }


}

