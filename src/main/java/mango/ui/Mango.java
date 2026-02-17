package mango.ui;

import mango.task.Deadline;
import mango.task.Event;
import mango.task.TaskManager;
import mango.task.Todo;
import mango.parser.MessageFormatter;
import mango.parser.MangoException;

import java.util.Scanner;

public class Mango {
    public static void main(String[] args) {
        printGreetingMessage();
        chat();
    }

    private static void chat() {
        TaskManager manager = new TaskManager();
        Scanner in = new Scanner(System.in);

        while (true) {
            String userInput = in.nextLine().trim();
            try {
                MessageFormatter msg = new MessageFormatter(userInput);
                switch (msg.getTaskType()) {
                case "bye":
                    in.close();
                    printGoodbyeMessage();
                    return;
                case "list":
                    manager.listTasks();
                    break;
                case "mark":
                    manager.markTaskAsDone(msg.getMarkedIndex());
                    break;
                case "unmark":
                    manager.unmarkTask(msg.getMarkedIndex());
                    break;
                case "todo":
                    manager.addTask(new Todo(msg.getTodoDescription()));
                    break;
                case "deadline":
                    String[] d = msg.getDeadlineDescription();
                    manager.addTask(new Deadline(d[0], d[1]));
                    break;
                case "event":
                    manager.addTask(new Event(msg.getEventDescription()[0],
                            msg.getEventDescription()[1],
                            msg.getEventDescription()[2]));
                    break;
                case "help":
                    printHelpMessage();
                    break;
                case "delete":
                    manager.deleteTask(msg.getMarkedIndex());
                    break;
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

    private static void printHelpMessage() {
        System.out.println("please follow these guidelines for the possible task inputs:");
        System.out.println("1. todo: todo [taskdetails]");
        System.out.println("2. deadline: deadline [taskdetails] /by [datedetails]");
        System.out.println("3. event: event [taskdetails] /from [startdate] /to [enddate]");
        System.out.println("please follow these guidelines for marking/unmarking/deleting:");
        System.out.println("1. mark/unmark [tasknumber]");
        System.out.println("2. delete [tasknumber]");
    }
}