import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<String> dictionary = new HashSet<>();
        Set<String>  words = new HashSet<>();
        int dicNum = Integer.parseInt(scanner.nextLine());
        for (int i=0; i < dicNum; i++){
            dictionary.add(scanner.nextLine().toLowerCase());
        }

        int inputs = Integer.parseInt(scanner.nextLine());
        for (int i=0; i<inputs; i++){
            words.addAll(List.of(scanner.nextLine().toLowerCase().split("\\s+")));
        }

        for (String word : words){
            if (!dictionary.contains(word)){
                System.out.println(word);
            }
        }

    }
}