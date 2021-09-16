package com.example.ssu_everything_contest;

public class WordGameList {
    int id;
    String sword;
    String nword;
    String question;
    String a1;
    String a2;
    String a3;
    String a4;
    int realAnswer;

    WordGameList(int id,String s,String n,String q,String a1,String a2,String a3,String a4,int an){
        this.id=id;
        this.sword=s;
        this.nword=n;
        this.question=q;
        this.a1=a1;
        this.a2=a2;
        this.a3=a3;
        this.a4=a4;
        this.realAnswer=an;
    }

}
