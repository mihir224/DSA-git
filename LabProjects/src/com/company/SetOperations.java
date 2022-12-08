package com.company;
//MIHIR SAINI 9920102054 E2
import java.util.HashSet;
import java.util.Vector;

public class SetOperations {
    public static void main(String[] args) {
        HashSet<Integer> s1=new HashSet<>();
        HashSet<Integer> s2=new HashSet<>();
//    S1={1, 2, 5, 7, 9, 10} and S1={3, 4, 8, 11, 12}
        s1.add(1);
        s1.add(2);
        s1.add(5);
        s1.add(7);
        s1.add(9);
        s1.add(10);
        s2.add(3);
        s2.add(4);
        s2.add(8);
        s2.add(11);
        s2.add(12);
        System.out.println("Set 2 is: " + s2);
        s1.addAll(s2); //Union
        System.out.println("Union is: " + s1);
        s1.retainAll(s2); //Intersection
        System.out.println("Intersection is: " + s1);
        s1.removeAll(s2); //s1-s2
        System.out.println("S1 - S2 = " + s1);
        s2.removeAll(s1); //s2-s1
        System.out.println("S2 - S1 = " + s2);
    }

}
