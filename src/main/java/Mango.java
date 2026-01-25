import java.util.Scanner;

public class Mango {
    public static void printList(String[] data, int currIndex) {
        for(int i=1; i<=currIndex; i++) {
            System.out.println(i + ". " + data[i-1]);
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] data = new String[100];
        int currIndex = 0;
        System.out.println("hey there, i'm mango!" + System.lineSeparator() + "how can i help?");
        String input = in.nextLine();
        while(!input.equals("bye")) {
            if(input.equals("list")) printList(data, currIndex);
            else {
                System.out.println("added: " + input);
                data[currIndex] = input;
                currIndex++;
            }
            input = in.nextLine();
        }
        System.out.println("bye! hope to see you again");
        in.close();
    }
}
