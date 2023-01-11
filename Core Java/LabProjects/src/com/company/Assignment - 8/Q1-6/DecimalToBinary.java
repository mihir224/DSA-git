package com.company;

import java.util.Scanner;
//MIHIR SAINI 9920102054 E2
public class DecimalToBinary {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = new int[10];
        int i=0;
        System.out.println("Enter the number to convert to binary: ");
        int n=scanner.nextInt();

    for(i=0;n>0;i++)
    {
        arr[i]=n%2;
        n=n/2;
    }

for(i= i-1;i>=0;i--)
    {
        System.out.println(arr[i]);
    }
try{
   throw new myException("222.5");
}
catch (myException ex){
    System.out.println("Caught");
    System.out.println(ex.getMessage());
}
    }
}
class myException extends Exception{
    public myException(String s){
        super(s);
    }
}