import java.net.*;
import java.io.*;
public class UDP_client {
    public static void main(String[] args) {
        DatagramSocket client_to_server=null;
        if(args.length < 3){
            System.out.println(" usage: java UDP_client <message> <host name> <port number>");
            System.exit(1);
        }
        try{
            client_to_server= new DatagramSocket();
            byte [] m = args[0].getBytes();
            InetAddress host= InetAddress.getByName(args[1]);
            int serverPort= Integer.valueOf(args[2]).intValue();
        }catch(SocketException e){
            System.out.println("socket: "+e.getMessage());
        }catch(IOException e){
            System.out.println("IO: "+ e.getMessage());
        }finally {
            if(client_to_server !=null){
                client_to_server.close();
            }
        }
    }
}
