import java.util.Scanner;

public class Mango {

    // Literals defined based on function calls
    private static final int MAX_TASKS = 100;
    private static final int BY_LENGTH = 4;
    private static final int FROM_LENGTH = 6;
    private static final int TO_LENGTH = 4;

    private static Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;
    private static int completedTasks = 0;

    public static void main(String[] args) {
        greetingMessage();
        helpMessage();
        chat();
        goodbyeMessage();
    }

    private static void chat() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        while (!input.equals("bye")) {
            processCommand(input);
            input = in.nextLine();
        }
        in.close();
    }
    /**
     * Parses user input, and early incorrect input detection.
     */

    private static void processCommand(String input) {
        String[] words = input.split(" ", 2);
        String prompt = words[0];

        // First, check if the prompt is 'list' (subsequent message, or lack thereof, is irrelevant)
        if (prompt.equals("list")) {
            printList(tasks, taskCount);
            return;
        }

        // For the remaining commands, return if no message after the prompt command
        boolean noMessage = words.length < 2 || words[1].trim().isEmpty();
        if (noMessage) {
            helpMessage();
            return;
        }

        String message = words[1];
        executeCommand(prompt, message);
    }
    /**
     * Executes the corresponding action based on the command.
     */

    private static void executeCommand(String command, String arguments) {
        switch (command) {
        case "todo":
            addTodo(arguments);
            break;
        case "deadline":
            addDeadline(arguments);
            break;
        case "event":
            addEvent(arguments);
            break;
        case "mark":
            mark(arguments);
            break;
        case "unmark":
            unmark(arguments);
            break;
        default:
            helpMessage();
            break;
        }
    }

    private static void greetingMessage() {
        System.out.println("hey there, i'm mango!" + System.lineSeparator() + "how can i help?");
    }

    private static void goodbyeMessage() {
        System.out.println("bye! hope to see you again!");
    }
    /**
     * Displays formatting requirements for user inputs.
     */

    private static void helpMessage() {
        System.out.println("please follow these guidelines for the possible task inputs:");
        System.out.println("1. todo: todo [taskDetails]");
        System.out.println("2. deadline: deadline [taskDetails] /by [dateDetails]");
        System.out.println("3. event: event [taskDetails] /from [startDate] /to [endDate]");
        System.out.println("please follow these guidelines for marking/unmarking:");
        System.out.println("1. mark/unmark [taskNumber]");
    }

    private static void printList(Task[] tasks, int taskCount) {
        if (taskCount == 0) {
            System.out.println("please add some tasks for us to list :) \n");
            helpMessage();
            return;
        }
        if (completedTasks != 0) {
            System.out.println("hooray! you have completed " + completedTasks + " task(s) :)");
        }
        int incompleteTasks = taskCount - completedTasks;
        if (incompleteTasks != 0) {
            System.out.println("oh dear, you have " + incompleteTasks + " task(s) you MUST complete!");
        }
        System.out.println("here's your task list:");
        for (int i = 1; i <= taskCount; i++) {
            System.out.println(i + ". " + tasks[i - 1]);
        }
    }

    private static void addTodo(String input) {
        if (isFull()) {
            return;
        }
        tasks[taskCount] = new Todo(input);
        taskCount++;
        addTask();
    }

    private static void addDeadline(String input) {
        if (isFull()) {
            return;
        }
        boolean containsDeadline = input.contains("/by");

        // Checks if the keyword '/by' is missing
        if (!containsDeadline) {
            System.out.println("oh dear, you have forgotten to add a deadline");
            helpMessage();
            return;
        }

        // Separates the string into two parts
        int idx = input.indexOf("/by");
        String description = input.substring(0, idx).trim();
        String by = input.substring(idx + BY_LENGTH).trim();

        // Checks if the date to complete by is missing
        if (by.isEmpty()) {
            System.out.println("oh dear, you have forgotten to add a deadline");
            helpMessage();
            return;
        }

        tasks[taskCount] = new Deadline(description, by);
        taskCount++;
        addTask();
    }

    private static void addEvent(String input) {
        if (isFull()) {
            return;
        }

        // Checks if the keyword '/from' or '/to' are missing
        boolean containsFrom = input.contains("/from");
        boolean containsTo = input.contains("/to");
        if (!containsFrom || !containsTo) {
            System.out.println("oh dear, you have forgotten to add the start or end date");
            helpMessage();
            return;
        }

        // Computes index of 'from' and 'to'
        int idx1 = input.indexOf("/from");
        int idx2 = input.indexOf("/to");

        // Returns early if 'to' is before 'from'
        if (idx1 >= idx2) {
            System.out.println("oh dear, /from should come before /to");
            return;
        }

        // Separates the string into its constituent parts
        String description = input.substring(0, idx1).trim();
        String from = input.substring(idx1 + FROM_LENGTH, idx2).trim();
        String to = input.substring(idx2 + TO_LENGTH).trim();

        // Returns if any of the field descriptions are missing
        if (from.isEmpty() || to.isEmpty()) {
            System.out.println("oh dear, you have forgotten to add the start or end date");
            helpMessage();
            return;
        }

        tasks[taskCount] = new Event(description, from, to);
        taskCount++;
        addTask();
    }

    private static void addTask() {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks[taskCount - 1]);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    private static boolean isFull() {
        if (taskCount >= MAX_TASKS) {
            System.out.println("sorry.. i cannot add anymore tasks!");
            return true;
        }
        return false;
    }

    private static void mark(String input) {
        int index = isValidIndex(input);
        if (index == -1) {
            return;
        }
        if (tasks[index].isDone()) {
            System.out.println("this task is already marked as done!");
        } else {
            tasks[index].markAsDone();
            System.out.println("hooray! you've completed this task. i've marked it as done: ");
            System.out.println(tasks[index]);
            completedTasks++;
        }
    }

    private static void unmark(String input) {
        int index = isValidIndex(input);
        if (index == -1) {
            return;
        }
        if (!tasks[index].isDone()) {
            System.out.println("this task is already unmarked!");
        } else {
            tasks[index].unmark();
            System.out.println("oh dear, it seems you have NOT completed this task. i've unmarked it: ");
            System.out.println(tasks[index]);
            completedTasks--;
        }
    }

    private static int isValidIndex(String input) {
        try {
            int index = Integer.parseInt(input) - 1;
            if (index < 0 || index >= taskCount) {
                System.out.println("invalid task number :(");
                return -1;
            }
            return index;
        } catch (NumberFormatException exception) {
            System.out.println("please input a number in the valid task range. try again");
            return -1;
        }
    }
}