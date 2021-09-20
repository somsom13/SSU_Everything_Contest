package com.example.ssu_everything_contest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SouthFoodMain extends AppCompatActivity {

    SouthFoodAlcohol Falcohol;
    SouthFoodNormal Fnormal;
    SouthFoodPublic Fpublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_food_main);

        Falcohol = new SouthFoodAlcohol();
        Fnormal = new SouthFoodNormal();
        Fpublic = new SouthFoodPublic();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, Fpublic).commit();

        //퍼블릭으로 가는 버튼
        Button pButton = (Button) findViewById(R.id.publicButton);
        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, Fpublic).commit();
            }
        });

        //알코올로 가는 버튼
        Button aButton = (Button) findViewById(R.id.alcoholButton);
        aButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, Falcohol).commit();
            }
        });

        //일반식당로 가는 버튼
        Button nButton = (Button) findViewById(R.id.normalButton);
        nButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, Fnormal).commit();
            }
        });
    }
}
