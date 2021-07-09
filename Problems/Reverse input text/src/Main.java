import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here
        ArrayList<Character> array = new ArrayList<>();
        int chars = reader.read();
        while(chars != -1){
            array.add((char)chars);
            chars = reader.read();
        }
        reader.close();

        for (int i=array.size() - 1; i >= 0; i--){
            System.out.print(array.get(i));
        }
    }
}