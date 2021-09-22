package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NorthLoading extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_loading);
        TextView name=findViewById(R.id.app_name);

        Intent it=getIntent();
        String dest=it.getExtras().getString("name");
        name.setText(dest+"로 이동중...");

        ImageView imageView = findViewById(R.id.iv_logo);
        Glide.with(this).load(R.raw.loading).into(imageView);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(dest.equals("평양숭실"))
                    intent = new Intent(NorthLoading.this, NorthMain.class);
                else if(dest.equals("식당"))
                    intent=new Intent(NorthLoading.this,NorthFoodMain.class);
                else
                    intent=new Intent(NorthLoading.this,NorthCultureMain.class);
                intent.putExtra("from","loading");
                startActivity(intent);
                finish();
            }
        }    ,2000);



    }
}
