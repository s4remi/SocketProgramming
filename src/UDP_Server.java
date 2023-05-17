import java.io.*;
import java.net.*;

public class UDP_Server {
    public static void main(String[] args) {
        //Datagram Socket: transferring or receiving data a datagram over the network
        DatagramSocket server = null;
        if(args.length <1){
            System.out.println("Usage: java UDP_server < port number>");
            System.exit(0);
        }
        try{
            int socket_number = Integer.valueOf(args[0]).intValue();
            server = new DatagramSocket(socket_number);
            byte [] buffer= new byte[1000];
            while (true){
                //datagram pocket: sending pockets to the specific port number on the specific host
                DatagramPacket request= new DatagramPacket(buffer,buffer.length);
                server.receive(request);
                DatagramPacket reply= new DatagramPacket(request.getData(),request.getLength(),
                        request.getAddress(),request.getPort());
                server.send(reply);
            }
        }catch(SocketException e){
            System.out.println(" socket: "+ e.getMessage());
        }catch(IOException  e){
            System.out.println("IO: "+ e.getMessage());
        }
        finally {
            if(server !=null){
                server.close();
            }
        }
    }
}
