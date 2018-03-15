import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

class ClientThread implements Runnable{
    Socket clientSocket;
    int uniqueConnectiionID;
    static Vector<ClientThread> connectionsVector = new Vector<>();
    BufferedReader bufR;
    /*
    For one of the examples I saw online CLientThread[] was keeping a max on the number of clients that could be
    online at a time.
     */
    private final ClientThread[] threads;
    boolean isOnline;
    private ObjectOutputStream connectOutput;
    private ObjectInputStream connectInput;
    private int maxClient;
    String clientName;

    public ClientThread(Socket clientSocket, ClientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        this.isOnline=true;
        this.maxClient = threads.length;
    }
    public void run(){
        //total of clients connecyed at a time
        int maxClientsCount = this.maxClient;
        ClientThread[] threads = this.threads;
        try {

            //When the server says clientThread start, it comes here.
            connectInput = new ObjectInputStream(clientSocket.getInputStream());
            connectOutput = new ObjectOutputStream(clientSocket.getOutputStream());

            String clientSentence;
            String name;


            //Theoretically, it is supposed to ask the client to input their name
            //That is not happening yet
            while (true) {
                connectOutput.writeUTF("Enter your name");
                name = connectInput.readUTF().trim();
                //It's breaking to go to the next loop where we askth e user conversational questions
                //The reason we don't want at in your name, is to be able direct a message to one person
                if (name.indexOf('@') == -1) {
                    break;
                } else
                    connectOutput.writeUTF("your name should not include '@");

            }
            connectOutput.writeUTF("Welcome "+name+" to the chat");

            /*synchronized(this){
            for (int n = 0; n < maxClientsCount; n++) {
                if (threads[n] != null && threads[n] == this) {
                    clientName = "@" + name;
                    break;
                }
            }
            for (int n = 0; n < maxClientsCount; n++) {
                if (threads[n] != null && threads[n] != this) {
                    threads[n].connectOutput.writeUTF("A client is online " + name);
                }
            }
        }*/
            while(true) {

                try{

                    clientSentence = connectInput.readUTF();
                    if(clientSentence.equals("exit")){
                        this.isOnline = false;
                        break;
                    }


                }catch (IOException ioe){ioe.printStackTrace();}
            }


            connectInput.close();
            connectOutput.close();
            clientSocket.close();
        }catch (Exception ioe){ioe.printStackTrace();}
    }
}