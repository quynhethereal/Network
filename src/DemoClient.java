public class DemoClient {

    public static void main(String[]  args)  {
        final int PORT_NUMBER = 8888;
        final String HOST = "localhost";

        Client client = new Client(HOST, PORT_NUMBER);
        client.start();


    }
}
