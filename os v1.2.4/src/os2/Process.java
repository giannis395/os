
package os2;


import java.util.Random;
import java.util.Scanner;

public class Process {
    
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    
    private int id;
    private int state = 0; // ready(0) running(1) blocked(2)
    private int priority; // 1 to 7 (1 is highest)
    private int arrivalTime;
    private int responseTime = -1;
    private boolean io;
    private int ioStartTime = 0;
    private int ioWaitTime = 0;
    private int burst_time;
    private int timeFinished = 0;
    private int remain_TimeSlice;
    
    public Process(int id, int priority, int arrivalTime, int burst_time, boolean io) {
        this.id = id;
        this.priority = priority;
        this.burst_time = burst_time;
        this.arrivalTime = arrivalTime;
        this.io = io;
        this.remain_TimeSlice = (burst_time < 10) ? burst_time : 10;
    }
    
    // getters and setters
    public int getId(){return id;}
    public int getArrivalTime(){ return arrivalTime;}
    public int getResponseTime(){return responseTime;}
    public boolean getIO(){return io;}
    public int getIoStartTime(){return ioStartTime;}
    public int getIoWaitTime(){return ioWaitTime;}
    public int getState(){return state;}
    public int getPriority(){return priority;}
    public int getRemain_TimeSlice(){return remain_TimeSlice;}
    public int getBurst_time(){return burst_time;}
    public int getTimeFinished(){return timeFinished;}

    public void setId(int id){this.id = id;}
    public void setResponseTime(int rt) {responseTime=rt;}
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
        double avg = responseTimeSum/size;
        return avg;
    }
    
    // average Turnaround Time
    static double avgTurnaroundTime(Process oTasksList[]){
        int turnaroundTimeSum = 0, size = oTasksList.length;
        for(int i=0; i<size; i++){
            turnaroundTimeSum += oTasksList[i].timeFinished - (oTasksList[i].arrivalTime + oTasksList[i].ioWaitTime);
        }
        double avg = turnaroundTimeSum/size;
        return avg;
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