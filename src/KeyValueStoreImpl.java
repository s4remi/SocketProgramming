

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeyValueStoreImpl extends UnicastRemoteObject implements KeyValueStore {
    private static final long serialVersionUID = 1L;
    private static final int NUM_REPLICAS = 5;
    private static ArrayList<KeyValueStore> replicas = new ArrayList<>();
    private static HashMap<String, String> store = new HashMap<String, String>();
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    private KeyValueStoreImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void put(String key, String value) throws RemoteException {
        store.put(key, value);
        System.out.println("Key-value pair stored successfully.");
    }

    @Override
    public synchronized String get(String key) throws RemoteException {
        String value = store.get(key);
        if (value == null) {
            return "No value found for the given key.";
        }
        return "The value of " + key + " is " + value + ".";
    }

    @Override
    public synchronized void delete(String key) throws RemoteException {
        String value = store.remove(key);
        if (value == null) {
            System.out.println("No value found for the given key.");
        }
        System.out.println("The item with key " + key + " is deleted.");
    }

    public static void main(String[] args) throws Exception {
        int port = 1234;
        Registry registry = LocateRegistry.createRegistry(port);
        KeyValueStoreImpl storeImpl = new KeyValueStoreImpl();
        registry.bind("RmiServer", storeImpl);
        System.out.println("Server is waiting for the client");

        while (true) {
            executor.execute(new RmiHandler(storeImpl));
        }
    }

    static class RmiHandler implements Runnable {
        private KeyValueStoreImpl storeImpl;

        public RmiHandler(KeyValueStoreImpl storeImpl) {
            this.storeImpl = storeImpl;
        }

        @Override
        public void run() {
            try {
                KeyValueStore stub = (KeyValueStore) UnicastRemoteObject.toStub(storeImpl);
                // Handle the request from the client using the stub object
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}



/*

 V1: want to set up a server-client multithread tcp that server and client communicate with each other with using Remote Procedure Calls (RPC)
instead of sockets, USing RMI

public class KeyValueStoreImpl extends UnicastRemoteObject implements KeyValueStore {
    private static final long serialVersionUID = 1L;
    private HashMap<String, String> store = new HashMap<String, String>();

    public KeyValueStoreImpl() throws RemoteException {
        super();
    }

    @Override
    public String put(String key, String value) throws RemoteException {
        store.put(key, value);
        return "Key-value pair stored successfully.";
    }

    @Override
    public String get(String key) throws RemoteException {
        String value = store.get(key);
        if (value == null) {
            return "Key not found.";
        } else {
            return "The value of the " + key + " is: " + value;
        }
    }

    @Override
    public String delete(String key) throws RemoteException {
        if (store.remove(key) == null) {
            return "Key not found.";
        } else {
            return "The item is deleted.";
        }
    }

    public static void main(String[] args) {
        try {
            KeyValueStoreImpl obj = new KeyValueStoreImpl();
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.bind("KeyValueStore", obj);
            System.out.println("Server is running.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}


 */