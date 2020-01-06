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
        //create instances of client, bcuz if create in different mains, the number of instances will be reset
        Client client = new Client ("localhost",server.getPort());
        client.assignName(client.getID());
        System.out.printf("%s just connected...\n", inetAddress.getHostAddress());
        System.out.println("who? "+ client.getName());

        try {
            while (true) {
                // listening to this client
                Message message = (Message) this.inputStream.readObject();
                message.setSenderName(client.getName());
                System.out.printf("[%s] %s said \"%s\"\n", message.getTime(),
                                                           message.getSenderName(),
                                                           message.getContent());

                //write to all running threads except this one
                this.server.writeToEveryoneExcept(message,this);
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
            this.outputStream.reset();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}