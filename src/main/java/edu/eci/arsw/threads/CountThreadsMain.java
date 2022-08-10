/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        CountThread thread1 = new CountThread(0,99);
        thread1.start();
        CountThread thread2 = new CountThread(100,199);
        thread2.start();
        CountThread thread3 = new CountThread(200,299);
        thread3.start();
    }
    
}
