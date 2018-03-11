import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.HashMap;
import java.util.TimerTask;
//This is mine
public class Client implements Runnable{

    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    public final static int PORT = 8888;

    private InetAddress ipAddress;
    private ObjectInputStream streamIn;
    private ObjectOutputStream streamOut;

    public static boolean closed = false;

    private String username;
    private String serverName;
    Queue<Object> messageQueue;
    HashMap<String, ClientDetails> otherUsers;


    //GUI stuff


    BufferedReader bufR;
    PrintStream out;
    InputStream is;



    public Client(){
        //this.serverName = sname;
        //this.username = uname;

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



        public void run(){
        String responseLine;

        try{
            while((responseLine = bufR.readLine()) !=null){
                System.out.println(responseLine);
                if(responseLine.indexOf("Bye") != -1)
                    break;
            }
            closed = true;
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        }

        public void start(){
        String host = "localHost";

        try{
            clientSocket = new Socket(host, PORT);
            bufR = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintStream(clientSocket.getOutputStream());
            is = new BufferedInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException uh){
            uh.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        if(clientSocket !=null && out !=null && is !=null){
            try{
                new Thread(new Client()).start();
                while(!closed){
                    out.println(bufR.readLine().trim());
                }
                out.close();
                is.close();
                clientSocket.close();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        }

        public static void main(String[] args){
            Client user = new Client();
            user.start();
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
      }*/

}
                       
