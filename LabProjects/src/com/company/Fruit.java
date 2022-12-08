package com.company;
//MIHIR SAINI 9920102054 E2
import java.util.ArrayList;
import java.util.Iterator;

public class Fruit {
    public static void main(String[] args) {
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Grapes");
        arrayList.add("Litchi");
            Fruit fruit=new Fruit(arrayList);
            fruit.printArrayList(arrayList);
        arrayList.remove(1);
        fruit.printArrayList(arrayList);
        }
        ArrayList<String> arrayLst;

    public Fruit(ArrayList<String> arrayList) {
        this.arrayLst = new ArrayList<>();
    }
    public void printArrayList(ArrayList<String> arrayList){
        Iterator iterator= arrayList.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}

