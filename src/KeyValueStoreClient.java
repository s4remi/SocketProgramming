import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


import java.util.Arrays;
import java.util.Scanner;

public class KeyValueStoreClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1234;
    static String command;
    static String key,value;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            //String[] boundObjects = registry.list();
            //System.out.println("Bound objects: " + Arrays.toString(boundObjects));
            System.out.println(" please write the host name");
            //String  server_id= scanner.nextInt();
            String server_name=scanner.nextLine();
            server_name.toLowerCase().trim();
            KeyValueStore stub = (KeyValueStore) registry.lookup(server_name);
            //KVS stub = (KVS) registry.lookup(server_name);
            //Scanner scanner = new Scanner(System.in);

            // Pre-populate the Key-Value store with data
            stub.put("ADAM","44");
            stub.put("BOB","4");
            stub.put("LI","4664");
            stub.put("JOY","88");
            stub.put("JOSH","656");

            // Get values for specific keys
            System.out.println("Getting values for specific keys...");
            System.out.println("Value for ADAM is: " + stub.get("ADAM"));
            System.out.println("Value for BOB is: " + stub.get("BOB"));
            System.out.println("Value for LI is: " + stub.get("LI"));
            System.out.println("Value for JOY is: " + stub.get("JOY"));
            System.out.println("Value for JOSH is: " + stub.get("JOSH"));

            // Delete values for specific keys
            System.out.println("Deleting values for specific keys...");
            stub.delete("ADAM");
            System.out.println("Value ADAM is deleted " );
            stub.delete("BOB");
            System.out.println("Value BOB is deleted " );
            stub.delete("LI");
            System.out.println("Value LI is deleted " );
            stub.delete("JOY");
            System.out.println("Value JOY is deleted " );
            stub.delete("JOSH");
            System.out.println("Value JOSH is deleted " );

            while (true) {
                System.out.print("Enter command: ");
                String input = scanner.nextLine();
                String[] tokens = input.split(",");
                if(tokens.length == 2){
                    command = tokens[0].trim();
                    key= tokens[1].trim();
                }else if(tokens.length==3){
                    command=tokens[0].trim();
                    key=tokens[1].trim();
                    value=tokens[2].trim();
                }
                if (command.equalsIgnoreCase("put")) {
                    // put command implementation
                    stub.put(key,value);
                } else if (command.equalsIgnoreCase("get")) {
                    // get command implementation
                    String result=stub.get(key);
                    System.out.println(result);
                } else if (command.equalsIgnoreCase("delete")) {
                    // delete command implementation
                    stub.delete(key);
                } else {
                    System.out.println("Invalid command.");
                }
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}

