package com.example.ssu_everything_contest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class setViewImg extends Activity {
    public static int[] idListForCultureData=new int[10];
    public static int[] idListForTourData=new int[10];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.south_culture);
        //ImageView image=(ImageView)findViewById(R.id.culture_image);
        int num=10;
        setContentView(R.layout.south_loading);

        ImageView imageView = findViewById(R.id.iv_logo2);
        Glide.with(this).load(R.raw.loading).into(imageView);

        //Intent intent=getIntent();
        //Bundle bundle = intent.getExtras(); //Extra들을 가져옴
       // String sender=bundle.getString("sender");
        //int num=bundle.getInt("num");
        //Log.v("checkCulture","receive from: "+sender);
        /*if(sender.equals("cultureData")){
            for(int i=0;i<num;i++) {
                int lid = this.getResources().getIdentifier("culture_" + (i+1), "drawable", this.getPackageName());
                idListForCultureData[i]=lid;
            }
            finish();
        }else if(sender.equals("tourData")){
            for(int i=0;i<num;i++) {
                int lid = this.getResources().getIdentifier("tour_" + (i+1), "drawable", this.getPackageName());
                idListForTourData[i]=lid;
            }
            finish();
        }*/
        for(int i=0;i<num;i++) {
            int lid = this.getResources().getIdentifier("culture_" + (i+1), "drawable", this.getPackageName());
            idListForCultureData[i]=lid;
        }

        for(int i=0;i<num;i++) {
            int lid = this.getResources().getIdentifier("tour_" + (i+1), "drawable", this.getPackageName());
            idListForTourData[i]=lid;
        }

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(setViewImg.this, SouthSchool.class);
                startActivity(intent);
                finish();
            }
        }    ,2000);
    }


}
