import java.io.FileWriter;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class KVSI extends UnicastRemoteObject implements KVS {
    private final Map<String, String> store;//this becomes a databse instance
    private final Map<String, AtomicBoolean> transactionStatus;

    public KVSI() throws RemoteException {
        store= new ConcurrentHashMap<>();
        transactionStatus= new ConcurrentHashMap<>();

    }
    @Override
    public synchronized String put(String key, String value) throws RemoteException {
        return store.put(key,value);

    }
    @Override
    public synchronized String get(String key) throws RemoteException {
        return store.get(key);
    }

    @Override
    public synchronized String delete(String key) throws RemoteException {
        return store.remove(key);

    }

    @Override
    public boolean prepare(String transactionID) throws RemoteException {
        transactionStatus.putIfAbsent(transactionID,new AtomicBoolean(false));
        return true;
    }

    @Override
    public void commit(String transactionID) throws RemoteException {
        AtomicBoolean status= transactionStatus.get(transactionID);
        if(status !=null){
            status.set(true);
        }
    }

    @Override
    public void abort(String transactionID) throws RemoteException {
        AtomicBoolean status=transactionStatus.get(transactionID);
        if(status != null){
            status.set(false);
        }
    }
}






















/*
    //private Map<String, Integer> vote = new HashMap<>();
    //private Map<String, Integer> ack = new HashMap<>();
    //private static final int NUM_REPLICAS = 5;
//    private static ArrayList<KVS> replicas = new ArrayList<>();
   // private static ExecutorService executor = Executors.newFixedThreadPool(10);
//    private int participantID;
//    private Map<String, List<String>> transactionParticipants = new HashMap<>();
//    private static final String HOST = "127.0.0.1";
//    private static final int PORT = 1234;

 */

//    public synchronized boolean prepare(String transactionID) throws RemoteException {
//        // check if transactionID is valid
//        if (!vote.containsKey(transactionID)) {
//            throw new RemoteException("Invalid transaction ID");
//        }
//        // check if transaction is already prepared
//        if (vote.get(transactionID) != 0) {
//            throw new RemoteException("Transaction already prepared or committed");
//        }
//        // set vote for transaction to prepared
//        vote.put(transactionID, 1);
//
//        // return true to indicate successful preparation
//        return true;
//    }
//    public synchronized boolean commit(String transactionID) throws RemoteException {
//        // check if transactionID is valid
//        if (!vote.containsKey(transactionID)) {
//            throw new RemoteException("Invalid transaction ID");
//        }
//
//        // check if transaction is already committed or aborted
//        if (vote.get(transactionID) != 1) {
//            throw new RemoteException("Transaction already committed or aborted");
//        }
//
//        // commit the changes in the uncommitted store to the main store
//        store.putAll(uncommittedStore);
//
//        // set vote for transaction to prepared
//        vote.put(transactionID, 2);
//        ack.put(transactionID, 2);
//
//        // clear the uncommitted store
//        uncommittedStore.clear();
//        return true;
//    }
    /*
    public synchronized boolean abort(String transactionID) throws RemoteException {
        // check if transactionID is valid
        if (!vote.containsKey(transactionID)) {
            throw new RemoteException("Invalid transaction ID");
        }

        // check if transaction is already committed or aborted
        if (vote.get(transactionID) != 1) {
            throw new RemoteException("Transaction already committed or aborted");
        }

        // set vote and ack for transaction to aborted
        vote.put(transactionID, 3);
        ack.put(transactionID, 3);

        // clear the uncommitted store
        uncommittedStore.clear();

        // return true to indicate successful abort
        return true;
    }
    8?
     */

    /*
    public synchronized String get(String key) throws RemoteException {
        return store.get(key);
    }
    public synchronized boolean put(String key, String value, String transactionID) throws RemoteException {
        // add the key-value pair to the uncommitted store
        uncommittedStore.put(key, value);

        // replicate the put operation to all replicas
        for (KVS replica : replicas) {
            if (replica != this) {
                replica.replicatePut(key, value, transactionID);
            }
        }
        // prepare two-phase commit
        for (KVS replica : replicas) {
            if (replica != this) {
                try {
                    replica.prepare(transactionID);
                } catch (RemoteException e) {
                    // handle communication failure with replica
                    System.out.println("Failed to prepare transaction on replica: " + replica);
                }
            }
        }
        // commit or abort the transaction based on the responses from replicas
        boolean allPrepared = true;
        for (KVS replica : replicas) {
            if (replica != this) {
                try {
                    if (!replica.vote(transactionID)) {
                        allPrepared = false;
                        replica.abort(transactionID);
                    }
                } catch (RemoteException e) {
                    // handle communication failure with replica
                    System.out.println("Failed to receive vote from replica: " + replica);
                    allPrepared = false;
                }
            }
        }
        if (allPrepared) {
            for (KVS replica : replicas) {
                if (replica != this) {
                    try {
                        replica.commit(transactionID);
                    } catch (RemoteException e) {
                        // handle communication failure with replica
                        System.out.println("Failed to commit transaction on replica: " + replica);
                    }
                }
            }
            // commit the changes in the uncommitted store to the main store
            store.putAll(uncommittedStore);

            // set vote and ack for transaction to committed
            vote.put(transactionID, 2);
            ack.put(transactionID, 2);

            // clear the uncommitted store
            uncommittedStore.clear();

            return true;
        } else {
            // set vote and ack for transaction to aborted
            vote.put(transactionID, 3);
            ack.put(transactionID, 3);

            // clear the uncommitted store
            uncommittedStore.clear();

            return false;
        }
    }
    public synchronized boolean delete(String key, String transactionID) throws RemoteException {
        // remove the key from the uncommitted store
        uncommittedStore.remove(key);

        // replicate the delete operation to all replicas
        for (KVS replica : replicas) {
            if (replica != this) {
                replica.replicateDelete(key, transactionID);
            }
        }
        // prepare two-phase commit
        for (KVS replica : replicas) {
            if (replica != this) {
                try {
                    replica.prepare(transactionID);
                } catch (RemoteException e) {
                    // handle communication failure with replica
                    System.out.println("Failed to prepare transaction on replica: " + replica);
                }
            }
        }
        // commit or abort the transaction based on the responses from replicas
        boolean allPrepared = true;
        for (KVS replica : replicas) {
            if (replica != this) {
                try {
                    if (!replica.vote(transactionID)) {
                        allPrepared = false;
                        replica.abort(transactionID);
                    }
                } catch (RemoteException e) {
                    // handle communication failure with replica
                    System.out.println("Failed to receive vote from replica: " + replica);
                    allPrepared = false;
                }
            }
        }

        if (allPrepared) {
            for (KVS replica : replicas) {
                if (replica != this) {
                    try {
                        replica.commit(transactionID);
                    } catch (RemoteException e) {
                        // handle communication failure with replica
                        System.out.println("Failed to commit transaction on replica: " + replica);
                    }
                }
            }

            // remove the key from the main store
            store.remove(key);

            // set vote and ack for transaction to committed
            vote.put(transactionID, 2);
            ack.put(transactionID, 2);

            return true;
        } else {
            // set vote and ack for transaction to aborted
            vote.put(transactionID, 3);
            ack.put(transactionID, 3);

            return false;
        }
    }
    public void replicateDelete(String key, String transactionID) {
        // remove the key from the uncommitted store
        uncommittedStore.remove(key);

        // send ACK to the replica
        ack.put(transactionID, 1);
    }
    public synchronized boolean vote(String transactionID, String participant, boolean vot) throws RemoteException {
        // check if transactionID is valid
        if (!vote.containsKey(transactionID)) {
            throw new RemoteException("Invalid transaction ID");
        }

        // if participant has already voted, return false
        if (ack.containsKey(transactionID) && ack.get(transactionID) == 2) {
            return false;
        }

        // record the vote
        if (vot) {
            this.vote.put(transactionID, this.vote.get(transactionID) + 1);
        } else {
            this.vote.put(transactionID, this.vote.get(transactionID) - 1);
        }

        // record the ack
        if (participant != null) {
            if (vot) {
                ack.put(transactionID, 1);
            } else {
                ack.put(transactionID, -1);
            }
        } else {
            ack.put(transactionID, 2);
        }

        // return true to indicate successful vote
        return true;
    }
    public synchronized void recordTransaction(String transactionID, String participantID) throws RemoteException {
        if (!transactionParticipants.containsKey(transactionID)) {
            transactionParticipants.put(transactionID, new ArrayList<>());
        }
        transactionParticipants.get(transactionID).add(participantID);
    }

    public synchronized boolean recordTransaction(String transactionID) throws RemoteException {
        // check if transactionID is valid
        if (!vote.containsKey(transactionID)) {
            throw new RemoteException("Invalid transaction ID");
        }

        // check if transaction is already committed or aborted
        if (vote.get(transactionID) != 2 && vote.get(transactionID) != 3) {
            throw new RemoteException("Transaction not yet committed or aborted");
        }

        // record the transaction in a persistent storage
        try (FileWriter writer = new FileWriter("transactions.txt", true)) {
            writer.write(transactionID + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failed to record transaction: " + e.getMessage());
            return false;
        }
        return true;
    }
    public synchronized void recordVote(String transactionID, String participant, int vote) throws RemoteException {
        // check if transactionID is valid
        if (!ack.containsKey(transactionID)) {
            throw new RemoteException("Invalid transaction ID");
        }

        // check if the participant has already voted
        if (ack.get(transactionID) == 2 || ack.get(transactionID) == 3) {
            throw new RemoteException("Transaction already committed or aborted");
        }

        // record the vote
        this.vote.put(participant + transactionID, vote);

        // count the number of votes
        int numVotes = 0;
        for (int v : this.vote.values()) {
            if (v == vote) {
                numVotes++;
            }
        }
        // if a majority of replicas voted to commit, set the ack to commit
        if (numVotes > NUM_REPLICAS / 2) {
            ack.put(transactionID, 2);
        }
        // if a majority of replicas voted to abort, set the ack to abort
        else if (this.vote.size() - numVotes > NUM_REPLICAS / 2) {
            ack.put(transactionID, 3);
        }
    }
    @Override
    public synchronized boolean vote(String transactionID) throws RemoteException {
        // check if transactionID is valid
        if (!vote.containsKey(transactionID)) {
            throw new RemoteException("Invalid transaction ID");
        }

        // check if transaction is already committed or aborted
        if (vote.get(transactionID) != 1) {
            throw new RemoteException("Transaction already committed or aborted");
        }
        // set ack for transaction to prepared
        ack.put(transactionID, 1);

        // check if all replicas are prepared
        int countPrepared = 0;
        for (KVS replica : replicas) {
            try {
                if (replica.prepare(transactionID)) {
                    countPrepared++;
                }
            } catch (RemoteException e) {
                // handle communication failure with replica
                System.out.println("Failed to receive prepare response from replica: " + replica);
            }
        }
        // return true if all replicas are prepared, false otherwise
        return countPrepared == NUM_REPLICAS - 1;
    }
    public static void main(String[] args) throws Exception {
        int port = 1234;

        KVSI server1= new KVSI();
        KVSI server2= new KVSI();
        KVSI server3= new KVSI();
        KVSI server4= new KVSI();
        KVSI server5= new KVSI();


        Registry registry = LocateRegistry.createRegistry(port);


        registry.bind("rmiserver01", server1);
        registry.bind("rmiserver02", server2);
        registry.bind("rmiserver03", server3);
        registry.bind("rmiserver04", server4);
        registry.bind("rmiserver05", server5);
        System.out.println("Server is waiting for the client");
        while (true) {
            executor.execute(new RmiHandler(server1));
            executor.execute(new RmiHandler(server2));
            executor.execute(new RmiHandler(server3));
            executor.execute(new RmiHandler(server4));
            executor.execute(new RmiHandler(server5));
        }
    }
    static class RmiHandler implements Runnable {
        private KVSI storeImpl;
        public RmiHandler(KVSI storeImpl) {
            this.storeImpl = storeImpl;
        }
        @Override
        public void run() {
            try {
                KVS stub = (KVS) UnicastRemoteObject.exportObject(storeImpl, 0);
                Registry registry = LocateRegistry.getRegistry("localhost", 1234);
                KeyValueStore remoteStore = (KeyValueStore) registry.lookup("RmiServer");
                // Invoke a remote method on the KeyValueStore object
                String key = "Emad";
                String value = "333";
                remoteStore.put(key, value);

                // should invoke get() and delete()
                String result = remoteStore.get(key);
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}










/*
public class KVSI extends UnicastRemoteObject implements KVS {
    private static final long serialVersionUID = 1L;
    private static final int NUM_REPLICAS = 5;
    private static ArrayList<KVS> replicas = new ArrayList<>();
    private static HashMap<String, String> store = new HashMap<String, String>();
    private static ExecutorService executor = Executors.newFixedThreadPool(10);
    private static ArrayList<String> preparedTransactions= new ArrayList<>();
    private KVSI() throws RemoteException {
        super();
    }

    @Override
    public synchronized void put(String key, String value) throws RemoteException {
        TwoPhaseCommitProtocol protocol = new TwoPhaseCommitProtocol();

        // Phase 1: Prepare
        ArrayList<KVS> preparedReplicas = new ArrayList<>();
        for (KVS replica : replicas) {
            try {
                boolean prepared = protocol.prepare(replica.toString());
                if(prepared){
                    preparedReplicas.add(replica);
                }
            } catch (Exception e) {
                String replicasString = String.join(",",preparedReplicas);
                protocol.abort(replicasString);
                throw new RemoteException("Failed to prepare update on replica", e);
            }
        }

        // Phase 2: Commit
        try {
            store.put(key, value);
            for (KVS replica : preparedReplicas) {
                protocol.commit(replica.toString());
            }
            System.out.println("Key-value pair stored successfully.");
            preparedTransactions.add(key);
        } catch (Exception e) {
            protocol.abort(preparedReplicas);
            throw new RemoteException("Failed to commit update on replica", e);
        }
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
        TwoPhaseCommitProtocol protocol = new TwoPhaseCommitProtocol();

        // Phase 1: Prepare
        for (KVS replica : replicas) {
            try {
                protocol.prepare(replica.toString());
            } catch (Exception e) {
                protocol.abort(replicas);
                throw new RemoteException("Failed to prepare update on replica", e);
            }
        }

        // Phase 2: Commit
        try {
            String value = store.remove(key);
            if (value == null) {
                System.out.println("No value found for the given key.");
            }
            for (KVS replica : replicas) {
                protocol.commit(replica.toString());
            }
            System.out.println("The key " + key + " is deleted.");
            preparedTransactions(key);
        } catch (Exception e) {
            protocol.abort(replicas);
            throw new RemoteException("Failed to commit update on replica", e);
        }
    }

    @Override
    public synchronized boolean prepare(String transactionID) throws RemoteException {
        // Add transaction ID to list of prepared transactions
        preparedTransactions.add(transactionID);
        return true;
    }

    @Override
    public synchronized boolean commit(String transactionID) throws RemoteException {
        // Check if transaction ID is in list of prepared transactions
        if (preparedTransactions.contains(transactionID)) {
            // Commit changes and remove transaction ID from list of prepared transactions
            store.commit(transactionID);
            preparedTransactions.remove(transactionID);
            return true;
        } else {
            // Transaction not prepared, commit fails
            return false;
        }
    }

    @Override
    public synchronized boolean abort(String transactionID) throws RemoteException {
        // Check if transaction ID is in list of prepared transactions
        if (preparedTransactions.contains(transactionID)) {
            // Abort changes and remove transaction ID from list of prepared transactions
            store.abort(transactionID);
            preparedTransactions.remove(transactionID);
            return true;
        } else {
            // Transaction not prepared, abort fails
            return false;
        }
    }


    @Override
    public boolean isReadyToCommit() {
        return false;
    }

    public static void main(String[] args) throws Exception {
        int port = 1234;
        Registry registry = LocateRegistry.createRegistry(port);
        KVSI storeImpl = new KVSI();
        replicas.add(storeImpl);
        for (int i = 1; i < NUM_REPLICAS; i++) {
            String host = "replica" + i + ".example.com";
            KVS replica = (KVS) Naming.lookup("rmi://" + host + ":" + port + "/RmiServer");
            replicas.add(replica);
        }
        registry.bind("RmiServer", storeImpl);
        System.out.println("Server is waiting for the client");

        while (true) {
            executor.execute(new RmiHandler(storeImpl));
        }
    }

    static class RmiHandler implements Runnable {
        private KVSI storeImpl;

        public RmiHandler(KVSI storeImpl) {
            this.storeImpl = storeImpl;
        }

        @Override
        public void run() {
            try {
                KVS stub = (KVS) UnicastRemoteObject.exportObject(storeImpl, 0);
                Registry registry = LocateRegistry.getRegistry("localhost", 1234);
                KVS remoteStore = (KVS) registry.lookup("RmiServer");

                // Invoke a remote method on the KeyValueStore object
                String key = "some_key";
                String value = "some_value";
                remoteStore.put(key, value);

                // You can also invoke other methods, like get() or delete()
                String result = remoteStore.get(key);
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
*/







/*

hi this for me

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KVSI extends UnicastRemoteObject implements KVS {
    private static final long serialVersionUID = 1L;
    private static final int NUM_REPLICAS = 5;
    private static ArrayList<KeyValueStore> replicas = new ArrayList<>();
    private static HashMap<String, String> store = new HashMap<String, String>();
    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    private KVSI() throws RemoteException {
        super();
    }

    @Override
    public synchronized void put(String key, String value) throws RemoteException {
        TwoPhaseCommitProtocol protocol = new TwoPhaseCommitProtocol();

        // Phase 1: Prepare
        for (KeyValueStore replica : replicas) {
            try {
                protocol.prepare(replica);
            } catch (Exception e) {
                protocol.abort(replicas);
                throw new RemoteException("Failed to prepare update on replica", e);
            }
        }

        // Phase 2: Commit
        try {
            store.put(key, value);
            for (KeyValueStore replica : replicas) {
                protocol.commit(replica);
            }
            System.out.println("Key-value pair stored successfully.");
        } catch (Exception e) {
            protocol.abort(replicas);
            throw new RemoteException("Failed to commit update on replica", e);
        }
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
        TwoPhaseCommitProtocol protocol = new TwoPhaseCommitProtocol();

        // Phase 1: Prepare
        for (KeyValueStore replica : replicas) {
            try {
                protocol.prepare(replica);
            } catch (Exception e) {
                protocol.abort(replicas);
                throw new RemoteException("Failed to prepare update on replica", e);
            }
        }

        // Phase 2: Commit
        try {
            String value = store.remove(key);
            if (value == null) {
                System.out.println("No value found for the given key.");
            }
            for (KeyValueStore replica : replicas) {
                protocol.commit(replica);
            }
            System.out.println("The item with key " + key + " is deleted.");
        } catch (Exception e) {
            protocol.abort(replicas);
            throw new RemoteException("Failed to commit update on replica", e);
        }
    }

    @Override
    public boolean prepare(String transactionID) throws RemoteException {
        return false;
    }

    @Override
    public boolean commit(String transactionID) throws RemoteException {
        return false;
    }

    @Override
    public boolean abort(String transactionID) throws RemoteException {
        return false;
    }

    public static void main(String[] args) throws Exception {
        int port = 1234;
        Registry registry = LocateRegistry.createRegistry(port);
        KVSI storeImpl = new KVSI();
        replicas.add(storeImpl);
        for (int i = 1; i < NUM_REPLICAS; i++) {
            String host = "replica" + i + ".example.com";
            KeyValueStore replica = (KeyValueStore) Naming.lookup("rmi://" + host + ":" + port + "/RmiServer");
            replicas.add(replica);
        }
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
                KeyValueStore stub = (KeyValueStore) UnicastRemoteObject.exportObject(storeImpl, 0);
                Registry registry = LocateRegistry.getRegistry("localhost", 1234);
                KeyValueStore remoteStore = (KeyValueStore) registry.lookup("RmiServer");

                // Invoke a remote method on the KeyValueStore object
                String key = "some_key";
                String value = "some_value";
                remoteStore.put(key, value);

                // You can also invoke other methods, like get() or delete()
                String result = remoteStore.get(key);
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
*/