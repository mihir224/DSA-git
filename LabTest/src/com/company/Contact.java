package com.company;
//MIHIR SAINI 9920102054 E2
public class Contact {
        String email;
        String address;
        int mobNo;
        public Contact(int mobNo, String email, String address){
            this.mobNo=mobNo;
            this.email=email;
            this.address=address;
        }
        public void printDetails(){
            System.out.println(mobNo);
            System.out.println(email);
            System.out.println(address);
        }
}
