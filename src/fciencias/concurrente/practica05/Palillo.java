/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fciencias.concurrente.practica05;

import fciencias.concurrente.practica05.graficos.Mesa;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Clase para representar los palillos con los que comen los
 * filosofos.
 * @author Jes&uacute;s Vila S&aacute;nchez, 309280970
 */
public class Palillo {
    
    private final int XSIZE = 10;
    private final int YSIZE = 10;
    private final int id;
    Lock ocupado;
    private final int origenX;
    private final int x;
    private final int origenY;
    private final int y;
    private Mesa mesa;
    private boolean arriba;
    
    public Palillo(int id, int origenX, int origenY, Mesa mesa) {
        if (id == 0) {
            this.id = id;
        } else {
            switch (id % 3) {
                case 0:
                    this.id = id - 2;
                    break;
                case 1:
                    this.id = id + 2;
                    break;
                default:
                    this.id = id;
                    break;
            }
        }
        ocupado = new ReentrantLock();
        this.origenX = origenX;
        this.origenY = origenY;
        this.x = origenX;
        this.y = origenY;
    }
    
    
    
    public boolean levantar() throws InterruptedException {
        arriba = true;
        return ocupado.tryLock(5, TimeUnit.SECONDS);
    }
    
    public void soltar(Filosofo f) {
        arriba = false;
        System.out.println(f + " solto " + this);
        ocupado.unlock();
    }
    
    public void draw(Graphics g) {
        g.setColor((arriba) ? Color.RED : Color.GREEN);
        g.fillOval(x-XSIZE/2, y-YSIZE/2, XSIZE, YSIZE);
    }

    @Override
    public String toString() {
        return "Palillo " + this.id;
    }
}
