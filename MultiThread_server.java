import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThread_server {
    public static void main(String[] args) throws IOException {
        int port_number= 1254;
        ServerSocket server = new ServerSocket(port_number);
        System.out.println("Server listening on port number: "+port_number);

        while (true) {
            Socket client = server.accept();
            System.out.println("Client IP address is:  " + client.getInetAddress().getHostAddress());

            Thread clientThread = new ClientThread(client);
            clientThread.start();
        }
    }
}

