import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


//in this server will be responsible for listening to client and create a new thread to handle than client
public class MultipleClientChat_server {
    //Socket: its job is listening  for incoming connections or clients and
    //creating a socket object to communicate
    private ServerSocket serverSocket;

    public MultipleClientChat_server(ServerSocket serverSocket){
        this.serverSocket=serverSocket;
    }

    public void startServer(){
        try{
            while (! serverSocket.isClosed()){
                Socket socket= serverSocket.accept();
                System.out.println(" A new client has connected");
                //having a parameter gives the server to be able to give
                // service to other client at the same time.
                //not working as a queue.
                MultipleClientChat_clientHandler clientHandler= new MultipleClientChat_clientHandler(socket);

                Thread thread= new Thread(clientHandler);
                thread.start();
            }
        }catch(IOException e){
            closeServerSocket();
        }
    }
    public void closeServerSocket(){
        try {
            // before closing it, make sure if it is exist
            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        int port_number= 1234;
        //the server will be listening to client via this port number
        ServerSocket serverSocket= new ServerSocket(port_number);
        MultipleClientChat_server server = new MultipleClientChat_server (serverSocket);
        server.startServer();


    }

}
