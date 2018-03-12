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

            Connection handleMe = new Connection(clientSocket, "Thabo " + clientCounter, serverInput, serverOutput);

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



    class Connection implements Runnable{
        Socket clientSocket;
        int uniqueConnectiionID;
        final DataOutputStream connectOutput;
        final DataInputStream connectInput;
        private String name;
        boolean isOnline;
        public Connection(Socket clientSocket, String name, DataInputStream input, DataOutputStream output) {
            this.clientSocket = clientSocket;
            this.connectInput = input;
            this.connectOutput = output;
            this.name = name;
            this.isOnline=true;
        }
        public void run(){
            try{

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
                        clientSentence = connectInput.readUTF();
                        System.out.println("clientSentence "+clientSentence);

                        if(clientSentence.equals("exit")){
                            this.isOnline = false;
                            this.clientSocket.close();
                            break;
                        }

                        //Messages and recepient
                        String MsgToSend ="";
                        String recipient ="";
                        StringTokenizer token = new StringTokenizer(clientSentence, ":");
                        while (token.hasMoreTokens()){
                            MsgToSend = token.nextToken();
                            recipient = token.nextToken();
                        }

                        for(Connection client: Server.connectionsVector){
                            if(client.name.equals(recipient) && client.isOnline){
                                client.connectOutput.writeUTF(this.name+" : "+MsgToSend);
                                client.connectOutput.flush();
                                break;
                            }
                        }

                    }catch (IOException ioe){ioe.printStackTrace();}
                }
                try{
                    connectOutput.close();
                    connectInput.close();

                }catch (IOException ioe){ioe.printStackTrace();}
            } finally {
                //System.out.println("whatever use you have");
            }
        }
    }
