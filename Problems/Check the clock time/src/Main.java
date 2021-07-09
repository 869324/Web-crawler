import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //String regex = "(0[0-9]+|[10-23]+):(0+[0-9]+|[10-59]+)";
        String regex = "(0[0-9]|[1][0-9]|[2][0-3]):(0[0-9]|[1-5][0-9])";


        String time = scanner.nextLine();
        System.out.println(time.matches(regex) ? "YES" : "NO");
    }
}