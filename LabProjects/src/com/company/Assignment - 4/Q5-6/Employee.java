package com.company;

import java.util.Iterator;
import java.util.LinkedList;

//MIHIR SAINI 9920102054 E2
public class Employee {
    private String employeeID;
    private String name;
    private String dept;
    private String dob;
    private String desgn;
    private int yearofJ;
    private long phoneNo;

    public Employee(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        this.employeeID = employeeID;
        this.name = name;
        this.dept = dept;
        this.dob = dob;
        this.desgn = desgn;
        this.yearofJ = yearofJ;
        this.phoneNo = phoneNo;
    }

    public void printDetails(){
        System.out.println("----THE DETAILS OF " + this.employeeID + " ARE----");
        System.out.println("Name: " + this.name);
        System.out.println("Department: " + this.dept);
        System.out.println("Date of birth: " + this.dob);
        System.out.println("Designation: " + this.desgn);
        System.out.println("Year of joining: " + this.yearofJ);
        System.out.println("Ph: " + phoneNo);

    }
}
class Faculty extends Employee{
   LinkedList<String> subject;
   public Faculty(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.subject=new LinkedList<String>();
    }
    public void addSubject(String subject){
        this.subject.add(subject);
    }
    public void printSubjects(LinkedList<String> subject){
       Iterator iterator=subject.iterator();
        System.out.println("The list of subjects is as follows: ");
       while(iterator.hasNext()){
           System.out.println(iterator.next());
       }
    }

    public LinkedList<String> getSubject() {
        return subject;
    }
}

class OfficeStaff extends Employee{
    LinkedList<String> skills;
    public OfficeStaff(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.skills=new LinkedList<>();
    }
    public void addSkill(String skill){
        this.skills.add(skill);
    }
    public void printSkills(LinkedList<String> skills){
        Iterator iterator=skills.iterator();
        System.out.println("The skills are: ");
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
//HOD, DUGC, DMPC, and DDPC
class HOD extends Faculty{
    String nm;
    public HOD(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.nm="Head of the Department";
    }

    @Override
    public String toString() {
        return this.nm;
    }
}
class DUGC extends Faculty{
    String nm;
    public DUGC(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.nm="Department Undergraduate Committee Conveyor";
    }
    @Override
    public String toString() {
        return this.nm;
    }
}
class DMPC extends Faculty{
    String nm;
    public DMPC(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.nm="Department Master Program Committee Conveyor";
    }
    @Override
    public String toString() {
        return this.nm;
    }
}
class DDPC extends Faculty{
    String nm;
    public DDPC(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.nm="Department Doctoral Program Committee Conveyor";
    }
    @Override
    public String toString() {
        return this.nm;
    }
}
class SkilledStaff extends OfficeStaff{
    String nm;
    public SkilledStaff(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.nm="Skilled Staff";
    }
    @Override
    public String toString() {
        return this.nm;
    }
}
class UnSkilledStaff extends OfficeStaff{
    String nm;
    public UnSkilledStaff(String employeeID, String name, String dept, String dob, String desgn, int yearofJ, long phoneNo) {
        super(employeeID, name, dept, dob, desgn, yearofJ, phoneNo);
        this.nm="Unskilled Staff";
    }
    @Override
    public String toString() {
        return this.nm;
    }
}