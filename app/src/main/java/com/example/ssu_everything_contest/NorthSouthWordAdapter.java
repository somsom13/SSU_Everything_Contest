package com.example.ssu_everything_contest;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Color;
import android.util.Log;
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
            southText.setTextColor(Color.parseColor("#ffffff"));
        } else { //아직 안누른거
            southText.setTextColor(Color.parseColor("#5A8CCA"));
        }

        return view;
    }

    public int changeStatus(int position) {
        int check;
        NorthWordSouthWordData temp=words.get(position);
        int id=temp.getID();
        Log.v("SQLcheck","sword: "+id);
        if(temp.getCheck()==false) {
            temp.setCheck(true);
            check=1;
            String sql="UPDATE WordDictionaryData SET _CHECK='O' WHERE _ID="+id;
            try{
                MainActivity.mDb.execSQL(sql);
            }catch (SQLException e){
                Log.v("SQLcheck","update error");
            }
        }
        else {
            temp.setCheck(false);
            check=-1;
            String sql="UPDATE WordDictionaryData SET _CHECK='X' WHERE _ID="+id;
            try{
                MainActivity.mDb.execSQL(sql);
            }catch (SQLException e){
                e.printStackTrace();
                Log.v("SQLcheck","update error");
            }
        }
        words.set(position,temp);
        return check;
    }
}
