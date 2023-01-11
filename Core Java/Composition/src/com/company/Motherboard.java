package com.company;

public class Motherboard {
    private int ramSlots;
    private int cardSlots;
    private String manufacturer;
    private String bios;
    private String model;

    public Motherboard(int ramSlots, int cardSlots, String manufacturer, String bios, String model) {
        this.ramSlots = ramSlots;
        this.cardSlots = cardSlots;
        this.manufacturer = manufacturer;
        this.bios = bios;
        this.model = model;
    }
    public void loadingScreen(String programName)
    {
        System.out.println(programName + " is loading...." );
    }

    public int getRamSlots() {
        return ramSlots;
    }

    public int getCardSlots() {
        return cardSlots;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getBios() {
        return bios;
    }

    public String getModel() {
        return model;
    }
}
