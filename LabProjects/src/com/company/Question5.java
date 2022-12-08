package com.company;

public class Question5 {
    public void CozaLozaWoza(int n){
        for(int i=0;i<n;i++) {
            boolean flag = false;
            if (n % 3 == 0) {
                System.out.println("Coza");
                flag = true;
            }
            if (n % 5 == 0) {
                System.out.println("Loza");
                flag = true;
            }
            if (n % 7 == 0) {
                System.out.println("Woza");
                flag = true;
            }
            if (n % 3 == 0 && n % 5 == 0) {
                System.out.println("CozaLoza");
                flag = true;
            }
            if (n % 5 == 0 && n % 7 == 0) {
                System.out.println("LozaWoza");
                flag = true;
            }
            if (n % 7 == 0 && n % 3 == 0) {
                System.out.println("WozaCoza");
                flag = true;
            }
            if (!flag) {
                System.out.println(i);
            }
            if(i%11==0){
                System.out.println();
            }
        }
        }
    }



