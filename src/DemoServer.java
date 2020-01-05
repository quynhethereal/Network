public class DemoServer {
    public static void main(String[] args) {
        final int PORT_NUMBER = 5555;
        final String HOST = "localhost";

        Server server = new Server(PORT_NUMBER);
        server.startConnection();



    }
}
