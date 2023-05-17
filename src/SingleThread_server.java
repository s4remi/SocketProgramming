import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
AFTER CLOSING THE CLIENT THE SERVER SHOULD BE CLOSE TOO AND YOU HAVE TO RE RUN THE SERVER FOR THE NEW START
 In this server class,
 */

public class SingleThread_server {
    private static HashMap<String, String> store = new HashMap<String,String>();

    public static void main(String argv[]) throws Exception {
        String message_input=null;
        String message_output = null;
        int port_number=1234;

        ServerSocket server_socket = new ServerSocket(port_number);
        System.out.println("Server is waiting for the client");
        Socket connectionSocket = server_socket.accept();
        BufferedReader server_input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        BufferedWriter server_output= new BufferedWriter( new OutputStreamWriter(connectionSocket.getOutputStream()));

        while (true) {

            message_input = server_input.readLine();
            if(message_input == null){
                continue;
            }
            //TODO if the client stopes we have to stop the server as well
            if(message_input != null){
                System.out.println("usr input "+message_input+ " length: "+message_input.length());
                String[] parts = message_input.split(",");
                if(parts.length==3 && parts[0].toLowerCase().equals("put")){
                    // Store a key-value pair
                    store.put(parts[1],parts[2]);
                    message_output = "Key-value pair stored successfully.";
                    server_output.write(message_output + '\n');
                    server_output.flush();
                    //TODO make sure to not allow having a 1 character
                }else if (parts.length == 2 ) {
                    if(parts[0].toLowerCase().equals("get")){
                        // get a value of a key
                        //store.get(parts[1]);
                        String mm=store.get(parts[1]);
                        message_output = "the value of the "+parts[1]+" is: "+mm;
                        server_output.write(message_output + '\n');
                        server_output.flush();
                    }else if (parts[0].toLowerCase().equals("delete")){
                        //delete
                        store.remove(parts[1]);
                        message_output=" the item is deleted";
                        server_output.write(message_output + '\n');
                        server_output.flush();
                    }
                }
            }else{
                System.out.println("the input is null");
                break;
            }

            Set<Map.Entry<String, String>> entries = store.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }
        }
    }
}

/*

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
 */

/*
import java.util.HashMap;

public class SingleThread_serve {
    private static HashMap<String, String> store = new HashMap<>();

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
            Socket connectionSocket = server_socket.accept();
            System.out.println("new client is accepted");
            System.out.println("reading from client ");
            BufferedReader server_input = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream server_output = new DataOutputStream(connectionSocket.getOutputStream());
            message_input = server_input.readLine();

            String[] parts = message_input.split(":");
            if (parts.length == 2) {
                // Store a key-value pair
                store.put(parts[0], parts[1]);
                message_output = "Key-value pair stored successfully.";
            } else if (parts.length == 1) {
                // Retrieve a value for a given key
                String value = store.get(parts[0]);
                if (value != null) {
                    message_output = value;
                } else {
                    message_output = "Key not found.";
                }
            } else {
                message_output = "Invalid input format. Use the format 'key:value' to store a key-value pair, or just 'key' to retrieve the value for a key.";
            }
            server_output.writeBytes(message_output + '\n');
            System.out.println("sending back the message to client");
            connectionSocket.close();
            server_output.close();
        }
    }
}

 */