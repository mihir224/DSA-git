package com.company;
//MIHIR SAINI 9920102054 E2
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Circle circle=new Circle(5.533);
        System.out.println(circle.getArea());
        System.out.println(circle.getPerimeter());
        Triangle triangle=new Triangle(4.5,6.8,2.3,2.3,8.32);
        System.out.println(triangle.getArea());
        System.out.println(triangle.getArea());
        Rectangle rectangle=new Rectangle(5.4,2.5);
        System.out.println(rectangle.getArea());
        System.out.println(rectangle.getArea());


    }
}
