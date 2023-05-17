
import  java.net.*;
import java.io.*;

public class Server_textbook13 {
    public static void main(String[] args) throws IOException{
        //register service on port 1254
        ServerSocket server= new ServerSocket(1254);
        System.out.println("done in creating a server and waiting for client");
        Socket client= server.accept();
        System.out.println("done in accepting a client");
        DataOutputStream server_out= new DataOutputStream(client.getOutputStream());
        //send a string to client
        server_out.writeUTF("hello world");
        System.out.println("meow");
        server_out.close();
        client.close();

    }
}




/*



        //register service on port 1254
        ServerSocket server= new ServerSocket(1254);
        System.out.println("done in creating a server");
        Socket client= server.accept();
        System.out.println("done in accepting a client");
        DataOutputStream server_out= new DataOutputStream(client.getOutputStream());
        //send a string to client
        server_out.writeUTF("hello world");
        System.out.println("meow");
        server_out.close();
        client.close();
 */
