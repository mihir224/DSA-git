package com.company;

import java.lang.reflect.Array;
import java.util.Arrays;
//MIHIR SAINI 9920102054 E2
public class MatrixMultiplication {
    public static void main(String[] args) {
        int[][] a={
                {2,4,3},
                {3,5,6},
                {4,3,5},
        };
        int[][] b={
                {1,6,7},
                {8,2,4},
                {5,4,2},
        };
        int[][] c=new int[a.length][a[0].length];
        for(int i=0;i<a.length;i++){
            for(int j=0;j<b[0].length;j++)
            {
                for(int k=0;k<a[0].length;k++){
                    c[i][j]=a[i][k]*b[k][j];
                }
            }
        }
        System.out.println(Arrays.deepToString(c));
    }
}
