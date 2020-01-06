import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private int port;
    ArrayList<ClientHandler> connectionPool = new ArrayList<>();
    //get input from user
    private int numberOfPlayers = 0;
    private int counterForPlayers = 0;
    public Server(int port){
        this.port = port;
    }

    public void start() {
        new Thread(() -> {
           try {

               ServerSocket serverSocket = new ServerSocket(this.port);
               System.out.println("server started");

               //TODO: GET INPUT FROM GUI
               System.out.println("Enter number of players ");
               Scanner input = new Scanner(System.in);
               numberOfPlayers = input.nextInt();

               while (counterForPlayers < numberOfPlayers) {
                   // waiting for a client to connect
                   Socket socket = serverSocket.accept();
                   // bingo! we got a connection
                   ClientHandler newConnection = new ClientHandler(this, socket);
                   // connectionPool is used in order to control
                   // the clients independently in the future
                   connectionPool.add(newConnection);
                   new Thread(newConnection).start();
                   //count for number of players connected
                   counterForPlayers++;
                   System.out.println("Number of connected players is: " + counterForPlayers);
               }
           } catch (IOException e) {
                System.out.println(e.getMessage());
           }
           //run this after server stops listening for incoming clients
            System.out.println("All players have connected!");
        }).start();

    }
    //write to everyone
    public void broadcast(Message message) {
        for (ClientHandler client:this.connectionPool) {
            client.send(message);
        }
    }

    public void writeToEveryoneExcept(Message message, ClientHandler clientHandler){
        for (ClientHandler client:this.connectionPool) {
            if (client == clientHandler)
                continue;
            else {
                client.send(message);
            }
        }
    }
    public boolean isServer() {
        return true;
    }

    public int getPort() {
        return port;
    }


}
