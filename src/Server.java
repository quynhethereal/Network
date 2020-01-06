import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server extends Connection {
    private int port;
    ArrayList<ClientHandler> connectionPool = new ArrayList<>();

    public Server(int port){
        this.port = port;
    }

    public void start() {
        new Thread(() -> {
           try {
               ServerSocket serverSocket = new ServerSocket(this.port);
               System.out.println("server started");

               while (true) {
                   // waiting for a client to connect
                   Socket socket = serverSocket.accept();
                   // bingo! we got a connection

                   ClientHandler newConnection = new ClientHandler(this, socket);
                   // connectionPool is used in order to control
                   // the clients independently in the future
                   connectionPool.add(newConnection);
                   new Thread(newConnection).start();
               }
           } catch (IOException e) {
                System.out.println(e.getMessage());
           }
        }).start();
    }

    public void broadcast(Message message) {
        for (ClientHandler client:this.connectionPool) {
            client.send(message);
        }
    }

    public boolean isServer() {
        return true;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        // FIXME: this is stupid
        return null;
    }

}
