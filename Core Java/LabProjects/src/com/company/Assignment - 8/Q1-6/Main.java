package com.company;
//MIHIR SAINI 9920102054 E2
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    StudentDetails studentDetails=new StudentDetails("Mihir Saini", 992010, "Veer");
    Address address=new Address("Noida", "Haridwar", "ms224@gmail.com", 992837);
    studentDetails.searchStudent(992010);
        try{
            throw new myException2("mihir saini");
        }
        catch(myException2 ex){
            System.out.println("caught");
        }
    }


}
