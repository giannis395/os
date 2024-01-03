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

import java.util.LinkedList;
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
        
        
        Queue<Process>[] queues = new LinkedList[7]; 
        for(int i=0; i<n; i++){ 
            queues[i] = fillQueue(n,false);
        
        }
        for(int i=0; i<7; i++){ 
            printQueue(queues[i]);
        }
    }
    
    
    static Queue<Process> fillQueue(int n, boolean manual){ // true(manual), false(automatic)
        
        Queue<Process> tempQueue = new LinkedList<>();
        
        if(manual){
            //manual fill
            for (int i=0; i<n; i++){
                int arrivalTime, burstTime, priority;
                do {
                    System.out.println("Please the arrival time of " + i + " process: ");
                    arrivalTime = scanner.nextInt();
                } while (arrivalTime < 0 );
                do {
                    System.out.println("Please the burst time, which must be above the zero, of " + i + " process: ");
                    burstTime = scanner.nextInt(); 
                } while (burstTime <= 0);
                do {
                    System.out.println("Please the priority, which must be between 1 and 7, of " + i + " process: ");
                    priority = scanner.nextInt(); 
                } while (priority < 0 || priority >7);
                Process task = new Process(i, priority, arrivalTime, rand.nextInt(10), burstTime);
                tempQueue.add(task);
            }
            
        }else{
            // automatic fill
            int k=0, user;
            do{
                Process task = new Process(k, rand.nextInt(7)+1, rand.nextInt(100), rand.nextInt(10), rand.nextInt(10)+1);
                tempQueue.add(task);
                k++;
                System.out.println("Do you want to continue create process? Please press 0 for YES, anything else intger for NO");
                user = scanner.nextInt();
            }while (user == 0);
        }
        return tempQueue;
    }
    
    static void printQueue(Queue<Process> queue){
        Process[] queueArray = queueToArray(queue);
        for (int i=0; i<queueArray.length; i++){
            System.out.println(i+1 + ")Process Id: " + queueArray[i].getId() + " " + "Process priority " + queueArray[i].getPriority());
        }
    }
    
    static Process[] queueToArray(Queue<Process> queue){
        
        // create array and copy queue
        int size = queue.size();
        Process[] queueArray = new Process[size];
        for (int i=0; i<size; i++){
            queueArray[i] = queue.poll();
        }
        
        // refill queue from array
        for (int i=0; i<size; i++){
            queue.add(queueArray[i]);
        }
        
        return queueArray;
    }
}