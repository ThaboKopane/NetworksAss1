import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

class ClientThread implements Runnable{
    Socket clientSocket;
    int uniqueConnectiionID;
    BufferedReader bufR;
    private ClientDetails userDetails;
    boolean isOnline;
    final PrintWriter connectOutput;
    final BufferedReader connectInput;

    public ClientThread(Socket clientSocket, ClientDetails userD, BufferedReader input, PrintWriter output) {
        this.clientSocket = clientSocket;
        this.connectInput = input;
        this.connectOutput = output;
        this.userDetails = userD;
        this.isOnline=true;
    }
    public void run(){
        try{
            bufR = new BufferedReader(new InputStreamReader(System.in));

            String clientSentence;

            while(true){
                try{

                    clientSentence = bufR.readLine();
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

                    /*for(ClientThread client: Server.connectionsVector){
                        if(client.name.equals(recipient) && client.isOnline){
                            client.connectOutput.writeUTF(this.name+" : "+MsgToSend);
                            client.connectOutput.flush();
                            break;
                        }
                    }*/

                }catch (IOException ioe){ioe.printStackTrace();}
            }
            /*try{
                connectOutput.close();
                connectInput.close();

            }catch (IOException ioe){ioe.printStackTrace();}
        } finally {
            //System.out.println("whatever use you have");*/
        }catch (Exception ioe){ioe.printStackTrace();}
    }
}
