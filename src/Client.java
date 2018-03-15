import java.io.*;
import java.net.*;
import java.util.Vector;

public class Client implements Runnable{


    static Vector<ClientThread> connectionsVector = new Vector<>();
    private static BufferedReader bufR =  null;
    private static ObjectOutputStream output = null;
    private static ObjectInputStream input = null;
    private static Socket clientSocket = null;
    private static boolean closed = false;


    public static final int PORT = 8888;
    Socket socket = null;
    public static void main(String[] args) throws Exception{
        String host = "localhost";
        Client client = new Client();


        try{
            System.out.println("input starts here");
            clientSocket = new Socket(host, PORT);
            bufR = new BufferedReader(new InputStreamReader(System.in));
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println(input.readUTF());

        } catch (UnknownHostException un){System.err.println("host unknown");}

        if(clientSocket !=null && output !=null && input !=null){
            (new Thread(new Client())).start();
            while(!closed){
                output.writeUTF(bufR.readLine().trim());
                output.flush();
            }
            output.close();
            input.close();
            clientSocket.close();
        }

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

    void SendToServer(String msg) throws Exception{
        //create output stream attached to socket
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //send msg to server
        outToServer.print(msg + '\n');
        outToServer.flush();
    }
    String RecieveFromServer() throws Exception{
        //create input stream attached to socket
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader (socket.getInputStream()));
        //read line from server
        String res = inFromServer.readLine(); // if connection closes on server end, this throws java.net.SocketException
        return res;
    }
    void close() throws IOException{
        socket.close();
    }
}


                       
