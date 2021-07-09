import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Main {
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         Map<Integer, String> map = new TreeMap<>();
         String range = scanner.nextLine();
         String inputs = scanner.nextLine();

         for (int i=0; i<Integer.parseInt(inputs); i++){
             String pair = scanner.nextLine();
             Scanner sc = new Scanner(pair);
             int key = sc.nextInt();
             String value = sc.next();
             map.put(key, value);
         }

         Scanner sc = new Scanner(range);
         int lowRange = sc.nextInt();
         int highRange = sc.nextInt();

         for (var pair : map.entrySet()){
             int key = pair.getKey();
             String value = pair.getValue();
             if (key >= lowRange && key <= highRange){
                 System.out.println(key +" " + value);
             }
         }
    }
}