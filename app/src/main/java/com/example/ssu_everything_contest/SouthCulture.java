package com.example.ssu_everything_contest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.example.ssu_everything_contest.MainActivity.cultureList;
import static com.example.ssu_everything_contest.MainActivity.mDb;

public class SouthCulture extends AppCompatActivity {
    //public static ArrayList<CultureData> cultureList=new ArrayList<>();
    private int favoriteGage;
    private int addedScore=0;
    public static int[] cultureLid;

    public static CultureDataAdapter cultureDataAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_culture);

        makeDialogForIntro();
        ListView listView=(ListView)findViewById(R.id.southCultureListView);
        cultureDataAdapter=new CultureDataAdapter(this,cultureList);
        favoriteGage=MainActivity.test.getInt("favoriteGage",0);

        String savedString = MainActivity.test.getString("cultureData", "");
        StringTokenizer st = new StringTokenizer(savedString, ",");
        cultureLid = new int[10];
        for (int i = 0; i < 10; i++) {
            cultureLid[i] = Integer.parseInt(st.nextToken());
        }

        //updateCultureList();
        listView.setAdapter(cultureDataAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int pos=listView.getFirstVisiblePosition();

                Intent intent = new Intent(SouthCulture.this, CultureDetail.class);
                intent.putExtra("position", position);
                startActivity(intent);
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int res=cultureDataAdapter.changeStatus(position);
                        parent.setAdapter((cultureDataAdapter));
                        listView.setSelection(pos);
                        if(res==1) {
                            MainActivity.editor.putInt("favoriteGage", favoriteGage += 2);
                            MainActivity.editor.apply();
                            addedScore += 2;
                        }
                        Log.v("checkCulture","added score: "+addedScore);
                        Log.v("checkCulture","favorite Gage: "+favoriteGage);
                    }
                }    ,2000);

            }
       });



        //나중에 관광페이지에서, 만약 모든 list의 check값이 true면 접근가능
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("checkCulture","enter back pressed");
        Toast.makeText(this,"학습 점수: "+addedScore,Toast.LENGTH_LONG).show();
        addedScore=0;
        finish();


    }

    private void makeDialogForIntro(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("북한의 관광지를 투어하기 위해 문화,사회,역사를 미리 배워보세요!\n한 카드마다 +2점 됩니다.");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



}
