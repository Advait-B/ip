package mango.ui;

import mango.storage.Storage;
import mango.task.TaskList;
import mango.parser.MessageFormatter;
import mango.parser.MangoException;

import java.util.Scanner;

/**
 * Entry point for the Mango chatbot application.
 * Handles the main program flow and delegates command execution.
 */
public class Mango {

    private static final String DIVIDER = "------------------------------";

    public static void main(String[] args) {
        printGreetingMessage();
        chat();
        printGoodbyeMessage();
    }

    /**
     * Reads user input and executes commands until an exit command is received.
     */
    private static void chat() {
        Storage storage = new Storage();
        TaskList manager = new TaskList(storage);
        Scanner in = new Scanner(System.in);

        while (true) {
            String userInput = in.nextLine().trim();
            System.out.println(DIVIDER);
            try {
                MessageFormatter msg = new MessageFormatter(userInput);
                boolean isExit = CommandExecutor.execute(msg, manager);
                if (isExit) {
                    in.close();
                    return;
                }
                System.out.println(DIVIDER);
            } catch (MangoException e) {
                System.out.println(e.getMessage());
                System.out.println(DIVIDER);
            }
        }
    }

    private static void printGreetingMessage() {
        System.out.println("hey there, i'm mango!");
        System.out.println("how can i help?");
        System.out.println(DIVIDER);
    }

    private static void printGoodbyeMessage() {
        System.out.println("bye! hope to see you again!");
        System.out.println(DIVIDER);
    }
}