package sockThread;

// for Socket com. importing here
import java.net.*;
import java.io.*;

public class srvSockTh extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    // string buffer
    private PrintWriter out;
    private BufferedReader in;
    public String lineStrBuf;

    public srvSockTh(){
        lineStrBuf = null;
        serverSocket = null;
        clientSocket = null;

        // for future...I'm gonna fix this as finding available available port and use that.....maybe..(240211)
        this.start(8000);
    }
    public srvSockTh(int listeningPort){
        lineStrBuf = null;
        serverSocket = null;
        clientSocket = null;
        this.start(listeningPort);
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
    public boolean isClientConnected(){
        if(clientSocket != null){
            return true;
        } else {
            return false;
        }
    }

    private void start(int port) {
        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void ending() {
        try{
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            clientSocket = null;
            serverSocket = null;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){            // do accepting and receiving here
            try {
                if(clientSocket == null){                  // When anyone DOESN'T connected to me am server, just being welcome;
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                }
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

