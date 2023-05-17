
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//server
public class TWO {
    private static HashMap<String, String> store = new HashMap<>();

    public static void main(String[] args) throws IOException {
        int portNumber = 1234;

        try (DatagramSocket serverSocket = new DatagramSocket(portNumber)) {
            System.out.println("Server is waiting for the client");
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client: " + message);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                String[] parts = message.split(",");
                if (parts.length == 3 && parts[0].toLowerCase().equals("put")) {
                    store.put(parts[1], parts[2]);
                    String response = "Key-value pair stored successfully.";
                    sendData = response.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    serverSocket.send(sendPacket);
                } else if (parts.length == 2) {
                    if (parts[0].toLowerCase().equals("get")) {
                        String value = store.get(parts[1]);
                        String response = "the value of the " + parts[1] + " is: " + value;
                        sendData = response.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    } else if (parts[0].toLowerCase().equals("delete")) {
                        store.remove(parts[1]);
                        String response = " the item is deleted";
                        sendData = response.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    }
                }
                Set<Map.Entry<String, String>> entries = store.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
