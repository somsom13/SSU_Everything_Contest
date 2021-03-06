package com.example.ssu_everything_contest;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CultureDataAdapter extends BaseAdapter {
    Context mContext=null;
    LayoutInflater mLayoutInflater=null;
    ArrayList<CultureData> cultureData;

    public CultureDataAdapter(Context context, ArrayList<CultureData> word) {
        mContext=context;
        cultureData=word;
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return cultureData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public CultureData getItem(int position) {
        return cultureData.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view=mLayoutInflater.inflate(R.layout.south_culture_listview_item,null);
        TextView title=(TextView)view.findViewById(R.id.culture_card_text);
        ImageView image=(ImageView)view.findViewById(R.id.culture_image);
        ImageView checkButton=(ImageView) view.findViewById(R.id.culture_check);

        String content=cultureData.get(position).getCultureTitle();
        String origTitle=content;
        String myString=cultureData.get(position).getCultureContent().split("n")[0];
        myString=myString.substring(0,myString.length()-1);
        content+="\n"+myString;
        SpannableString spannableString=new SpannableString(content);
        int start=content.indexOf(origTitle);
        int end=start+origTitle.length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.7f), start, end, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(spannableString);

        //Log.v("checkCulture","getView position : "+position);
        if(position<=9) {
            int lid=SouthCulture.cultureLid[position];
            image.setImageResource(lid);//img ???????????? ????????? ??????!
        }


        if (cultureData.get(position).getCheck() == true) { //?????? ?????????
            checkButton.setImageResource(R.drawable.full_round);
        } else { //?????? ????????????
            checkButton.setImageResource(R.drawable.round);
        }
        return view;
    }

    public int changeStatus(int position) {
        //????????????????????? ????????? ????????? ????????? ?????? changeStatus??? ???????????? ?????? ??????
        int res=0;
        CultureData temp=cultureData.get(position);
        int id=temp.getID();
        if(temp.getCheck()==false) {
            res=1;
            temp.setCheck(true);
           String sql="UPDATE CultureData SET _CHECK='O' WHERE _ID="+id;
            try{
                MainActivity.mDb.execSQL(sql);
            }catch (SQLException e) {
                Log.v("SQLcheck", "update error");
            }
        }

        cultureData.set(position,temp);
        return res;
    }
}
