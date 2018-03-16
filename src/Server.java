import java.net.*;
import java.io.*;
//import java.util.Hashmap;
//import java.util.Observable;
//import java.util.StringTokenizer;
//import java.util.Vector;
import java.util.*;

public class Server{
    public static int PORT = 8888; //default
    
    public static int OTHERPORT; //for me
    String FILE;
    /*
    static Vector<ClientThread> connectionsVector = new Vector<>();
    public static int clientCounter = 0;
    private static ClientThread[] threads = new ClientThread[15];
    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    */
    
    static boolean isStarted = false;
    ServerSocket serverSocket;
    int clientCount = 1;
    List<Client> clientList = new ArrayList<Client>(); //figured this is just simpler, but feel free
    
    public static void main(String[] args){
        PORT = 8888; //functionality to change it here instead of just == 8888 (default)
        new Server().serverGo();
        }//end main
    
    
        
    public void serverGo(){
        try{
            serverSocket = new ServerSocket(PORT);
            isStarted = true;
            }catch(Exception e){
                System.out.println("Error with the port, I guess.");
                System.exit(0);
                }
        
        try{
            while(isStarted){
                Socket sock = serverSocket.accept();
                Client client = new Client(sock);
                new Thread(client).start();
                clientList.add(client);
                clientCount++;
                }
                }catch(Exception er){er.printStackTrace();}
                
            finally{
                try{serverSocket.close();}catch(Exception someError){someError.printStackTrace();}
                
                        
        }//end serverGo
    
    //set/getters  + misc (might not use all, necessarily)
	/*
    public static void setStart(){ isStarted = true;}    
    
    public boolean getStart(){ return isStarted;}
    
    public void printClientList(){
        for (Client c : clientList)
            System.out.println(c);
            }
    public List<Client> getClientList(){return clientList;}
            
    public int getPort(){return PORT;}
    
    public static void setPort(int p){ PORT = p;}           
    */
    }//end Server class


    class Client implements Runnable{
        private boolean isConnected; //= false;
        private Socket socket;
        
        private ObjectInputStream input;
        private ObjectOutputStream output;
        
        private int num = clientCount;
        String data;
        
            public Client(Socket socket){
                this.socket = socket;}
                
            public void sendMsg(String msg){
                try{
                    output.writeObject(msg);
                    output.flush();
                    }catch(Exception e){e.printStackTrace();//clientList.remove(this); } 
                    }    
                    
                }//end sendMsg
                
                
                
            public void sendFile(String path){ //no idea if this'll work. Going by examples/my ass
                try{
                    File file = new File(path);
                    FileInputStream fileIS = new FileInputStream(file);
                    BufferedInputStream bufIS = new BufferedInputStream(fileIS);
                    OutputStream outS = socket.getOutputStream();
                    
                    
                    byte[] stuff;
                    long fileSize = file.length();
                    String fileClient = Long.toString(fileSize);
                    sendMsg(fileClient); //send fileSize back to client            Thurs 830
                    long curr = 0;
                    
                    while(curr != fileSize){
                        int size = 10000;
                        if((fileSize - curr) >= size){curr = fileSize;}
                        else{
                            size = (int)(fileSize - curr);
                            curr = fileSize;}
                            
                        stuff = new byte[size];
                        bufIS.read(stuff, 0, size);
                        outS.write(stuff); 
                    }//end curr/fileSize while
                       outS.flush();
                  }catch(Exception e){e.printStackTrace(); //clientList.remove(this);
                      }
              }//end sendFile
              public void run(){
              
                  try{
                     input = new ObjectInputStream(socket.getInputStream());
                     output = new ObjectOutputStream(socket.getOutputStream());
                     output.flush();
                     
                     isConnected = true;
                     
                     while(isConnected){
                        data = (String)input.readObject();
                        String[] tempArray = data.split("\\s+");
                        String whoGets = tempArray[0];
                        String type = tempArray[1];
                        
                        if (type.equals("f")){ //FILE METHOD
                           FILE = tempArray[2].substring(1, tempArray.length - 1);
                        }
                        
                        String trueMessage = findContent(data); //findContent method to do later
                        if (whoGets.equals("bc")){ //BROADCOST METHOD
                           if (type.equals("message")){
                              for (int i = 0; i < clientList.size(); i++){
                                 if (i == num-1){
                                    continue;
                                    } 
                                    Client cl = clientList.get(i);
                                    cl.sendMsg("@client" + num + ":" + trueMessage);
                                   }
                                  }
                              if (type.equals("f")){
                                 for (int i = 0; i<clientList.size();i++){
                                    if (i==num-1){
                                       continue;
                                       }
                                    Client cl = clientList.get(i);
                                    cl.sendMsg("file: ");   
                                    cl.sendMsg(Integer.toString(num)); 
                                    cl.sendMsg(extractFileName(tempArray[2]));
                                    cl.sendFile(FILE); //send the file, no idea if this'll work but whatever
                                    }
                                    }
                                    
                                    System.out.println("Client " + num + " " + whoGets + " " + type);
                                    
                                    }else if(type.equals("pm")){ //else if for the type.equals bc if; pm = private message PRIVATE MESSAGE
                                       int target = 0;
                                       if (type.equals("message")){
                                          String client = tempArray[tempArray.length-1];
                                          target = getTarget(client);
                                          for(int i = 0; i<clientList.size();i++){
                                             if (i==target){
                                                Client c = clientList.get(i);
                                                c.sendMsg("@client" + num + ":" + trueMessage);
                                                
                                             
                                            }
                                         }
                                      }
                                      
                                      if (type.equals("f")){
                                          String client = tempArray[tempArray.length-1];
                                          target = getTarget(client);
                                          for (int i = 0; i<clientList.size(); i++){
                                             if (target == i){
                                                Client c = clientList.get(i);
                                                c.sendMsg("file: ");
                                                c.sendMsg(Integer.toString(num));
                                                c.sendMsg(extractFileName(tempArray[2]));
                                                c.sendFile(FILE);
                                                }
                                         }
                                         
                                         
                                     }
                                     
                                     int rcv_Client = target + 1;
                                     System.out.println("Client" + num + " " + whoGets + " " + type + "to client" +rcv_Client + "]");
                                     
                                     
                                    }else if(whoGets.equals("mc")){    //MULTICAST FUNC, kinda: send all, except someone.
                                    
                                       int target = 0;
                                       if (type.equals("message")){
                                          String client = tempArray[tempArray.length - 1];
                                          target = getTarget(client);
                                          for (int i = 0; i<clientList.size(); i++){
                                             if (target == i || i == num-1){
                                                continue;
                                             }
                                             Client c = clientList.get(i);
                                             c.sendMsg("@client" + num + ":" + trueMessage);
                                          }
                                          
                                        }
                                        
                                        if (type.equals("f")){
                                          String client = tempArray[tempArray.length - 1];
                                          target = getTarget(client);
                                          for(int i=0; i<clientList.size(); i++){
                                             if (target == i || i == num-1){
                                                continue;
                                                }
                                             Client c = clientList.get(i);
                                             c.sendMsg("file: ");
                                             c.sendMsg(Integer.toString(num));
                                             c.sendMsg(extractFileName(tempArray[2]));
                                             c.sendFile(FILE);
                                           }
                                           
                                        }
                                        
                                       int notClient = target+1;
                                       System.out.println("client"+ num +" " + whoGets + " " + type +" excluding [client" + notClient + "]");
                                     } else{   //else for mc if
                                         System.out.println("error wtf alex");
                                     }
                                  }//close the biggest fucking while loop
                           }catch(Exception e){
                              clientList.remove(this);
                              clientCount--;
                              System.out.println("Client " + this.num + " was disconnected");
                
                              }finally{
                                 try{
                                    if(output!= null){
                                       output.close();
                                       }
                                    }catch(Exception e){e.printStackTrace();}
                                    
                                    if (input!=null){
                                       try{
                                          input.close();
                                          }catch(Exception e){e.printStackTrace();}
                                          }
                                    if (socket!=null){
                                       try{
                                          socket.close();
                                          socket = null;
                                          }catch(Exception e){e.printStackTrace();}
                                       }
                                       
                                       } 
                                       
                                     }//end run
                           
              
                  //misc methods
                  
                  
                  
                  
                  public String findContent(String data){
                     int tracker = 0;
                     while(data.charAt(tracker) != '\"'){
                        tracker++;
                        }
                     int start = tracker++;
                     
                     while(data.charAt(tracker)!= '\"'){
                        tracker++;
                        }
                     int end = tracker++;
                     String trueMessage = data.substring(start+1, end);
                     return trueMessage;
                     }
                     
                     
                     }//client class
                     
                 public String extractFileName(String filename){
                     String[] tempB = filename.split("/");
                     
                     String tempStr = tempB[tempB.length - 1];
                     if (tempStr.charAt(0) == '"'){
                        return tempStr.substring(1,tempStr.length() - 1);
                     }else{
                        return tempStr.substring(0, tempStr.length() - 1);
                       }
                       
                   }
                   
                   
                   
                 public int getTarget(String client){
                        return (client.charAt(client.length() - 1) - '0') - 1;
                     }
                     
               
               }//end Server class
               
            
