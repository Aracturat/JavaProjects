package threadPool;

import java.util.LinkedList;

public class WorkedThread extends Thread {

    private LinkedList<PooledTask> queue;

    private static int count = 0;

    private String name;

    public WorkedThread(ThreadGroup group, LinkedList<PooledTask> queue) {
        super(group, new String("Thread " + count++));

        this.queue = queue;
        name = new String("Thread " + count);
    }

    public void run() {
        PooledTask task = null;
        while (true) {
            synchronized (queue) {
                if ( 0 == queue.size() ) {
                    try {
                        queue.wait();
                    } catch ( InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                //    System.out.println("Thread \"" + name + "\" get task");
                    task = queue.pop();
                }
            }
            if (null != task) {
                task.prepare();
                task.go();
                task.finish();
            }
            task = null;
        }
    }


}
