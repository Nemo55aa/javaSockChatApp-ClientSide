// Kinda GUI thing (swingLib) imports here
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// for Socket com. importing here
import java.net.*;
import java.io.*;

// ownLib
import myThread.helloTh;
import myThread.worldTh;

public class App {
    GreetClient cli;
     void main(String[] args) throws IOException   {
        cli = new GreetClient();
        
        MyFrame f = new MyFrame();
        f.l1.setText("Connecting to server...");
        f.setVisible(true);
        cli.startConnection("127.0.0.1", 8000);

        String response = cli.sendMessage("hello server");
        helloTh heTh = new helloTh();
        worldTh wldTh = new worldTh();
        
        heTh.start();
        wldTh.start();

        System.out.println("Hello, World!");
    }
}

// ================== swing ================== 
class MyFrame extends JFrame implements ActionListener{
    JButton b1;
    JLabel l1;
    public MyFrame() {
        Container contentPane = getContentPane();
        b1 = new JButton("Button1");
        l1 = new JLabel("label");

        setSize(300, 200);
        setTitle("My Window Application");
        
        b1.addActionListener(this);
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        contentPane.add(b1);
        contentPane.add(l1);
        addWindowListener(new MyWindowAdapter());
    }
    
    public void actionPerformed(ActionEvent ae){
    if (ae.getSource() == b1) 
        System.out.println("b1ActionEvent called");
        l1.setText("Button Pressed");
    }
}
/**
 * MyWindowAdapter extend
 */
class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}

// ================== socket ================== 
class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try{
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            String resp = in.readLine();
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


