package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CultureDetail extends AppCompatActivity {
    public static int position=-1;
    public static int success=0;
    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.culture_detail);

        TextView title=(TextView)findViewById(R.id.showTitle);
        ImageView image=(ImageView)findViewById(R.id.showImage);
        content=(TextView)findViewById(R.id.showContent);
        Button btn=(Button) findViewById(R.id.culture_ok);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras(); //Extra들을 가져옴
        int pos = bundle.getInt("position");   //가져온 Extars 중에서 꺼내기

        CultureData newData=MainActivity.cultureList.get(pos);
        title.setText(newData.getCultureTitle());
        //content.setText(newData.getCultureContent());
        setContentText(newData.getCultureContent());
        image.setImageResource(setViewImg.idListForCultureData[pos]);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position=pos;
                success=1;
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        position=-1;
        success=0;
        finish();
    }

    public static int[] returnResult(){
        int[] res=new int[2];
        res[0]=position;
        res[1]=success;
        return res;
    }

    private void setContentText(String contentText){
        String myText="";
        String[] splitRes=contentText.split("n");
        for(String s:splitRes){
            s=s.substring(0,s.length()-1);
            myText+=s+"\n";
        }
        content.setText(myText);
    }
}
