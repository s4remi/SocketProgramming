import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import static java.rmi.Naming.rebind;

public class RMI_server{

    public static void main(String[] args) {
        try {
            //RMI_server rmi_server = new RMI_server();
            LocateRegistry.createRegistry(1254);
            Sort sort= new SortServiceImpl();
            Naming.rebind("//Localhost/Sort",sort);
            //rebind("Sort", rmi_server);
            System.out.println("Server is ready and started successfully");
        } catch (Exception e) {
            System.out.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

