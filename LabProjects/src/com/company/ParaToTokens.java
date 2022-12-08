package com.company;

//MIHIR SAINI 9920102054 E2
import java.util.StringTokenizer;
class myException1 extends Exception{
    public myException1(String s){
        super(s);
    }
}
public class ParaToTokens {
    public static void main(String[] args) {
        ParaToTokens paraToTokens=new ParaToTokens("mihir");
        try{
            throw new myException1("mihir");
        }
        catch(myException1 ex){
            System.out.println("Caught");
            System.out.println(ex.getMessage());
        }

    }
    String str;
    StringTokenizer st;

    public ParaToTokens(String str) {
        this.str = str;
        this.st = new StringTokenizer(str,",");
    }
    public void printTokens(StringTokenizer stringTokenizer){
        while(stringTokenizer.hasMoreTokens()){
            System.out.println(stringTokenizer.nextToken());
        }
    }


}
