import java.io.*;
import java.net.Socket;

class Connection implements Runnable{
    Socket clientSocket;
    int uniqueConnectiionID;
    private Client client;
    private static ClientDetails userDetails;
    static PrintStream connectOutput = null;
    static ObjectInputStream connectInput = null;
    //String name;
    boolean isOnline;
    /*public Connection(Socket clientSocket, ObjectInputStream input, PrintStream output) {
        this.clientSocket = clientSocket;
        this.connectInput = input;
        this.connectOutput = output;
        this.isOnline=true;
    }*/
    public void run(){
        BufferedReader bufR = new BufferedReader(new InputStreamReader(System.in));
        try{
            System.out.println("eish");
            //Socket clientSocket = new Socket(ip, PORT);
            int idKey=0;
            String name;
            do{
                try{
                System.out.println("Enter your idKey");
                idKey = Integer.parseInt(bufR.readLine());
                System.out.println("enter your name");
                name = bufR.readLine();
                    userDetails = new ClientDetails(name, idKey);
                    Client client = new Client(userDetails);
                    if(userDetails!=null || userDetails.getIdkey() != 0)
                        break;
                }catch (IOException ioe){ioe.printStackTrace();}
            }while(true);

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