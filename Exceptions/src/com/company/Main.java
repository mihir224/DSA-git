package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int x=getInt();
        System.out.println(x);
    }
    public static int getInt(){
        Scanner scanner =new Scanner(System.in);
        return scanner.nextInt();
    }
    public static int getIntLBYL(){
        Scanner scanner=new Scanner(System.in);
        boolean isValid=true;
        String str=scanner.nextLine();
        for(int i=0;i<=str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                isValid=false;
                break;
            }
        }
        if(isValid){
            return Integer.parseInt(str);
        }
        return 0;
    }
    public static int getIntEAFP(){
        Scanner scanner =new Scanner(System.in);
        try{
            return scanner.nextInt();
        }
        catch(InputMismatchException e){
            return 0;
        }
    }

    private static int divideLBYL(int x, int y){ //look before you leap approach
        if(y!=0){
            return x/y;
        }else{
            return 0;
        }
    }
    private static int divideEAFP(int x, int y){ //easy to ask for forgiveness than permission
        try {
            return x / y;
        }
            catch(ArithmeticException e){
            return 0;
        }
    }
}
