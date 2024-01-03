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
        
        
        Queue<Process>[] queues = fillQueue(n, false); 
        if(queues[0] != null){
            printQueue(queues[0]);
            System.out.println();
        }
        if(queues[1] != null){
            printQueue(queues[1]);
            System.out.println();
        }
        if(queues[2] != null){
            printQueue(queues[2]);
            System.out.println();
        }
        if(queues[3] != null){
            printQueue(queues[3]);
            System.out.println();
        }
        if(queues[4] != null){
            printQueue(queues[4]);
            System.out.println();
        }
        if(queues[5] != null){
            printQueue(queues[5]);
            System.out.println();
        }
        if(queues[6] != null){
            printQueue(queues[6]);
        }
    }
    
    
    static Process fillProcess(int i,boolean manual){ // true(manual), false(automatic)
        
        Process task;
        
        if(manual){
            //manual fill
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
            task = new Process(i, priority, arrivalTime, rand.nextInt(10), burstTime);
            
        }else{
            // automatic fill
            task = new Process(i, rand.nextInt(7)+1, rand.nextInt(100), rand.nextInt(10), rand.nextInt(10)+1);
        }
        return task;
    }
    
    static Queue<Process>[] fillQueue(int n, boolean manual){
        
        Queue<Process>[] queues = new LinkedList[7]; 
        for(int i=0; i<n; i++){ 
            Process temp = fillProcess(i, manual);
            switch(temp.getPriority()){
                case 1:
                    if(queues[0] == null){
                        queues[0] = new LinkedList<>();
                    }
                    queues[0].add(temp);
                    break;
                case 2:
                    if(queues[1] == null){
                        queues[1] = new LinkedList<>();
                    }
                    queues[1].add(temp);
                    break;
                case 3:
                    if(queues[2] == null){
                        queues[2] = new LinkedList<>();
                    }
                    queues[2].add(temp);
                    break;
                case 4:
                    if(queues[3] == null){
                        queues[3] = new LinkedList<>();
                    }
                    queues[3].add(temp);
                    break;
                case 5:
                    if(queues[4] == null){
                        queues[4] = new LinkedList<>();
                    }
                    queues[4].add(temp);
                    break;
                case 6:
                    if(queues[5] == null){
                        queues[5] = new LinkedList<>();
                    }
                    queues[5].add(temp);
                    break;
                case 7:
                    if(queues[6] == null){
                        queues[6] = new LinkedList<>();
                    }
                    queues[6].add(temp);
                    break;
            }
        }
        return queues;
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