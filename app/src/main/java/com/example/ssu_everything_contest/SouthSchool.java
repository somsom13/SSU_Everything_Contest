package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SouthSchool extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_school);
        Button b1=(Button) findViewById(R.id.button);
        Button toFood=(Button) findViewById(R.id.button2);
        Button toCulture=(Button) findViewById(R.id.button3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SouthMain.class);
                startActivity(intent);
            }
        });

        toFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SouthFoodMain.class);
                startActivity(intent);
            }
        });

        toCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SouthCulture.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(SouthSchool.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
