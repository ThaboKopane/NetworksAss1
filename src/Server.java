import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
    public static final int PORT = 8888;


    //Vector for active clients
    static Vector<ClientThread> connectionsVector = new Vector<>();
    public static int clientCounter = 0;

    public static void main(String args[]) throws IOException {
        //Server serve = new  Server(PORT);
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket;

        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("New Client received :v" + clientSocket);

            DataInputStream serverInput = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream serverOutput = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println(" thread for client");
            //serve.name = "Francis";

            ClientThread handleMe = new ClientThread();

            Thread clientThread = new Thread(new ClientThread());

            System.out.println("Adding to active client list");

            //Add to the online people
            connectionsVector.add(handleMe);

            System.out.println(connectionsVector.iterator());

            //Start
            clientThread.start();
            clientCounter++;

        }
    }
}




