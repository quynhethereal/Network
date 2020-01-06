public class demo {
    public static void main(String[] args)  {
        final int PORT_NUMBER = 8888;
        final String HOST = "localhost";

        Client client1 = new Client(HOST, PORT_NUMBER);
        client1.start();




    }
}
