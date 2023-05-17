import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Coordinator extends UnicastRemoteObject implements CoordinatorInterface {
    private final List<KVS> serverList;
    private final HashMap<String,Boolean> transactionStatus;
    public Coordinator(List<KVS>serverList)throws RemoteException {
        super();
        this.serverList=serverList;
        this.transactionStatus= new HashMap<>();
    }
    public String put(String key,String value)throws RemoteException{
        String transactionId= UUID.randomUUID().toString();
        boolean success= true;

        //phase 1 prepare
        for(KVS server: serverList){
            try{
                success &= server.prepare(transactionId);
            }catch(RemoteException e){
                e.printStackTrace();
                success=false;
            }
        }
        //phase2 for commit and abort
        if(success){
            for(KVS server:serverList){
                try{
                    server.commit(transactionId);
                    server.put(key,value);
                }catch(RemoteException e){
                    e.printStackTrace();
                }
            }
            transactionStatus.put(transactionId,true);
        }else{
            for(KVS server:serverList){
                try{
                    server.abort(transactionId);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
            transactionStatus.put(transactionId,false);
        }
        return transactionId;
    }
    public  String delete(String key) throws RemoteException{
        boolean success=true;
        String transactionId=UUID.randomUUID().toString();
        //phase1
        for(KVS server:serverList){
            try {
                success &= server.prepare(transactionId);
            }catch(RemoteException e){
                e.printStackTrace();
                success=false;
            }
        }
        //phase2
        if (success) {
            for(KVS server:serverList){
                try{
                    server.commit(transactionId);
                    server.delete(key);
                }catch(RemoteException e){
                    e.printStackTrace();
                }
            }
            transactionStatus.put(transactionId,true);
        }else{
            for(KVS server:serverList){

            }
            transactionStatus.put(transactionId,false);
        }
        return transactionId;
    }

    public boolean getTransactionStatus(String transactionId){
        return transactionStatus.getOrDefault(transactionId,false);

    }
}
