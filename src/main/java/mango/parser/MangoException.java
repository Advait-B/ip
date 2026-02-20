package mango.parser;

public class MangoException extends Exception {
    public MangoException(String taskType) {
        super(getErrorMessage(taskType));
    }

    /**
     * Returns an appropriate error message corresponding to the specified error type.
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
        case "mark":
            return "oh dear, invalid mark command. please follow these guidelines:\n"
                    + "mark: mark [tasknumber]";
        case "unmark":
            return "oh dear, invalid unmark command. please follow these guidelines:\n"
                    + "unmark: unmark [tasknumber]";
        case "task limit":
            return "sorry.. i cannot add anymore tasks.";
        case "invalid index":
            return "invalid task number.";
        case "not a number":
            return "please input a number in the valid task range. try again.";
        case "already marked":
            return "this task is already marked as done.";
        case "already unmarked":
            return "this task is already unmarked.";
        default:
            return "oh dear, i don't recognize that command.\n"
                    + "use \"help\" to see the list of all commands.";
        }
    }
}