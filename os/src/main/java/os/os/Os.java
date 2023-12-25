
package os.os;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Os {

    public static void main(String[] args) {
        
        int n=10;
        
        Queue<Process> tasks = priorityQueue(n);
        
        while (!tasks.isEmpty()) {
            System.out.println(tasks.poll());
        }
    }
    
    public static Queue<Process> priorityQueue(int n){
        
        Random rand = new Random();
        Process task[] = new Process[n];
        for (int i=0; i<n; i++){
            task[i] = new Process(i, rand.nextInt(7)+1, rand.nextBoolean(), i, rand.nextInt(10)+1);
        }
        
        Queue<Process> tasks = new LinkedList<>();
        Process temp = new Process();
        for (int i=0; i<n-1; i++){
            for (int j=0; j<n-i-1; i++){
                if(task[j].getPriority() > task[j+1].getPriority()){
                    temp = task[j];
                    task[j] = task[j+1];
                    task[j+1] = temp;
                }
            }
        }
        for(int i=0; i<n; i++){
            System.out.println(task[i].toString());
        }
        return tasks;
    }
}
    
