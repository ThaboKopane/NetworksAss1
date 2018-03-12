import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    //String name="";
    //FOR now call it a final
    public static final String HOST = "localhost";
    public static final int  PORT = 8888;

    private HashMap<String, ClientDetails> otherClients;

    /*Client(String host, int _port) throws Exception{

        clientSocket = new Socket(HOST, PORT);
        otherClients = new HashMap<String, ClientDetails>();
    }
    Client() throws Exception{
        clientSocket = new Socket(HOST, PORT);
    }*/

    public static void main(String[] args) throws UnknownHostException, IOException{


        BufferedReader bufR = new BufferedReader(new InputStreamReader(System.in));
        //local ip
        InetAddress ip = InetAddress.getByName(HOST);

        Socket clientSocket = new Socket(ip, PORT);

        //Input and output
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

        //New Threads
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //read the message to delive
                    try{
                        String msg = bufR.readLine();
                        output.writeUTF(msg);
                        output.flush();
                    }catch (IOException ioe){ioe.printStackTrace();}
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        String msg = input.readUTF();
                        System.out.println(msg);
                    }catch (IOException ioe){ioe.printStackTrace();}
                }
            }
        });
        sendMessage.start();
        readMessage.start();

        /*System.out.println("What is your name");
        String name = bufR.readLine();
        if(!client.otherClients.containsKey(name)){
            client.otherClients.put(name, new ClientDetails(name, PORT));
            System.out.println("User added");
        } else { System.out.println("welcomeBack");}
        System.out.println("user");
        //Iterator<String> it = client.otherClients.
        client.otherClients.put(name, new ClientDetails(name, PORT));
        System.out.println(Arrays.asList(client.otherClients.toString()));




        while(!bufR.equals("quit")){

            //System.out.println("user added");

            String reading = bufR.readLine();
            client.SendToServer(reading, 10);
            System.out.println("Server Said(1): "+client.RecieveFromServer());

        }

        client.close();*/
    }

    /*public void SendToServer(String msg, int idKey) throws Exception{
        //create output stream attached to socket
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //send msg to server
        outToServer.print(msg + '\n');
        outToServer.flush();
    }
    public boolean send(String msg, ServerSocket serverSocket, int port){
        try{
            ObjectOutputStream obj = new ObjectOutputStream(clientSocket.getOutputStream());
            obj.writeObject(msg);
            obj.flush();
        } catch (IOException ioe){
            ioe.printStackTrace();
            return false;
        }
        return true;
    }
    public String RecieveFromServer() throws Exception{
        //create input stream attached to socket
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader (clientSocket.getInputStream()));
        //read line from server
        String res = inFromServer.readLine(); // if connection closes on server end, this throws java.net.SocketException
        return res;
    }
    public void close() throws IOException{
        clientSocket.close();
    }*/


    /*public void start(){
        serverReceiver = new Thread(new ServerReceiver());

        serverReceiver.start();
        clientReceiver = new Thread(new ClientReceiver());
        clientReceiver.start();




    }*/




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



      /*class ServerReceiver implements Runnable{
          public void run(){
              while (true) {
                  try {
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
                    RecieveFromServer();

                } catch (IOException io) {
                    io.printStackTrace();
                    continue;

                } catch (Exception err){
                    System.err.println("else doesn't compile");
                }

            }
        }
      }

      class stillOnline extends TimerTask{
        @Override
          public void run(){
            //throughSocket("Stillbreathing"+" "+clientSocket.getLocalPort(), serverSocket, ipAddress, PORT);
        }
      }*/
}


                       
