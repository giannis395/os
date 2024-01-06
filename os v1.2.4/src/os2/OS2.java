
package os2;


import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class OS2 {

    //public static Scanner scanner = new Scanner(System.in);
    public static Scanner scanner = new Scanner("true 2 false");
    public static Random rand = new Random();
    public static int clock=0;
    public static int reset;
    
    public static Queue<Process>[] queues;
    public static Process[] oBlockedList = new Process[0];
    public static Process[] oCompleteProcess = new Process[0];

    public static void main(String[] args) {

        System.out.println("Please give the program running type (automatic(false), manual(true): ");
        boolean programRunType = scanner.nextBoolean();
        
        if(programRunType){
            System.out.println("Please write the number of the processes: ");
            int n = scanner.nextInt();
            System.out.println("Please give the type of tasks creation (automatic(false), manual(true): ");
            boolean typeTasksCreation = scanner.nextBoolean();    

            queues = QueueClass.fillQueue(n, typeTasksCreation);
            QueueClass.printQueueArray(queues);
            Process task;

            for(int i=0; i<7; i++){
                while(!(queues[i].isEmpty())){
                    task = queues[i].poll();
                    if(task.getArrivalTime()<=clock){
                        execute(task);
                        randomIO();
                    }else{
                        queues[i].add(task);
                    }
                    if(reset == 1){
                        reset = 0;
                        i = 0;
                        break;
                    }
                }
            }
        }
        else{
            
        }
    }
    
    // It simulates a process running on the cpu
    static void execute(Process task){
        task.setState(1);
        int timeSlice = task.getRemain_TimeSlice();
        
        if(task.getResponseTime() == -1 && task.getBurst_time() > 0){task.setResponseTime(clock);}
        for(int i=0; i<timeSlice; i++){
            if(task.getBurst_time() > 0){
                // if the process is not complete
                
                // simulate the normal run of process (no I/O)
                task.setBurst_time(task.getBurst_time()-1);
                task.setRemain_TimeSlice(task.getRemain_TimeSlice()-1);  
                
                if(rand.nextInt(10)+1 <= 2 && task.getIO()){
                    // simulate I/O of process
                    task.setState(2);
                    task.setIoStartTime(clock);
                    oBlockedList = Process.addToArray(oBlockedList, task);
                    clock++;
                    return;
                }
                clock++;
            }else{
                // if the process is complete
                task.setTimeFinished(clock);
                oCompleteProcess = Process.addToArray(oBlockedList, task);
                clock++;
                return;
            }
        }  
        // timeout of process
        task.setRemain_TimeSlice((task.getBurst_time()<10) ? task.getBurst_time() : 10); // reset remain time of process
        task.setState(0);
        queues[task.getPriority()-1].add(task);
    }
    
    //Give random I/O in process that are in blocked list
    static void randomIO(){
        int tempIoWaitTime;
        for(int i=0; i<oBlockedList.length; i++){
            if(rand.nextBoolean()){
                tempIoWaitTime = clock - oBlockedList[i].getIoStartTime();
                oBlockedList[i].setIoWaitTime(oBlockedList[i].getIoWaitTime() + tempIoWaitTime);
                oBlockedList[i].setState(0);
                queues[oBlockedList[i].getPriority()-1].add(oBlockedList[i]);
                reset = 1;
            }
        }
    }
}