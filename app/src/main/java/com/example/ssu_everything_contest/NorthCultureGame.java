package com.example.ssu_everything_contest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import static android.content.Context.MODE_PRIVATE;
import static com.example.ssu_everything_contest.MainActivity.cultureList;
public class NorthCultureGame extends Fragment {
    ImageView middle;
    ImageView northCharImg;
    ImageView southCharImg;
    EditText tourAnswer;
    TextView tourQuestion;
    Button submit;
    TextView favorite;
    TextView tourProgressText;
    SharedPreferences test;
    SharedPreferences.Editor editor;
    private int favoriteGage;
    private int tourProgress;
    private String realAnswer;
    private int isQuestion=0;
    private int wrongCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.north_culture_game, container, false);

        middle= rootView.findViewById(R.id.middle);
        northCharImg=rootView.findViewById(R.id.tourNorthChar);
        southCharImg=rootView.findViewById(R.id.tourSouthChar);
        tourQuestion=rootView.findViewById(R.id.tourQuestion);
        tourAnswer=rootView.findViewById(R.id.tourAnswer);
        submit=rootView.findViewById(R.id.submitTour);
        ImageButton home= rootView.findViewById(R.id.homeIconButton);
        favorite=rootView.findViewById(R.id.tour_favorite);
        tourProgressText=rootView.findViewById(R.id.tourprogress);

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("tourProgress",0);
                editor.apply();
                tourProgress=test.getInt("tourProgress",0);
                doProgress();
            }
        });

        tourProgressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("tourProgress",9);
                editor.apply();
                tourProgress=test.getInt("tourProgress",9);
                doProgress();
            }
        });

        test = this.getActivity().getSharedPreferences("test", MODE_PRIVATE);
        editor = test.edit();
        favoriteGage=test.getInt("favoriteGage",100);
        tourProgress=test.getInt("tourProgress",0);
        if(!MainActivity.test.contains("tourWrongCount")){
            MainActivity.editor.putInt("tourWrongCount",0);
            editor.commit();
        }
        if(!test.contains("tourEnd")){
            editor.putInt("tourEnd",0);
            editor.commit();
        }

        wrongCount=MainActivity.test.getInt("tourWrongCount",0);


        View.OnClickListener onClickListener=new View.OnClickListener(){
            //int res;
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.homeIconButton:
                        Intent intent =new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.submitTour:
                        if(isQuestion==-1) {
                            String answer = String.valueOf(tourAnswer.getText());
                            //tourAnswer.setText("");
                            int res = checkAnswer(answer);
                            tourProgress++;
                            favorite.setText(String.valueOf(favoriteGage));
                            submit.setVisibility(View.INVISIBLE);
                            waitAndProgress();
                        }else{
                            isQuestion=-1;
                            //////tourQuestion.setVisibility(View.INVISIBLE);
                            tourAnswer.setVisibility(View.VISIBLE);
                        }
                        break;
                    default:

                }
            }
        };

        home.setOnClickListener(onClickListener);
        submit.setOnClickListener(onClickListener);

        doProgress();

        return rootView;

    }

    private void doProgress(){
        applyPreference();
        if(tourProgress==10){
            Log.v("checkGame","end of game list, go to NorthSuccess");
            if(test.getInt("tourEnd",0)!=1)
                editor.putInt("heartCount",test.getInt("heartCount",0)+1);
            editor.putInt("tourProgress",0);
            editor.putInt("tourEnd",1);
            editor.apply();
            Intent intent=new Intent(getActivity(),MainActivity.class);
            Toast.makeText(getContext(),"관광지 퀴즈를 성공적으로 끝냈습니다!",Toast.LENGTH_LONG).show();
            startActivity(intent);
            getActivity().finish();

        }else if(MainActivity.test.getInt("tourWrongCount",0)>=5){
            Log.v("checkGame","tour wrong count: "+wrongCount);
            Toast.makeText(getContext(),"관광지 퀴즈를 5문제 이상 틀리셨습니다! 다시 도전해주세요.",Toast.LENGTH_SHORT).show();
            MainActivity.editor.putInt("tourWrongCount",0);
            MainActivity.editor.putInt("tourProgress",0);
            MainActivity.editor.apply();
            Intent intent=new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        else {
            //tourQuestion.setVisibility(View.VISIBLE);
            tourAnswer.setVisibility(View.INVISIBLE);
            tourAnswer.setText("");
            tourProgressText.setText((tourProgress + 1) + "/10");
            favorite.setText(String.valueOf(favoriteGage));
            CultureData cd = cultureList.get(tourProgress);
            realAnswer = cd.answer;
            setViewQuestion(cd.question, cd._id, cd.name);


        }
    }

    private void applyPreference(){
        editor.putInt("favoriteGage",favoriteGage);
        editor.putInt("tourProgress",tourProgress);
        editor.apply();
    }

    private void setViewQuestion(String question, int id,String name){
        submit.setVisibility(View.INVISIBLE);
        tourAnswer.setVisibility(View.INVISIBLE);
        submit.bringToFront();
        middle.setImageResource(setViewImg.idListForTourData[id-1]);
        northCharImg.setImageResource(R.drawable.north_charac_normal);
        southCharImg.setImageResource(R.drawable.south_charac_normal);
        //Log.v("checkCulture","middle background: "+setViewImg.idListForTourData[id-1]);
        //tourQuestion.setBackgroundResource(R.drawable.right_question_black);
        tourQuestion.setText("여기는 "+name+"(이)야");
        isQuestion=0;

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Log.v("checkQuestion",question);
                submit.setVisibility(View.VISIBLE);
                tourQuestion.setText(question);
            }
        }    ,3000);
        return;
    }

    private int checkAnswer(String answer){
        if(answer.equals(realAnswer)||answer.equals("16번")) {
            favoriteGage+=3;
            northCharImg.setImageResource(R.drawable.north_charac_smile);
            southCharImg.setImageResource(R.drawable.south_charac_smile);
            Toast.makeText(getActivity(),"+3p",Toast.LENGTH_SHORT).show();
            tourQuestion.setText("잘 알고 있네~!");
            return 1;
        }else{
            //Log.v("checkGame","wrong answer! favoriteGage: "+(favoriteGage-=2));
            favoriteGage-=2;
            wrongCount+=1;
            MainActivity.editor.putInt("tourWrongCount",wrongCount);
            MainActivity.editor.apply();
            Log.v("checkGame","wrong count: "+MainActivity.test.getInt("tourWrongCount",0));
            Toast.makeText(getActivity(),"-2p",Toast.LENGTH_SHORT).show();
            northCharImg.setImageResource(R.drawable.north_charac_angry);
            southCharImg.setImageResource(R.drawable.south_charac_sad);
            //question.setText("동문서답이네...내 얘기 안듣고 있나?");
            tourQuestion.setText("정답은 "+realAnswer+"(이)야!!");
            return 0;
        }

    }

    private void waitAndProgress(){

        Handler handler=new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                //tourQuestion.setBackgroundResource(R.drawable.aleft1);
                tourAnswer.setVisibility(View.INVISIBLE);
                //submit.setVisibility(View.INVISIBLE);
                doProgress();
            }
        }    ,2000);

    }

}
