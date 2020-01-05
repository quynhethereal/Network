import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server  {
    private int port;
    private ObjectOutputStream serverOutputStream;

    public Server(int port){
        this.port = port;
    }

    public void send(Object data) throws IOException {
        serverOutputStream.writeObject(data);
        serverOutputStream.reset();
    }
    public void startConnection() {
        try {
            //if server
            ServerSocket server = new ServerSocket(getPort());
            //if player
            System.out.println("Server's up");
            Socket socket = server.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            serverOutputStream = outputStream;
            System.out.println(outputStream);
            Object object = new Object("if u see this server object is sent","2");

            send(object);
            while (true) {

                Object object1 = (Object) inputStream.readObject();
                System.out.println(object1.getName());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

}

