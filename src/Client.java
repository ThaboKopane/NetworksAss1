import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.TimerTask;

public class Client {
    //String name="";
    //FOR now call it a final
    public static final String HOST = "localhost";
    public static final int  PORT = 8888;
    Socket clientSocket = null;
    ServerSocket serverSocket = null;
    HashMap<Integer, ClientDetails> otherCLients;

    Thread serverReceiver, clientReceiver;
    public static void main(String[] args) throws Exception{
        Client client = new Client(HOST, PORT);

        BufferedReader bufR = new BufferedReader(new InputStreamReader(System.in));
        while(!bufR.equals("quit")){
            String reading = bufR.readLine();
            client.SendToServer(reading, 10);
            System.out.println("Server Said(1): "+client.RecieveFromServer());

        }

        client.close();
    }

    Client(String _host, int _port) throws Exception{

        clientSocket = new Socket(HOST, PORT);
    }
    Client() throws Exception{
        clientSocket = new Socket(HOST, PORT);
    }

    public void SendToServer(String msg, int idKey) throws Exception{
        //create output stream attached to socket
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //send msg to server
        outToServer.print(msg + '\n');
        outToServer.flush();
    }
    /*public void sendMessageToClient(ClientDetails user, String mes){
        //Output stream attached to socket
        try{
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());


        } catch (IOException ioe){
            ioe.printStackTrace();
        }

    }*/
    /*public void connectToClient(int idkey){
        ClientDetails ud = otherCLients.get(idkey);
        if(ud==null)
            return;

    }*/
    public String RecieveFromServer() throws Exception{
        //create input stream attached to socket
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
        //read line from server
        String res = inFromServer.readLine(); // if connection closes on server end, this throws java.net.SocketException
        return res;
    }
    public void close() throws IOException{
        clientSocket.close();
    }




      /*class ListenFromServer extends Thread{
          public void run(){
              while(true){
                  try{
                      String mess = (String) streamIn.readObject();
                      //gui here
                      }catch(Exception e){
                          System.out.println("Error: " + e);
                          }

                 }
            }
        }*/

      class ServerReceiver implements Runnable {
          public void run() {
              while (true) {
                  try {
                     /* clientSocket = serverSocket.accept();
                      InputStream in = clientSocket.getInputStream();
                      int data = in.read();*/

                      BufferedReader inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
                      //read line from server
                      String res = inFromServer.readLine(); // if connection closes on server end, this throws java.net.SocketException
                  } catch (IOException io) {
                      io.printStackTrace();
                      continue;

                  }

              }
          }
      }

      class ClientReceiver implements Runnable{
        public void run(){
            while (true) {
                try {
                    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //read frim the other client
                    String reading = inFromClient.readLine();
                } catch (IOException io) {
                    io.printStackTrace();
                    continue;

                }

            }
        }
      }

      class stillOnline extends TimerTask{
        @Override
          public void run(){
            //throughSocket("Stillbreathing"+" "+clientSocket.getLocalPort(), serverSocket, ipAddress, PORT);
        }
      }
}


                       
