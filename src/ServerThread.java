import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.StringTokenizer;
import java.util.Vector;

public class ServerThread implements Runnable{

    private static int port;
    ServerSocket serverSocket;
    public ServerThread(int port,ServerSocket serverSocket){
        this.port = port;
        this.serverSocket = serverSocket;
    }


    public void run(){
        try{
            Socket clientSocket;
            port = 8824;
            ServerSocket server = new ServerSocket(port);
            ServerThread serverThread = new ServerThread(port, server);
            while(true){
                clientSocket = server.accept();
                System.out.println(" A client is bound to this port");
            }
        } catch (IOException ioe){ioe.printStackTrace();}
    }
}
