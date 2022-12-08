package com.company;
//MIHIR SAINI 9920102054 E2
public class Payment {
    protected int paymentID;
    public Payment(int paymentID){this.paymentID=paymentID;}

}
class PaymentDD extends Payment{
    //  Date of Issue of the instrument.
    //Name of the drawee bank and branch.
    //MICR code of the bank branch.
    //Name of the drawee.
    protected String doi;
    protected String draweeName;
    protected String micrCode;
    protected String draweeBank;
    protected double payableAmt;
    public PaymentDD(int paymentID, String doi, String draweeName, String micrCode, String draweeBank, double payableAmt){
        super(paymentID);
        this.doi=doi;
        this.draweeName=draweeName;
        this.micrCode=micrCode;
        this.draweeBank=draweeBank;;
        this.payableAmt=payableAmt;
    }
    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getDraweeName() {
        return draweeName;
    }

    public void setDraweeName(String draweeName) {
        this.draweeName = draweeName;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getDraweeBank() {
        return draweeBank;
    }

    public void setDraweeBank(String draweeBank) {
        this.draweeBank = draweeBank;
    }

    public double getPayableAmt() {
        return payableAmt;
    }

    public void setPayableAmt(double payableAmt) {
        this.payableAmt = payableAmt;
    }
}
class PaymentUPI extends Payment{
    protected String upiId;
    protected int mobileNo;

    public PaymentUPI(int paymentID, String upiId, int mobileNo, double payableAmt) {
        super(paymentID);
        this.upiId = upiId;
        this.mobileNo = mobileNo;
        this.payableAmt = payableAmt;
    }

    protected double payableAmt;
}
class PaymentNB extends Payment{
    protected String userId;
    protected String pass;
    protected int otp;

    public PaymentNB(int paymentID, String userId, String pass, int otp) {
        super(paymentID);
        this.userId = userId;
        this.pass = pass;
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}

