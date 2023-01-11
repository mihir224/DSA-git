package com.company;

public class Main {

    public static void main(String[] args) {
	int myValue=1024;
	int myMinIntValue=Integer.MIN_VALUE;
	int myMaxIntValue=Integer.MAX_VALUE;
	System.out.println("Min value of integer: "+myMinIntValue);
	System.out.println("Max value of integer: "+myMaxIntValue);
	System.out.println("Busted max value: "+(myMaxIntValue+1));
    byte myMinByteValue=Byte.MIN_VALUE;
    byte myMaxByteValue=Byte.MAX_VALUE;
    System.out.println("Min value of byte: "+myMinByteValue);
    System.out.println("Max value of byte: "+myMaxByteValue);
        short myMinShortValue=Short.MIN_VALUE;
        short myMaxShortValue=Short.MAX_VALUE;
        System.out.println("Min value of short: "+myMinShortValue);
        System.out.println("Max value of short: "+myMaxShortValue);
        long myLongValue=1020L;
        long myMinLongValue=Long.MIN_VALUE;
        long myMaxLongValue=Long.MAX_VALUE;
        System.out.println("Min value of long: "+myMinLongValue);
        System.out.println("Max value of long: "+myMaxLongValue);
        byte NewByteValue=(byte)(myMinByteValue/2); //casting
    float myMinFloatValue=Float.MIN_VALUE;
    float myMaxFloatValue=Float.MAX_VALUE;
        System.out.println("Min value of float: "+myMinFloatValue);
        System.out.println("Max value of float: "+myMaxFloatValue);
        double myMinDoubleValue=Double.MIN_VALUE;
        double myMaxDoubleValue=Double.MAX_VALUE;
        System.out.println("Min value of double: "+myMinDoubleValue);
        System.out.println("Max value of double: "+myMaxDoubleValue);        
    int myIntValue=5;
    float myFloatValue=5.25f;
    double myDoubleValue=5d;
        System.out.println("My int value="+myIntValue);
        }
}
