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
    static ClientDetails userDetails;

    public static void main(String args[]) throws IOException {
        //Server serve = new  Server(PORT);
        ServerSocket serverSocket = new ServerSocket(PORT);
        Socket clientSocket;
        String clientInput;

        while (true) {
            clientSocket = serverSocket.accept();
            System.out.println("New Client received :v" + clientSocket);

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream());


            String name ="";
            int userID =0;

            while(userDetails !=null && (clientInput = inFromClient.readLine()) != null && !inFromClient.equals("quit")){
                serverOutput.print("What is your name");
                serverOutput.flush();
                name = inFromClient.readLine();
                serverOutput.print("your unique ID");
                serverOutput.flush();
                userID = Integer.parseInt(inFromClient.readLine());
                userDetails = new ClientDetails(name, userID);

            }


            System.out.println(" thread for client");
            //serve.name = "Francis";

            ClientThread handleMe = new ClientThread(clientSocket, userDetails, inFromClient, serverOutput);

            Thread clientThread = new Thread(handleMe);

            System.out.println("Adding to active client list");

            //Add to the online people
            connectionsVector.add(handleMe);

            //Start
            clientThread.start();
            clientCounter++;

        }
    }
}




