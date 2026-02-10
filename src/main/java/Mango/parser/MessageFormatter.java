package Mango.parser;

public class MessageFormatter {

    private static final int BY_LENGTH = 3;
    private static final int FROM_LENGTH = 5;
    private static final int TO_LENGTH = 3;

    protected String taskType;
    protected String message;

    public MessageFormatter(String userInput) throws MangoException {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new MangoException("null input");
        }
        String[] words = userInput.split(" ", 2);
        taskType = words[0].toLowerCase();
        message = words.length > 1 ? words[1] : "";
        validateCommand();
    }

    private void validateCommand() throws MangoException {
        switch (taskType) {
        case "list":
        case "bye":
        case "help":
        case "todo":
        case "deadline":
        case "event":
        case "mark":
        case "unmark":
            return;
        default:
            throw new MangoException(taskType);
        }
    }

    public String getTaskType() {
        return taskType;
    }

    public String getTodoDescription() throws MangoException {
        if (message.trim().isEmpty()) {
            throw new MangoException("todo");
        }
        return message;
    }

    public String[] getDeadlineDescription() throws MangoException {
        boolean containsDeadline = message.contains("/by");
        if (!containsDeadline) {
            throw new MangoException("deadline");
        }

        int idx = message.indexOf("/by");
        String description = message.substring(0, idx).trim();
        String by = message.substring(idx + BY_LENGTH).trim();

        if (description.isEmpty()) {
            throw new MangoException("deadline");
        }

        if (by.isEmpty()) {
            throw new MangoException("deadline");
        }

        return new String[]{description, by};
    }

    public String[] getEventDescription() throws MangoException {
        boolean containsFrom = message.contains("/from");
        boolean containsTo = message.contains("/to");
        if (!containsFrom || !containsTo) {
            throw new MangoException("event");
        }

        int idx1 = message.indexOf("/from");
        int idx2 = message.indexOf("/to");

        if (idx1 >= idx2) {
            throw new MangoException("event");
        }

        if (idx1 + FROM_LENGTH >= idx2) {
            throw new MangoException("event");
        }

        String description = message.substring(0, idx1).trim();
        String from = message.substring(idx1 + FROM_LENGTH, idx2).trim();
        String to = message.substring(idx2 + TO_LENGTH).trim();

        if (description.isEmpty()) {
            throw new MangoException("event");
        }

        if (from.isEmpty() || to.isEmpty()) {
            throw new MangoException("event");
        }

        return new String[]{description, from, to};
    }

    public int getMarkedIndex() throws MangoException {
        try {
            int index = Integer.parseInt(message.trim()) - 1;
            if (index < 0) {
                throw new MangoException("invalid index");
            }
            return index;
        } catch (NumberFormatException exception) {
            throw new MangoException("not a number");
        }
    }
}