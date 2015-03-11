package factory;

import car.*;
import storage.Storage;
import suppliers.*;
import threadPool.ThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Factory{

    private ThreadPool workerPool;


    private Storage<Body>  storageBody;

    private Storage<Engine> storageEngine;

    private Storage<Accessory> storageAccessory;

    private Storage<Car> storageCar;


    private SupplierGroup suppliersAccessory;

    private SupplierGroup suppliersBody;

    private SupplierGroup suppliersEngine;

    private SupplierGroup dealers;



    private Controller controller;

    Properties properties;


    public Factory(String path) {

        getProperties(path);
        if (properties.isEmpty()) {
            return;
        }

        workerPool = new ThreadPool(Integer.parseInt(properties.getProperty("Workers")));

        storageBody = new Storage<Body>(Integer.parseInt(properties.getProperty("StorageBodySize")));
        storageEngine = new Storage<Engine>(Integer.parseInt(properties.getProperty("StorageEngineSize")));
        storageAccessory = new Storage<Accessory>(Integer.parseInt(properties.getProperty("StorageAccessorySize")));

        storageCar = new Storage<Car>(Integer.parseInt(properties.getProperty("StorageCarSize")));

        suppliersBody = new SupplierGroup("Suppliers Body");
        suppliersAccessory = new SupplierGroup("Suppliers Accessory");
        suppliersEngine = new SupplierGroup("Suppliers Engine");

        dealers = new SupplierGroup("Dealers");

        controller = new Controller(storageCar, storageBody, storageEngine, storageAccessory, workerPool);

    }

    public void start() {
        controller.start();
        workerPool.start();

        getThread();
    }


    public Storage getStorageByName(String name) {
        if (name.equalsIgnoreCase("Car")) {
            return storageCar;
        } else if (name.equalsIgnoreCase("Body")) {
            return storageBody;
        } else if (name.equalsIgnoreCase("Engine")) {
            return storageEngine;
        } else if (name.equalsIgnoreCase("Accessory")) {
            return storageAccessory;
        }

        return null;
    }

    public SupplierGroup getSuppliersByName(String name) {
        if (name.equalsIgnoreCase("Car")) {
            return dealers;
        } else if (name.equalsIgnoreCase("Body")) {
            return suppliersBody;
        } else if (name.equalsIgnoreCase("Engine")) {
            return suppliersEngine;
        } else if (name.equalsIgnoreCase("Accessory")) {
            return suppliersAccessory;
        }

        return null;
    }



    private void getProperties(String path) {
        properties = new Properties();
        try {
            //Open string as input stream.
            InputStream stream = ClassLoader.getSystemResourceAsStream(path);

            properties.load(stream);

        }
        catch (IOException e) {
            System.out.println("Cannot load " + path);
        }
    }

    private void getThread() {
        int accessorySuppliers = Integer.parseInt(properties.getProperty("AccessorySuppliers"));
        int engineSuppliers = Integer.parseInt(properties.getProperty("EngineSuppliers"));
        int bodySuppliers = Integer.parseInt(properties.getProperty("BodySuppliers"));
        int dealer = Integer.parseInt(properties.getProperty("Dealers"));

        for(int i = 0; i < bodySuppliers; i++) {
            Supplier s = new SupplierBody(storageBody, 0, suppliersBody);
            s.start();
            suppliersBody.add(s);
        }

        for(int i = 0; i < accessorySuppliers; i++) {
            Supplier s = new SupplierAccessory(storageAccessory, 0, suppliersAccessory);
            s.start();
            suppliersAccessory.add(s);
        }

        for(int i = 0; i < engineSuppliers; i++) {
            Supplier s =  new SupplierEngine(storageEngine, 0, suppliersEngine);
            s.start();
            suppliersEngine.add(s);
        }

        for(int i = 0; i < dealer; i++) {
            Dealer d = new Dealer(controller, storageCar, 0, dealers);
            d.start();
            dealers.add(d);
        }
    }

    public void finalize() {
        workerPool.finalize();

        suppliersAccessory.destroy();
        suppliersBody.destroy();
        suppliersEngine.destroy();
        dealers.destroy();

    }
}

