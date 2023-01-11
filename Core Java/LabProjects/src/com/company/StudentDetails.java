package com.company;
//MIHIR SAINI 9920102054 E2
class myException2 extends Exception{
    public myException2(String name){
        super(name);
    }
}
public class StudentDetails {
    protected String name;
   public int regNo;
    protected String fName;

    public StudentDetails(String name, int regNo, String fName) {
        this.name = name;
        this.regNo = regNo;
        this.fName = fName;
    }
    public void searchStudent(int regNo){
        if (this.regNo==regNo){
            System.out.println("Student found");

        }
        else System.out.println("Student not found");
    }


}
