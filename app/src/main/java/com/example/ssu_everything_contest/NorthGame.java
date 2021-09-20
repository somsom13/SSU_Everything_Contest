package com.example.ssu_everything_contest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.MODE_PRIVATE;

public class NorthGame extends Fragment{
    private TextView question,progressGage,favorite,wordView;
    private TextView a1,a2,a3,a4;
    int progressCount;
    int favoriteGage;
    int res;

    private int realAnswer;
    SharedPreferences test;
    SharedPreferences.Editor editor;

    ImageView nCharacterNormal;
    ImageView sCharacterNormal;
    ImageView nCharacterAngry;
    ImageView sCharacterSad;
    ImageView nCharacterSmile;
    ImageView sCharacterSmile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.north_game,container,false);

        ImageButton home= (ImageButton) rootView.findViewById(R.id.homeIconButton);
        progressGage=(TextView) rootView.findViewById(R.id.progress);
        question=(TextView) rootView.findViewById(R.id.tourQuestion);
        a1=(TextView) rootView.findViewById(R.id.choose1);
        a2=(TextView) rootView.findViewById(R.id.choose2);
        a3=(TextView) rootView.findViewById(R.id.choose3);
        a4=(TextView) rootView.findViewById(R.id.choose4);
        favorite=(TextView) rootView.findViewById(R.id.tour_favorite);
        wordView=(TextView)rootView.findViewById(R.id.tourText);

        test = this.getActivity().getSharedPreferences("test", MODE_PRIVATE);
        editor = test.edit();
        favoriteGage=test.getInt("favoriteGage",100);
        progressCount=test.getInt("progressCount",0);

        nCharacterNormal=rootView.findViewById(R.id.tourNorthChar);
        sCharacterNormal=rootView.findViewById(R.id.tourSouthChar);
        nCharacterAngry=rootView.findViewById(R.id.character1angry);
        sCharacterSad= rootView.findViewById(R.id.character2sad);
        nCharacterSmile=rootView.findViewById(R.id.character1smile);
        sCharacterSmile=rootView.findViewById(R.id.character2smile);

        nCharacterAngry.setVisibility(View.INVISIBLE);
        sCharacterSad.setVisibility(View.INVISIBLE);
        nCharacterSmile.setVisibility(View.INVISIBLE);
        sCharacterSmile.setVisibility(View.INVISIBLE);

        View.OnClickListener answerClick=new View.OnClickListener(){
            //int res;
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.choose1:
                        res=checkAnswer(1);
                        progressCount++;
                        favorite.setText(String.valueOf(favoriteGage));
                        waitAndProgress();
                        break;
                    case R.id.choose2:
                        res=checkAnswer(2);
                        progressCount++;
                        favorite.setText(String.valueOf(favoriteGage));
                        waitAndProgress();
                        break;
                    case R.id.choose3:
                        res=checkAnswer(3);
                        progressCount++;
                        favorite.setText(String.valueOf(favoriteGage));
                        waitAndProgress();
                        break;
                    case R.id.choose4:
                        res=checkAnswer(4);
                        progressCount++;
                        favorite.setText(String.valueOf(favoriteGage));
                        waitAndProgress();
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
        if(progressCount==50){
            Log.v("checkGame","end of game list, go to NorthSuccess");
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            NorthSuccess fragment2 = new NorthSuccess();
            transaction.replace(R.id.container, fragment2);
            transaction.commit();
        }

        wordView.setVisibility(View.INVISIBLE);
       nCharacterNormal.setVisibility(View.VISIBLE);
       sCharacterNormal.setVisibility(View.VISIBLE);
       nCharacterSmile.setVisibility(View.INVISIBLE);
       sCharacterSmile.setVisibility(View.INVISIBLE);
       nCharacterAngry.setVisibility(View.INVISIBLE);
       sCharacterSad.setVisibility(View.INVISIBLE);
        progressGage.setText((progressCount+1)+"/50");
        WordGameList wg=MainActivity.wordGameListList.get(progressCount);
        realAnswer=wg.realAnswer;
        setTextView(wg.question,wg.a1,wg.a2,wg.a3,wg.a4,favoriteGage);

        applyPreference();
   }


    private int checkAnswer(int chosen){
        showWord();
        if(chosen==realAnswer) {
            Log.v("checkGame", "correct answer!, favoriteGage: "+(favoriteGage+=2));
            //dialog 표시, 호감도 up
            //sendDialog("correct");
            Toast.makeText(getActivity(),"+2p",Toast.LENGTH_SHORT).show();
            question.setText("말이 통하는 친구네~");
            return 1;
        }else{
            Log.v("checkGame","wrong answer! favoriteGage: "+(favoriteGage-=3));
            Toast.makeText(getActivity(),"-3p",Toast.LENGTH_SHORT).show();

            question.setText("동문서답이네...내 얘기 안듣고 있나?");

            if(favoriteGage<100){
                applyPreference();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                NorthFail fragment3 = new NorthFail();
                transaction.replace(R.id.container, fragment3);
                transaction.commit();
            }
            return 0;
        }

    }

    private void setTextView(String q,String an1,String an2, String an3, String an4,int favNum){
        question.setText(q);
        a1.setText(an1);
        a2.setText(an2);
        a3.setText(an3);
        a4.setText(an4);
        favorite.setText(String.valueOf(favNum));
        return;
    }

    private void sendDataToActivity(int res) {
        Intent intent = new Intent(getActivity(), NorthMain.class);
        intent.putExtra("quizResult", res);//1:ok, 0:wrong
        startActivity(intent);
    }

    private void applyPreference(){
        editor.putInt("favoriteGage",favoriteGage);
        editor.putInt("progressCount",progressCount);
        editor.apply();
    }


    private void waitAndProgress(){

        Handler handler=new Handler();
        if (res == 1) {
            nCharacterNormal.setVisibility(View.INVISIBLE);
            sCharacterNormal.setVisibility(View.INVISIBLE);
            nCharacterSmile.setVisibility(View.VISIBLE);
            sCharacterSmile.setVisibility(View.VISIBLE);
        } else {
            nCharacterNormal.setVisibility(View.INVISIBLE);
            sCharacterNormal.setVisibility(View.INVISIBLE);
            nCharacterAngry.setVisibility(View.VISIBLE);
            sCharacterSad.setVisibility(View.VISIBLE);
        }
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                doProgress();
            }
        }    ,2000);

    }

    private void showWord(){
        WordGameList getAnswer=MainActivity.wordGameListList.get(progressCount);
        String sword=getAnswer.sword;
        String nword=getAnswer.nword;
        String content=nword+" : "+sword;
        SpannableString spannableString=new SpannableString(content);
        int start=content.indexOf(sword);
        int end=start+sword.length();
        Log.v("checkSpan","start: "+start+", end: "+end);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#C28FF0")),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordView.setText(spannableString);
        wordView.setVisibility(View.VISIBLE);
    }



}
