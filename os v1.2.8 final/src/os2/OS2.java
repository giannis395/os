/*
    *Students/Participant: Tsoplakis Ioannis 2020180, Emmanuel Sfakianakis 2021163
    *Course: Design of Operating Systems
    *Code Version: 1.2.8 (final)
*/

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

        // program setup block
        boolean typeTasksCreation, showmore;
        {
            String strTypeTasksCreation, strShowmore;
            do {
                System.out.println("Please give the type of tasks creation (Automatic or Manual): ");
                strTypeTasksCreation = scanner.next(); 
            } while (!(strTypeTasksCreation.equalsIgnoreCase("manual") || strTypeTasksCreation.equalsIgnoreCase("automatic")));
            do {
                System.out.println("Do you want to see the results in detail? (Yes or No)");
                strShowmore = scanner.next();
            } while (!(strShowmore.toLowerCase().equals("yes") || strShowmore.toLowerCase().equals("no")));

            typeTasksCreation = (strTypeTasksCreation.toLowerCase().equals("manual")); 
            showmore = (strShowmore.toLowerCase().equals("yes"));
        }
        
        // selects type of tasks creation
        if(typeTasksCreation){
            // Manual
            String n;
            do {
                do {
                    System.out.println("Please write the number of the processes: ");
                    n = scanner.next(); 
                } while (!QueueClass.isNum(n));
            } while (Integer.parseInt(n) < 0); 

            arrivalQueue = QueueClass.fillArivalQueue(Integer.parseInt(n));
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

        // print the results
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
        
        // calculate response time if its run for first time
        if(task.getResponseTime() == -1 && task.getBurst_time() > 0){task.setResponseTime(clock - task.getArrivalTime()); task.setTimeStarted(clock);}
        
        for(int i=0; i<timeSlice; i++){
            clock();
            
            // simulate the normal run of process (no I/O)
            task.setBurst_time(task.getBurst_time()-1);
            task.setRemain_TimeSlice(task.getRemain_TimeSlice()-1);
           
            // if the process is complete
            if(task.getBurst_time() <= 0){
                task.setTimeFinished(clock);
                task.setTurnaroundTime(task.getTimeFinished() - (task.getArrivalTime()+task.getIoWaitTime()));// calculate turnaround time
                task.setState(3);
                oCompleteProcess = Process.addToArray(oCompleteProcess, task);
                return;
            }
            
            // simulate I/O of process
            if(task.getIO() && rand.nextBoolean()){
                task.setState(2);
                task.setIoStartTime(clock);
                task.setRemain_TimeSlice((task.getBurst_time()<3 && task.getBurst_time()>0) ? task.getBurst_time() : 3); // reset remain time of process
                oBlockedList = Process.addToArray(oBlockedList, task);
                return;
            }
        }  
        // end of time slice of the process
        task.setRemain_TimeSlice((task.getBurst_time()<3 && task.getBurst_time()>0) ? task.getBurst_time() : 3); // reset remain time of process
        task.setState(0);
        readyQueues[task.getPriority()-1].add(task);
    }
    
    /*Simulates random I/O in process that are in blocked list
      and then adds it to readyQueue, removes it from blocked list
      and calculate the I/O waiting time */
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
