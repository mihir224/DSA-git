package com.company;
//MIHIR SAINI 9920102054 E2
public class Address {
    protected String currentAddress;
    protected String permanentAddress;
    protected String email;
    protected long phn;

    public Address(String currentAddress, String permanentAddress, String email, long phn) {
        this.currentAddress = currentAddress;
        this.permanentAddress = permanentAddress;
        this.email = email;
        this.phn = phn;
    }
}
