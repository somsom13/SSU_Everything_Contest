package com.example.ssu_everything_contest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NorthMain extends AppCompatActivity {
    NorthGame northGame;
    NorthFail northFail;
    NorthSuccess northSuccess;
    private static int progressCount=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_main);

        northGame=new NorthGame();
        northFail=new NorthFail();
        northSuccess=new NorthSuccess();

        Intent getintent=getIntent();
        String from=getintent.getExtras().getString("from");
        if(from.equals("Main")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, northSuccess).commit();
        }else {


            int wordGameProgress = MainActivity.test.getInt("progressCount", 0);
            if (wordGameProgress == 100) {
                makeDialog(0);
                Intent intent = new Intent(NorthMain.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, northGame).commit();
                makeDialog(1);
            }
        }


        

        Button goSouth=(Button)findViewById(R.id.failButton);
        goSouth.setVisibility(View.GONE);
        goSouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, northFail).commit();
            }
        });

        //성공으로 가는 버튼
        Button goNorth=(Button)findViewById(R.id.successButton);
        goNorth.setVisibility(View.GONE);
        goNorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, northSuccess).commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NorthMain.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void makeDialog(int request) {
        if (request == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("").setMessage("단어 퀴즈를 풀어주세요!\n맞으면 +2점, 틀리면 -3점 입니다.\n호감도가 100아래로 내려가지 않게 주의하세요!");
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(this, "이미 단어 퀴즈를 완료하셨습니다!\n", Toast.LENGTH_SHORT).show();
        }
    }

}
