
package os2;


import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;

public class Process {
    
    public static Scanner scanner = OS2.scanner;
    public static Random rand = new Random();
    
    private int id;
    private int state = 0; // ready(0) running(1) blocked(2) complete(4)
    private int priority; // 1 to 7 (1 is highest)
    private int arrivalTime;
    private int responseTime = -1;
    private int turnaroundTime;
    private boolean io;
    private int ioStartTime = 0;
    private int ioWaitTime = 0;
    private int burst_time;
    private int sBurstTime;
    private int timeFinished = 0;
    private int remain_TimeSlice;
    
    public Process(int id, int priority, int arrivalTime, int burst_time, boolean io) {
        this.id = id;
        this.priority = priority;
        this.burst_time = burst_time;
        this.sBurstTime = burst_time;
        this.arrivalTime = arrivalTime;
        this.io = io;
        this.remain_TimeSlice = (burst_time < 3) ? burst_time : 3;
    }

    @Override
    public String toString() {
        return "Process{" + "id=" + id + ", state=" + state + ", priority=" + priority + ", arrivalTime=" + arrivalTime + ", responseTime=" + responseTime + ", turnaroundTime=" + turnaroundTime + ", io=" + io + ", ioStartTime=" + ioStartTime + ", ioWaitTime=" + ioWaitTime + ", burst_time=" + burst_time + ", sBurstTime=" + sBurstTime + ", timeFinished=" + timeFinished + ", remain_TimeSlice=" + remain_TimeSlice + '}';
    }
    
    
    
    // getters and setters
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
    
    // average Response Time
    static double avgResponseTime(Process oTasksList[]){
        int responseTimeSum = 0, size = oTasksList.length;
        for(int i=0; i<size; i++){
            responseTimeSum += oTasksList[i].responseTime;
        }
        double avg = responseTimeSum/((size==0) ? 1 : size);
        return avg;
    }
    
    // average Turnaround Time
    static double avgTurnaroundTime(Process oTasksList[]){
        int turnaroundTimeSum = 0, size = oTasksList.length;
        for(int i=0; i<size; i++){
            turnaroundTimeSum += oTasksList[i].timeFinished - (oTasksList[i].arrivalTime + oTasksList[i].ioWaitTime);
        }
        double avg = turnaroundTimeSum/((size==0) ? 1 : size);
        return avg;
    }

    // print Process obj list
    static void printProcessArray(Process oList[]){
        Formatter fmt = new Formatter();
        fmt.format("%4s %3s %7s %9s %11s %12s %12s %11s %6s %10s %10s %12s %7s\n", "#|", "ID |", "State |", "Priority |", "ArivalTime |", "ResponseTime |", "TurnaroundTime |", "Burst_Time |", "IO |", "ioStartTime |", "ioWaitTime |", "TimeFinished |", "Remain_TimeSlice |"); 
        
        for (int i=0; i<oList.length; i++){
            fmt.format("%3s %3s %5s %8s %12s %13s %15s %19s %6s %8s %12s %14s %16s\n", i+1, oList[i].getId(), oList[i].getState(), oList[i].getPriority(), oList[i].getArrivalTime(), oList[i].getResponseTime(), oList[i].getTurnaroundTime(), oList[i].getBurst_time()+" Start("+oList[i].getSBurstTime()+")", oList[i].getIO(), oList[i].getIoStartTime(), oList[i].getIoWaitTime(), oList[i].getTimeFinished(), oList[i].getRemain_TimeSlice());
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
}