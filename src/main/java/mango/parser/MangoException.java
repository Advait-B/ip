package mango.parser;

/**
 * Represents specific exceptions for invalid user inputs or task operations.
 */
public class MangoException extends Exception {

    public MangoException(String taskType) {
        super(getErrorMessage(taskType));
    }

    /**
     * Returns a user-friendly error message corresponding to the specified error key.
     *
     * @param taskType Error scenario.
     * @return Error message to display to the user.
     */
    private static String getErrorMessage(String taskType) {
        if (taskType == null || taskType.isEmpty()) {
            return "oh dear, no command entered. use \"help\" to see available commands.";
        }
        switch (taskType) {
        case "null input":
            return "please type in a command or use \"help\" to see the list of commands.";
        case "todo":
            return "oh dear, invalid todo command. please follow these guidelines:\n"
                    + "todo: todo [taskdetails]";
        case "deadline":
            return "oh dear, invalid deadline command. please follow these guidelines:\n"
                    + "deadline: deadline [taskdetails] /by [datedetails]";
        case "event":
            return "oh dear, invalid event command. please follow these guidelines:\n"
                    + "event: event [taskdetails] /from [startdate] /to [enddate]";
        case "delete":
            return "oh dear, invalid delete. please follow these guidelines:\n"
                    + "delete: delete [tasknumber]";
        case "find":
            return "oh dear, invalid find command. please follow these guidelines:\n"
                    + "find: find [keyword]";
        case "mark":
            return "oh dear, invalid mark command. please follow these guidelines:\n"
                    + "mark: mark [tasknumber]";
        case "unmark":
            return "oh dear, invalid unmark command. please follow these guidelines:\n"
                    + "unmark: unmark [tasknumber]";
        case "task limit":
            return "sorry.. i cannot add anymore tasks.";
        case "invalid index":
            return "oh dear, that task number doesn't exist.";
        case "not a number":
            return "oh dear, please enter a valid task number.";
        case "already marked":
            return "it looks like that task is already marked as done :)";
        case "already unmarked":
            return "it looks like that task is already marked as not done :)";
        default:
            return "oh dear, i don't recognize that command.\n"
                    + "use \"help\" to see the list of all commands.";
        }
    }
}