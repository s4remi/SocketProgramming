import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

//client

public class ONE {
    public static void main(String argv[]) throws Exception {
        String message;
        String server_respond;
        //TODO I can use the socket.getInputStream() instead of the System.in
        BufferedReader client_input = new BufferedReader(new InputStreamReader(System.in));
        if (argv.length != 2) {
            System.out.println("Usage: java Client <hostname or IP address> <port number>");
            System.exit(1);
        }
        String hostName=argv[0];
        int port_number = Integer.valueOf(argv[1]);
        int timeout = 1000000;
        //TODO I don't need the while loop to verify or I have to modify it to work on the command line too
        while (port_number < 0 || port_number > 65535) {
            System.out.println("the port number should be in 0 to 65535 range.");
            //port_number = input.nextInt();
        }
        // TODO, for using time out i should use  socket.connect(new InetSocketAddress(hostname, port), timeout);
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(hostName);

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            String message_put_ADAM = "put,ADAM,44";
            sendData = message_put_ADAM.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for ADAM: " + server_respond);

            String message_put_BOB = "put,BOB,4";
            sendData = message_put_BOB.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for BOB: " + server_respond);

            String message_put_LI = "put,LI,4664";
            sendData = message_put_LI.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for LI: " + server_respond);

            String message_put_JOY = "put,JOY,88";
            sendData = message_put_JOY.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for LI: " + server_respond);

            // get
            String message_get_JOY = "get,JOY";
            sendData = message_get_JOY.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for LI: " + server_respond);

            String message_get_LI = "get,LI";;
            sendData = message_get_LI.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for LI: " + server_respond);


            String message_get_ADAM = "get,ADAM";
            sendData = message_get_ADAM.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for BOB: " + server_respond);

            String message_get_BOB = "get,BOB";
            sendData = message_put_BOB.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for BOB: " + server_respond);

            //delete

            String message_delete_JOY = "delete,JOY";
            sendData = message_delete_JOY.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for LI: " + server_respond);

            String message_delete_LI = "delete,LI";;
            sendData = message_delete_LI.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for LI: " + server_respond);


            String message_delete_ADAM = "delete,ADAM";
            sendData = message_delete_ADAM.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for BOB: " + server_respond);

            String message_delete_BOB = "delete,BOB";
            sendData = message_delete_BOB.getBytes();
            sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
            clientSocket.send(sendPacket);
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("prepopulating the result for BOB: " + server_respond);




            while (true) {
                System.out.print("Enter a message (\"get, [name]\", \"put, [name], [value]\"): ");
                message = client_input.readLine();
                sendData = message.getBytes();
                DatagramPacket sendPacket1 = new DatagramPacket(sendData, sendData.length, IPAddress, port_number);
                clientSocket.send(sendPacket1);
                DatagramPacket receivePacket1 = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket1);
                server_respond = new String(receivePacket1.getData());
                System.out.println("FROM SERVER: " + server_respond);
            }
        } catch (SocketTimeoutException e) {
            System.out.println("time out, fail to connect to the server");
        } catch (UnknownHostException e) {
            System.out.println("Unknown host exception, please check the host name.");
        } catch (IOException e) {
            System.out.println("IOException, fail to connect to the server.");
        }
    }
}





























    /*
    public static void main(String argv[]) throws Exception {
        String message;
        String server_respond;
        BufferedReader client_input = new BufferedReader(new InputStreamReader(System.in));
        if (argv.length != 2) {
            System.out.println("Usage: java Client <hostname or IP address> <port number>");
            System.exit(1);
        }
        String hostName = argv[0];
        int port_number = Integer.valueOf(argv[1]);
        int timeout = 1000000;
        while (port_number < 0 || port_number > 65535) {
            System.out.println("the port number should be in 0 to 65535 range.");
        }

        DatagramSocket clientSocket = new DatagramSocket();
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String[] messages = {
                "put,ADAM,44",
                "put,BOB,4",
                "put,LI,4664",
                "put,JOY,88",
                "put,JOSH,656",
                "get,ADAM",
                "get,BOB",
                "get,LI",
                "get,JOY",
                "get,JOSH",
                "delete,JOSH",
                "delete,ADAM",
                "delete,BOB",
                "delete,LI",
                "delete,JOY"
        };
        InetAddress address = InetAddress.getByName(hostName);

        for (String msg : messages) {
            sendData = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port_number);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            server_respond = new String(receivePacket.getData());
            System.out.println("Server responded: " + server_respond.trim());
        }
        clientSocket.close();
    }

     */


