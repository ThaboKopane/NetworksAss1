import java.net.*;
import java.io.*;
public class SimpleHTTPServer{
	public static void main(String[] args) throws Exception{
		ServerSocket server = new ServerSocket(8080);
		System.out.println("Listening for connection on port 8080 .....");
		while(true){
			Socket clientSocket = server.accept();
            InputStreamReader isr =  new InputStreamReader(clientSocket.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();            
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
		}
	}
}