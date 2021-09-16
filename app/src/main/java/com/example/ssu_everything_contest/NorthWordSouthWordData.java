package com.example.ssu_everything_contest;

public class NorthWordSouthWordData {
    String nWord;
    String sWord;
    boolean check;

    public NorthWordSouthWordData(String nWord, String sWord,boolean check) {
        this.nWord=nWord;
        this.sWord=sWord;
        this.check=check;
    }

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
