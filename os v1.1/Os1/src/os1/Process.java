/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os1;

/**
 *
 * @author SHAKA
 */
public class Process implements Comparable<Process>{
    
    private int id;
    private int state = 0; // ready(0) running(1) blocked(2)
    private int priority;
    private int pc = 0;
    private int arrivalTime;
    private int waitingTime;
    private boolean io;
    private int memory_point;
    private int burst_time;
    
    public Process(int id, int priority, int arrivalTime, boolean io, int memory_point, int burst_time) {
        this.id = id;
        this.priority = priority;
        this.io = io;
        this.memory_point = memory_point;
        this.burst_time = burst_time;
        this.arrivalTime = arrivalTime;
    }
    
    @Override
    public int compareTo(Process otherProcess){
        int comparison = Integer.compare(this.priority, otherProcess.priority);
        if (comparison == 0)
            return Integer.compare(this.arrivalTime, otherProcess.arrivalTime);
        return comparison;
    }
    
    @Override 
    public String toString(){return "Process{" + "id=" + id + ", state=" + state + ", priority=" + priority + ", pc=" + pc + ", io=" + io + ", memory_point=" + memory_point + ", burst_time=" + burst_time + '}';}

    public int getId(){return id;}
    public int getArrivalTime() { return arrivalTime;}
    public int getState(){return state;}
    public int getPriority(){return priority;}
    public int getPc(){return pc;}
    public boolean isIo(){return io;}
    public int getMemory_point(){return memory_point;}
    public int getBurst_time(){return burst_time;}

    public void setId(int id){this.id = id;}
    public void setState(int state){this.state = state;}
    public void setArrivalTime(int arrivalTime){this.arrivalTime = arrivalTime;}
    public void setPriority(int priority){this.priority = priority;}
    public void setPc(int pc){this.pc = pc;}
    public void setIo(boolean io){this.io = io;}
    public void setMemory_point(int memory_point){this.memory_point = memory_point;}
    public void setBurst_time(int burst_time){this.burst_time = burst_time;}
    
    
}
