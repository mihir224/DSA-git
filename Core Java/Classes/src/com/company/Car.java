package com.company;

import java.util.Locale;

//setters can be used for the validation of a variable
public class Car { //1st letter of a class should always be capital
    private int wheels; //fields
    private int doors;
    private String model;
    private String engine;
    private String colour; //a setter is defined below
    public void setModel(String model)//this model is different field the field model. It is just a parameter for reference.
    {
        String validModel = model.toLowerCase();
        if (validModel.equals("hurracan")) {    //equals method tests if the value of one string is equal to another
            this.model = model; // The field model is accessed by using the 'this' keyword
        } else {
            this.model = "Unknown";
        }
    }//this method is used to update the value of the field model with the contents of the parameter model
    public String getModel() //defining a getter
    {
        return this.model;
    }
    //therefore, by using getters and setters we can make sure that the code that is creating objects can't assign values that we haven't defined valid in the car class
    //basically they can be used in data encapsulation
}
