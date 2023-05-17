import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TCPKeyValueStoreServer {
    //TODO final port = 1234

    private static Map<String, String> store = new HashMap<>();

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(1234);
            System.out.println("Server is running and waiting for a client to connect...");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Client connected: " + socket.getInetAddress() + ":" + socket.getPort());

                BufferedReader input = null;
                DataOutputStream output = null;

                try {
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new DataOutputStream(socket.getOutputStream());

                    String message_input = input.readLine();
                    //TODO CHECK LENGTH IS AT LEAST 2
                    String[] parts=message_input.split(".");

                    if (parts[0].equals("GET")) {
                        String value = store.get(parts[1]);
                        if (value != null) {
                            output.writeUTF(value);
                            System.out.println("get Response sent: VALUE " + value);
                        } else {
                            output.writeUTF("Not found.");
                            System.out.println("Response sent: NOT_FOUND");
                        }
                    } else if (parts[0].equals("PUT")) {
                        store.put(parts[1], parts[2]);
                        //output.writeObject("OK");

                        System.out.println("Response sent: OK");
                    } else {
                        //output.writeObject("ERROR");
                        System.out.println("Response sent: ERROR");
                    }
                    output.flush();
                } catch (Exception e) {
                    System.out.println("Received malformed request from " + socket.getInetAddress() + ":" + socket.getPort() + e);
                } finally {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    socket.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
}