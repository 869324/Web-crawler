import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;

class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine().toLowerCase();
        String line2 = scanner.nextLine().toLowerCase();

        Map<Character, Integer> letters1 = new HashMap<>();

        for (int i=0; i<line1.length(); i++){
            char letter = line1.charAt(i);

            if (letters1.containsKey(letter)){
                letters1.put(letter, letters1.get(letter) + 1);
            }

            else{
                letters1.put(line1.charAt(i), 1);
            }
        }

        Map<Character, Integer> letters2 = new HashMap<>();

        for (int i=0; i<line2.length(); i++){
            char letter = line2.charAt(i);

            if (letters2.containsKey(letter)){
                letters2.put(letter, letters2.get(letter) + 1);
            }

            else{
                letters2.put(line2.charAt(i), 1);
            }
        }

        int counter = 0;

        for (var pair : letters2.entrySet()){
            char key = pair.getKey();
            int value = pair.getValue();
            int value2 = 0;

            if (letters1.containsKey(key)){
                value2 = letters1.get(key);
            }
            
            if (value2 == value){
                letters1.remove(key);
            }
            
            else if (value2 > value){
                int diff = value2 - value;
                letters1.put(key, diff);
            }

            else {
                int diff = value - value2;
                letters1.remove(key);
                counter += diff;
            }

        }

        for (int num : letters1.values()){
            counter += num;
        }

        System.out.println(counter);

    }
}