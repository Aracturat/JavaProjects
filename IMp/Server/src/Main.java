public class Main {

    public static void main(String[] args) {
        System.out.println("I'm Server!");

        Server server = new Server();
        View view = new View(server);
    }

}
                