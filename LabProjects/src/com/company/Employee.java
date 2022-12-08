package com.company;

import java.util.Scanner;

//MIHIR SAINI 9920102054 E2
public class Employee {
    private String name;
    private String empNo;
    private String hireDate;

    public Employee(){}
    public Employee(String name, String empNo, String hireDate) {
        this.name = name;
        this.empNo = empNo;
        this.hireDate = hireDate;
    }
    public void setEmpNo(String empNo){
        if(isValidEmp(empNo)){
            this.empNo=empNo;
        }
        else{
            System.out.println("The employee number is invalid! ");
        }
    }
    public boolean isValidEmp(String empNo)
    {
        boolean flag=true;
        if(empNo.length()!=5){
            flag= false;
        }
        if((!Character.isDigit(empNo.charAt(0)))||
                (!Character.isDigit(empNo.charAt(1)))||
                (!Character.isDigit(empNo.charAt(2)))||(empNo.charAt(3)!='-')||
                (!Character.isLetter(empNo.charAt(4)))||
                (Character.isLetter(empNo.charAt(5)))){
            flag= false;
        }
        else{
            flag=true;
    }
        return flag;
    }
    public void printDetails(){
        System.out.println("Following are the employee details: ");
        System.out.println("Name: " + this.name);
        System.out.println("Employee Number: " + this.empNo);
        System.out.println("Date of joining: " + this.hireDate);
    }
}
class ProductionWorker extends Employee{
    private int shift;
    private double hourlyPayRate;
    public ProductionWorker(){}
    public ProductionWorker(String name, String empNo, String hireDate, int shift, double hourlyPayRate){
        super(name,empNo,hireDate);
        this.shift=shift;
        this.hourlyPayRate=hourlyPayRate;
    }
    public String getShift(){
        boolean quit=false;
        while(!quit){
            switch(shift){
                case 1:
                {
                    return "Day shift";
                }
                case 2:
                {
                    return "Night shift";
                }
                default:
                {
                    return "Invalid input";
                }
            }
        }
        return "";
    }

    @Override
    public void printDetails() {
        super.printDetails();
        System.out.println("Shift: " + getShift());
        System.out.println("Hourly Pay Rate: " + this.hourlyPayRate);

    }
}
class ShiftSupervisor extends Employee{
        private double annualSalary;
        private double annualProductionBonus;

    public ShiftSupervisor(String name, String empNo, String hireDate, double annualSalary, double annualProductionBonus) {
        super(name, empNo, hireDate);
        this.annualSalary = annualSalary;
        this.annualProductionBonus = annualProductionBonus;
    }
    @Override
    public void printDetails() {
        super.printDetails();
        System.out.println("Annual salary: " + this.annualSalary);
        System.out.println("Annual production bonus: " + this.annualProductionBonus);
    }
}
class TeamLeader extends ProductionWorker{
    double monthlyBonus;
    int reqHours;
    int trainedHours;

    public TeamLeader(String name, String empNo, String hireDate, int shift, double hourlyPayRate, double monthlyBonus, int reqHours, int trainedHours) {
        super(name, empNo, hireDate, shift, hourlyPayRate);
        this.monthlyBonus = monthlyBonus;
        this.reqHours = reqHours;
        this.trainedHours = trainedHours;
    }
    public void printEligibility(){
        if(isValidHrs(this.reqHours,this.trainedHours)){
            System.out.println("The employee has successfully completed all the " + reqHours + " of training");
        }
        else
            System.out.println("The employee has not yet completed the required " + reqHours + " of training");
    }
    public boolean isValidHrs(int reqHours,int trainedHours){
        boolean flag=true;
        if(trainedHours<reqHours){
            flag=false;
        }
        else {flag=true;};
        return flag;
    }
}