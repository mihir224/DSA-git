package com.company;

public class Main {

    public static void main(String[] args) {
        int result = 5 + 9;
        System.out.println("5 + 9 = " + result);
        int previousResult = result;
        result = result - 1; //value of result updated but previousResult would be same as it's value is not updated
        System.out.println("14 - 1 = " + result);
        System.out.println("Previous result = " + previousResult);
        result = result * 8;
        System.out.println("13 * 8 = " + result);
        result = result / 2;
        System.out.println("104 / 2 = " + result);
        result = result % 8;
        System.out.println("52 % 8 = " + result);
        result++; //returns value of result=result+1;
        System.out.println(result);
        result += 2;//abbreviating two operators: "+" and "="
        // returns value of result=result + 2;
        System.out.println(result);
        boolean isMeReal=false;
        if(!isMeReal)
        {
            System.out.println("No, you are real af. ");
            System.out.println("Ahh shoot!");
        }

    }

}
