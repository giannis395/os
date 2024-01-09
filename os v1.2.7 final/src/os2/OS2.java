
package os2;


import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;

public class OS2 {

    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    public static int clock=-1;
    
    private static final Queue<Process>[] readyQueues = new LinkedList[7]; // array of queues
    private static Queue<Process> arrivalQueue;
    private static Process[] oBlockedList = new Process[0];
    private static Process[] oCompleteProcess = new Process[0];

    public static void main(String[] args){
    
        // create Queues for readyQueues array
        for(int i=0; i<7; i++){readyQueues[i] = new LinkedList<>();}

        System.out.println("Please give the type of tasks creation (automatic(false), manual(true): ");
        boolean typeTasksCreation = scanner.nextBoolean(); 
        System.out.println("Do you want to see the results in detail? | YES(true), NO(false): ");
        boolean showmore = scanner.nextBoolean();
        
        if(typeTasksCreation){
            // Manual
            System.out.println("Please write the number of the processes: ");
            int n = scanner.nextInt();  

            arrivalQueue = QueueClass.fillArivalQueue(n);
        }
        else{
            // Automatic
            arrivalQueue = QueueClass.fillArivalQueue();
        }
        
        if(showmore){
            System.out.println("Arrival Queue:");
            QueueClass.printQueue(arrivalQueue);
        }
        
        //*************************main program*********************************
        int nextProcess = -1;
        Process currentProcess;
        
        clock();
        
        // Running unlit there is no more process in any list
        while(nextProcess != -1 || !QueueClass.readyQueueIsEmpty(readyQueues) || oBlockedList.length != 0 || !arrivalQueue.isEmpty()){
            nextProcess = nextProcess();
            if(nextProcess != -1){
                randomIO();
                currentProcess = readyQueues[nextProcess].poll();
                execute(currentProcess);
            }else{
                randomIO();
                clock();
            }

        }

        if(showmore){
            System.out.println("CompleteProcess list:");
            Process.printProcessArray(Process.sort(oCompleteProcess));
        }
        System.out.println("Avg Response time " + Process.avgResponseTime(oCompleteProcess));
        System.out.println("Avg turnaround time " + Process.avgTurnaroundTime(oCompleteProcess));
        
        //**********************************************************************
    }
    
    // Simulates a process running on the cpu
    static void execute(Process task){
        task.setState(1);
        int timeSlice = task.getRemain_TimeSlice();
        
        if(task.getResponseTime() == -1 && task.getBurst_time() > 0){task.setResponseTime(clock - task.getArrivalTime()); task.setTimeStarted(clock);}
        for(int i=0; i<timeSlice; i++){
            clock();
            
            // simulate the normal run of process (no I/O)
            task.setBurst_time(task.getBurst_time()-1);
            task.setRemain_TimeSlice(task.getRemain_TimeSlice()-1);
            
            // simulate I/O of process
            if(task.getIO() && rand.nextBoolean()){
                task.setState(2);
                task.setIoStartTime(clock);
                oBlockedList = Process.addToArray(oBlockedList, task);
                return;
            }
           
            if(task.getBurst_time() <= 0){
                // if the process is complete
                if(task.getBurst_time()<0){task.setBurst_time(0);}
                task.setTimeFinished(clock);
                task.setTurnaroundTime(task.getTimeFinished() - (task.getArrivalTime()+task.getIoWaitTime()));
                task.setState(3);
                oCompleteProcess = Process.addToArray(oCompleteProcess, task);
                return;
            }
        }  
        // end of time slice of the process
        task.setRemain_TimeSlice((task.getBurst_time()<3 && task.getBurst_time()>0) ? task.getBurst_time() : 3); // reset remain time of process
        task.setState(0);
        readyQueues[task.getPriority()-1].add(task);
    }
    
    //Gives random I/O in process that are in blocked list
    static void randomIO(){
        
        int tempIoWaitTime, k=0, size = oBlockedList.length;
        for(int i=0; i<size-k; i++){
            if(rand.nextInt(10)+1 <= 2){
                tempIoWaitTime = clock - oBlockedList[i].getIoStartTime();
                oBlockedList[i].setIoWaitTime(oBlockedList[i].getIoWaitTime() + tempIoWaitTime);
                oBlockedList[i].setState(0);
                readyQueues[oBlockedList[i].getPriority()-1].add(oBlockedList[i]);
                oBlockedList = Process.removeFromArray(oBlockedList, i);
                k++;
            }
        }
    }
    
    // Simulates the Clock and the Arrival of new processes
    static void clock(){
        clock++;
        if(!arrivalQueue.isEmpty()){
            for(int i=0; i<=arrivalQueue.size(); i++){
                if(arrivalQueue.peek().getArrivalTime() == clock){
                    QueueClass.addToReadyQueues(readyQueues, arrivalQueue.poll());
                }else{
                    return;
                }
            }
        }
    }
    

    static int nextProcess(){
        for(int i=0; i<7; i++){
            if(readyQueues[i].peek() != null){
                return i;
            }
        }
        return -1;
    }    
}