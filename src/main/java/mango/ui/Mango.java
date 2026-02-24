package mango.ui;

import mango.storage.Storage;
import mango.task.TaskList;
import mango.parser.MessageFormatter;
import mango.parser.MangoException;

import java.util.Scanner;

public class Mango {
    public static void main(String[] args) {
        printGreetingMessage();
        chat();
        printGoodbyeMessage();
    }

    /**
     * Reads user input and executes commands.
     */
    private static void chat() {
        Storage storage = new Storage();
        TaskList manager = new TaskList(storage);
        Scanner in = new Scanner(System.in);

        while (true) {
            String userInput = in.nextLine().trim();
            try {
                MessageFormatter msg = new MessageFormatter(userInput);
                boolean isExit = CommandExecutor.execute(msg, manager);
                if (isExit) {
                    in.close();
                    return;
                }
            } catch (MangoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void printGreetingMessage() {
        System.out.println("hey there, i'm mango!");
        System.out.println("how can i help?");
    }

    private static void printGoodbyeMessage() {
        System.out.println("bye! hope to see you again!");
    }
}