package com.company;

public class Dog extends Animal { //extends keyword is used to describe the relation between base class and child class. Here Dog is the child class
    private int eyes;
    private int legs;
    private int tail;
    private String coat;

    @Override //overriding the eat method of the base class
    public void eat() {
        System.out.println("Dog.eat called");
        chew();
        super.eat(); //calls eat method in the Animal class


    }
    private void chew()
    {
        System.out.println("Dog.chew called");
    }

    public Dog(String name, int size, int weight, int eyes, int legs, int tail, String coat) {
        super(name, 1, 1, size, weight); //super keyword is used to call a constructor from the base class
        //this line initializes the base characteristics of an animal
        this.eyes=eyes;
        this.legs=legs;
        this.tail=tail;
        this.coat=coat;


    }
}
