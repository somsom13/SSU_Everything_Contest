package com.example.ssu_everything_contest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;

public class SouthMain extends AppCompatActivity {
    public static int favoriteGage;
    SharedPreferences test;

    //ArrayList<NorthWordSouthWordData> nsWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_main);

        //this.InitializeWord();
        test = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = test.edit();
        favoriteGage=test.getInt("favoriteGage",50);
        Log.v("checkFavorite","got favoriteGage in south mainActivity: "+favoriteGage);

        ListView listView=(ListView)findViewById(R.id.southListView);
        final NorthSouthWordAdapter nsAdapter=new NorthSouthWordAdapter(this,MainActivity.nsWordList);


        listView.setAdapter(nsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int pos=listView.getFirstVisiblePosition();
                int valueForUpdateFavorite=nsAdapter.changeStatus(position);
                favoriteGage+=valueForUpdateFavorite;
                editor.putInt("favoriteGage",favoriteGage);
                editor.apply();
                Log.v("checkSouth","update favoriteGage: "+favoriteGage);
                parent.setAdapter((nsAdapter));
                listView.setSelection(pos);
            }
        });

        makeDialog();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void initializeFavoriteGage() {
        favoriteGage=test.getInt("favoriteGage",100);
        Log.v("checkFavorite","favorite gage from resume in south: "+favoriteGage);
    }


    private void makeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("학습한 단어를 클릭하세요!\n오른쪽 남한말이 표시되어 있으면 학습 완료한 단어, 표시되어 있지 않으면 학습이 안된 단어입니다.\n각 단어를 학습 완료할 때 마다 호감도가 +1 됩니다!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SouthMain.this,MainActivity.class);
        Log.v("checkFavorite","close south, check favorite: "+test.getInt("favoriteGage",10));
        startActivity(intent);
        finish();
    }


}

