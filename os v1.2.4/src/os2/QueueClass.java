
package os2;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Formatter;

public class QueueClass {
    
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    
    // fills a array of queues (sorted by priority) with objects based on their priority
    static Queue<Process>[] fillQueue(int n, boolean manual){
        
        Queue<Process>[] queues = new LinkedList[7]; // array of queues
        
        for(int i=0; i<n; i++){     
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

            }else{
                // automatic fill
                oTask = new Process(i, rand.nextInt(7)+1, rand.nextInt(10)+OS2.clock, rand.nextInt(100)+1, rand.nextBoolean());
            }
            
            //sorts the object into the corresponding queue based on priority
            switch(oTask.getPriority()){
                case 1:
                    if(queues[0] == null){
                        queues[0] = new LinkedList<>();
                    }
                    queues[0].add(oTask);
                    break;
                case 2:
                    if(queues[1] == null){
                        queues[1] = new LinkedList<>();
                    }
                    queues[1].add(oTask);
                    break;
                case 3:
                    if(queues[2] == null){
                        queues[2] = new LinkedList<>();
                    }
                    queues[2].add(oTask);
                    break;
                case 4:
                    if(queues[3] == null){
                        queues[3] = new LinkedList<>();
                    }
                    queues[3].add(oTask);
                    break;
                case 5:
                    if(queues[4] == null){
                        queues[4] = new LinkedList<>();
                    }
                    queues[4].add(oTask);
                    break;
                case 6:
                    if(queues[5] == null){
                        queues[5] = new LinkedList<>();
                    }
                    queues[5].add(oTask);
                    break;
                case 7:
                    if(queues[6] == null){
                        queues[6] = new LinkedList<>();
                    }
                    queues[6].add(oTask);
                    break;
            }
        }
        return queues;
    }
    
    static void printQueue(Queue<Process> queue){
        Formatter fmt = new Formatter();
        fmt.format("%4s %3s %7s %9s %11s %12s %11s %6s %10s %10s %12s %7s\n", "#|", "ID |", "State |", "Priority |", "ArivalTime |", "ResponseTime |", "Burst_Time |", "IO |", "ioStartTime |", "ioWaitTime |", "TimeFinished |", "Remain_TimeSlice |"); 
        
        Process[] oQueueArray = queueToArray(queue);
        for (int i=0; i<oQueueArray.length; i++){
            fmt.format("%3s %3s %5s %8s %12s %13s %13s %10s %8s %12s %14s %16s\n", i+1, oQueueArray[i].getId(), oQueueArray[i].getState(), oQueueArray[i].getPriority(), oQueueArray[i].getArrivalTime(), oQueueArray[i].getResponseTime(), oQueueArray[i].getBurst_time(), oQueueArray[i].getIO(), oQueueArray[i].getIoStartTime(), oQueueArray[i].getIoWaitTime(), oQueueArray[i].getTimeFinished(), oQueueArray[i].getRemain_TimeSlice());
        }
        System.out.println(fmt);
    }
    
    static void printQueueArray(Queue<Process>[] queues){
        for(int i=0; i<queues.length; i++){
            if(queues[i] != null){
                System.out.println("\nPriority " + (i+1));
                printQueue(queues[i]);
            }
        }
    }
    
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
