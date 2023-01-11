package com.company;

public class Main {

    public static void main(String[] args) {
	int arr[]=new int[10]; //count starts from 0 ie first element is arr[1]

        System.out.println(arr.length); //.length field returns the size of the array
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=i*10;

        }
        printArray(arr);
    }
    public static void printArray(int[] array)
    {
        for(int i=0;i<array.length;i++)
        {

            System.out.println("arr[" + i + "] is " + array[i]);
        }
    }
}

