import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        Long num1 = sc.nextLong();
        String operator = sc.next();
        Long num2 = sc.nextLong();

        switch (operator){
            case "+":
                System.out.println(num1 + num2);
                break;
            case "-":
                System.out.println(num1 - num2);
                break;
            case "/":
                try{
                    System.out.println(num1 / num2);
                }catch (Exception ex){
                    System.out.println("Division by 0!");
                }
                break;
            case "*":
                System.out.println(num1 * num2);
                break;
            default:
                System.out.println("Unknown operator");
        }
    }
}