package com.example.ssu_everything_contest;

public class NorthWordSouthWordData {
    int _id;
    String nWord;
    String sWord;
    boolean check;

    public NorthWordSouthWordData(int id,String sWord, String nWord,boolean check) {
        this._id=id;
        this.nWord=nWord;
        this.sWord=sWord;
        this.check=check;
    }

    public int getID(){ return _id;}

    public void setID(int id){ this._id=id;}
    public String getnWord() {
        return nWord;
    }

    public void setnWord(String nWord) {
        this.nWord = nWord;
    }

    public String getsWord() {
        return sWord;
    }

    public void setsWord(String sWord) {
        this.sWord = sWord;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
