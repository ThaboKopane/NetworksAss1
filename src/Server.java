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
    private static ClientThread[] threads = new ClientThread[15];
    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;

    public static void main(String args[]) throws IOException {
        //Server serve = new  Server(PORT);
        ServerSocket serverSocket = new ServerSocket(PORT);


        /*String clientInput;
        String name ="";
        serverOutput.writeUTF("Enter your name");
        serverOutput.flush();
        name = inFromClient.readUTF().trim();*/


        while (true) {
            try{
                clientSocket = serverSocket.accept();
                System.out.println("New Client received : " + clientSocket);

                ClientThread connection = new ClientThread(clientSocket, threads);
                connectionsVector.add(connection);
                connection.run();
                break;

            } catch (Exception eror){eror.printStackTrace();}


            /*//System.out.println(" thread for client");
            serverOutput.writeUTF("creating a thread for you");
            serverOutput.flush();
            //serve.name = "Francis";

            ClientThread handleMe = new ClientThread(clientSocket, userDetails);

            Thread clientThread = new Thread(handleMe);

            serverOutput.writeUTF("Adding you to client list");
            serverOutput.flush();

            //Add to the online people
            connectionsVector.add(handleMe);

            //Start
            clientThread.start();
            clientCounter++;*/

        }
    }
}




