/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fciencias.concurrente.practica05;

import fciencias.concurrente.practica05.graficos.Mesa;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para representar a los filosofos, estos pueden
 * estar en tres estados pensando, esperando y comiendo.
 * @author Jes&uacute;s Vila S&aacute;nchez, 309280970
 */
public class Filosofo extends Thread {
    
    private final int id;
    private final Palillo izq;
    private final Palillo der;
    private final boolean diestro;
    private final int XSIZE = 25;
    private final int YSIZE = 25;
    private int x;
    private int y;
    private boolean esperando;
    private final Random rnd = new Random();
    private final Mesa mesa;
    private Color color;
    
    public Filosofo(int id, Palillo izq, Palillo der, boolean diestro,
            int x, int y, Mesa mesa) {
        this.id = id;
        this.izq = izq;
        this.der = der;
        this.diestro = diestro;
        this.mesa = mesa;
        this.x = x;
        this.y = y;
        System.out.println("Soy " + id + " y soy " + (diestro ? "diestro":"zurdo")
        + " con " + izq + " y " + der);
    }
    
    public void draw(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillOval(x-XSIZE/2, y-YSIZE/2, XSIZE, YSIZE);
    }
    
    private void pensar() throws InterruptedException {
        System.out.println(this + " está pensando.");
        color = Color.GREEN;
        mesa.repaint();
        Thread.sleep(Math.abs(rnd.nextLong()) % 5000);
    }
    
    private void esperar() throws InterruptedException {
        System.out.println(this + " está esperando el palillo.");
        esperando = true;
        color = Color.YELLOW;
        mesa.repaint();
        Thread.sleep(Math.abs(rnd.nextLong()) % 5000);
    }
    
    private void comer() throws InterruptedException {
        System.out.println(this + " está comiendo.");
        color = Color.RED;
        mesa.repaint();
        Thread.sleep(Math.abs(rnd.nextLong()) % 5000);
        esperando = false;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                if (!esperando)
                    pensar();
                if (diestro) {
                    if (der.levantar()) {
                        esperar();
                        if (izq.levantar()) {
                            comer();
                            izq.soltar(this);
                            der.soltar(this);
                        }
                    }
                } else {
                    if (izq.levantar()) {
                        esperar();
                        if (der.levantar()) {
                            comer();
                            der.soltar(this);
                            izq.soltar(this);
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Filosofo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Filosofo " + this.id;
    }
}
