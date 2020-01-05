public class Server extends Connection {
    private int port;

    public Server(int port){
        this.port = port;
    }

    @Override
    public boolean isServer() {
        return true;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getIp() {
        return null;
    }

}

