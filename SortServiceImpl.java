import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;

import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;


public class SortServiceImpl extends UnicastRemoteObject implements Sort {
    private int clientCount = 0;

    public SortServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<Integer> sort(List<Integer> list) throws RemoteException {
        //clientCount++;
        //RemoteRef ref=((RemoteObject)this).getRef();
        //InetAddress clientAddress = ref.getClientHost();
        //System.out.println("Client " + clientCount + " connected from IP address: " + clientAddress);
        Collections.sort(list);
        //list.add(-99);
        return list;
    }
}
