import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Connection {
    private int port;
    private String ip;
    private String name;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Client(String ip,int port) {
        this.ip = ip;
        this.port = port;
    }

    private void establishConnectionToServer() throws IOException {
        this.socket = new Socket(ip, port);
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void start() {
        try {
            establishConnectionToServer();
        } catch (IOException e) {
            // you probably forgot to turn on the server, doofus!!!
            System.out.println("unable to connect to the server!");
            System.exit(69);
        }

        promptClientName();
        startListeningToTheServer();

        System.out.printf("Hi %s, you can start chatting now!\n", this.name);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String message = "";
        while (true) {
            try {
                message = br.readLine();
                if (message.equals("stop")) {
                    disconnect();
                    break;
                }
                Message new_message = new Message(this.name, message);
                this.outputStream.writeObject(new_message);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void disconnect() {
        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void promptClientName() {
        Scanner input = new Scanner(System.in);
        System.out.print("Your name: ");
        this.name = input.nextLine();
    }

    private void startListeningToTheServer() {
        new Thread(() -> {
            try {
                while (true) {
                    // listening to the server
                    Message message = (Message) this.inputStream.readObject();
                    if (message.getSenderName().equals(this.name)) {
                        // skip this message if this is the client sending it
                        // FIXME: not a very nice solution, tbh. Try to not letting the server to broadcast my messages to me instead!
                        continue;
                    }
                    System.out.printf("[%s] %s said \"%s\"\n", message.getTime(),
                                                               message.getSenderName(),
                                                               message.getContent());
                }
            } catch (IOException e) {
                // server probably dissed me, fuck!
                System.out.printf(e.getMessage());
            } catch (ClassNotFoundException e) {
                // shits gone wild, idk what happened
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public boolean isServer() {
        return false;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
