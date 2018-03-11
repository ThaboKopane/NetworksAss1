import java.io.*;

public class Message implements Serializable{ //Serializable lets you send objects over streams, google it.
    
    private static final long serialVersionUID = 1212121212121212L; //1-Thabo, 2-Nathan, 3-Alex... and some zeroes.
    
    //define message types here: MESSAGE, WHO, EXIT (or LOGOUT, I guess), and any other things we want.
    
    static final int WHO = 0; //who's in the chatroom
    static final int MESSAGE = 1; //message
    static final int EXIT = 2; //disconnect
    
    //static final int STUFF...
    //static final int PRIVATE_MSG = 3 ???
    //put multimedia stuff here somewhere
    
    private int type;
    private String message;
    
    Message(int type, String message){
        this.type = type;
        this.message = message;
        }
        
    //getters, etc.
    int getType(){
        return type;
        }
        
    String getMessage(){
        return message;
        }
 
}               
        
        
     