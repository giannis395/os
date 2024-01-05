/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Process {
    
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    
    private int id;
    private int state = 0; // ready(0) running(1) blocked(2)
    private int priority; // 1 to 7 (1 is highest)
    private int arrivalTime;
    private int responseTime =-1;
    private int io;
    private int burst_time;
    private int timeStarted;
    private int remain;
    private int turn;
    
    public Process(int id, int priority, int arrivalTime, int io, int burst_time) {
        this.id = id;
        this.priority = priority;
        this.io = io;
        this.burst_time = burst_time;
        this.arrivalTime = arrivalTime;
        remain = burst_time;
    }
    
    @Override 
    public String toString(){return "Process{" + "id=" + id + ", state=" + state + ", priority=" + priority + ", io=" + io + ", burst_time=" + burst_time + '}';}

    // getters and setters
    public int getId(){return id;}
    public int getArrivalTime() { return arrivalTime;}
    public int getResponseTime() {return responseTime;}
    public int getState(){return state;}
    public int getPriority(){return priority;}
    public int getRemain() {return remain;}
    public int getIo(){return io;}
    public int getTurn() { return turn;}
    public int getBurst_time(){return burst_time;}
    public int getTimeStarted() {return timeStarted;}

    public void setId(int id){this.id = id;}
    public void setTurn(int turn) {this.turn=turn;}
    public void setResponseTime(int rt) {responseTime=rt;}
    public void setRemain(int remain) {this.remain=remain;}
    public void setState(int state){this.state = state;}
    public void setArrivalTime(int arrivalTime){this.arrivalTime = arrivalTime;}
    public void seTimeStarted(int time) {timeStarted=time;}
    public void setPriority(int priority){this.priority = priority;}
    public void setIo(int io){this.io = io;}
    public void setBurst_time(int burst_time){this.burst_time = burst_time;}
    
    public void execute(int time, Queue<Process>[] queues){
       if (responseTime == -1) {
            responseTime = Clock.getTime();
        }

        int executionTime = Math.min(time, remain);
        System.out.println("Executing " + id + " with priority " +priority+ " for " + executionTime + " units.");

        remain -= executionTime;

        if (remain == 0) {
            turn = Clock.getTime() - arrivalTime;
        } else if (io > 0) {
            // Simulate I/O operation
            System.out.println(id + " performing I/O for " + io + " units.");
            queues[7].add(this);
            Clock.tick(io);
            System.out.println(id + " I/O completed.");
            
            queues[priority -1].add(this);
        }
    }
  
    static Process fillProcess(int i,boolean manual){ // true(manual), false(automatic)
        
        Process task;
        
        if(manual){
            //manual fill
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
            task = new Process(i, priority, arrivalTime, rand.nextInt(10), burstTime);
            
        }else{
            // automatic fill
            task = new Process(i, rand.nextInt(7)+1, rand.nextInt(100), rand.nextInt(10), rand.nextInt(10)+1);
        }
        return task;
    }
    
    static Queue<Process>[] fillQueue(int n, boolean manual){
        
        Queue<Process>[] queues = new LinkedList[7]; 
        for(int i=0; i<n; i++){ 
            Process temp = fillProcess(i, manual);
            switch(temp.getPriority()){
                case 1:
                    if(queues[0] == null){
                        queues[0] = new LinkedList<>();
                    }
                    queues[0].add(temp);
                    break;
                case 2:
                    if(queues[1] == null){
                        queues[1] = new LinkedList<>();
                    }
                    queues[1].add(temp);
                    break;
                case 3:
                    if(queues[2] == null){
                        queues[2] = new LinkedList<>();
                    }
                    queues[2].add(temp);
                    break;
                case 4:
                    if(queues[3] == null){
                        queues[3] = new LinkedList<>();
                    }
                    queues[3].add(temp);
                    break;
                case 5:
                    if(queues[4] == null){
                        queues[4] = new LinkedList<>();
                    }
                    queues[4].add(temp);
                    break;
                case 6:
                    if(queues[5] == null){
                        queues[5] = new LinkedList<>();
                    }
                    queues[5].add(temp);
                    break;
                case 7:
                    if(queues[6] == null){
                        queues[6] = new LinkedList<>();
                    }
                    queues[6].add(temp);
                    break;
            }
        }
        return queues;
    }
    
    static void printQueue(Queue<Process> queue){
        Process[] queueArray = queueToArray(queue);
        for (int i=0; i<queueArray.length; i++){
            System.out.println(i+1 + ")Process Id: " + queueArray[i].getId() + " " + "Process priority " + queueArray[i].getPriority());
        }
    }
    
    static Process[] queueToArray(Queue<Process> queue){
        
        // create array and copy queue
        int size = queue.size();
        Process[] queueArray = new Process[size];
        for (int i=0; i<size; i++){
            queueArray[i] = queue.poll();
        }
        
        // refill queue from array
        for (int i=0; i<size; i++){
            queue.add(queueArray[i]);
        }
        
        return queueArray;
    }
}