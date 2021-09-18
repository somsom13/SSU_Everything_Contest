package com.example.ssu_everything_contest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.ArrayList;
import java.util.List;

public class SouthMain extends AppCompatActivity {
    public static int favoriteGage;
    SharedPreferences test;
    EditText inputWord;
    ImageButton searchButton;
    String searchWord;
    private int addedScore=0;

    //ArrayList<NorthWordSouthWordData> nsWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_main);

        inputWord=(EditText) findViewById(R.id.searchWord);
        searchButton=(ImageButton) findViewById(R.id.searchButton);

        //this.InitializeWord();
        test = getSharedPreferences("test", MODE_PRIVATE);
        SharedPreferences.Editor editor = test.edit();
        favoriteGage=test.getInt("favoriteGage",50);




        ListView listView=(ListView)findViewById(R.id.southListView);
        final NorthSouthWordAdapter nsAdapter=new NorthSouthWordAdapter(this,MainActivity.nsWordList);


        listView.setAdapter(nsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int pos=listView.getFirstVisiblePosition();
                int valueForUpdateFavorite=nsAdapter.changeStatus(position);
                favoriteGage+=valueForUpdateFavorite;
                addedScore+=valueForUpdateFavorite;
                editor.putInt("favoriteGage",favoriteGage);
                editor.apply();
                //Log.v("checkSouth","update favoriteGage: "+favoriteGage);
                parent.setAdapter((nsAdapter));
                listView.setSelection(pos);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int listSize=MainActivity.nsWordList.size();
                searchWord=inputWord.getText().toString();
                Log.v("checkSouth","insert value: "+searchWord);
                if(searchWord.equals("")){
                    MainActivity.nsWordList.clear();
                    MainActivity.insertDataToDictionary();
                }else {
                    for (int i = 0; i <listSize; i++) {
                        NorthWordSouthWordData test= MainActivity.nsWordList.get(i);
                        if (test.nWord.equals(searchWord)) {
                            MainActivity.nsWordList.clear();
                            Log.v("checkSouth", "found value: " + searchWord);
                            MainActivity.nsWordList.add(test);
                            break;
                        }
                    }
                }
                nsAdapter.notifyDataSetChanged();
            }
        });

        makeDialog("firstAlert");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void initializeFavoriteGage() {
        favoriteGage=test.getInt("favoriteGage",100);
        Log.v("checkFavorite","favorite gage from resume in south: "+favoriteGage);
    }


    private void makeDialog(String alert) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(alert.equals("firstAlert"))
            builder.setTitle("").setMessage("학습한 단어를 클릭하세요!\n오른쪽 남한말이 표시되어 있으면 학습 완료한 단어, 표시되어 있지 않으면 학습이 안된 단어입니다.\n각 단어를 학습 완료할 때 마다 호감도가 +1 됩니다!");
        if(alert.equals("scoreAlert"))
            builder.setTitle("").setMessage("학습 점수: "+addedScore+"점");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.v("checkSouth","added score: "+addedScore);
        makeDialog("scoreAlert");
        addedScore=0;
        Intent intent=new Intent(SouthMain.this,MainActivity.class);
        Log.v("checkFavorite","close south, check favorite: "+test.getInt("favoriteGage",10));
        startActivity(intent);
        finish();
    }


}

