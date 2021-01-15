/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilocerrojos;

/**
 *
 * @author Usuario
 */
public class Buffer {
    
    private int[] buff;
    private boolean prodestru;
    private boolean consdestru;
    int contadorCodicion; // variable condicion
    private int utilproduc = 0; // indice del buffer circular donde va a producir la productora
    private int utilcons = 0; // indice del buffer circular donde va a consumir la consumidora
    String amarillo = "\033[33m";
    String restore = "\033[30m";
    String rojo = "\033[31m";
    
    public Buffer(int tamanio, boolean prodestru, boolean consdestru) {
        this.prodestru = prodestru;
        this.consdestru = consdestru;
        buff = new int[tamanio];
    }
    
    synchronized public void poner(String nombreproduc) throws InterruptedException {
        
        do {
            while(contadorCodicion == buff.length) { // bucle de espera activa 
                wait();
            }
            
            buff[utilproduc] = 1;
            System.out.println("Hola, soy " + nombreproduc + " y produzco en la posicion " + utilproduc);      
            this.ImprimirBufferProduc(utilproduc);
            utilproduc = (utilproduc + 1) % buff.length;
                    
            contadorCodicion++;
            
            notifyAll();
        }
        while(this.prodestru);// para destruir la hebra en false y true para no destruirla condicion booleana
    }
    
    synchronized public void quitar(String nombrecons) throws InterruptedException {
        do {
            while(contadorCodicion == 0) { // bucle de espera activa
                wait();
            }
             
            buff[utilcons] = 0;
            System.out.println("Hola, soy "+ nombrecons + " y consumo en la posicion " + utilcons);        
            this.ImprimirBufferCons(utilcons);
            utilcons = (utilcons + 1) % buff.length;
            
            contadorCodicion--;
            
            notifyAll();
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
