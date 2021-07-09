import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static boolean isVowel(char ch) {
        // write your code here
        boolean status = false;
        Character[] vowelsList = {'a', 'e', 'i', 'o', 'u','A', 'E', 'I', 'O', 'U'};
        HashSet<Character> vowels = new HashSet<>(Arrays.asList(vowelsList));
        if (vowels.contains(ch)){
            status = true;
        }
        return status;
    }

    /* Do not change code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char letter = scanner.nextLine().charAt(0);
        System.out.println(isVowel(letter) ? "YES" : "NO");
    }
}