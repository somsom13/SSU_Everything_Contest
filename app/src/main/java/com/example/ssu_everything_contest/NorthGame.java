package com.example.ssu_everything_contest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NorthGame extends Fragment{
    private TextView question,progressGage;
    private TextView a1,a2,a3,a4;
    private static int progressCount=0;
    private int realAnswer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.north_game,container,false);

        ImageButton home= (ImageButton) rootView.findViewById(R.id.homeIconButton);
        progressGage=(TextView) rootView.findViewById(R.id.progress);
        question=(TextView) rootView.findViewById(R.id.northSay);
        a1=(TextView) rootView.findViewById(R.id.choose1);
        a2=(TextView) rootView.findViewById(R.id.choose2);
        a3=(TextView) rootView.findViewById(R.id.choose3);
        a4=(TextView) rootView.findViewById(R.id.choose4);

        View.OnClickListener answerClick=new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.choose1:
                        checkAnswer(1);
                        progressCount++;
                        doProgress();
                        break;
                    case R.id.choose2:
                        checkAnswer(2);
                        progressCount++;
                        doProgress();
                        break;
                    case R.id.choose3:
                        checkAnswer(3);
                        progressCount++;
                        doProgress();
                        break;
                    case R.id.choose4:
                        checkAnswer(4);
                        progressCount++;
                        doProgress();
                        break;
                    case R.id.homeIconButton:
                        Intent intent =new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        //chosenAnswer=0;
                }
            }
        };

        a1.setOnClickListener(answerClick);
        a2.setOnClickListener(answerClick);
        a3.setOnClickListener(answerClick);
        a4.setOnClickListener(answerClick);
        home.setOnClickListener(answerClick);


        doProgress();

       return rootView;
    }


   private void doProgress(){
        if(progressCount==49){
            Log.v("checkGame","end of game list");
            return;
        }
        progressGage.setText((progressCount+1)+"/50");
        WordGameList wg=MainActivity.wordGameListList.get(progressCount);
        realAnswer=wg.realAnswer;
        setTextView(wg.question,wg.a1,wg.a2,wg.a3,wg.a4);
   }


    private int checkAnswer(int chosen){
        if(chosen==realAnswer) {
            Log.v("checkGame", "correct answer!, favoriteGage: "+(MainActivity.favoriteGage+=10));
            //dialog 표시, 호감도 up
            return 1;
        }else{
            Log.v("checkGame","wrong answer! favoriteGage: "+(MainActivity.favoriteGage-=10));
            //dialog표시, 호감도 up
            return 0;
        }

    }

    private void setTextView(String q,String an1,String an2, String an3, String an4){
        question.setText(q);
        a1.setText(an1);
        a2.setText(an2);
        a3.setText(an3);
        a4.setText(an4);
        return;
    }


}
