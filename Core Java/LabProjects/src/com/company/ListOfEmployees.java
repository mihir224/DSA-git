package com.company;

import java.util.ArrayList;
import java.util.StringTokenizer;

//MIHIR SAINI 9920102054 E2
public class ListOfEmployees {
    String str;
    StringTokenizer st;

    public ListOfEmployees(String str, StringTokenizer st) {
        this.str = str;
        this.st = st;
    }

    public void printTokens(StringTokenizer stringTokenizer){
        while(stringTokenizer.hasMoreTokens()){
            System.out.println(stringTokenizer.nextToken());
        }
    }

    public static void main(String[] args) {
        String str=new String("MIHIR SAINI, AJITESH MISHRA, ADEEB MOHSIN");
        StringTokenizer st=new StringTokenizer(str);
        ListOfEmployees listOfEmployees=new ListOfEmployees(str,st);
    }

}
