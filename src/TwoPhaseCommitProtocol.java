
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
/*
public class TwoPhaseCommitProtocol {
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

}



*/

/*

    public boolean prepare(String replicaUrl) {
        try {
            KVS replica = (KVS) Naming.lookup(replicaUrl);
            boolean isPrepared = replica.prepareTransaction(transactionId, updates);
            prepareResponses.add(isPrepared);
            return isPrepared;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean commit(String replicaUrl) {
        try {
            KVS replica = (KVS) Naming.lookup(replicaUrl);
            boolean isCommitted = replica.commitTransaction(transactionId);
            commitResponses.add(isCommitted);
            return isCommitted;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean abort(String replicaUrl) {
        try {
            KVS replica = (KVS) Naming.lookup(replicaUrl);
            boolean isAborted = replica.abortTransaction(transactionId);
            abortResponses.add(isAborted);
            return isAborted;
        } catch (Exception e) {
            return false;
        }
    }


    private boolean isReadyToCommit(ArrayList<Boolean> prepareResponses) {
        for (Boolean response : prepareResponses) {
            if (!response) {
                // at least one replica responded with "not ready"
                return false;
            }
        }
        // all replicas responded with "ready"
        return true;
    }

 */