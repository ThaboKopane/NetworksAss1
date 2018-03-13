import java.net.InetAddress;

public class ClientDetails {
    private String name;
    private int idkey;
    private int port;
    private InetAddress ipAddress;

    public ClientDetails(String name, int idKey){
        this.name = name;
        this.idkey=idkey;
        //ipAddress = ip;
    }

    public String getName(){
        return name;
    }
    public int getPort(){
        return port;
    }
    public InetAddress getIP(){
        return ipAddress;
    }
    public int getIdkey(){
        return idkey;
    }
}
