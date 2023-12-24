
package os.os;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Os {

    public static void main(String[] args) {
        
        Random rand = new Random();
        
        Queue<Process> tasks = new LinkedList<>();
        for (int i=0; i<10; i++){
            Process task = new Process(i, rand.nextInt(7)+1, rand.nextBoolean(), i, rand.nextInt(10)+1);
            tasks.add(task);
        }
        
        
        for (int i=0; i<10; i++) {
            
        }
    }
}
    
