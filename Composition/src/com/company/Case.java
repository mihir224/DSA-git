package com.company;

public class Case {
    private String manufacturer;
    private String model;
    private Dimension dimension;
    private int powerSupply;
    public Case(String manufacturer, String model, Dimension dimension, int powerSupply) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.dimension = dimension;
        this.powerSupply = powerSupply;
    }
    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getPowerSupply() {
        return powerSupply;
    }
    public void powerButton()
    {
        System.out.println("Power button pressed");
    }

}
