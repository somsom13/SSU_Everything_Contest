package com.example.ssu_everything_contest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private int seeEnding,setImg=0;
    public static SharedPreferences test;
    public static SharedPreferences.Editor editor;
    private TextView favoriteText,heart1,heart2,heart3;
    TextView southText,northText,foodText, cultureText;
    private String userName;
    ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        southText=findViewById(R.id.southSoongsilText);
        northText= findViewById(R.id.northSoongsilText);
        foodText=findViewById(R.id.foodText);
        cultureText=findViewById(R.id.cultureText);
        heart1=findViewById(R.id.heart1);
        heart2=findViewById(R.id.heart2);
        heart3=findViewById(R.id.heart3);

        layout=(ConstraintLayout)findViewById(R.id.mainBackground);

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

        if(wordGameListList.size()==0)
            insertDataToGame();
        if(nsWordList.size()==0)
            insertDataToDictionary();
        if(cultureList.size()==0)
            insertDataToCulture();



        favoriteText=findViewById(R.id.tour_favorite);
        test = getSharedPreferences("test", MODE_PRIVATE);
        editor = test.edit();

        if(!test.contains("favoriteGage"))
            editor.putInt("favoriteGage", 0);
        if(!test.contains("progressCount"))
            editor.putInt("progressCount",0);
        if(!test.contains("tourProgress"))
            editor.putInt("tourProgress",0);
        if(!test.contains("userName")) {
            userName=makeDialogForName();
            makeDialogForIntro();
        }
        if(!test.contains("heartCount")){
            editor.putInt("heartCount",0);
        }if(!test.contains("seeEnding")){
            editor.putInt("seeEnding",0);
        }
        editor.commit(); //완료한다.

        favoriteGage=test.getInt("favoriteGage",100);
        favoriteText.setText(String.valueOf(favoriteGage));
        userName=test.getString("userName","none");
        seeEnding=test.getInt("seeEnding",0);
        int heartCount=test.getInt("heartCount",0);
        setHeartCount(heartCount);


        Log.v("checkHeart","get heart count: "+test.getInt("heartCount",0));

        if(favoriteGage>=100) { layout.setBackgroundResource(R.drawable.first_page_image); }
        else {layout.setBackgroundResource(R.drawable.division); }

        //북쪽 로딩으로 가는 버튼
        ImageButton goNorth= (ImageButton) findViewById(R.id.northButton);
        goNorth.bringToFront();
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //favoriteGage 따라 전환 설정
                northText.bringToFront();
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

            }
        });

        //남쪽 로딩으로 가는 버튼
        ImageButton goSouth=(ImageButton)findViewById(R.id.southButton);
        goSouth.bringToFront();
        goSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                southText.bringToFront();
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
                Intent intent=new Intent(getApplicationContext(),setViewImg.class);
                startActivity(intent);
                finish();
            }
        });

        northText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                    intent.putExtra("name","평양숭실");
                    startActivity(intent);
                    finish();
                }else{
                    makeDialog("평양숭실");
                }
            }
        });

        foodText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                    intent.putExtra("name","식당");
                    startActivity(intent);
                    finish();
                }else{
                    makeDialog("식당");
                }
            }
        });

        cultureText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test.getInt("favoriteGage",0)>=100){
                    Intent intent =new Intent(getApplicationContext(),NorthLoading.class);
                    intent.putExtra("name","관광지");
                    startActivity(intent);
                    finish();
                }else{
                    makeDialog("관광지");
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

                if(id==4)
                    question="만수대는 평양시에 위치한 OO지대야.";

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

    private void makeDialog(String word) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("호감도가 낮아서 "+word+"에 방문할 수 없습니다. \n호감도를 100이상으로 높여주세요!");
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

    private String makeDialogForName(){
        final EditText editText=new EditText(MainActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사용할 이름을 입력해주세요!");
        builder.setView(editText);
        builder.setIcon(R.drawable.south_charac_smile);
        builder.setPositiveButton("입력", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userName=editText.getText().toString();
                editor.putString("userName",userName);
                editor.commit();
                Toast.makeText(getApplicationContext(),"잘 부탁해, "+userName+"!",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return userName;
    }

    private void makeDialogForIntro(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("평양숭실에 다니는 친구와 친해지세요!\n 북한의 컨텐트 3개를 모두 통과하여 좌측상단의 하트 게이지를 채워주세요!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setHeartCount(int count){
        Log.v("checkHeart","enter setHeartCount, count: "+count);
        switch(count){
            case 3:
                heart3.setBackgroundResource(R.drawable.heart_empty);
            case 2:
                heart2.setBackgroundResource(R.drawable.heart_empty);
            case 1:
                heart1.setBackgroundResource(R.drawable.heart_empty);
                break;
            default:

        }

        if(count==3&&test.getInt("seeEnding",0)==0){
            editor.putInt("seeEnding",1);
            editor.apply();
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent successIntent=new Intent(MainActivity.this,NorthMain.class);
                    successIntent.putExtra("from","Main");
                    startActivity(successIntent);
                    finish();
                }
            }    ,2000);
        }
        return;
    }


}

