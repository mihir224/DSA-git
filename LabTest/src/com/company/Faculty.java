package com.company;
//MIHIR SAINI 9920102054 E2
public class Faculty extends Employee {
    String department;
    public Faculty(String ID, String name, String dob, String designation, Contact contact, String department)
    {
        super(ID, name, dob, designation, contact);
        this.department=department;
    }
    public String getDepartment(){
        return department;
    }
    @Override
    public void printDetails() {
        System.out.println("Employee ID: " + ID);
        System.out.println("Employee Name: " + name);
        System.out.println("Date of birth: " + dob);
        System.out.println("Designation: " + designation);
        System.out.println("Contact: " + contact.address);
        System.out.println("Department: " + department);
    }

    @Override
    public double getPayment() {
        if(designation.equalsIgnoreCase("Assistant Professor")){
            return 100000.00;
        }
        else if(designation.equalsIgnoreCase("Associate Professor")){
            return 150000.00;
        }
        else if(designation.equalsIgnoreCase("Professor"))
        {
            return 200000.00;
        }
        else return 0;

    }
}
