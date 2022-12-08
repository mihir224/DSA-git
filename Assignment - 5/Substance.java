package com.company;
//MIHIR SAINI 9920102054 E2
import java.util.Arrays;

public class Substance {
   String[][] arr;

    public Substance(String[][] arr) {
        this.arr = arr;
    }
    public void printArray(String arr[][]){
        System.out.println(Arrays.toString(arr));
    }
    public static void main(String[] args) {
        String[][] arr={
                {"water","sulphur","copper"},
                {"0 deg", "112.8 deg", "1085 deg"},
                {"100 deg", "444.6 deg","2526 deg"}
        };
        Substance substance=new Substance(arr);
        substance.printArray(arr);
    }

}
