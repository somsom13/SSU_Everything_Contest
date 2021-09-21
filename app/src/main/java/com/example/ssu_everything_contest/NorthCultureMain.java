package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NorthCultureMain extends AppCompatActivity {
    NorthCultureGame northCultureGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_culture_main);

        northCultureGame=new NorthCultureGame();
        int tourProgress=MainActivity.test.getInt("tourProgress",0);
        if(tourProgress==100){
            Toast.makeText(this,"이미 관광지 퀴즈를 완료하셨습니다!",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(NorthCultureMain.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            makeDialog();
            getSupportFragmentManager().beginTransaction().replace(R.id.container2, northCultureGame).commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NorthCultureMain.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void makeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("북한 친구의 관광지 설명을 듣고 빈칸에 들어갈 말을 입력해주세요!\n5개 이상 맞춰야 통과됩니다.");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
