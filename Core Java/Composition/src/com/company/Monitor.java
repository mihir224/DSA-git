package com.company;

public class Monitor {
    private String manufacturer;
    private String model;
    private int size;
    private Resolution nativeResolution; //Here composition is being applied as resolution class is a part of class monitor

    public Monitor(String manufacturer, String model, int size, Resolution nativeResolution) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.size = size;
        this.nativeResolution = nativeResolution;
    }
    public void drawPixelAt(int x, int y, String color) {
        System.out.println("Drawing pixel at: " + x + "," + y + " in colour " + color );
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public int getSize() {
        return size;
    }

    public Resolution getNativeResolution() {
        return nativeResolution;
    }
}
