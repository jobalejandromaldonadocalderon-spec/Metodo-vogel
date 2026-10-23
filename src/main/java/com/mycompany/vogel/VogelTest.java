
package com.mycompany.vogel;

import java.util.Scanner;


public class VogelTest {
    public static void main(String[] args) {
        Scanner op=new Scanner(System.in);
        
        System.out.println("Ingresa cuantas filas");
        int filas=op.nextInt();
        
        System.out.println("Ingresa cuantas columnas");
        int columnas=op.nextInt();
        
        Vogel obj=new Vogel(filas,columnas);
        
        obj.llenarMatriz();
        obj.mostrarMatriz();
        obj.resolver();
    }
   
}
