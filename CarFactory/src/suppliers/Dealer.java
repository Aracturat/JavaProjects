package suppliers;

import factory.Controller;
import storage.Storage;
import car.Car;

public class Dealer extends Supplier<Car> {

    private static Integer allNumberOfCar = 0;

    Controller controller;

    public Dealer(Controller controller, Storage storage, int delay, ThreadGroup group) {
        super(storage, delay, group);
        this.controller = controller;
    }

    public void run() {
        while(true) {
            try {
                sleep(delay);
                Car car = storage.get();
                synchronized (allNumberOfCar) {
                    allNumberOfCar++;
                    if (300 == allNumberOfCar) {
                        allNumberOfCar = 0;
                        System.gc();
                    }
                }
                System.out.println(car.getAllInfo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
