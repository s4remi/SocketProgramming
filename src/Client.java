import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        //port_id=1254  && server name for ex: localhost
        int port_id=1254;
        //creatinga socket
        Socket client= new Socket("localhost", port_id);
        /*
        the alternative
        //InputStream cl_in= client.getInputStream();
        //DataInputStream client_in= new DataInputStream(cl_in);
         */
        DataInputStream client_in = new DataInputStream(client.getInputStream());
        String read_stream= new String (client_in.readUTF());
        System.out.println(read_stream);
        System.out.println(" finishing the stream");

        client_in.close();
        client.close();
    }
}
