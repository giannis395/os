
package os.os;

public class Process {
    
    private int id;
    private int state = 0; // ready(0) running(1) blocked(2)
    private int priority;
    private int pc = 0;
    private boolean io;
    private int memory_point;
    private int burst_time;
    
    public Process(int id, int priority, boolean io, int memory_point, int burst_time) {
        this.id = id;
        this.priority = priority;
        this.io = io;
        this.memory_point = memory_point;
        this.burst_time = burst_time;
    }

    public String toString(){return "Process{" + "id=" + id + ", state=" + state + ", priority=" + priority + ", pc=" + pc + ", io=" + io + ", memory_point=" + memory_point + ", burst_time=" + burst_time + '}';}

    public int getId(){return id;}
    public int getState(){return state;}
    public int getPriority(){return priority;}
    public int getPc(){return pc;}
    public boolean isIo(){return io;}
    public int getMemory_point(){return memory_point;}
    public int getBurst_time(){return burst_time;}

    public void setId(int id){this.id = id;}
    public void setState(int state){this.state = state;}
    public void setPriority(int priority){this.priority = priority;}
    public void setPc(int pc){this.pc = pc;}
    public void setIo(boolean io){this.io = io;}
    public void setMemory_point(int memory_point){this.memory_point = memory_point;}
    public void setBurst_time(int burst_time){this.burst_time = burst_time;}
    
    public boolean higherPriority(Process task2){
        
    }
}
