import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KVSC{
    private static int serverIndex;
    private final List<KVS> serveList;
    private final CoordinatorInterface coordinator;
    public KVSC() throws Exception {
        serveList= new ArrayList<>();
        Registry registry = LocateRegistry.getRegistry("localhost", 1234);
        serveList.add((KVS) registry.lookup("server1"));
        serveList.add((KVS) registry.lookup("server2"));
        serveList.add((KVS) registry.lookup("server3"));
        serveList.add((KVS) registry.lookup("server4"));
        serveList.add((KVS) registry.lookup("server5"));
        //connect to the coordinator
        coordinator = (CoordinatorInterface) registry.lookup("coordinator");
    }
    public String get(String key,int serverIndex){
        try{
            KVS server= serveList.get(serverIndex);
            return server.get(key);
            //return coordinator.getTransactionStatus()

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        //return null;
    }
    public boolean put(String key, String value, int serverIndex){
        try{
            String transactionId = coordinator.put(key,value);
            KVS server= serveList.get(serverIndex);
            server.commit(transactionId);
            return coordinator.getTransactionStatus(transactionId);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(String key,int serverIndex){
        try{
            String transactionId= coordinator.delete(key);
            KVS server= serveList.get(serverIndex);
            server.commit(transactionId);
            return coordinator.getTransactionStatus(transactionId);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        try{
            KVSC client= new KVSC();
            System.out.println("put key1,value1 on server1");
            client.put("ali","22",0);
            System.out.println("get key 1 from server 1 after delete "+ client.get("ali",0));
            System.out.println("delete ali from server1");
            client.delete("ali",0);
            System.out.println("get key 1 from server 1 after delete "+ client.get("ali",0));
            // Pre-populate the Key-Value store with data
            client.put("ADAM","44",0);
            client.put("BOB","4",0);
            client.put("LI","4664",0);
            client.put("JOY","88",0);
            client.put("JOSH","656",0);
            // Get values for specific keys
            System.out.println("Getting values for specific keys...");
            System.out.println("Value for ADAM is: " + client.get("ADAM",0));
            System.out.println("Value for BOB is: " + client.get("BOB",0));
            System.out.println("Value for LI is: " + client.get("LI",0));
            System.out.println("Value for JOY is: " + client.get("JOY",0));
            System.out.println("Value for JOSH is: " + client.get("JOSH",0));
            // Delete values for specific keys
            System.out.println("Deleting values for specific keys...");
            client.delete("ADAM",0);
            System.out.println("Value ADAM is deleted " );
            client.delete("BOB",0);
            System.out.println("Value BOB is deleted " );
            client.delete("LI",0);
            System.out.println("Value LI is deleted " );
            client.delete("JOY",0);
            System.out.println("Value JOY is deleted " );
            client.delete("JOSH",0);
            System.out.println("Value JOSH is deleted " );
            Scanner scanner = new Scanner(System.in);
            String command="";
            String key="";
            String value="";

            while (true) {
                System.out.print("Enter command: ");
                String input = scanner.nextLine();
                String[] tokens = input.split(",");
                if(tokens.length == 3){
                    command = tokens[0].trim();
                    key= tokens[1].trim();
                    serverIndex=Integer.valueOf(tokens[2].trim());

                }else if(tokens.length==4){
                    command=tokens[0].trim();
                    key=tokens[1].trim();
                    value=tokens[2].trim();
                    serverIndex=Integer.valueOf(tokens[3].trim());
                }
                if (command.equalsIgnoreCase("put")) {
                    // put command implementation
                    client.put(key,value,1);
                } else if (command.equalsIgnoreCase("get")) {
                    // get command implementation
                    String result=client.get(key,1);
                    System.out.println(result);
                } else if (command.equalsIgnoreCase("delete")) {
                    // delete command implementation
                    client.delete(key,0);
                } else {
                    System.out.println("Invalid command.");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
