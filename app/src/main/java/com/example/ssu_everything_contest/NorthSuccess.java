package com.example.ssu_everything_contest;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NorthSuccess extends Fragment {
    ImageView division;
    ImageView division_N;
    ImageView division_S;
    TextView divisionText;

    ImageView one;
    ImageView one_N;
    ImageView one_S;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.north_success,container,false);

        division=rootView.findViewById(R.id.division);
        division_N=rootView.findViewById(R.id.division_north);
        division_S=rootView.findViewById(R.id.division_south);
        divisionText=rootView.findViewById(R.id.divisionText);

        one=rootView.findViewById(R.id.one);
        one_N=rootView.findViewById(R.id.one_north);
        one_S=rootView.findViewById(R.id.one_south);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                division.setVisibility(View.INVISIBLE);
                division_N.setVisibility(View.INVISIBLE);
                division_S.setVisibility(View.INVISIBLE);
                divisionText.setVisibility(View.INVISIBLE);

                one.setVisibility(View.VISIBLE);
                one_N.setVisibility(View.VISIBLE);
                one_S.setVisibility(View.VISIBLE);
            }
        },5000);
        return rootView;
    }


}
