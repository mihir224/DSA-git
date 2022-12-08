package com.company;
//MIHIR SAINI 9920102054 E2
public class UGStudent extends Student {
    protected double marks;
    protected String pastQualifications;
    public UGStudent(String name, int regNo, long phn, String fName, String currentAddress, String permanentAddress, String email, double marks, String pastQualifications){
        super(name, regNo, phn, fName, currentAddress, permanentAddress, email);
        this.marks=marks;
        this.pastQualifications=pastQualifications;
    }

    public double getMarks() {
        return marks;
    }

    public String getPastQualifications() {
        return pastQualifications;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public void setPastQualifications(String pastQualifications) {
        this.pastQualifications = pastQualifications;
    }
}
