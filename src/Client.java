import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    //String name="";
    //FOR now call it a final
    public static final String HOST = "localhost";
    public static final int  PORT = 8888;

    static HashMap<Integer, Client> otherClients;
    static ClientDetails userDetails;
    static Socket clientSocket = null;

    Client(ClientDetails userDetails){
        this.userDetails = userDetails;

    }

    /*public void SendToServer(String msg) throws Exception{
        //create output stream attached to socket
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        //send msg to server
        outToServer.print(msg + '\n');
        outToServer.flush();
    }*/


    public static void main(String[] args) throws UnknownHostException, IOException{

        BufferedReader bufR = new BufferedReader(new InputStreamReader(System.in));

        //local ip
        InetAddress ip = InetAddress.getByName(HOST);

        Socket clientSocket = new Socket(ip, PORT);
        int idKey=0;
        if(args.length==0){
            System.out.println("Enter your idKey");
            idKey = Integer.parseInt(bufR.readLine());
        }
        String name;
        do{
            System.out.println("enter your name");
            name = bufR.readLine();
            try{
                userDetails = new ClientDetails(name, idKey);
                Client client = new Client(userDetails);
                if(userDetails!=null || userDetails.getIdkey() != 0)
                    break;
            }catch (Exception ioe){ioe.printStackTrace();}
        }while(true);

        while(clientSocket.isBound()){
            System.out.println("Enter you message");
            String message = bufR.readLine();

        }



        //Input and output
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());



        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //read the message to delive
                    System.out.println("Okay, try sending a message");
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
                System.out.println("Incoming");
                while(true){
                    try{
                        String msg = input.readUTF();
                        System.out.println(msg);
                    }catch (IOException ioe){ioe.printStackTrace();}
                }
            }
        });
        //if()
        sendMessage.start();
        readMessage.start();


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


                       
