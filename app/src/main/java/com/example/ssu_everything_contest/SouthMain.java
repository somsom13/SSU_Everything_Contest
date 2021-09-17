package com.example.ssu_everything_contest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SouthMain extends AppCompatActivity {

    //ArrayList<NorthWordSouthWordData> nsWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_main);

        //this.InitializeWord();

        ListView listView=(ListView)findViewById(R.id.southListView);
        final NorthSouthWordAdapter nsAdapter=new NorthSouthWordAdapter(this,MainActivity.nsWordList);


        listView.setAdapter(nsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int pos=listView.getFirstVisiblePosition();
                nsAdapter.changeStatus(position);
                parent.setAdapter((nsAdapter));
                listView.setSelection(pos);
            }
        });

    }

    /*public void InitializeWord() {
        nsWordList=new ArrayList<NorthWordSouthWordData>();
        nsWordList.add(new NorthWordSouthWordData("얼음보숭이","아이스크림",true));
        nsWordList.add(new NorthWordSouthWordData("닭알두부","계란찜",false));
    }*/


}

