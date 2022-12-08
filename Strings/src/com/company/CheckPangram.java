package com.company;

public class CheckPangram {
    public static void main(String[] args) {
String str="Bawds jog, flick quartz, vex nymph";
        System.out.println(checkPangram(str));
    }
    static boolean checkPangram(String str){
//    int index=0;
//        for(int i=0;i<str.length();i++){
//            if('A'<=str.charAt(i)&&str.charAt(i)<='Z'){
//                index=str.charAt(i)-
//            }
//        }
        int index=0;
        boolean[] count=new boolean[26];
       for (int i=0;i<str.length();i++){
         if(str.charAt(i)>='a'&&str.charAt(i)<='z'){
             index=str.charAt(i)-'a';
         }
     else if(str.charAt(i)>='A'&&str.charAt(i)<='Z'){
         index=str.charAt(i)-'A';
         }
        count[index]=true;
       }
        for(int i=0;i<count.length;i++){
            if(!count[i]){
                return false;
            }
        }
        return true;
    }
}
