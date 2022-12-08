package com.company;

public class Main {

    public static void main(String[] args) {
        String numberAsString="1989"; //an integer can only pass values from a string which has integer value
        System.out.println("Number as String is: " + numberAsString);
        int number=Integer.parseInt(numberAsString); //parsing values from the string using Integer class and parse int method
        System.out.printf("Number: " + number);

    }
}
