package threadPool;

abstract public class PooledTask {

    String name;

    public PooledTask(String name) {
        this.name = name;
    }

    abstract public void go();

    public void finish() {}

    public void prepare() {}

    public void interrupted() {}

    public String getName() {
        return name;
    }

}
