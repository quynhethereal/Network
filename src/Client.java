public class Client extends Connection {
    private int port;
    private String ip;
    public Client(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean isServer() {
        return false;
    }

    @Override
    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
