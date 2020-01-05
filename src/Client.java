import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client  {
    private ObjectOutputStream clientOutputStream;
    private int port;
    private String ip;
    public Client(String ip,int port){
        this.ip = ip;
        this.port = port;
    }



    public void send(Object data) throws IOException {
        clientOutputStream.writeObject(data);
        clientOutputStream.reset();
    }
    public void startConnection() {
        try {
            System.out.println("Client's up");
            Socket socket = new Socket(getIp(),getPort());
            // create input, output stream
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            clientOutputStream = outputStream;

            System.out.println(outputStream);
            Object object = new Object("if u see this object is sent","2");
            send(object);
            while (true) {
                object.setName("If u see this object's name is changed");
                send(object);

                Object object1 = (Object) inputStream.readObject();
                System.out.println(object1.getName());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getClientOutputStream() {
        return clientOutputStream;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
