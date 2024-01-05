
package os2;


import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class OS2 {

    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();
    public static int clock=0;
    
    public static Queue<Process>[] queues;
    public static Process[] blockedList = new Process[0];

    public static void main(String[] args) {

        System.out.println("Please write the number of the processes: ");
        int n = scanner.nextInt();
        System.out.println("Please give the type of tasks creation (automatic(false), manual(true): ");
        boolean manual = scanner.nextBoolean();    
        
        queues = QueueClass.fillQueue(n, manual);
        QueueClass.printQueueArray(queues);
    }
    
    // It simulates a process running on the cpu
    static int execute(int clockIn, Process task){
        task.setState(1);
        int timeOut = task.getRemain();
        
        for(int cl=1; cl<=timeOut; cl++){
            if(task.getBurst_time() > 0){
                // if the process is not complete
                if(rand.nextInt(10)+1 <= 2){
                    // simulate I/O of process
                    task.setBurst_time(task.getBurst_time()-1);
                    task.setRemain(task.getRemain()-1);
                    task.setState(2);
                    blockedList = Process.addToArray(blockedList, task);
                    return clockIn + cl;
                }else{
                    // simulate the normal run of process (no I/O)
                    task.setBurst_time(task.getBurst_time()-1);
                    task.setRemain(task.getRemain()-1);
                }
            }else{
                // if the process is complete
                return clockIn + cl;
            }
        }
        
        // timeout of process
        task.setRemain(10); // reset remain time of process
        task.setState(0);
        queues[task.getPriority()].add(task);
        return clockIn + 10;
    }
}