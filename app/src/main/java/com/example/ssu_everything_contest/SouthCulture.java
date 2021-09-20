package com.example.ssu_everything_contest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.ssu_everything_contest.MainActivity.mDb;

public class SouthCulture extends AppCompatActivity {
    public static ArrayList<CultureData> cultureList=new ArrayList<>();

    public static CultureDataAdapter cultureDataAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.south_culture);

        ListView listView=(ListView)findViewById(R.id.southCultureListView);
        cultureDataAdapter=new CultureDataAdapter(this,cultureList);

        updateCultureList();
        listView.setAdapter(cultureDataAdapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                int pos=listView.getFirstVisiblePosition();
                Log.v("checkCulture","position: "+position);
                //상세페이지로 이동 후, 거기서 finish(), 학습 완료 여부 전달
                /*if(어쩌구 받은 값==1)
                    cultureDataAdapter.changeStatus(position);*/
                Intent intent = new Intent(SouthCulture.this, CultureDetail.class);
                intent.putExtra("position", position);    //값 전달
                startActivity(intent);  //다음 activity로 넘어가기
                //finish();
                Log.v("checkCulture","여기로 돌아옴!");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cultureDataAdapter.changeStatus(position);
                        parent.setAdapter((cultureDataAdapter));
                        listView.setSelection(pos);
                    }
                }    ,3000);

            }
       });



        //나중에 관광페이지에서, 만약 모든 list의 check값이 true면 접근가능
    }

    private void updateCultureList(){
        String sql="SELECT * FROM CultureData";
        Cursor cur = mDb.rawQuery(sql, null);

        if (cur != null) {
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                //String name = cur.getString(1);
                String title = cur.getString(2);
                String detail=cur.getString(3);
                String check=cur.getString(6);
                //Log.v("checkWordGame","add new value, id: "+id);
                cultureList.add(new CultureData(id,title,detail,check));
            }
        }
    }

}
