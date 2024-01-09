
package os2;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class QueueClass {
    
    public static Scanner scanner = OS2.scanner;
    public static Random rand = new Random();
    
    // fills a Queue (sorted by Arrival Time) with objects
    static Queue<Process> fillArivalQueue(Process oTasksArray[]){

        // sort array by Arrival Time
        Process temp;
        int n = oTasksArray.length;
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n-i-1; j++){
                if(oTasksArray[j].getArrivalTime() > oTasksArray[j+1].getArrivalTime()){
                    temp = oTasksArray[j];
                    oTasksArray[j] = oTasksArray[j+1];
                    oTasksArray[j+1] = temp;
                }
            }
        }
        
        // create the arrival queue
        Queue<Process> arrivalQueue = new LinkedList<>();
        
        for(int i=0; i<n; i++){
            arrivalQueue.add(oTasksArray[i]);
        }
        
        return arrivalQueue;
    }
    
    // Manual fill of Arrival Queue
    static Queue<Process> fillArivalQueue(int n){
        
        Process oTasksArray[] = new Process[0];
        
        for(int i=0; i<n; i++){
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
            Process oTask = new Process(i, priority, arrivalTime, burstTime, rand.nextBoolean());
            oTasksArray = Process.addToArray(oTasksArray, oTask);
        }
        return fillArivalQueue(oTasksArray);
    }
    
    // Automatic fill of Arrival Queue
    static Queue<Process> fillArivalQueue(){
        
        Process oTasksArray[] = new Process[0];
        int i=0, user=0;
        
        do{     
            Process oTask;
            oTask = new Process(i, rand.nextInt(7)+1, rand.nextInt(10), rand.nextInt(10)+1, rand.nextBoolean());

            System.out.println("Do you want to continue create processes? Press 0 to continueue");
            user = scanner.nextInt();
            oTasksArray = Process.addToArray(oTasksArray, oTask);
            i++;
        }while (user == 0);
        
        return fillArivalQueue(oTasksArray);
    }
    
    // Add an obj to Ready Queue (based on the priority)
    static void addToReadyQueues(Queue<Process>[] readyQueues, Process oTask){
        
        //sorts the object into the corresponding queue based on priority
        switch(oTask.getPriority()){
            case 1:
                readyQueues[0].add(oTask);
                break;
            case 2:
                readyQueues[1].add(oTask);
                break;
            case 3:
                readyQueues[2].add(oTask);
                break;
            case 4:
                readyQueues[3].add(oTask);
                break;
            case 5:
                readyQueues[4].add(oTask);
                break;
                case 6:
                readyQueues[5].add(oTask);
                break;
            case 7:
                readyQueues[6].add(oTask);
                break;
        }
    }
    
    // print a queue in list form
    static void printQueue(Queue<Process> queue){
        Process.printProcessArray(queueToArray(queue));
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
    
    // checks if the ready queues are empty
    static boolean readyQueueIsEmpty(Queue<Process>[] readyQueues){
        for(int i=0; i<7; i++){
            if(!readyQueues[i].isEmpty()){
                return false;
            }
        }
        return true;
    }
}