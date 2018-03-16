import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class Client{


    static Vector<ClientThread> connectionsVector = new Vector<>();
    private static BufferedReader bufR =  null;
    private static ObjectOutputStream output = null;
    private static ObjectInputStream input = null;
    private static Socket clientSocket = null;
    private static boolean closed = false;


    public static final int PORT = 8888;
    public static void main(String[] args) throws Exception{
        String host = "localhost";
        Client client = new Client();

        String sentence = null;

        try{
            clientSocket = new Socket(host, PORT);
            bufR = new BufferedReader(new InputStreamReader(System.in));
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Type in something");


            if(clientSocket !=null && output !=null && input !=null){
                while(true){
                    System.out.println("writing something to server");
                    output.writeUTF(bufR.readLine().trim());
                    output.flush();

                    System.out.println("message: "+input.readUTF());
                }
            }


        } catch (UnknownHostException un){System.err.println("host unknown");}



    }

    public void run() {
        String responseLine;
        try {
            while ((responseLine = input.readUTF()) != null) {
                System.out.println(responseLine);
                if (responseLine.indexOf("quit") != -1)
                    break;
            }
            closed = true;
        } catch (IOException ioerr) {
            System.out.println("IOE: " + ioerr);
        }
    }

    public void SendToServer(String msg) throws Exception{
        //create output stream attached to socket
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        //send msg to server
        outToServer.writeUTF(msg + '\n');
        outToServer.flush();
    }
    public String RecieveFromServer() throws Exception{
        //create input stream attached to socket
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        //read line from server
        String res = inFromServer.readUTF();// if connection closes on server end, this throws java.net.SocketException
        return res;
    }
    void close() throws IOException{
        clientSocket.close();
    }
}


                       
