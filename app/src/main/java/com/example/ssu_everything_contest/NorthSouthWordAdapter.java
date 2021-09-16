package com.example.ssu_everything_contest;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NorthSouthWordAdapter extends BaseAdapter {

    Context mContext=null;
    LayoutInflater mLayoutInflater=null;
    ArrayList<NorthWordSouthWordData> words;

    public NorthSouthWordAdapter(Context context, ArrayList<NorthWordSouthWordData> word) {
        mContext=context;
        words=word;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public NorthWordSouthWordData getItem(int position) {
        return words.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view=mLayoutInflater.inflate(R.layout.south_listview_item,null);
        TextView northText=(TextView)view.findViewById(R.id.nWord);
        TextView southText=(TextView)view.findViewById(R.id.sWord);

        northText.setText(words.get(position).getnWord());
        southText.setText(words.get(position).getsWord());
        if (words.get(position).getCheck() == true) { //이미 누른거
            southText.setTextColor(Color.parseColor("#ff0000"));
        } else { //아직 안누른거
            southText.setTextColor(Color.parseColor("#ffffff"));
        }

        return view;
    }

    public void changeStatus(int position) {
        NorthWordSouthWordData temp=words.get(position);
        temp.setCheck(true);
        words.set(position,temp);
    }
}
