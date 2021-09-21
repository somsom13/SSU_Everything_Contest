package com.example.ssu_everything_contest;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodDataAdapter extends BaseAdapter {
    Context mContext=null;
    LayoutInflater mLayoutInflater=null;
    ArrayList<FoodData> foods;

    public FoodDataAdapter(Context context, ArrayList<FoodData> food) {
        mContext=context;
        foods=food;
        mLayoutInflater=LayoutInflater.from(mContext);
        Log.v("FOODDATA","adapter생성자까지는 성공!!!!!!!!!!!!!!!!!!!!11");
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return foods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String hihi() {
        return "hihi";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=mLayoutInflater.inflate(R.layout.north_food_listview_item,null);

        ImageView foodImage=(ImageView)view.findViewById(R.id.food_menu_image);
        TextView foodName=(TextView)view.findViewById(R.id.food_menu_name);
        TextView foodEx=(TextView)view.findViewById(R.id.food_menu_explanation);

        foodImage.setImageResource(foods.get(position).getFoodImage());
        foodName.setText(foods.get(position).getFoodName());
        foodEx.setText(foods.get(position).getFoodExplanation());

        return view;
    }
}
