import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.StringTokenizer;
import java.util.Vector;

public class Server {
    public static final int PORT = 8888;


    //Vector for active clients
    static Vector<Connection> connectionsVector = new Vector<>();
    public static int clientCounter = 0;
    private BufferedReader bufR;
    private Client client;
    private ClientDetails userDetails;
    static HashMap<Integer, Client> otherClients;
    public static void main(String args[]) throws IOException {
        Server serve = new  Server();
        serve.bufR = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket;

        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("New Client received : " + clientSocket);

            //The following is wrong, doesn't do as intended
            System.out.println("Enter your name");
            String name = serve.bufR.readLine();
            System.out.println("Enter your unique identifier");
            int idKey = serve.bufR.read();


            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println(" thread for client");
            //serve.name = "Francis";

            Connection handleMe = new Connection(clientSocket, serverInput, serverOutput);

            Thread clientThread = new Thread(handleMe);

            System.out.println("Adding to active client list");

            //Add to the online people
            connectionsVector.add(handleMe);
            for (Connection con : connectionsVector){
                System.out.println(con);
            }

            //Start
            clientThread.start();
        }
    }
}




