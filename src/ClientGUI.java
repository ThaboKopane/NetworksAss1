import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.io.*;

import javax.swing.*;
import java.awt.*;

public class ClientGUI extends JFrame implements ActionListener{

    Server starting;


    JLabel serverPort;
    JTextField serverPortInput;
    JButton startServer;
    JTextField cnTxt;
    JLabel clientPort;
    JButton clientStart;
    JTextField cnPort;
    JLabel clientNum;
    JTextArea response;
    public ClientGUI(){
        this.setTitle("Hello friend");
        this.setSize(320, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane();

        serverPort = new JLabel("Server port: ");
        serverPort.setBounds(10,10,90,21);
        add(serverPort);

        serverPortInput = new JTextField();
        serverPortInput.setBounds(105, 10, 90, 21);
        add(serverPortInput);

        startServer = new JButton("serverStart");
        startServer.setBounds(200, 10, 90, 21);
        startServer.addActionListener(this);
        add(startServer);

        JLabel clientName = new JLabel("your name");
        clientName.setBounds(10, 35, 90, 21);
        add(clientName);

        cnTxt = new JTextField();
        cnTxt.setBounds(105, 35, 90, 21);
        add(cnTxt);

        clientPort = new JLabel("portToC :");
        clientPort.setBounds(10, 60, 90, 21);
        add(clientPort);

        clientStart = new JButton("clientStart");
        clientStart.setBounds(200, 60, 90, 21);
        startServer.addActionListener(this);
        add(clientStart);

        cnPort = new JTextField();
        cnPort.setBounds(105, 60, 90, 21);
        add(cnPort);


        /*clientNum = new JLabel("clientNum :");
        clientNum.setBounds(105, 85, 90, 21);
        add(clientNum);*/

        response = new JTextArea();
        response.setBounds(10, 85, 290, 120);
        add(response);



        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource().equals(clientStart)){
            try {
                Client client = new Client();
                client.start(Integer.parseInt(cnPort.getText()));
            }catch (Exception ioe){ioe.printStackTrace();}
        }
    }
    public static void main(String[] args){
        new ClientGUI();
    }
}