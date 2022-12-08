package com.company;
//MIHIR SAINI 9920102054 E2
public class Main {
    public static void main(String[] args) {
        Staff staff=new Staff("9920102054-M", "MIHIR SAINI", "25-12-02", "Clerk level-1", new Contact(99270, "mihirsaini25@gmail.com", "Noida"), "Software Development");
        staff.printDetails();
        staff.getPayment();
        System.out.println("Salary: " + staff.getPayment());
        Faculty faculty=new Faculty("9920102054-M", "MIHIR SAINI", "25-12-02", "Associate Professor", new Contact(99270, "mihirsaini25@gmail.com", "Noida"), "Mathematics");
        faculty.printDetails();
        System.out.println("Salary: " + faculty.getPayment());
    }
}
