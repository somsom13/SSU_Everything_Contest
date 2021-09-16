package com.example.ssu_everything_contest;

public class WordDictionary {

    public int id;
    public String sword;
    public String nword;
    boolean check;

    WordDictionary(int id,String s,String n){
        this.id=id;
        this.sword=s;
        this.nword=n;
        this.check=false;
    }

    public void setCheck(boolean check1){
        this.check=check1;
    }

}


