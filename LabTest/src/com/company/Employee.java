package com.company;
//MIHIR SAINI 9920102054 E2
public abstract class Employee {
    String ID;
    String name;
    String dob;
    String designation;
    Contact contact;

    public Employee(String ID, String name, String dob, String designation, Contact contact) {
        this.ID = ID;
        this.name = name;
        this.dob = dob;
        this.designation = designation;
        this.contact = contact;
    }

    public abstract void printDetails();
    public abstract double getPayment();

}

