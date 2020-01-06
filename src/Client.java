import java.io.*;
import java.net.Socket;

public class Client {
    private static int numberOfPlayers = 0;
    int ID;
    {
        numberOfPlayers +=1;
        ID = numberOfPlayers;
    }
    private int port;
    private String ip;
    private String name;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean currentUser;

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
        //FIXME: becuz each main keeps separate number of Client instances, name of this user will always be Green,
        //FIXME: so maybe we don't need this?
        //promptClientName();
        startListeningToTheServer();

        System.out.println("Hi player, you can start chatting now!\n");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String message = "";
        while (true) {
            try {
                message = br.readLine();
                if (message.equals("stop")) {
                    disconnect();
                    break;
                }

                Message new_message = new Message(message);
                this.outputStream.writeObject(new_message);
                this.outputStream.reset();
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
        assignName(this.ID);
    }

    public void assignName(int orderOfPlayers){
        switch (orderOfPlayers){
            case 1:
                name = "Green";
                break;
            case 2:
                name = "Red";
                break;
            case 3:
                name = "Yellow";
                break;
            case 4:
                name = "Blue";
                break;
        }
    }
    public void writeToAllExcept(String name) throws IOException, ClassNotFoundException {
        this.name = name;
        while (true) {
            Message message = (Message) this.inputStream.readObject();
            if (name.equals(getName())) {
                continue;
            }
            System.out.printf("[%s] %s said \"%s\"\n",  message.getTime(),
                                                        message.getSenderName(),
                                                        message.getContent());
        }
    }
    private void startListeningToTheServer() {
        new Thread(() -> {
            try {
                while (true) {

                    // listening to the server
                    Message message = (Message) this.inputStream.readObject();
                    System.out.printf("[%s] %s said \"%s\"\n", message.getTime(),
                                                               message.getSenderName(),
                                                               message.getContent());
                }
            } catch (IOException e) {
                // server probably dissed me, fuck!
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                // shits gone wild, idk what happened
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public int getID() {
        return ID;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
