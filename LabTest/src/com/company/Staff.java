package com.company;
//MIHIR SAINI 9920102054 E2
public class Staff extends Employee{
    String workAssigned;
    public Staff(String ID, String name, String dob, String designation, Contact contact, String workAssigned){
        super(ID, name, dob, designation, contact);
        this.workAssigned=workAssigned;
    }
    public String getWorkAssigned(){
    return workAssigned;
    }

    @Override
    public void printDetails() {
        System.out.println("Employee ID: " + ID);
        System.out.println("Employee Name: " + name);
        System.out.println("Date of birth: " + dob);
        System.out.println("Designation: " + designation);
        System.out.println("Contact: " + contact.address);
        System.out.println("Work Assigned: " + workAssigned);
    }

    @Override
    public double getPayment() {
        if(designation.equalsIgnoreCase("Clerk level-1")){
        return 15000.00;
        }
        else if(designation.equalsIgnoreCase("Clerk level-2")){
            return 20000.00;
        }
        else return 0;
    }

}