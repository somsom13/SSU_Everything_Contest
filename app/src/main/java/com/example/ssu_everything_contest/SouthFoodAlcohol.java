package com.example.ssu_everything_contest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SouthFoodAlcohol extends Fragment {
    public SouthFoodAlcohol() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.south_food_alcohol,container,false);
        return rootView;
    }

}
