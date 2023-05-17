import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CoordinatorInterface extends Remote {
    String put(String key, String value) throws RemoteException;
    String delete(String key)throws RemoteException;
    boolean getTransactionStatus(String transactionId)throws RemoteException;
}
