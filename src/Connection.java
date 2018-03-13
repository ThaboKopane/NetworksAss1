import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Connection implements Runnable{
    Socket clientSocket;
    int uniqueConnectiionID;
    private Client client;
    final ObjectOutputStream connectOutput;
    final ObjectInputStream connectInput;
    //String name;
    boolean isOnline;
    public Connection(Socket clientSocket, ObjectInputStream input, ObjectOutputStream output) {
        this.clientSocket = clientSocket;
        this.connectInput = input;
        this.connectOutput = output;
        this.isOnline=true;
    }
    public void run(){
        try{

            System.out.println("eish");

            String clientSentence;
            while(true){
                try {

                    clientSentence = connectInput.readUTF();
                    if (!clientSentence.isEmpty())
                        System.out.println("client sentence " + clientSentence);
                    else
                        System.out.println("not working well");


                    if (clientSentence.equals("exit")) {
                        this.isOnline = false;
                        System.out.println("Sad to see you go");
                        this.clientSocket.close();
                        break;
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