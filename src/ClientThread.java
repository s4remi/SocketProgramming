import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ClientThread extends Thread {
    private Socket client;

    public ClientThread(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
