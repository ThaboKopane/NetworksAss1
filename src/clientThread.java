import java.io.*;
import java.net.*;

public class clientThread extends Thread{
    private String clientN = null;
    private BufferedReader dts = null;
    private PrintStream ps = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientCount;

    public clientThread(Socket clientSocket, clientThread[] threads){
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientCount = threads.length;
    }

    public void run() {
        clientThread[] threads = this.threads;

        try{
            //input and output
            dts = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ps = new PrintStream(clientSocket.getOutputStream());
            String name;

            while(true){
                ps.println("Enter your name love");
                name = dts.readLine().trim();
                if(name.indexOf('@') ==-1)
                    break;
                else
                    ps.println("your name should lack");

                ps.println("Welcome "+name+", \nTo leave enter /quit in a newline");
            }

            while(true) {
                String line = dts.readLine();
                if (line.startsWith("/quit"))
                    break;
                if (line.startsWith("@")){
                    String[] words = line.split("\\s", 2);
                    if(words.length>1 && words[1] != null){
                        words[1]=words[1].trim();
                        if(words[1].isEmpty()){
                            synchronized (this){
                                for(int i=0; i<5; i++){
                                    if(threads[i] != null && threads[i] !=this && threads[i].clientN !=null
                                            && threads[i].clientN.equals(words[0])){
                                        threads[i].ps.println(">"+name+">"+words[i]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else{
                    synchronized (this){
                        for(int i=0; i<5; i++){
                            if(threads[i] != null && threads[i].clientN != null){
                                threads[i].ps.println("<"+name+">"+line);
                            }
                        }
                    }
                }
            }

            synchronized (this){
                for(int i=0; i<5; i++){
                    if(threads[i]==this){
                        threads[i]=null;
                    }
                }
            }
            dts.close();
            ps.close();
            clientSocket.close();


        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
