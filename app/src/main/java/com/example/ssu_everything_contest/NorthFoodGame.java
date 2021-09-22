package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NorthFoodGame extends Fragment {
    private static int imgID;
    ImageView foodImage;
    String nEat, sEat;
    TextView nEatText, sEatText;
    private int Nprogress, Sprogress;

    public static NorthFoodGame newInstance(int foodID) {
        imgID = foodID;
        return new NorthFoodGame();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.north_food_game, container, false);
        Nprogress = 0;
        Sprogress = 0;
        nEat = "";
        sEat = "";
        nEatText = rootView.findViewById(R.id.northGameNeat);
        sEatText = rootView.findViewById(R.id.northGameSeat);
        if(!MainActivity.test.contains("foodEnd")){
            MainActivity.editor.putInt("foodEnd",0);
            MainActivity.editor.apply();
        }

        foodImage = rootView.findViewById(R.id.northGameFoodImage);
        foodImage.setImageResource(imgID);
        foodImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //남한애 먹는거
                if (Sprogress < 100) {
                    Sprogress += 1;
                    ProgressBar Sbar = (ProgressBar) rootView.findViewById(R.id.southProgress);
                    Sbar.setProgress(Sprogress);
                    if (Sprogress % 4 == 0) {
                        sEat += "뇸";
                        sEatText.setText(sEat);
                    }
                    if(Sprogress%10==0)
                        Log.v("checkFood","sprogress: "+Sprogress);
                }else{
                    gameEnd();
                }
            }
        });

        Handler handler = new Handler();
        ProgressBar Nbar = (ProgressBar) rootView.findViewById(R.id.northProgress);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Sprogress < 100&&Nprogress<101) {
                    if(Nprogress==100)
                        gameEnd();

                    Nprogress += 1;
                    if (Nprogress % 4 == 0) {
                        nEat += "냠";
                    }

                    if(Nprogress%10==0){
                        Log.v("checkFood","nprogress: "+Nprogress);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Nbar.setProgress(Nprogress);
                            if (Nprogress % 4 == 0) {
                                nEatText.setText(nEat);
                            }

                        }
                    });

                    try {
                        Thread.sleep(210);
                    } catch (InterruptedException e) {

                    }
                }

            }
        });
        t.start();

        /*
            이렇게 결과처리해야 되는데 안뜬다 어떻게 해야할지 모르겠다
        */

        //바로 위 주석부터 여기까지 실행이 안됨.....믿습니다 소멘..아 그 메인에 하트연결도........

        return rootView;
    }

    private void gameEnd(){
        if (Nprogress == 100 || Sprogress == 100) {

            if (Nprogress > Sprogress) { //게임 졌음
                makeToast("lose");
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else { //게임 이겼음
                int heart=MainActivity.test.getInt("heartCount",0);

                if(MainActivity.test.getInt("foodEnd",0)!=1) {
                    MainActivity.editor.putInt("heartCount", heart + 1);
                }
                MainActivity.editor.putInt("foodEnd",1);
                MainActivity.editor.apply();
                Log.v("checkHeart","food game win, heart: "+MainActivity.test.getInt("heartCount",1)+"food end: "+MainActivity.test.getInt("foodEnd",0));
                makeToast("win");
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void makeToast(String word){
        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Toast.makeText(getContext(),word,Toast.LENGTH_LONG).show();
            }
        },0);
    }

}
