package tests;

import storage.Storage;

import java.util.LinkedList;

public class StorageTest {

    static int count = 0;

    class TestClass {

        String name;

        public TestClass(String name) {
            this.name = name + " " + count++;
        }

        public String getName() {
            return this.name;
        }
    }

    LinkedList<Thread> allThread;

    Thread add;
    Thread get;

    Thread add1;
    Thread get1;

    Storage<TestClass> store;

    public void start() {
        allThread = new LinkedList<Thread>();
        store = new Storage<TestClass>(1);

        add = new Thread("0 Add") {
            @Override
            public void run() {
                while (true) {
                    System.out.println("0 Add element" + count);
                    store.add(new TestClass("TestElement"));

                    try {
                        sleep(10);
                    }  catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        add1 = new Thread("1 Add") {
            @Override
            public void run() {
                while (true) {
                    System.out.println("1 Add element" + count);
                    store.add(new TestClass("TestElement"));

                    try {
                        sleep(1000);
                    }  catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        get = new Thread("0 Get") {
            @Override
            public void run() {
                while (true) {
                    System.out.println("0 Get element " + store.get().getName());
                    System.out.println();
                    try {
                        sleep(1000);
                    }  catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        get1 = new Thread("1 Get") {
            @Override
            public void run() {
                while (true) {
                    System.out.println("1 Get element " + store.get().getName());
                    System.out.println();
                    try {
                        sleep(1000);
                    }  catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        add.start();
        get.start();
        //add1.start();
        get1.start();
    }

}
