import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RMI_client {
    public static void main(String[] args) {
        try {
            Sort sortt = (Sort) Naming.lookup("rmi://localhost/Sort");
            Scanner sc = new Scanner(System.in);
            List<Integer> list = new ArrayList<>();
            System.out.println("Enter 10 integers: ");
            for (int i = 0; i < 10; i++) {
                list.add(sc.nextInt());
            }
            System.out.println("the original list: "+ list);
            List<Integer> sortedList = sortt.sort(list);
            System.out.println("Sorted list: ");
            for (int i : sortedList) {
                System.out.print(i + " ");
            }
        } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
