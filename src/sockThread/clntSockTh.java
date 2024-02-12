package sockThread;

// for Socket com. importing here
import java.net.*;
import java.io.*;

public class clntSockTh extends Thread {
    private Socket clientSocket;

    // string buffer
    private PrintWriter out;
    private BufferedReader in;
    public String lineStrBuf;

    public clntSockTh(){
        lineStrBuf = null;
        clientSocket = null;
        connect("127.0.0.1", 8000);
    }
    public clntSockTh(String ip, int port){
        lineStrBuf = null;
        clientSocket = null;
        connect(ip, port);
    }

    public String readLineStr(){
        return this.lineStrBuf;
    }
    public String readLineStrClear(){
        String tmpString = this.lineStrBuf;
        this.lineStrBuf = null;
        return tmpString;
    }
    public void sendLineStr(String msg) {
        if(clientSocket != null){
            try {
                out.println(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isConnected(){
        if(clientSocket != null){
            return true;
        } else {
            return false;
        }
    }

    public void connect(String ip, int port) {
        try{
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));   
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    private void ending() {
        try{
            in.close();
            out.close();
            clientSocket.close();
            clientSocket = null;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){            // do accepting and receiving here
            try {
                if(clientSocket != null){                  // When someone DO connected to me am server, focusing to read buffer;
                //if(isConnected(clientSocket)){
                    lineStrBuf = in.readLine();
                }

            } catch (Exception e) {
                e.printStackTrace();
                //this.ending();
            }
        }
    }
    
}
