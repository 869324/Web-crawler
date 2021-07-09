import java.util.*;

class Main {
    public static void main(String[] args) {
        // put your code here
        boolean status = true;
        Map<Character, Character> braces = Map.of('[',']', '{','}', '(',')');
        Deque<Character> stack = new ArrayDeque<>();
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        for (int i=0; i<line.length(); i++){
            Character chr = line.charAt(i);
            if (braces.containsKey(chr)){
                stack.offerLast(chr);
            }
            else if(braces.containsValue(chr)){
                if (!stack.isEmpty()) {
                    if (braces.get(stack.peekLast()).equals(chr)) {
                        stack.pollLast();
                    } else {
                        status = false;
                    }
                }
                else {
                    status = false;
                }

            }
        }
        if (!stack.isEmpty()){
            status = false;
        }
        System.out.println(status);
    }
}