import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    //String name="";
    //FOR now call it a final
    public static final String HOST = "localhost";
    public static final int  PORT = 8888;
    private static BufferedReader bufR = null;
    private static PrintStream output = null;
    private static ObjectInputStream input = null;

    static HashMap<Integer, Client> otherClients;
    static ClientDetails userDetails;
    static Socket clientSocket = null;
    private static boolean closed = false;

    Client(ClientDetails userDetails){
        this.userDetails = userDetails;

    }



    public static void main(String[] args) throws UnknownHostException, IOException{

        bufR = new BufferedReader(new InputStreamReader(System.in));

        //local ip
        InetAddress ip = InetAddress.getByName(HOST);

        //Socket clientSocket = new Socket(ip, PORT);
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

        try{
            clientSocket = new Socket(ip, PORT);
            bufR = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe){ioe.printStackTrace();}

        if(clientSocket != null && input !=null && output!=null){
            try{
                new Thread(new Connection(clientSocket, input, output)).start();
                while(!closed){
                    output.println(bufR.readLine());
                }

                output.close();
                input.close();
                clientSocket.close();
            } catch (IOException ioe){ioe.printStackTrace();}
        }





    }

    public void run(){
        String waitngForTheServer;
        try{
            while((waitngForTheServer = input.readUTF()) != null){
                System.out.println(waitngForTheServer);
                if(waitngForTheServer.equals("exit"))
                    break;
            }
            closed = true;
        } catch (IOException ioe){ioe.printStackTrace();}
    }
}


                       
