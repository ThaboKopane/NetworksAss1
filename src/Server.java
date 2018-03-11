
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;



public class Server {

    //The TCP connection
    private ServerSocket serverSocket;

    private static Socket clientSocket;

    //Hashmap - to store clients
    private HashMap<String, ClientDetails> onlineUsers;

    private BufferedReader bufR;
    private OutputStream out;
    DataInputStream inputStream;
    //private InputStream inputStream;
    private int portNumber;
    String host;

    //This the constructor
    public Server() {

        onlineUsers = new HashMap<String, ClientDetails>();

        bufR = new BufferedReader(new InputStreamReader(System.in));
    }

    //Starting a thread.
    public void start() {
        System.out.println("The server is running, type 'exit'");
        String command = "";
        portNumber = 8888;
        try {
            serverSocket = new ServerSocket(portNumber);

        } catch (IOException socErr) {
            System.err.println("Server not working");
        }
        try {
            clientSocket = serverSocket.accept();
            command = bufR.readLine();
            inputStream = new DataInputStream(clientSocket.getInputStream());
            out = new PrintStream(clientSocket.getOutputStream());

            while (true) {
                host = inputStream.readUTF();
                int data = inputStream.read();
                System.out.println(data+" "+host);
                if (command.equals("exit")) {
                    System.out.println("exit");
                    bufR.close();
                    clientSocket.close();
                    break;

                }
            }
        } catch (IOException ioe) {
            System.err.println("error");

            close();
        }
    }

    //Adding clients to map
    public boolean updateUsers(String userName, InetAddress clientIP, int userPort, int servePort){
        if(onlineUsers.containsKey(userName)){
            ClientDetails ud = onlineUsers.get(userName);
            if(userPort==ud.getPort()){
                return true;
            } else{ //make new name or use nickname
                return false;
            }
        } else{
            onlineUsers.put(userName, new ClientDetails(userName, clientIP, userPort));
            System.out.println("Added new user"+userName);

            return true;
        }
    }
    public void sendList(InetAddress clientIP, int clientPort){
        StringBuilder sb = new StringBuilder();
        Iterator<ClientDetails> it=onlineUsers.values().iterator();
        sb.append("list").append("/n");

        while(it.hasNext())
            sb.append(it.next().toString()).append("/n");
        String reply = sb.toString();

        try{
            bufR = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = clientSocket.getOutputStream();

            out.write(reply.getBytes());
            out.write(clientPort);
            //out.write(clientIP); //for the address
            //out.flush();



        } catch (IOException ioerr) {
            System.err.println("IOEerrror");

            ioerr.printStackTrace();
        }

    }
    public void close(){
        try{
            out.close();
            inputStream.close();
            bufR.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    public static void main(String[] args){
        Server serve = new Server();
        serve.start();


    }
}