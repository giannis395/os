/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os2;

import java.util.PriorityQueue;

/**
 *
 * @author SHAKA
 */
    public class Process implements Comparable<Process>{
    
    private int id;
    private int state = 0; // ready(0) running(1) blocked(2)
    private int priority; // 1 to 7 (1 is highest)
    private int arrivalTime;
    private int responseTime;
    private int io;
    private int memory_point;
    private int burst_time;
    private int timeStarted;
    
    public Process(int id, int priority, int arrivalTime, int io, int memory_point, int burst_time) {
        this.id = id;
        this.priority = priority;
        this.io = io;
        this.memory_point = memory_point*10;
        this.burst_time = burst_time;
        this.arrivalTime = arrivalTime;
    }
    
    @Override
    public int compareTo(Process other){
        if (this.priority == other.priority) {
            if (this.timeStarted == other.timeStarted) {
                return this.id - other.id;
            } else {
                return other.timeStarted - this.timeStarted;
            }
        } else {
            return this.priority - other.priority;
        }
    }
    
    @Override 
    public String toString(){return "Process{" + "id=" + id + ", state=" + state + ", priority=" + priority + ", io=" + io + ", memory_point=" + memory_point + ", burst_time=" + burst_time + '}';}

    // getters and setters
    public int getId(){return id;}
    public int getArrivalTime() { return arrivalTime;}
    public int getResponseTime() {return responseTime;}
    public int getState(){return state;}
    public int getPriority(){return priority;}
    public int getIo(){return io;}
    public int getMemory_point(){return memory_point;}
    public int getBurst_time(){return burst_time;}
    public int getTimeStarted() {return timeStarted;}

    public void setId(int id){this.id = id;}
    public void setResponseTime(int rt) {responseTime=rt;}
    public void setState(int state){this.state = state;}
    public void setArrivalTime(int arrivalTime){this.arrivalTime = arrivalTime;}
    public void seTimeStarted(int time) {timeStarted=time;}
    public void setPriority(int priority){this.priority = priority;}
    public void setIo(int io){this.io = io;}
    public void setMemory_point(int memory_point){this.memory_point = memory_point;}
    public void setBurst_time(int burst_time){this.burst_time = burst_time;}
    
    public void execute(int time){
        
    }
}
