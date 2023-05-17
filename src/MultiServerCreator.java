import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class MultiServerCreator {
    public static void main(String[] args) {
        try{
            KVSI server1 = new KVSI();
            KVSI server2 = new KVSI();
            KVSI server3 = new KVSI();
            KVSI server4 = new KVSI();
            KVSI server5 = new KVSI();

            //RMI registry
            Registry registry= LocateRegistry.createRegistry(1234);

            //binding the servers to registry
            registry.bind("server1",server1);
            registry.bind("server2",server2);
            registry.bind("server3",server3);
            registry.bind("server4",server4);
            registry.bind("server5",server5);
            List<KVS> serverList= new ArrayList<>();
            serverList.add(server1);
            serverList.add(server2);
            serverList.add(server3);
            serverList.add(server4);
            serverList.add(server5);
            // new coordinator object and bind it to the RMI registry
            Coordinator coordinator=new Coordinator(serverList);
            registry.bind("coordinator",coordinator);

            System.out.println("KVS server is running");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
