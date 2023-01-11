package com.company;
//MIHIR SAINI 9920102054 E2
public interface Shape {
    public double getArea();
    public double getPerimeter();
}
class Circle implements Shape{
    double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return this.radius*this.radius;
    }

    @Override
    public double getPerimeter() {
        return 2*Math.PI*this.radius;
    }
}
class Triangle implements Shape{
    double base;
    double height;
    double a;
    double b;
    double c;

    public Triangle(double base, double height, double a, double b, double c) {
        this.base = base;
        this.height = height;
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    public double getArea() {
        return 0.5*this.base*this.height;
    }

    @Override
    public double getPerimeter() {
        return this.a+this.b+this.c;
    }
}
class Rectangle implements Shape{
    double a;
    double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double getArea() {
       return this.a*this.b;
    }

    @Override
    public double getPerimeter() {
        return this.a+this.b;
    }
}
