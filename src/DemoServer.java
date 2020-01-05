public class DemoServer {
    public static void main(String[] args) throws Exception {
        final int PORT_NUMBER = 5555;
        final String HOST = "localhost";

        Server server = new Server(PORT_NUMBER);
        server.startConnection();
        System.out.println("server's up");

        //demo


    }
}
