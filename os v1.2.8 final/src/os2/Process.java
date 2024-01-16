
package os2;


import java.util.Formatter;
import java.util.Random;

public class Process {
    
    public static Random rand = new Random();
    
    private int id;
    private int priority; // 1 to 7 (1 is highest)
    private int arrivalTime;
    private int sBurstTime;
    private boolean io;
    
    private int state = 0; // ready(0) running(1) blocked(2) complete(3)
    private int ioStartTime = 0;
    private int ioWaitTime = 0;
    private int timeFinished = -1;
    private int timeStarted = -1;
    private int burst_time;
    private int remain_TimeSlice;
    
    private int responseTime = -1;
    private int turnaroundTime;
    
    public Process(int id, int priority, int arrivalTime, int burst_time, boolean io) {
        this.id = id;
        this.priority = priority;
        this.burst_time = burst_time;
        this.sBurstTime = burst_time;
        this.arrivalTime = arrivalTime;
        this.io = io;
        this.remain_TimeSlice = (burst_time < 3) ? burst_time : 3;
    }
    
    // getters
    public int getId(){return id;}
    public int getArrivalTime(){ return arrivalTime;}
    public int getResponseTime(){return responseTime;}
    public int getTurnaroundTime(){return turnaroundTime;}
    public boolean getIO(){return io;}
    public int getIoStartTime(){return ioStartTime;}
    public int getIoWaitTime(){return ioWaitTime;}
    public int getState(){return state;}
    public int getPriority(){return priority;}
    public int getRemain_TimeSlice(){return remain_TimeSlice;}
    public int getBurst_time(){return burst_time;}
    public int getSBurstTime(){return sBurstTime;}
    public int getTimeFinished(){return timeFinished;}
    public int getTimeStarted(){return timeStarted;}

    // setters
    public void setId(int id){this.id = id;}
    public void setResponseTime(int rt) {responseTime=rt;}
    public void setTurnaroundTime(int turnaroundTime){this.turnaroundTime = turnaroundTime;}
    public void setRemain_TimeSlice(int remain_TimeSlice) {this.remain_TimeSlice=remain_TimeSlice;}
    public void setIO(boolean io){this.io = io;}
    public void setIoStartTime(int ioStartTime){this.ioStartTime = ioStartTime;}
    public void setIoWaitTime(int ioWaitTime){this.ioWaitTime = ioWaitTime;}
    public void setState(int state){this.state = state;}
    public void setArrivalTime(int arrivalTime){this.arrivalTime = arrivalTime;}
    public void setPriority(int priority){this.priority = priority;}
    public void setBurst_time(int burst_time){this.burst_time = burst_time;}
    public void setTimeFinished(int timeFinished){this.timeFinished = timeFinished;}
    public void setTimeStarted(int timeStarted){this.timeStarted = timeStarted;}
    
    // average Response Time
    static double avgResponseTime(Process oTasksList[]){
        int responseTimeSum = 0, size = oTasksList.length;
        for(int i=0; i<size; i++){
            responseTimeSum += oTasksList[i].responseTime;
        }
        double avg = (double)responseTimeSum/((size==0) ? 1 : size);
        return avg;
    }
    
    // average Turnaround Time
    static double avgTurnaroundTime(Process oTasksList[]){
        int turnaroundTimeSum = 0, size = oTasksList.length;
        for(int i=0; i<size; i++){
            turnaroundTimeSum += oTasksList[i].timeFinished - (oTasksList[i].arrivalTime + oTasksList[i].ioWaitTime);
        }
        double avg = (double)turnaroundTimeSum/((size==0) ? 1 : size);
        return avg;
    }

    // print Process obj list
    static void printProcessArray(Process oList[]){
        Formatter fmt = new Formatter();
        fmt.format("%4s %3s %7s %9s %11s %12s %12s %11s %6s %10s %10s %12s %7s\n", "#|", "ID |", "State |", "Priority |", "ArivalTime |", "ResponseTime |", "TurnaroundTime |", "Burst_Time |", "IO |", "ioWaitTime |", "TimeStarted |", "TimeFinished |", "Remain_TimeSlice |"); 
        
        for (int i=0; i<oList.length; i++){
            fmt.format("%3s %3s %5s %8s %12s %13s %15s %19s %6s %7s %12s %14s %16s\n", i+1, oList[i].getId(), oList[i].getState(), oList[i].getPriority(), oList[i].getArrivalTime(), oList[i].getResponseTime(), oList[i].getTurnaroundTime(), oList[i].getBurst_time()+" Start("+oList[i].getSBurstTime()+")", oList[i].getIO(), oList[i].getIoWaitTime(), oList[i].timeStarted, oList[i].getTimeFinished(), oList[i].getRemain_TimeSlice());
        }
        System.out.println(fmt);
    }
    
    // add an element to arrayList
    static Process[] addToArray(Process oldArray[], Process newElement){
        int size = oldArray.length;
        Process[] newArray = new Process[size+1];
        
        // copy oldArray to newArray
        for(int i=0; i<size; i++){
            newArray[i] = oldArray[i];
        }
        newArray[size] = newElement; // add newElement
        return newArray;
    }
    
    // remove an element from a array
    static Process[] removeFromArray(Process oldArray[], int elementPosition){
        int size = oldArray.length;
        Process[] newArray = new Process[size-1];
        
        for(int i=0, k=0; i<size; i++){
            if(i != elementPosition){
                newArray[k]=oldArray[i];
                k++;
            }
        }
        return newArray;
    }
    
    // sort a process array by priority
    static Process[] sort(Process oTasksArray[]){

        Process temp;
        int n = oTasksArray.length;
        for(int i=0; i<n-1; i++){
            for(int j=0; j<n-i-1; j++){
                if(oTasksArray[j].getPriority() > oTasksArray[j+1].getPriority()){
                    temp = oTasksArray[j];
                    oTasksArray[j] = oTasksArray[j+1];
                    oTasksArray[j+1] = temp;
                }
            }
        }
        return oTasksArray;
    }
}