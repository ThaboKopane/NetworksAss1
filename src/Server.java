import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    public static final int PORT = 8888;

    HashMap<Integer, ClientDetails> onlineClients;


    Server() throws Exception{
        //create welcoming socket at port 6789
        ServerSocket welcomeSocket = new ServerSocket(PORT);
        onlineClients = new HashMap<Integer, ClientDetails>();

        while (true) {
            //block on welcoming socket for contact by a client
            Socket clientSocket = welcomeSocket.accept();
            // create thread for client
            Connection c = new Connection(clientSocket);
        }
    }
    public static void main (String args[]) throws Exception{
        new Server();
    }
    class Connection extends Thread{
        Socket clientSocket;
        Connection(Socket _clientSocket){
            clientSocket = _clientSocket;
            this.start();
        }
        public void run(){
            try{
                //create input stream attached to socket
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
                //create output stream attached to socket
                //Out to new client.
                PrintWriter outToClient = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                //read in line from the socket
                String clientSentence;
                while ((clientSentence = inFromClient.readLine()) != null || !inFromClient.equals("quit")) {
                    System.out.println("Client sent: "+clientSentence);
                    //process
                    String last = clientSentence.substring(0,clientSentence.length()-1)+"2";
                    String capitalizedSentence = last.toUpperCase() +'\n';
                    //write out line to socket
                    outToClient.print(capitalizedSentence);
                    outToClient.flush();
                }
            }catch(Exception e){}
        }
    }
}
