package com.example.ssu_everything_contest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class NorthFoodGame extends Fragment {
    public static NorthFoodGame newInstance() {
        return new NorthFoodGame();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.north_food_game,container,false);
        return rootView;
    }

}
