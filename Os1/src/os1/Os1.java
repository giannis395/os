package os1;

/**
 *
 * @author SHAKA
 */
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Os1 {
    
    //public boolean higherPriority(Process task2);

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please write the number of the processes: ");
        int n= scanner.nextInt();
        int arrivalTime, burstTime, priority;
        Random rand = new Random();
        PriorityQueue<Process> tasks1 = new PriorityQueue<>();
        for (int i=0; i<n; i++){
            do {
                System.out.println("Please the arrival time of " + i + " process: ");
                arrivalTime = scanner.nextInt();
            } while (arrivalTime < 0 );
            do {
                System.out.println("Please the burst time, which must be above the zero, of " + i + " process: ");
                burstTime = scanner.nextInt(); 
            } while (burstTime <= 0);
            do {
                System.out.println("Please the priority, which must me between 1 and 7, of " + i + " process: ");
                priority = scanner.nextInt(); 
            } while (priority < 0 || priority >7);
            Process task = new Process(i, priority, arrivalTime, rand.nextBoolean(), i, burstTime);
            tasks1.add(task);
        }
        while (!tasks1.isEmpty()) {
            Process process = tasks1.poll();
            System.out.println("Process Id: " + process.getId() + " " + "Process priority " + process.getPriority());
        }
        tasks1.clear();
        System.out.println("End of manual ");
        for (int i=0; i<n; i++){
            Process task = new Process(i, rand.nextInt(7)+1, rand.nextInt(100), rand.nextBoolean(), i, rand.nextInt(10)+1);
            tasks1.add(task);
        }
        
        Queue<Process> tasks = new LinkedList<>();
        for (int i=0; i<n; i++){
            Process task = new Process(i, rand.nextInt(7)+1, rand.nextInt(100), rand.nextBoolean(), i, rand.nextInt(10)+1);
            tasks.add(task);
        }
        
        while (!tasks1.isEmpty()) {
            Process process = tasks1.poll();
            System.out.println("Process Id: " + process.getId() + " " + "Process priority " + process.getPriority());
        }
        //for (int i=0; i<10; i++) ;
    }
}
