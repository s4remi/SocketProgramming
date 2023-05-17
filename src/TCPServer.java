import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPServer {
    public static void main(String argv[]) throws Exception {
        String message_input;
        String message_output;
        System.out.println("server port: ");
        Scanner input = new Scanner(System.in);
        int port_number= input.nextInt();
        while(port_number <0 || port_number>65535){
            System.out.println("the port number should be in 0 to 65535 range. try again");
            port_number= input.nextInt();
        }
        ServerSocket server_socket = new ServerSocket(port_number);
        System.out.println("Server is waiting for the client");
        while (true) {
            //System.out.println(" iam here");
            Socket connectionSocket = server_socket.accept();
            System.out.println("new client is accepted");
            System.out.println("reading from client ");
            BufferedReader server_input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream server_output = new DataOutputStream(connectionSocket.getOutputStream());
            message_input = server_input.readLine();
            if(message_input.length() > 80) {
                message_input = message_input.substring(0, 80);
            }
            System.out.println("the original message is: \t"+ message_input);
            // Reverse the characters
            message_output = new StringBuilder(message_input).reverse().toString();
            // Reverse the capitalization of the string
            char [] ch= message_output.toCharArray();
            for(int i=0; i< ch.length;i++){
                if(Character.isLowerCase(ch[i])){
                    ch[i]= Character.toUpperCase(ch[i]);
                }else{
                    ch[i]= Character.toLowerCase(ch[i]);
                }
            }
            String final_string = new String(ch);
            server_output.writeBytes(final_string + '\n');
            System.out.println("sending back the message to client");
            connectionSocket.close();
            server_output.close();
        }
    }
}
