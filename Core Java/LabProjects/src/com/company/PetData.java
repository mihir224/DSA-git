package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
class Pet {
    String animal;
    String owner;

    public Pet(String animal, String owner) {
        this.animal = animal;
        this.owner = owner;
    }

    public String getAnimal() {
        return animal;
    }

    public String getOwner() {
        return owner;
    }
}
public class PetData {
    public static void main(String[] args) {
        try{
            String owner;
            String animal;
            ArrayList<Pet> pet=new ArrayList<>();
            FileReader fr1=new FileReader("animal.txt");
            FileReader fr2=new FileReader("owner.txt");
            FileWriter fw=new FileWriter("output.txt");
            BufferedReader br1=new BufferedReader(fr1);
            BufferedReader br2=new BufferedReader(fr2);
            while((animal=br1.readLine())!=null&&(owner=br2.readLine())!=null){
              Pet p=new Pet(animal,owner);
              pet.add(p);
            }
            for(int i=0;i<pet.size();i++){

                String output=pet.get(i).getAnimal()+" : " + pet.get(i).getOwner();
               fw.write(output);
                System.out.println(output);
             }
fw.close();

        } catch (IOException e) {
            e.getMessage();
        }
    }
;
}
