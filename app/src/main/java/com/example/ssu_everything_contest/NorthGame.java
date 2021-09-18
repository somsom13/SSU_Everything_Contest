package com.example.ssu_everything_contest;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class NorthGame extends Fragment{
    private TextView question,progressGage,favorite;
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
        question=(TextView) rootView.findViewById(R.id.northSay);
        a1=(TextView) rootView.findViewById(R.id.choose1);
        a2=(TextView) rootView.findViewById(R.id.choose2);
        a3=(TextView) rootView.findViewById(R.id.choose3);
        a4=(TextView) rootView.findViewById(R.id.choose4);
        favorite=(TextView) rootView.findViewById(R.id.fav);

        test = this.getActivity().getSharedPreferences("test", MODE_PRIVATE);
        editor = test.edit();
        favoriteGage=test.getInt("favoriteGage",100);
        progressCount=test.getInt("progressCount",0);

        nCharacterNormal=rootView.findViewById(R.id.character1normal);
        sCharacterNormal=rootView.findViewById(R.id.character2normal);
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
        if(chosen==realAnswer) {
            Log.v("checkGame", "correct answer!, favoriteGage: "+(favoriteGage+=2));
            //dialog 표시, 호감도 up
            //sendDialog("correct");
            Toast.makeText(getActivity(),"+2p",Toast.LENGTH_SHORT).show();
            question.setText("말이 통하는 친구네~");
            return 1;
        }else{
            Log.v("checkGame","wrong answer! favoriteGage: "+(favoriteGage-=3));
            //dialog표시, 호감도 up, 진짜 정답 표시 (background 컬러 변경)
            //sendDialog("wrong");
            Toast.makeText(getActivity(),"-3p",Toast.LENGTH_SHORT).show();

            question.setText("동문서답이네...내 얘기 안듣고 있나?");
            WordGameList getAnswer=MainActivity.wordGameListList.get(progressCount);
            String sword=getAnswer.sword;
            String nword=getAnswer.nword;

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

    private void sendDialog(String result){
        Log.v("checkGame","enter send dialog");
        Bundle args=new Bundle();
        args.putString("answerResult",result);
        DialogFragment dialogFragment=new MyDialogFragment();
        dialogFragment.setArguments(args);
        dialogFragment.show(getActivity().getSupportFragmentManager(),"test");
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



}
