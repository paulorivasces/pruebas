/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilosemaforos;

import java.util.concurrent.Semaphore;


/**
 *
 * @author Usuario
 */
public class Buffer {
    
    private int[] buff;
    private Semaphore mutex = new Semaphore(1,true);
    private Semaphore lleno = new Semaphore(0,true);
    private Semaphore vacio;
    private int utilproduc = 0; // indice del buffer circular donde va a producir la productora
    private int utilcons = 0; // indice del buffer circular donde va a consumir la consumidora
    private boolean prodestru;
    private boolean consdestru;
    String amarillo = "\033[33m";
    String restore = "\033[30m";
    String rojo = "\033[31m";
    
    public Buffer(int tamanio, boolean prodestru, boolean consdestru) {
        this.prodestru = prodestru;
        this.consdestru = consdestru;
        buff = new int[tamanio];
        vacio = new Semaphore(tamanio, true);
    }
    
    public void poner(String nombreproduc) throws InterruptedException {
        
        do {
            vacio.acquire();
            mutex.acquire();
            
            buff[utilproduc] = 1;
            System.out.println("Hola, soy " + nombreproduc + " y produzco en la posicion " + utilproduc);   
            this.ImprimirBufferProduc(utilproduc);
            utilproduc = (utilproduc + 1) % buff.length;
            
            mutex.release();
            lleno.release();
        }
        while(this.prodestru);// para destruir la hebra en false y true para no destruirla condicion booleana
    }
    
    public void quitar(String nombrecons) throws InterruptedException {

        do {
            lleno.acquire();
            mutex.acquire();
            
            buff[utilcons] = 0;
            System.out.println("Hola, soy "+ nombrecons + " y consumo en la posicion " + utilcons);
            this.ImprimirBufferCons(utilcons);
            utilcons = (utilcons + 1) % buff.length;
            
            mutex.release();
            vacio.release();
        }
        while(this.consdestru); // para destruir la hebra en false y true para no destruirla condicion booleana
    }
    
    public void ImprimirBufferProduc(int util) {
        for(int i = 0; i < buff.length; i++) {
            if (i == util) {
                System.out.print(amarillo + buff[i] + " ");
            }
            else {
                System.out.print(restore + buff[i] + " ");
            }
        }
        System.out.println();
    }
    
    public void ImprimirBufferCons(int util) {
        for(int i = 0; i < buff.length; i++) {
            if (i == util) {
                System.out.print(rojo + buff[i] + " ");
            }
            else {
                System.out.print(restore + buff[i] + " ");
            }
        }
        System.out.println();
    }
}
