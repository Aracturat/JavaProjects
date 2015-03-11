import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        System.out.println("I'm Client!");

        Client client = new Client();
        View view = new View(client.getController());
        view.setVisible(true);
    }
}
