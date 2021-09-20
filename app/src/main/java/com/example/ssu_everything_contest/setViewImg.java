package com.example.ssu_everything_contest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class setViewImg extends Activity {
    public static int[] idListForCultureData=new int[10];
    public static int[] idListForTourData=new int[10];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.south_culture);
        //ImageView image=(ImageView)findViewById(R.id.culture_image);

        Intent intent=getIntent();
        Bundle bundle = intent.getExtras(); //Extra들을 가져옴
        String sender=bundle.getString("sender");
        int num=bundle.getInt("num");
        Log.v("checkCulture","receive from: "+sender);
        if(sender.equals("cultureData")){
            for(int i=0;i<num;i++) {
                int lid = this.getResources().getIdentifier("culture_" + (i+1), "drawable", this.getPackageName());
                idListForCultureData[i]=lid;
            }
            Intent it=new Intent(setViewImg.this,MainActivity.class);
            finish();
        }else if(sender.equals("tourData")){
            for(int i=0;i<num;i++) {
                int lid = this.getResources().getIdentifier("culture_" + (i+1), "drawable", this.getPackageName());
                idListForTourData[i]=lid;
            }
            Intent it=new Intent(setViewImg.this,MainActivity.class);
            finish();
        }else{//foodData 에서 오는 경우!

        }
    }


}
