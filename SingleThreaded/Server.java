package SingleThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

public void run(){
     int port = 8010;
     int timeout = 10000;
     ServerSocket socket;

     try{
     socket = new ServerSocket(port);
     socket.setSoTimeout(timeout); 
     while(true){ 
        try{
        System.out.println("Server is listening on port" + port);
        Socket acceptedConnection = socket.accept();
        System.out.println("Connection accepted from client" +acceptedConnection.getRemoteSocketAddress());
        
        PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(acceptedConnection.getInputStream()));
        String clientMessage = fromClient.readLine();
        System.out.println("Client says:" + clientMessage);
        toClient.println("Hello form the server");

     }catch(IOException ex){
        ex.printStackTrace();
     }
  }
    
} catch(IOException ex){
    ex.printStackTrace();
}
}

public static void main(String[] args){
    Server server = new Server();
    try{
        server.run();
    }catch(Exception ex){
        ex.printStackTrace();
    }


}


}