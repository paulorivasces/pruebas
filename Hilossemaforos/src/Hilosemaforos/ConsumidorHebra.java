/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilosemaforos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class ConsumidorHebra implements Runnable {
    
    private Buffer buff; // clase de tipo buffer
    public Thread thr; //objeto hebra encapsulado
    
    public ConsumidorHebra(String nombre, Buffer buff) {
        this.buff = buff;
        thr = new Thread(this,nombre);
    }

    @Override
    public void run() {
        try {
            //System.out.println("Hola, soy "+thr.getName());
            buff.quitar(thr.getName());
        } catch (InterruptedException ex) {
            Logger.getLogger(ConsumidorHebra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
