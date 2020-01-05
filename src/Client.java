import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client  {
    private int port;
    private String ip;
    public Client(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    public void send (ObjectOutputStream out,Object data) throws IOException {
        out.writeObject(data);
        out.reset();
    }
    public void startConnection() {
        try {
            System.out.println("Client's up");
            Socket socket = new Socket(getIp(),getPort());
            // create input, output stream
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(outputStream);
            Object object = new Object("if u see this object is sent","2");
            while (true) {
                /*outputStream.writeObject(object);
                outputStream.reset();
                outputStream.writeObject(object);*/
                send(outputStream,object);
                object.setName("If u see this object's name is changed");
                send(outputStream,object);
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

    public String getIp() {
        return ip;
    }
}
