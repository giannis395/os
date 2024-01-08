
package os2;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Formatter;

public class QueueClass {
    
    public static Scanner scanner = OS2.scanner;
    public static Random rand = new Random();
    
    // fills a array of queues (sorted by priority) with objects based on their priority
    static Queue<Process>[] fillQueue(boolean manual){
        return fillQueue(1, manual);
    }
    static Queue<Process>[] fillQueue(int n, boolean manual){
        
        Queue<Process>[] queues = new LinkedList[7]; // array of queues
        for(int i=0; i<7; i++){queues[i] = new LinkedList<>();}
        
        int i=0, user=0;
        do{     
            //fill a process obj
            Process oTask;
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
                oTask = new Process(i, priority, arrivalTime, burstTime, rand.nextBoolean());
                if(i>=n-1){user=1;}

            }else{
                // automatic fill
                oTask = new Process(i, rand.nextInt(7)+1, rand.nextInt(3)+OS2.clock, rand.nextInt(10)+1, false);//rand.nextBoolean());
                
                System.out.println("Do you want to continue create processes? Press 0 to continueue");
                user = scanner.nextInt();
            }
            
            //sorts the object into the corresponding queue based on priority
            switch(oTask.getPriority()){
                case 1:
                    queues[0].add(oTask);
                    break;
                case 2:
                    queues[1].add(oTask);
                    break;
                case 3:
                    queues[2].add(oTask);
                    break;
                case 4:
                    queues[3].add(oTask);
                    break;
                case 5:
                    queues[4].add(oTask);
                    break;
                case 6:
                    queues[5].add(oTask);
                    break;
                case 7:
                    queues[6].add(oTask);
                    break;
            }
            i++;
        }while (user == 0);
        return queues;
    }
    
    // print a queue in list form
    static void printQueue(Queue<Process> queue){
        Formatter fmt = new Formatter();
        fmt.format("%4s %3s %7s %9s %11s %12s %11s %6s %10s %10s %12s %7s\n", "#|", "ID |", "State |", "Priority |", "ArivalTime |", "ResponseTime |", "Burst Time |", "IO |", "ioStartTime |", "ioWaitTime |", "TimeFinished |", "Remain_TimeSlice |"); 
        
        Process[] oQueueArray = queueToArray(queue);
        for (int i=0; i<oQueueArray.length; i++){
            fmt.format("%3s %3s %5s %8s %12s %13s %13s %10s %8s %12s %14s %16s\n", i+1, oQueueArray[i].getId(), oQueueArray[i].getState(), oQueueArray[i].getPriority(), oQueueArray[i].getArrivalTime(), oQueueArray[i].getResponseTime(), oQueueArray[i].getBurst_time(), oQueueArray[i].getIO(), oQueueArray[i].getIoStartTime(), oQueueArray[i].getIoWaitTime(), oQueueArray[i].getTimeFinished(), oQueueArray[i].getRemain_TimeSlice());
        }
        System.out.println(fmt);
    }
    
    // print a queue array in list form
    static void printQueueArray(Queue<Process>[] queues){
        for(int i=0; i<queues.length; i++){
            if(queues[i] != null && !queues[i].isEmpty()){
                System.out.println("\nPriority " + (i+1));
                printQueue(queues[i]);
            }
        }
    }
    
    // converts a queue to array
    static Process[] queueToArray(Queue<Process> queue){
        
        // create array and copy queue
        int size = queue.size();
        Process[] oQueueArray = new Process[size];
        for (int i=0; i<size; i++){
            oQueueArray[i] = queue.poll();
        }
        
        // refill queue from array
        for (int i=0; i<size; i++){
            queue.add(oQueueArray[i]);
        }
        return oQueueArray;
    }
}
