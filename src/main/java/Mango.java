import java.util.Scanner;

public class Mango {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("hey there, i'm mango!" + System.lineSeparator() + "how can i help?");
        String input = in.nextLine();
        while(!input.equals("bye")) {
            System.out.println(input);
            input = in.nextLine();
        }
        System.out.println("bye! hope to see you again");
    }
}
