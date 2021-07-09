import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        Deque<Integer> queue = new ArrayDeque<>();
        int num = Integer.parseInt(sc.nextLine());
        for (int i=0; i<num; i++){
            int elem = Integer.parseInt(sc.nextLine());
            if ((elem % 2) == 0){
                queue.offerFirst(elem);
            }
            else {
                queue.offerLast(elem);
            }
        }
        while (queue.size() != 0){
            System.out.println(queue.pollFirst());
        }
    }
}