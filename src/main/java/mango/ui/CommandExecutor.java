package mango.ui;

import mango.parser.MangoException;
import mango.parser.MessageFormatter;
import mango.task.Deadline;
import mango.task.Event;
import mango.task.TaskList;
import mango.task.Todo;

/**
 * Executes user commands by dispatching to the appropriate task operations.
 */
public class CommandExecutor {

    /**
     * Executes the command based on the user input.
     *
     * @param msg Parsed user input containing the command type and arguments.
     * @param manager Task list to operate on.
     * @return True if the command should terminate the program; False otherwise.
     * @throws MangoException If the command is unknown or if the command arguments are invalid.
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
     * Prints a summary of supported command formats.
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