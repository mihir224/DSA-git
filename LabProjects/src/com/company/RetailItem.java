package com.company;
class myException4 extends Exception{
    public myException4(String s){
        super(s);
    }
}
public class RetailItem {
    public static void main(String[] args) {
        RetailItem retailItem1=new RetailItem("Jacket", 12, 59.95);
        RetailItem retailItem2=new RetailItem("Designer Jeans", 40, 34.95);
        RetailItem retailItem3=new RetailItem("Shirt",20, 24.95);
        try{
            throw new myException4("-223.555");
        }
        catch (myException4 exception){
            System.out.println("caught");
            System.out.println(exception.getMessage());
        }
    }
    String description;
    int unitsOnHand;
    double price;

    public RetailItem(String description, int unitsOnHand, double price) {
        this.description = description;
        this.unitsOnHand = unitsOnHand;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public int getUnitsOnHand() {
        return unitsOnHand;
    }

    public double getPrice() {
        return price;
    }
}
