package com.company;
//MIHIR SAINI 9920102054 E2
public class BankAccount {
    String name;
    int accNo;
    String accType;
    double balance;
   public BankAccount(String name, int accNo, String accType, double balance)
   {
       this.name=name;
       this.accNo=accNo;
       this.accType=accType;
       this.balance=balance;
   }
   public void setDeposit(int depositAmt){
       balance+=depositAmt;
       System.out.println(depositAmt + " Rs. deposited. New balance: " + balance);
   }
    public void withdrawAmt(int withdrawAmt){
       balance-=withdrawAmt;
        System.out.println(withdrawAmt + " Rs. withdrawn. Remaining balance: " + balance);
   }
   public void displayNameBalance(){
       System.out.println(name + "Name: " + balance + "Balance: ");
   }

    public static void main(String[] args) {
        BankAccount bankAccount=new BankAccount("MIHIR SAINI", 20102054, "Savings", 19847);
        bankAccount.setDeposit(10000);
        bankAccount.withdrawAmt(96464);
        bankAccount.displayNameBalance();
    }
}
