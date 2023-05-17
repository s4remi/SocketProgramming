import java.util.ArrayList;
import java.util.List;

public class LomutoPartition {
    int count=0;
    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        int count=0;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                count++;
                System.out.println("the count is : "+ count);
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                for (int index=0;index< arr.length;index++){
                    System.out.printf(String.valueOf(arr[index]));
                    // System.out.println(arr[index]);
                }
                System.out.println(" i is: "+i+" j is : "+j);
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        for (int index=0;index< arr.length;index++){
            System.out.printf(String.valueOf(arr[index]));
           // System.out.println(arr[index]);
        }


        return i + 1;
    }

    public static void main(String[] args) {
        //int[] arr = {3, 1, 5, 7, 6, 2, 4};
        int[] arr = {1,2,3,4,5,6,7};
        System.out.println("the orginal arr is : ");
        for(Integer aa:arr){
            System.out.printf(String.valueOf(aa)+" ");
        }
        int high= arr.length-1;
        System.out.println("\nhigh is: "+high);
        System.out.println("here");
        partition(arr,0,high);

    }
}
