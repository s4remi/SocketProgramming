import java.io.*;
import java.net.*;
import java.rmi.ConnectException;
import java.util.*;

public class TCPClient {
    public static void main(String argv[]) throws Exception {
        String message;
        String modifiedSentence;
        BufferedReader client_input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("client port: ");
        Scanner input = new Scanner(System.in);
        int port_number= input.nextInt();
        while(port_number <0 || port_number>65535){
            System.out.println("the port number should be in 0 to 65535 range. try again");
            port_number= input.nextInt();
        }
        try{
            Socket clientSocket = new Socket("localhost", port_number);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("write your message");
            message = client_input.readLine();
            if(message.length() > 80) {
                message = message.substring(0, 80);
            }
            outToServer.writeBytes(message + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("FINAL MESSAGE FROM SERVER: \t" + modifiedSentence);
            clientSocket.close();
            outToServer.close();
        }catch(ConnectException e){
            e.printStackTrace();
        }
//        Socket clientSocket = new Socket("localhost", port_number);
//        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        System.out.println("write your message");
//        message = client_input.readLine();
//        if(message.length() > 80) {
//            message = message.substring(0, 80);
//        }
//        outToServer.writeBytes(message + '\n');
//        modifiedSentence = inFromServer.readLine();
//        System.out.println("FINAL MESSAGE FROM SERVER: \t" + modifiedSentence);
//        clientSocket.close();
//        outToServer.close();
    }
}
