import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class NEWServerAttemp_tcp {
    private static HashMap<String, String> store = new HashMap<String,String>();
    public static void main(String argv[]) throws Exception {
        String message_input=null;
        String message_output = null;
        int port_number=1234;
        ServerSocket server_socket = new ServerSocket(port_number);
        System.out.println("Server is listening on port " + port_number + "...");
        while (true) {
            Socket client_socket = server_socket.accept();
            System.out.println("Accepted connection from " + client_socket.getInetAddress().getHostName());
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
            BufferedWriter outToClient = new BufferedWriter(new OutputStreamWriter(client_socket.getOutputStream()));
            while ((message_input = inFromClient.readLine()) != null) {
                System.out.println("Received message: " + message_input);
                String[] msg = message_input.split(",");
                if (msg.length != 3) {
                    message_output = "ERROR: Invalid input";
                } else if (msg[0].equalsIgnoreCase("put")) {
                    store.put(msg[1], msg[2]);
                    message_output = "OK: Value stored successfully";
                } else if (msg[0].equalsIgnoreCase("get")) {
                    if (store.containsKey(msg[1])) {
                        message_output = "Value: " + store.get(msg[1]);
                    } else {
                        message_output = "ERROR: Key not found";
                    }
                } else if (msg[0].equalsIgnoreCase("delete")) {
                    if (store.containsKey(msg[1])) {
                        store.remove(msg[1]);
                        message_output = "OK: Key deleted successfully";
                    } else {
                        message_output = "ERROR: Key not found";
                    }
                } else {
                    message_output = "ERROR: Invalid input";
                }
                System.out.println("Sending response: " + message_output);
                outToClient.write(message_output + '\n');
                outToClient.flush();
            }
            client_socket.close();
        }
    }
}
