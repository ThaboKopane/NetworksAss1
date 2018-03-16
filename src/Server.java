import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static final int PORT = 8888;






    //Vector for active clients
    static Vector<ClientThread> connectionsVector = new Vector<>();
    public static int clientCounter = 0;
    private static ClientDetails userDetails;
    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    private static ObjectOutputStream output;
    private static ArrayList<ObjectOutputStream> clientArray;

    public static void main(String args[]) throws IOException {
        //Server serve = new  Server(PORT);
        ServerSocket serverSocket = new ServerSocket(PORT);


        /*String clientInput;
        String name ="";
        serverOutput.writeUTF("Enter your name");*/
        while (true) {
            try{
                clientSocket = serverSocket.accept();
                clientArray = new ArrayList<>();



                System.out.println("New Client received : " + clientSocket);

                ClientThread connection = new ClientThread(clientSocket);

                //This is how a thread should be initiated
                Thread handleMe = new Thread(connection);
                handleMe.start();


                output = new ObjectOutputStream(clientSocket.getOutputStream());
                clientArray.add(output);
                break;

            } catch (Exception eror){eror.printStackTrace();}

            System.out.println(connectionsVector.iterator());

        }
    }
}




