import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        String input;
        while (true){
            input = sc.nextLine();
            if(input.equals("0")){
                break;
            }
            else {
                try{
                    System.out.println(Integer.parseInt(input) * 10);
                }catch (NumberFormatException e){
                    System.out.println("Invalid user input: " + input);
                }
            }
        }
    }
}