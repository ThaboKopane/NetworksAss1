import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

class ClientThread implements Runnable{
    Socket clientSocket;
    int uniqueConnectiionID;
    BufferedReader bufR;
    private String name;
    boolean isOnline;
    final PrintStream connectOutput = null;
    final ObjectInputStream connectInput = null;
    /*public ClientThread(Socket clientSocket, String name, DataInputStream input, DataOutputStream output) {
        this.clientSocket = clientSocket;
        this.connectInput = input;
        this.connectOutput = output;
        this.name = name;
        this.isOnline=true;
    }*/
    public void run(){
        try{
            bufR = new BufferedReader(new InputStreamReader(System.in));

            String clientSentence;

            while(true){
                try{
                    clientSentence = connectInput.readUTF();
                    System.out.println("clientSentence "+clientSentence);
                    System.out.println("WHAT IS YOUR name");
                    name = bufR.readLine();
                    System.out.println("your unique identifier");
                    uniqueConnectiionID = Integer.parseInt(bufR.readLine());

                    ClientDetails userD = new ClientDetails(name, uniqueConnectiionID);

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
