package factory;

import car.*;
import storage.Storage;
import threadPool.PooledTask;
import threadPool.ThreadPool;

public class Controller extends Thread {

    Storage<Body>  storageBody;

    Storage<Engine> storageEngine;

    Storage<Accessory> storageAccessory;

    Storage<Car> storageCar;

    ThreadPool pool;

    PooledTask task;

    public Controller(Storage<Car> stC, Storage<Body> stB, Storage<Engine> stE, Storage<Accessory> stA, ThreadPool pool) {
        this.pool = pool;

        this.storageCar = stC;
        this.storageAccessory = stA;
        this.storageBody = stB;
        this.storageEngine = stE;

        this.task = new PooledTask("Do cars") {
            @Override
            public void go() {
                Body body = storageBody.get();
                Engine engine = storageEngine.get();
                Accessory accessory = storageAccessory.get();

                Car car = new Car(body, engine, accessory);

                synchronized (storageCar) {
                    storageCar.add(car);
                }
            }
        };
    }

    public void run() {
        while(true) {
            synchronized (storageCar) {
                if (!storageCar.isFull()) {
                    int delta = storageCar.getCapacity() - storageCar.getSize();
                    while (0 != delta) {
                        pool.addTask(task);
                        delta--;
                    }
                } else {
                    try {
                        storageCar.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

