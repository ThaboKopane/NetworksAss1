import java.io.*;
import java.net.*;

public class clientLike {
    public static void main(String[] args){
        Socket clientSocket = null;
        InputStream is = null;
        PrintStream ps = null;
        BufferedReader bufr = null;


        try{
            clientSocket = new Socket("localhost", 8888);
            ps = new PrintStream(clientSocket.getOutputStream());
            is = clientSocket.getInputStream();
            bufr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch(UnknownHostException uh){
            uh.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        if(clientSocket !=null && ps !=null && is !=null){
            try{
                String responseLine;
                ps.println(bufr.readLine());

                while((responseLine = bufr.readLine()) !=null){
                    System.out.println(responseLine);
                    if(responseLine.indexOf("Ok") != -1){
                        break;
                    }
                    ps.println(bufr.readLine());
                }


                ps.close();
                is.close();
                clientSocket.close();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
}
