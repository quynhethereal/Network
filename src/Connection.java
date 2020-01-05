
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Connection {
    private ClientHandlingThread thread = new ClientHandlingThread();

    public void startConnection(){
        thread.start();
    }

    public void send(Object data) throws Exception {
        System.out.println(thread.outputStream);

    }
    public void closeConnection() throws Exception {
        thread.socket.close();
    }

    public abstract boolean isServer();
    public abstract int getPort();
    public abstract String getIp();

    private class ClientHandlingThread extends Thread{

        private ObjectOutputStream outputStream;
        private Socket socket;

        public void run() {
            try {
                //if server
                ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                //if player
                Socket socket = isServer() ? server.accept() : new Socket(getIp(),getPort());
                // create input, output stream
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                this.outputStream = outputStream;
                System.out.println(outputStream);
                this.socket = socket;

                    while (true) {
                        Object object1 = (Object) inputStream.readObject();
                        System.out.println(object1.getName());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public ObjectOutputStream getOutputStream() {
            return outputStream;
        }
    }
}
