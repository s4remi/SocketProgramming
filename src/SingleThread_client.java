import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.ConnectException;


/*
AFTER CLOSING THE CLIENT THE SERVER SHOULD BE CLOSE TOO AND YOU HAVE TO RE RUN THE SERVER FOR THE NEW START
 In this client class there are
 */

public class SingleThread_client {
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
            Socket clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(hostName,port_number));
            BufferedWriter outToServer= new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            outToServer.write("put,ADAM,44\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("put,BOB,4\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("put,LI,4664\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("put,JOY,88\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("put,JOSH,656\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            // get method
            outToServer.write("get,ADAM\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("get,BOB\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("get,LI\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("get,JOY\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("get,JOSH\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            //delete method
            outToServer.write("delete,JOSH\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("delete,ADAM\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("delete,BOB\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("delete,LI\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);

            outToServer.write("delete,JOY\n");
            outToServer.flush();
            server_respond = inFromServer.readLine();
            System.out.println("prepopulating the reault"+server_respond);




            System.out.println("use \",\" for separation. ex: put,ali,2" );

            while (true){
                message = client_input.readLine();
                outToServer.write(message + '\n');
                System.out.println("outToServer.write is: "+message+" and the length is"+message.length());
                outToServer.flush();
                server_respond = inFromServer.readLine();
                System.out.println("FINAL MESSAGE FROM SERVER: \t" + server_respond);

            }
        } catch (ConnectException e) {
            e.printStackTrace();
        }catch (IOException e) {
            System.err.println("Error: Unable to connect to server."+e);
        }
    }
}

/*
           //message = client_input.readLine();
//            if (message.length() > 80) {
//                message = message.substring(0, 80);
//            }
            //outToServer.writeline(message);
 */

/*


//    public static void setup(DataOutputStream dataOutputStream){
//        try{
//            dataOutputStream.writeUTF("put,ali,22");
//            dataOutputStream.writeUTF("put,mali,33");
//            dataOutputStream.writeUTF("put,nali,44");
//            dataOutputStream.writeUTF("put,zali,77");
//            dataOutputStream.writeUTF("put,oali,55");
//            dataOutputStream.flush();
//        }catch (IOException e){
//            System.out.println("iin set up"+e);
//        }
//    }
 */

/*
 String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        int timeout = 5000; // 5 seconds

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(hostname, port), timeout);
            // Send and receive data from the server
        } catch (IOException e) {
            System.err.println("Error: Unable to connect to server.");
        }
    }

 */

/*

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(hostname, port)) {
            // Send and receive data from the server
        } catch (IOException e) {
            System.err.println("Error: Unable to connect to server.");
        }
    }
}
 */


/*

    public static void main(String[] args) throws IOException {
        int port_number=1254;
        Socket socket = new Socket("localhost", port_number);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        byte[] message=" Knock, Knock!".getBytes();
        out.write(message);
        byte[] buffer = new byte[1024];
        int bytesRead = in.read(buffer);
        System.out.println("Received message from the server is: " + new String(buffer, 0, bytesRead));
        socket.close();
    }
 */

//changes for the udp part
//datagramssocket, data