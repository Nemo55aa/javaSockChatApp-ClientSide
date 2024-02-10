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
    static GreetServer srv;
    public static void main(String[] args) throws IOException   {
        String tmpString;
        srv = new GreetServer();
        MyFrame f = new MyFrame();
        f.l1.setText("waiting Connection from Client...");
        f.setVisible(true);
        tmpString = srv.start(8000);
        f.l1.setText("received: " + tmpString);

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
class GreetServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    //private acceptTh acTh;\
    String greeting;

    public String start(int port) {
        try{
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            greeting = in.readLine();
            if ("hello server".equals(greeting)) {
                System.out.println("hello client");
            }
            else {
                System.out.println("unrecognised greeting");
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return greeting;
    }

    public void stop() {
        try{
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

