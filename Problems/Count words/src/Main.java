import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder line = new StringBuilder();
        String chars = null;
        while ((chars = reader.readLine()) != null) {
            line.append(chars);
        }
        reader.close();
        int counter = 1;
        String words = line.toString().trim();
        for (int i=0; i < words.length(); i++){
            if (words.charAt(i) == ' ' && words.charAt(i+1) != ' '){
                counter += 1;
            }

        }

        if (counter == 1 ){
            System.out.println(0);
        }
        else {
            System.out.println(counter);
        }
    }
}