package mango.ui;

import mango.parser.MangoException;
import mango.parser.MessageFormatter;
import mango.task.Deadline;
import mango.task.Event;
import mango.task.TaskList;
import mango.task.Todo;

public class CommandExecutor {

    /**
     * Executes command based on formatting.
     */
    public static boolean execute(MessageFormatter msg, TaskList manager) throws MangoException {
        switch (msg.getTaskType()) {
        case "bye":
            return true;
        case "list":
            manager.listTasks();
            return false;
        case "mark":
            manager.markTaskAsDone(msg.getMarkedIndex());
            return false;
        case "unmark":
            manager.unmarkTask(msg.getMarkedIndex());
            return false;
        case "todo":
            manager.addTask(new Todo(msg.getTodoDescription()));
            return false;
        case "deadline": {
            String[] d = msg.getDeadlineDescription();
            manager.addTask(new Deadline(d[0], d[1]));
            return false;
        }
        case "event": {
            String[] e = msg.getEventDescription();
            manager.addTask(new Event(e[0], e[1], e[2]));
            return false;
        }
        case "help":
            printHelpMessage();
            return false;
        case "delete":
            manager.deleteTask(msg.getMarkedIndex());
            return false;
        case "find":
            manager.findTask(msg.getFindKeyword());
            return false;
        default:
            throw new MangoException("oh dear, i don't recognize that command.\n"
                    + "use \"help\" to see the list of all commands.");
        }
    }

    /**
     * Prints instructions describing the valid command formats supported by the program.
     */
    private static void printHelpMessage() {
        System.out.println("please follow these guidelines for the possible task inputs:");
        System.out.println("1. todo: todo [taskdetails]");
        System.out.println("2. deadline: deadline [taskdetails] /by [datedetails]");
        System.out.println("3. event: event [taskdetails] /from [startdate] /to [enddate]");
        System.out.println("please follow these guidelines for actions:");
        System.out.println("1. mark/unmark [tasknumber]");
        System.out.println("2. delete [tasknumber]");
        System.out.println("3. find [taskname]");
    }
}