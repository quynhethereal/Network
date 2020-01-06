import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

class ClientHandler implements Runnable {
    // FIXME: NOT SURE IF IT'S SAFE TO INJECT `Server` INTO A `ClientHandler`
    private Server server;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private InetAddress inetAddress;

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        this.inetAddress = socket.getInetAddress();
    }

    public void run() {
        System.out.printf("%s just connected...\n", inetAddress.getHostAddress());
        try {
            while (true) {
                // listening to this client
                Message message = (Message) this.inputStream.readObject();
                System.out.printf("[%s] %s said \"%s\"\n", message.getTime(),
                                                           message.getSenderName(),
                                                           message.getContent());
                this.server.broadcast(message);
            }
        } catch (IOException e) {
            // client probably disconnected
            System.out.printf("%s disconnected.\n", this.inetAddress.getHostAddress());
        } catch (ClassNotFoundException e) {
            // shits gone wild, idk what happened
            System.out.println(e.getMessage());
        }
    }

    public void send(Message message) {
        try {
            this.outputStream.writeObject(message);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}