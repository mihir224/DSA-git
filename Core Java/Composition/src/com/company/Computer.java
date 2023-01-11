package com.company;

public class Computer {
    private Case thisCase; //composition
    private Monitor monitor;
    private Motherboard motherboard;

    public Computer(Case thisCase, Monitor monitor, Motherboard motherboard) {
        this.thisCase = thisCase;
        this.monitor = monitor;
        this.motherboard = motherboard;
    }

    public Case getThisCase() {
        return thisCase;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public Motherboard getMotherboard() {
        return motherboard;
    }
}
