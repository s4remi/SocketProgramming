import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MultiThread_client {
    public static void main(String[] args) throws IOException {
        int port_number= 1254;
        Socket socket = new Socket("localhost", port_number);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        byte[] message= " knock, knock!".getBytes();

        out.write(message);
        byte[] buffer = new byte[1024];
        int bytesRead = in.read(buffer);
        System.out.println("Received message from the server is " + new String(buffer, 0, bytesRead));

        socket.close();
    }
}

