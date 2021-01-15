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
public class Hiloscerrojos {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        
        boolean destruirConsumidora = Boolean.valueOf(args[4]); // true => las hebras consumidoras no se destrullen false => se destrullen 
        boolean destruirProductora = Boolean.valueOf(args[2]); // true => las hebras productoras no se destrullen false => se destrullen 
       
        // creamos el Buffer con su tamaño
        Buffer buff = new Buffer(Integer.valueOf(args[0]), destruirProductora, destruirConsumidora);
        
        int numHebrascons = Integer.valueOf(args[3]); 
        int numHebrasproduc = Integer.valueOf(args[1]);
        
        ProductorHebra[] productoras = new ProductorHebra[numHebrasproduc] ; // crea vector de hebras productoras 
        ConsumidorHebra[] consumidoras = new ConsumidorHebra[numHebrascons] ; // crea vector de hebras consumidoras 
        
        for ( int i = 0 ; i < numHebrasproduc ; i++ ) {
            String nombre = "Productora."+ (i+1);
            productoras[i] = new ProductorHebra(nombre, buff); // crear hebra productora.
            productoras[i].thr.start(); // comienza su ejec.
        }
        
        for ( int j = 0 ; j < numHebrascons ; j++ ) {
            String nombre = "Consumidora."+ (j+1);
            consumidoras[j] = new ConsumidorHebra(nombre, buff); // crear hebra consumidora.
            consumidoras[j].thr.start(); // comienza su ejec.
        }
        
        //esperar para que terminen todas las hebras productoras
        for ( int i = 0 ; i < numHebrasproduc ; i++ ) {
             productoras[i].thr.join();
        }
        // esperar para que terminen todas las hebras consumidoras
        for ( int j = 0 ; j < numHebrascons ; j++ ) {
             consumidoras[j].thr.join();
        }   
    }
      
}
