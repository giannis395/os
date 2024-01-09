
package os2;


import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

public class OS2 {

    public static Scanner scanner = new Scanner("false 0 0 0 0 0 0 0 0 0 0 0 1");
    public static Random rand = new Random();
    public static int clock=-1;
    
    private static final Queue<Process>[] readyQueues = new LinkedList[7]; // array of queues
    private static Queue<Process> arrivalQueue;
    private static Process[] oBlockedList = new Process[0];
    private static Process[] oCompleteProcess = new Process[0];
    private static int nextProcess = -1;

    public static void main(String[] args){
    
        // create Queues for readyQueues array
        for(int i=0; i<7; i++){readyQueues[i] = new LinkedList<>();}

        System.out.println("Please give the type of tasks creation (automatic(false), manual(true): ");
        boolean typeTasksCreation = scanner.nextBoolean(); 
        
        if(typeTasksCreation){
            // Manual
            System.out.println("Please write the number of the processes: ");
            int n = scanner.nextInt();  

            arrivalQueue = QueueClass.fillArivalQueue(n);
            QueueClass.printQueue(arrivalQueue);
        }
        else{
            // Automatic
            arrivalQueue = QueueClass.fillArivalQueue();
            QueueClass.printQueue(arrivalQueue);
        }
        
        //************************************************
        clock();

        Process currentProcess;
        while(nextProcess != -1 || !QueueClass.readyQueueIsEmpty(readyQueues) || oBlockedList.length != 0 || !arrivalQueue.isEmpty()){
            if(nextProcess != -1){
                currentProcess = readyQueues[nextProcess].poll();
                execute(currentProcess);
                randomIO();
                nextProcess();
            }

        }
        System.out.println(clock);
        System.out.println("Avg Response time " + Process.avgResponseTime(oCompleteProcess));
        System.out.println("Avg turnaround time " + Process.avgTurnaroundTime(oCompleteProcess));
        Process.printProcessArray(oCompleteProcess);
    }
    
    // It simulates a process running on the cpu
    static void execute(Process task){
        task.setState(1);
        int timeSlice = task.getRemain_TimeSlice();
        
        if(task.getResponseTime() == -1 && task.getBurst_time() > 0){task.setResponseTime(clock - task.getArrivalTime());}
        for(int i=0; i<=timeSlice; i++){
            if(task.getBurst_time() > 0){
                clock();
                // if the process is not complete
                
                // simulate the normal run of process (no I/O)
                task.setBurst_time(task.getBurst_time()-1);
                task.setRemain_TimeSlice(task.getRemain_TimeSlice()-1);  
                
                if(rand.nextInt(10)+1 <= 2 && task.getIO()){
                    // simulate I/O of process
                    task.setState(2);
                    task.setIoStartTime(clock);
                    oBlockedList = Process.addToArray(oBlockedList, task);
                    return;
                }
            }else{
                // if the process is complete
                task.setTimeFinished(clock);
                task.setTurnaroundTime(task.getTimeFinished() - (task.getArrivalTime()+task.getIoWaitTime()));
                task.setState(3);
                oCompleteProcess = Process.addToArray(oCompleteProcess, task);
                return;
            }
        }  
        // timeout of process
        task.setRemain_TimeSlice((task.getBurst_time()<3) ? task.getBurst_time() : 3); // reset remain time of process
        task.setState(0);
        readyQueues[task.getPriority()-1].add(task);
    }
    
    //Give random I/O in process that are in blocked list
    static void randomIO(){
        
        int tempIoWaitTime, size = oBlockedList.length;
        for(int i=0; i<size; i++){
            if(rand.nextBoolean()){
                tempIoWaitTime = clock - oBlockedList[i].getIoStartTime();
                oBlockedList[i].setIoWaitTime(oBlockedList[i].getIoWaitTime() + tempIoWaitTime);
                oBlockedList[i].setState(0);
                readyQueues[oBlockedList[i].getPriority()-1].add(oBlockedList[i]);
            }
        }
    }
    
    static void clock(){
        clock++;
        if(!arrivalQueue.isEmpty()){
            for(int i=0; i<=arrivalQueue.size(); i++){
                if(arrivalQueue.peek().getArrivalTime() == clock){
                    QueueClass.addToReadyQueues(readyQueues, arrivalQueue.poll());
                }
            }
        }
        nextProcess();
    }
    

    static void nextProcess(){
        for(int i=0; i<7; i++){
            if(readyQueues[i].peek() != null){
                nextProcess = i;
                return;
            }
        }
        System.out.println("ready Queues is currently empty");
        nextProcess = -1;
    }    
}