
package os;

/**
 *
 * @author SHAKA
 */
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Os {
    
    public static Scanner scanner = new Scanner(System.in);
    public static Random rand = new Random();

    public static void main(String[] args) {
        
        System.out.println("Please write the number of the processes: ");
        int n = scanner.nextInt();
        
        PriorityQueue<Process> tasksQueue = new PriorityQueue<>();
        
        // manual fill of n processes
        tasksQueue = fillQueue(n, true);
        printOnlyPriorityOfQueue(tasksQueue);
        
        tasksQueue.clear();
        System.out.println("End of manual ");
        
        // automatic fill of n processes
        tasksQueue = fillQueue(n, false);
        printOnlyPriorityOfQueue(tasksQueue);
    }
    
    
    static PriorityQueue<Process> fillQueue(int n, boolean manual){ // true(manual), false(automatic)
        
        PriorityQueue<Process> tempQueue = new PriorityQueue<>();
        
        if(manual){
            //manual fill
            for (int i=0; i<n; i++){
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
                Process task = new Process(i, priority, arrivalTime, rand.nextBoolean(), i, burstTime);
                tempQueue.add(task);
            }
            
        }else{
            // automatic fill
            for (int i=0; i<n; i++){
                Process task = new Process(i, rand.nextInt(7)+1, rand.nextInt(100), rand.nextBoolean(), i, rand.nextInt(10)+1);
                tempQueue.add(task);
            }
        }
        return tempQueue;
    }
    
    static void printOnlyPriorityOfQueue(PriorityQueue<Process> queue){
        Process[] queueArray = queueToArray(queue);
        for (int i=0; i<queueArray.length; i++){
            System.out.println(i+1 + ")Process Id: " + queueArray[i].getId() + " " + "Process priority " + queueArray[i].getPriority());
        }
    }
    
    static Process[] queueToArray(PriorityQueue<Process> queue){
        
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

