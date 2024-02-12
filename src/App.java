// Kinda GUI thing (swingLib) imports here
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// for Socket com. importing here
import java.net.*;
import java.io.*;

// timer
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

// ownLib
import sockThread.*;

public class App {
    public static void main(String[] args) throws IOException   {
        MyFrame f = new MyFrame();

        f.l1.setText("connecting to server...");
        f.setVisible(true);
        while (f.client.isConnected() != true) {
            //f.client.connect("127.0.0.1", 8000);
            f.client.connect("192.168.0.101", 8000);
        }

        while(true){
            // Checking/Displaying Connection between me and Client.
            if(f.client.isConnected()){
                f.l1.setText("connected!");
            }else{
                f.l1.setText("not connected...");
            }

            if(f.client.lineStrBuf != null){
                f.readbutton.doClick();
            }
        }
    }
}

// ================== swing ================== 
class MyFrame extends JFrame implements ActionListener{
    JButton readbutton, sendButton;
    JLabel l1;
    JTextField tf1;
    JTextArea ta1;
    int width, height;
    
    //srvSockTh srv;
    clntSockTh client;

    public MyFrame() {
        // ==================== STYLING WINDOW ====================
        {
        Container contentPane = getContentPane();
        
        JPanel buttonsJPanel = new JPanel();
        readbutton = new JButton("read Line");
        sendButton = new JButton("send Line");
        buttonsJPanel.setLayout(new GridLayout(2,1));
        buttonsJPanel.add(readbutton);
        buttonsJPanel.add(sendButton);

        JPanel textJPanel = new JPanel();
        l1 = new JLabel("label");
        ta1 = new JTextArea(100, 100);
        ta1.setEditable(false);
        tf1 = new JTextField("input Text here!");
        textJPanel.setLayout(new BorderLayout());
        textJPanel.add("North", l1);
        textJPanel.add("Center", ta1);
        textJPanel.add("South", tf1);

        width = 640; height = 480;
        setSize(width, height);
        setTitle("ClientSide App");
        
        contentPane.setLayout(new BorderLayout());
        contentPane.add("West", buttonsJPanel);
        contentPane.add("Center",textJPanel);

        readbutton.addActionListener(this);
        sendButton.addActionListener(this);
        addWindowListener(new MyWindowAdapter());
        }

        // ==================== STARTING SERVER ====================
        client = new clntSockTh();
        client.start();
        
    }

    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == readbutton) {
            String tmpString = client.readLineStrClear();
            System.out.print("I read :");
            System.out.println(tmpString);
            if(tmpString != null){
                ta1.append(new Date() + ": " + tmpString + '\n');
            }
        }
        if (ae.getSource() == sendButton) {
            String tmpString = tf1.getText(); 
            tf1.setText(null);
            client.sendLineStr(tmpString);
            System.out.print("I sent :");
            System.out.println(tmpString);
        }
            
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