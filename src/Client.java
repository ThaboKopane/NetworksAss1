import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.HashMap;
import java.util.TimerTask;
//This is mine
public class Client {

    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    public final static int PORT = 8888;

    private InetAddress ipAddress;
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    private Timer timer;
    private Thread serverReceiver, clientReceiver;

    private String username;
    private String serverName;
    Queue<Object> messageQueue;
    HashMap<String, ClientDetails> otherUsers;


    //GUI stuff


    BufferedReader bufR;
    OutputStream out;
    InputStream is;



    public Client(String sname, String uname){
        this.serverName = sname;
        this.username = uname;

        try{
            serverSocket = new ServerSocket();
            clientSocket = new Socket();
        } catch (IOException ioerr){
            System.err.println("hello");
            ioerr.printStackTrace();
        }

        try{
            ipAddress = InetAddress.getByName(serverName);
        } catch (UnknownHostException e){
            System.err.println("UnknownServer");
            System.exit(-1);
        }

        bufR = new BufferedReader(new InputStreamReader(System.in));
        messageQueue = new LinkedList<Object>();
        }

    public boolean throughSocket(String message, Socket socket, InetAddress address, int port){
        try{
            port = PORT;
            socket = new Socket(serverName, port);
            out = socket.getOutputStream();
            out.write(message.getBytes());
            out.flush();
            out.close();
        } catch (IOException err){
            System.err.println("errror");
            err.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean throughSocket(String message, ServerSocket serverSocket, InetAddress serverAdd, int porting){
        porting = PORT;
        try{
            clientSocket = serverSocket.accept();
            out = clientSocket.getOutputStream();
            out.write(message.getBytes());
            out.flush();
            out.close();

        }catch (IOException ioer){
            ioer.printStackTrace();
            return false;
        }
        return true;
    }
    public void start(){
        serverReceiver = new Thread(new ServerReceiver());
        serverReceiver.start();
        clientReceiver = new Thread(new ClientReceiver());
        clientReceiver.start();


        throughSocket("hey "+clientSocket.getLocalPort(), serverSocket, ipAddress, PORT);
        try{
            clientSocket = new Socket(serverName, PORT);
        }catch(Exception e){
            System.err.println("Error: " + e);
            }

        System.out.println("Connection made: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());




        //data streams
        try{

            streamIn = new ObjectInputStream(clientSocket.getInputStream());
            streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
            }catch (IOException e){
                System.out.println("Error: " + e);
                }

       //data streams



       new ListenFromServer().start();
       try{
            streamOut.writeObject(username);
            }catch(Exception e){
                System.out.println("Error: " + e);
                }
      }

       //close everything method
       private void disconnect(){
           try{
               if (streamIn != null)
                   streamIn.close();
               }catch(Exception e){}

           try{
               if (streamOut != null)
                   streamOut.close();
               }catch(Exception e){}
           try{
               if (clientSocket != null)
                   clientSocket.close();
               }catch (Exception e){}

           }//discon

    private void connectToClient(String name){
        ClientDetails ud = otherUsers.get(name);
        if(ud==null)
            return;

        throughSocket("request", clientSocket, ud.getIP(), ud.getPort());
        System.out.println("trying to connect to "+name);
    }



    public void send(Message message){ //need to make a Message class; seems more consistent to send Message objects instead of strings. (to me)

               throughSocket(message.getMessage(), clientSocket, ipAddress, PORT);
               System.out.println("message sent");
               }



     //main
     public static void main(String[] args){
         int port = 8888;
         String server = "localhost";
         String clientName = "Anonymous";

         switch(args.length){    //assuming input in form of: java Client [username] [port number] [server name]

             case 0:
                 break;
             case 1:
                 clientName = args[0];
             case 2:
                 try{
                     port = Integer.parseInt(args[1]);
                 }catch(Exception e){
                     System.out.println("Error: Invalid port number: " + e);
                     return;
                 }

             case 3:
                 server = args[2];

                 //can add case for GUI too, I guess: ...[server name] [gui]
             default:
                 System.out.println("Using default: Anonymous, port 8888, on server Networks Assignment Host");
                 return;
         }//close switch

          while(true){
              try{
                  clientSocket = new Socket(server,PORT);

                  //could check if userMessage == LOGOUT, EXIT, WHO to handle commands?
                  Client hank = new Client("Hank", "hankyPanky");


                  //Message m = new Message(Message.MESSAGE, userMessage);
                  Message m = new Message(Message.MESSAGE, "hello");
                  hank.throughSocket(m.getMessage(), clientSocket, InetAddress.getByName(server), port);
              } catch (IOException eerr){
                  System.out.println("helloError");
                  eerr.printStackTrace();
              }




              }

          //client.disconnect();
      }

      class ListenFromServer extends Thread{
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
        }

      class ServerReceiver implements Runnable {
          public void run() {
              while (true) {
                  try {
                      clientSocket = serverSocket.accept();
                      InputStream in = clientSocket.getInputStream();
                      int data = in.read();
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
                    clientSocket = serverSocket.accept();
                    InputStream in = clientSocket.getInputStream();
                    int data = in.read();
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
            throughSocket("Stillbreathing"+" "+clientSocket.getLocalPort(), serverSocket, ipAddress, PORT);
        }
      }

}
                       
