
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KVS extends Remote {

    String put(String key, String value) throws RemoteException;
    String get(String key) throws RemoteException;
    String delete(String key) throws RemoteException;
    boolean prepare(String transactionID) throws RemoteException;
    void commit(String transactionID) throws RemoteException;
    void abort(String transactionID) throws RemoteException;
}
