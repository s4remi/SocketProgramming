import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Sort extends Remote {
    List<Integer> sort(List<Integer> list) throws RemoteException;
}

