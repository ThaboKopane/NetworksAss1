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
    boolean isOnline;
    private ObjectOutputStream connectOutput;
    private ObjectInputStream connectInput;
    private int maxClient;
    private String name;
    private ClientDetails userDetails;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.isOnline=true;
        this.name = name;
        this.userDetails = userDetails;
    }

    public void run(){
        //total of clients connecyed at a time
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
                connectOutput.flush();
                name = connectInput.readUTF().trim();
                //It's breaking to go to the next loop where we askth e user conversational questions
                //The reason we don't want at in your name, is to be able direct a message to one person
                if (name.indexOf('@') == -1) {
                    break;
                } else
                    connectOutput.writeUTF("your name should not include '@");

            }
            connectOutput.writeUTF("Welcome "+name+" to the chat");
            connectOutput.flush();

            while(true) {

                try{
                    clientSentence = connectInput.readUTF();
                    System.out.println("got this from server "+clientSentence);
                    connectOutput.writeUTF(clientSentence+ " coming back from the server");
                    connectOutput.flush();
                    System.out.println("waiting for the nextLine...");


                }catch (IOException ioe){ioe.printStackTrace();}
            }


        }catch (Exception ioe){ioe.printStackTrace();}
    }
}