import java.util.Scanner;

public class Mango {

    public static void printList(Task[] tasks, int taskCount) {
        int undoneCount = 0;
        for (int i = 0; i < taskCount; i++) {
            if (!tasks[i].isDone) undoneCount++;
        }
        if (taskCount - undoneCount != 0) System.out.println("hooray! you have done " + (taskCount - undoneCount) + " task(s) :)");
        System.out.println("BUT oh dear, you have " + undoneCount + " task(s) you MUST complete!");
        for (int i = 1; i <= taskCount; i++) {
            System.out.println(i + ". " + tasks[i - 1]);
        }
    }
    public static void markAsDone(Task[] tasks, int taskCount, int index) {
        if (index < 0 || index >= taskCount) {
            System.out.println("invalid task number... don't scam me :(");
            return;
        }
        tasks[index].markAsDone();
    }
    public static void unmark(Task[] tasks, int taskCount, int index) {
        if (index < 0 || index >= taskCount) {
            System.out.println("invalid task number... don't scam me :(");
            return;
        }
        tasks[index].unmark();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("hey there, i'm mango!" + System.lineSeparator() + "how can i help?");
        String input = in.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                printList(tasks, taskCount);
            } else if (input.startsWith("mark ")) {
                input = input.substring(input.indexOf(" ") + 1);
                int index = Integer.parseInt(input) - 1;
                markAsDone(tasks, taskCount, index);
            } else if (input.startsWith("unmark ")) { // FIXED
                input = input.substring(input.indexOf(" ") + 1);
                int index = Integer.parseInt(input) - 1;
                unmark(tasks, taskCount, index);
            } else {
                Task t = new Task(input);
                tasks[taskCount] = t;
                taskCount++;
                System.out.println("added: " + t.getDescription());
            }
            input = in.nextLine();
        }
        System.out.println("bye! hope to see you again!");
        in.close();
    }
}
