public class DemoClient {

    public static void main(String[] args) throws Exception {
        final int PORT_NUMBER = 8888;
        final String HOST = "157.230.37.0";

        Client client = new Client(HOST, PORT_NUMBER);
        client.start();
    }
}
