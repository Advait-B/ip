package mango.parser;

/**
 * Parses a raw user input line into a command type and its argument string.
 * Provides helper methods to validate and extract command parameters.
 */
public class MessageFormatter {

    private static final int BY_LENGTH = 3;
    private static final int FROM_LENGTH = 5;
    private static final int TO_LENGTH = 3;
    protected String taskType;
    protected String message;

    /**
     * Creates a formatter from the given user input.
     *
     * @param userInput Full input line entered by the user.
     * @throws MangoException If the input is null or blank.
     */
    public MessageFormatter(String userInput) throws MangoException {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new MangoException("null input");
        }
        String[] words = userInput.split(" ", 2);
        taskType = words[0].toLowerCase();
        message = words.length > 1 ? words[1] : "";
    }

    public String getTaskType() {
        return taskType;
    }

    /**
     * Returns the task description for a todo command.
     *
     * @return Task description.
     * @throws MangoException If the description is missing.
     */
    public String getTodoDescription() throws MangoException {
        if (message.trim().isEmpty()) {
            throw new MangoException("todo");
        }
        return message;
    }

    /**
     * Returns the task description and deadline component for a deadline command.
     *
     * @return Array of length 2: [description, by].
     * @throws MangoException If the command format is invalid or either component is missing.
     */
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

        return new String[] {description, by};
    }

    /**
     * Returns the task description, start time, and end time for an event command.
     *
     * @return Array of length 3: [description, from, to].
     * @throws MangoException If the command format is invalid or any component is missing.
     */
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

        return new String[] {description, from, to};
    }

    /**
     * Returns the zero-based task index specified by mark, unmark, or delete.
     *
     * @return Zero-based task index.
     * @throws MangoException If the index is missing, not a number, or less than 1.
     */
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

    /**
     * Returns the keyword used by the find command.
     *
     * @return Lowercased keyword for matching against task descriptions.
     * @throws MangoException If the keyword is missing.
     */
    public String getFindKeyword() throws MangoException {
        if (message.trim().isEmpty()) {
            throw new MangoException("find");
        }
        return message.trim().toLowerCase();
    }
}