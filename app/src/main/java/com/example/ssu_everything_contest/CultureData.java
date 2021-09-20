package com.example.ssu_everything_contest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class CultureData {
    //사진(base64), 제목(남한에 보여줄거), 상세내용(남한용), 학습여부, _id
    //관광지는 10개 밖에 안되니까 그냥 바로 db에서 가져와서 보여줘도 될듯
    int _id;
    String title;
    String content;
    boolean check;
    String name;
    String question;
    String answer;
    //Bitmap bitmap;

    public CultureData(int id,String name,String title, String content,String question,String answer,String check) {
        this._id=id;
        this.title=title;
        this.content=content;
        this.question=question;
        this.answer=answer;
        this.name=name;
        if(check.equals("O"))
            this.check=true;
        else
            this.check=false;
        /*byte[] decodedString = Base64.decode(base, Base64.DEFAULT);
        this.bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);*/
    }

    public int getID(){ return _id;}

    public void setID(int id){ this._id=id;}
    public String getCultureTitle() {
        return title;
    }

    public void setCultureTitle(String nWord) {
        this.title=title;
    }

    public String getCultureContent() {
        return content;
    }

    public void setCultureContent(String sWord) {
        this.content=content;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getQuestion(){return question;}
    public String getAnswer(){return answer;}

    /*public void setImageBitmap(Bitmap bitmap){ this.bitmap=bitmap;}

    public Bitmap getImageBitmap(){ return bitmap; }*/
}
