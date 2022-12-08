package com.company;

import javax.xml.namespace.QName;

public class Main {

    public static void main(String[] args) {

                java.util.Scanner scanner=new java.util.Scanner(System.in); //using the scanner variable to read and store input from user
        System.out.println("ENTER DOB");
        boolean hasNextInt= scanner.hasNextInt(); //to check if dob entered is correct or not
        if(hasNextInt) {

            int dob = scanner.nextInt(); //to convert the input string directly to integer
            scanner.nextLine(); //to handle next line character
            int age=2021-dob;
            System.out.println("ENTER NAME: ");
            String name = scanner.nextLine();//to read a line of input from user
            if(age>=0&&age<=100) {
                System.out.println("NAME IS: " + name + "AGE IS: " + age);
            }
            else
                System.out.println("Invalid value");

        }
        else
            System.out.println("invalid dob");
        scanner.close();

            }
        }


