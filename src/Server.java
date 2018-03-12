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
    static int clientCounter =0;
    private Connection connect;
    static ServerSocket serverSocket;
    static Socket clientSocket;
    private ObjectOutputStream serverOutput;
    private ObjectInputStream serverInput;

    public Server(int porting) throws Exception{
        //create welcoming socket at port 6789
        porting = PORT;
        serverSocket = new ServerSocket(PORT);
        clientSocket = serverSocket.accept();
        this.serverOutput = serverOutput;
        this.serverInput = serverInput;
        //onlineClients = new HashMap<Integer, ClientDetails>();

        /*while (true) {
            //block on welcoming socket for contact by a client
            clientSocket = serverSocket.accept();
            // create thread for client
            connect= new  Connection(clientSocket);

        }*/
    }
    public static void main (String args[]) throws Exception{
        Server serve = new  Server(PORT);
        Socket clientSocket;
        clientSocket = serverSocket.accept();

        while(true) {
            System.out.println("New Client received :v" + clientSocket);

            ObjectInputStream serverInput = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println(" thread for client");

            Connection handleMe = new Connection(clientSocket, "Thabo "+clientCounter, serverInput, serverOutput);

            Thread clientThread = new Thread(handleMe);

            System.out.println("Adding to active client list");

            //Add to the online people
            connectionsVector.add(handleMe);

            //Start
            clientThread.start();
            clientCounter++;
        }
    }


    class Connection implements Runnable{
        Socket clientSocket;
        int uniqueConnectiionID;
        private ObjectOutputStream output;
        private ObjectInputStream input;
        private String name;
        volatile boolean isOnline;
        public Connection(Socket clientSocket, String name, ObjectInputStream input, ObjectOutputStream output){
            this.clientSocket = clientSocket;
            this.input = input;
            this.output = output;
            this.name = name;
            this.isOnline=true;
        }
        public void run(){
            try{
                //create input stream attached to socket
                /*BufferedReader inFromClient = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
                //create output stream attached to socket
                //Out to new client.
                PrintWriter outToClient = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));*/

                //read in line from the socket
                String clientSentence;
                /*while ((clientSentence = inFromClient.readLine()) != null || !inFromClient.equals("quit")) {
                    System.out.println("Client sent: "+clientSentence);
                    //process
                    String last = clientSentence.substring(0,clientSentence.length()-1)+"2";
                    String capitalizedSentence = last.toUpperCase() +'\n';
                    //write out line to socket
                    outToClient.print(capitalizedSentence);
                    outToClient.flush();
                }*/
                while(true){
                    try{
                        clientSentence = input.readUTF();
                        System.out.println("clientSentence "+clientSentence);

                        if(clientSentence.equals("exit")){
                            this.isOnline = false;
                            this.clientSocket.close();
                            break;
                        }

                        //Messages and recepient
                        StringTokenizer token = new StringTokenizer(clientSentence, ":");
                        String MsgToSend = token.nextToken();
                        String recipient = token.nextToken();

                        for(Connection client: connectionsVector){
                            if(client.name.equals(recipient) && client.isOnline){
                                client.output.writeObject(this.name+" : "+MsgToSend);
                                client.output.flush();
                                break;
                            }
                        }

                    }catch (IOException ioe){ioe.printStackTrace();}
                }
                try{
                    output.close();
                    input.close();

                }catch (IOException ioe){ioe.printStackTrace();}
            } finally {
                //System.out.println("whatever use you have");
            }
        }
    }
}
