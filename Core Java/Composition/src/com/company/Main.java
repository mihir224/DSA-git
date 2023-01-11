package com.company;

public class Main {

    public static void main(String[] args) {
        Dimension theDimension=new Dimension(2,4,5);
	Case theCase=new Case("Dell","XPS", theDimension, 240 );
    Resolution theResolution=new Resolution(4,5);
    Monitor theMonitor=new Monitor("HP", "Pavillion", 13, theResolution  );
    Motherboard theMotherboard=new Motherboard(5, 2, "Asus", "v2.44", "BJ-200");
    Computer computer=new Computer(theCase,theMonitor,theMotherboard); //composition; calling 3 different class which are part of computer class
    computer.getMonitor().drawPixelAt(2,4,"BLUE");
    computer.getMotherboard().loadingScreen("WINDOWS 7 PROFESSIONAL");
    computer.getThisCase().powerButton();
    }
}
