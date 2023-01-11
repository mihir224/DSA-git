package com.company;

public class Main {

    public static void main(String[] args) {
	Animal animal=new Animal("Animal",1,1,6,45); //animal is the reference to the object of class Animal
    Dog dog=new Dog("German Shephard", 4,25,2,1, 1,"Golden fur");
        System.out.println();
        dog.eat(); //inheriting class can use the public methods of the base class;

    }
}
