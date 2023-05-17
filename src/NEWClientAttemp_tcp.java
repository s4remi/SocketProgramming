import java.io.BufferedReader;
        import java.io.BufferedWriter;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.net.InetSocketAddress;
        import java.net.Socket;
        import java.util.HashMap;

public class NEWClientAttemp_tcp {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java SingleThreadClient <hostname or IP address> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        int timeout = 1000000;

        try (Socket clientSocket = new Socket()) {
            clientSocket.connect(new InetSocketAddress(hostName, portNumber), timeout);

            try (BufferedWriter outToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                 BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                outToServer.write("put,client,009\n");
                outToServer.write("put,mali,33\n");
                outToServer.write("put,nali,44\n");
                outToServer.write("put,zali,77\n");
                outToServer.write("put,oali,55\n");
                outToServer.flush();

                for (int i = 0; i < 5; i++) {
                    outToServer.write("put,key" + i + ",value" + i + "\n");
                }
                outToServer.flush();

                for (int i = 0; i < 5; i++) {
                    outToServer.write("get,key" + i + "\n");
                    System.out.println("GET response from server: " + inFromServer.readLine());
                }

                for (int i = 0; i < 5; i++) {
                    outToServer.write("delete,key" + i + "\n");
                }
                outToServer.flush();

                for (int i = 0; i < 5; i++) {
                    outToServer.write("get,key" + i + "\n");
                    System.out.println("GET response from server after DELETE: " + inFromServer.readLine());
                }
            }
        }
    }
}



