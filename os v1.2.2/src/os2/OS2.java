/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os2;

/**
 *
 * @author SHAKA
 */

import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class OS2 {

    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    public static int clock;

    public static void main(String[] args) {
        int timeQuantum = 10;
        System.out.println("Please write the number of the processes: ");
        int n = scanner.nextInt();
        
        
        Queue<Process>[] queues = Process.fillQueue(n, false); 
        if(queues[0] != null){
            Process.printQueue(queues[0]);
            System.out.println();
        }
        if(queues[1] != null){
            Process.printQueue(queues[1]);
            System.out.println();
        }
        if(queues[2] != null){
            Process.printQueue(queues[2]);
            System.out.println();
        }
        if(queues[3] != null){
            Process.printQueue(queues[3]);
            System.out.println();
        }
        if(queues[4] != null){
            Process.printQueue(queues[4]);
            System.out.println();
        }
        if(queues[5] != null){
            Process.printQueue(queues[5]);
            System.out.println();
        }
        if(queues[6] != null){
            Process.printQueue(queues[6]);
        }
    }
}