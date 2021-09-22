package com.example.ssu_everything_contest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NorthFoodMain extends AppCompatActivity {
    NorthFoodMenu nFoodMenu;
    NorthFoodGame nFoodGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.north_food_main);

        nFoodMenu=new NorthFoodMenu();
        nFoodGame=new NorthFoodGame();

        getSupportFragmentManager().beginTransaction().replace(R.id.container_north_food, nFoodMenu).commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.container_north_food,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(NorthFoodMain.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
