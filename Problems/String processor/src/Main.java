import java.util.ArrayList;
import java.util.Scanner;

class StringProcessor extends Thread {

    final Scanner scanner = new Scanner(System.in); // use it to read string from the standard input

    @Override
    public void run() {
        // implement this method
        ArrayList<String> array = new ArrayList<>();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (!line.equals(line.toUpperCase())){
                array.add(line.toUpperCase());
            }
            else {
                array.add("FINISHED");
                break;
            }
        }
        for (String line: array){
            System.out.println(line);
        }
    }
}