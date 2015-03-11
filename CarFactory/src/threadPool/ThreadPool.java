package threadPool;


import java.util.LinkedList;
import java.util.Vector;

public class ThreadPool {

    LinkedList<PooledTask> workQueue;

    Vector<WorkedThread> workedThreads;

    ThreadGroup threads;

    public ThreadPool(int countOfWorkers) {
        workQueue = new LinkedList<PooledTask>();
        workedThreads = new Vector<WorkedThread>(countOfWorkers);
        threads = new ThreadGroup("Thread Pool");

        for (int i = 0; i < countOfWorkers; i++) {
            WorkedThread worker = new WorkedThread(threads, workQueue);
            workedThreads.add(worker);

            System.out.println("Thread \"" + i + "\" was created into Thread Pool");
        }

    }

    public void addTask(PooledTask task) {
        synchronized (workQueue) {
            workQueue.addLast(task);
            workQueue.notify();
        }
    }

    synchronized public int getCountOfTask() {
        return workQueue.size();
    }

    public void start() {
        for (int i = 0; i < workedThreads.size(); i++) {
            workedThreads.elementAt(i).start();
        }

    }

    public void finalize() {
        threads.destroy();
        try {
            super.finalize();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}

