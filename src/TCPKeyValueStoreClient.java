import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TCPKeyValueStoreClient{
    private static String hostname="";
    private static int port;
    private static final int TIMEOUT = 50000; // 5 seconds timeout
    private static Logger logger = Logger.getLogger("ClientLog");
    private static FileHandler fh;

    public static void main(String[] args) throws Exception {
        String sentence;
        String modifiedSentence;
        BufferedReader inFromServer;
        //TODO validate args so no out of index error
        hostname=args[0];
        port=Integer.valueOf(args[1]);

        // Set up the logger
        fh = new FileHandler("ClientLog.log");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        int run=3;
        while (run > 0) {
            try {
                Socket clientSocket = new Socket();
                System.out.println("hi");

                // java src/TCPKeyValueStoreClient.java localhost 1234
                InetSocketAddress ipadd = new InetSocketAddress("127.0.0.1",port);
                clientSocket.connect(ipadd, TIMEOUT);

                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outToServer.writeUTF("GET 3");
                String serverResponse = inFromServer.readLine();
                System.out.println("in client???");
                logger.warning(serverResponse);

               /* BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Enter request type (GET, SET, DELETE):");
                String requestType = inFromUser.readLine();
                System.out.println("Enter key:");
                String key = inFromUser.readLine();
                String value = "";
                if (requestType.equals("SET")) {
                    System.out.println("Enter value:");
                    value = inFromUser.readLine();
                }
                sentence = requestType + " " + key + " " + value + "\n";
                outToServer.writeBytes(sentence);
                modifiedSentence = inFromServer.readLine();
                if (modifiedSentence.startsWith("ERROR")) {
                    logger.warning("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "] Received unsolicited response: " + modifiedSentence);
                } else {
                    System.out.println("FROM SERVER: " + modifiedSentence);
                }*/
                clientSocket.close();
                run--;
            } catch (SocketTimeoutException e) {
                logger.warning("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "] Request timed out");
            } catch (IOException e) {
                logger.warning("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()) + "] Error connecting to server"
                +e);
            }
        }
    }
}
