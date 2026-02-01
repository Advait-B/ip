import java.util.Scanner;

public class Mango {

    private static final int MAX_TASKS = 100;

    public static void printList(Task[] tasks, int taskCount) {
        int undoneCount = 0;
        for (int i = 0; i < taskCount; i++) {
            if (!tasks[i].isDone()) {
                undoneCount++;
            }
        }
        if (taskCount - undoneCount != 0) {
            System.out.println("hooray! you have completed " + (taskCount - undoneCount) + " task(s) :)");
        }
        if (undoneCount != 0) {
            System.out.println("oh dear, you have " + undoneCount + " task(s) you MUST complete!");
        }
        for (int i = 1; i <= taskCount; i++) {
            System.out.println(i + ". " + tasks[i - 1]);
        }
    }

    public static void markAsDone(Task[] tasks, int taskCount, int index) {
        if (index < 0 || index >= taskCount) {
            System.out.println("invalid task number :(");
            return;
        }
        if (tasks[index].isDone()) {
            System.out.println("this task is already marked as done!");
        } else {
            tasks[index].markAsDone();
            System.out.println("hooray! you've completed this task. i've marked it as done: ");
            System.out.println(tasks[index]);
        }
    }

    public static void unmark(Task[] tasks, int taskCount, int index) {
        if (index < 0 || index >= taskCount) {
            System.out.println("invalid task number :(");
            return;
        }
        if (!tasks[index].isDone()) {
            System.out.println("this task is already unmarked!");
        } else {
            tasks[index].unmark();
            System.out.println("oh dear, it seems you have NOT completed this task. i've unmarked it: ");
            System.out.println(tasks[index]);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        System.out.println("hey there, i'm mango!" + System.lineSeparator() + "how can i help?");
        String input = in.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                printList(tasks, taskCount);
            } else if (input.startsWith("mark ")) {
                try {
                    input = input.substring(input.indexOf(" ") + 1).trim();
                    int index = Integer.parseInt(input) - 1;
                    markAsDone(tasks, taskCount, index);
                } catch (NumberFormatException exception) {
                    System.out.println("please input a number in the valid task range. try again");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    input = input.substring(input.indexOf(" ") + 1).trim();
                    int index = Integer.parseInt(input) - 1;
                    unmark(tasks, taskCount, index);
                } catch (NumberFormatException exception) {
                    System.out.println("please input a number in the valid task range. try again");
                }
            } else {
                if (taskCount >= MAX_TASKS) {
                    System.out.println("sorry.. i cannot add anymore tasks!");
                } else if (input.trim().isEmpty()) {
                    System.out.println("task description cannot be empty. try again");
                } else {
                    Task t = new Task(input);
                    tasks[taskCount] = t;
                    taskCount++;
                    System.out.println("added: " + t.getDescription());
                }
            }
            input = in.nextLine();
        }
        System.out.println("bye! hope to see you again!");
        in.close();
    }
}