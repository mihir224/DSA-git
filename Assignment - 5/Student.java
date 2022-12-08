package com.company;
//MIHIR SAINI 9920102054 E2
public class Student {
    protected String name;
    protected int regNo;
    protected String fName;
    protected long phn;
    protected String currentAddress;
    protected String permanentAddress;
    protected String email;

    Payment payment=new Payment(394875);
    PaymentDD paymentDD=new PaymentDD(payment.paymentID, "25/12/2002", "JAYPEE","27773BN", "HDFC", 8877665.29);
    Course course=new Course("22B11CS242", "Java Programming lab", 1.5);

    @Override
    public String toString() {
        return ("Following are the course details of: " + getName()
       + " \nCourse: " + course.getName() +
     " \nCourse Code: " + course.getCode() +
       " \nDrawee Name: " + paymentDD.getDraweeName() +
       " \nAmount payable: " + paymentDD.getPayableAmt());
    }

    public Student(String name, int regNo, long phn, String fName, String currentAddress, String permanentAddress, String email) {
        this.name = name;
        this.regNo = regNo;
        this.phn = phn;
        this.fName = fName;
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.email = email;
    }
    public void printDetails(int regNo){
        this.regNo=regNo;
        System.out.println("Following are the course details of: " + getName());
        System.out.println("Course: " + course.getName());
        System.out.println("Course Code: " + course.getCode());
        System.out.println("Drawee Name: " + paymentDD.getDraweeName());
        System.out.println("Amount payable: " + paymentDD.getPayableAmt());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegNo() {
        return regNo;
    }

    public void setRegNo(int regNo) {
        this.regNo = regNo;
    }

    public long getPhn() {
        return phn;
    }

    public void setPhn(long phn) {
        this.phn = phn;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
