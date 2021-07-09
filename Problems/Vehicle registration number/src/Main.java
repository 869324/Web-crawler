import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regNum = scanner.nextLine(); // a valid or invalid registration number

        /* write your code here */
        String regex = "[ABEKMHOPCTYX]\\d{3}[ABEKMHOPCTYX][ABEKMHOPCTYX]";
        System.out.println(regNum.matches(regex));
    }
}