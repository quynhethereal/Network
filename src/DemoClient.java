public class DemoClient {

    public static void main(String[] args) throws Exception {
        final int PORT_NUMBER = 5555;
        final String HOST = "localhost";

        Client client = new Client(HOST,PORT_NUMBER);
        client.startConnection();
        System.out.println("client's up");


        //demo client
        Object object = new Object("1","2");
        client.send(object);
    }
}
