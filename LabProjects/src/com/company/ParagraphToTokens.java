package com.company;
//MIHIR SAINI 9920102054 E2
import java.util.StringTokenizer;

public class ParagraphToTokens {
    public static void main(String[] args) {
        String para="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin augue nisi, cursus nec velit nec, condimentum laoreet ex. Morbi vitae suscipit dui, eu egestas ipsum.";
        StringTokenizer st=new StringTokenizer(para);
        ParagraphToTokens paragraphToTokens=new ParagraphToTokens(para, st);
        paragraphToTokens.printTokens(st);
    }
    String para;
    StringTokenizer st;
    public ParagraphToTokens(String para, StringTokenizer st) {
        this.para = para;
        this.st = st;
    }
    public void printTokens(StringTokenizer st){
        while(this.st.hasMoreTokens()){
            System.out.println(this.st.nextToken());
        }
}}
