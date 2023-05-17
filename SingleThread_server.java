import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//import java.io.*;
//import java.net.*;
public class SingleThread_server {
    public static void main(String[] args) throws IOException {
        int port_number=1254;
        ServerSocket server = new ServerSocket(port_number);
        System.out.println("Server listening on port: "+port_number);

        Socket client = server.accept();
        System.out.println("Client IP address is: " + client.getInetAddress().getHostAddress());

        InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        client.close();
        server.close();
    }
}