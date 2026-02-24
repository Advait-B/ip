package mango.task;

import mango.storage.Storage;
import mango.parser.MangoException;

import java.util.ArrayList;

/**
 * Stores and manages the user's tasks, and persists changes via Storage.
 */
public class TaskList {
    private static final int MAX_TASKS = 100;
    private final Storage storage;
    private final ArrayList<Task> tasks;
    private int completedTasks;

    /**
     * Creates a task list backed by the given storage and loads existing tasks.
     *
     * @param storage Storage used to load and save tasks.
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = storage.load();
        this.completedTasks = 0;
        for (Task t : tasks) {
            if (t.isDone()) {
                completedTasks++;
            }
        }
    }

    /**
     * Adds a task to the task list and saves the updated list.
     *
     * @param task Task to add.
     * @throws MangoException If the task limit is reached.
     */
    public void addTask(Task task) throws MangoException {
        if (tasks.size() >= MAX_TASKS) {
            throw new MangoException("task limit");
        }
        tasks.add(task);
        storage.save(tasks);

        System.out.println("got it. i've added this task:");
        System.out.println("  " + task);
        System.out.println("now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Deletes the task at the specified index and saves the updated list.
     *
     * @param index Zero-based task index.
     * @throws MangoException If the index is out of range.
     */
    public void deleteTask(int index) throws MangoException {
        if (index < 0 || index >= tasks.size()) {
            throw new MangoException("invalid index");
        }

        Task removed = tasks.remove(index);
        storage.save(tasks);

        if (removed.isDone()) {
            completedTasks--;
        }

        System.out.println("noted. i've removed this task:");
        System.out.println("  " + removed);
        System.out.println("now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Prints tasks whose descriptions contain the given keyword.
     *
     * @param keyword Keyword used to match task descriptions.
     */
    public void findTask(String keyword) {
        System.out.println("looking for the matching tasks in your list:");
        int matchIndex = 1;
        for (Task task : tasks) {
            String description = task.getDescription().toLowerCase();
            if (description.contains(keyword)) {
                System.out.println(matchIndex + ". " + task);
                matchIndex++;
            }
        }
        if (matchIndex == 1) {
            System.out.println("oh dear, no matching tasks found :(");
        }
    }

    /**
     * Prints all tasks currently in the list.
     */
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("please add some tasks for us to list.");
            return;
        }

        if (completedTasks != 0) {
            System.out.println("hooray! you have completed " + completedTasks + " task(s).");
        }

        int remaining = tasks.size() - completedTasks;
        if (remaining != 0) {
            System.out.println("oh dear, you have " + remaining + " task(s) you must complete.");
        }

        System.out.println("here's your task list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Marks the task at the specified index as done and saves the updated list.
     *
     * @param index Zero-based task index.
     * @throws MangoException If the index is out of range.
     */
    public void markTaskAsDone(int index) throws MangoException {
        if (index < 0 || index >= tasks.size()) {
            throw new MangoException("invalid index");
        }

        Task task = tasks.get(index);
        if (task.isDone()) {
            throw new MangoException("already marked");
        }

        task.markAsDone();
        completedTasks++;
        storage.save(tasks);

        System.out.println("hooray! you've completed this task. i've marked it as done:");
        System.out.println(task);
    }

    /**
     * Marks the task at the specified index as not done and saves the updated list.
     *
     * @param index Zero-based task index.
     * @throws MangoException If the index is out of range.
     */
    public void unmarkTask(int index) throws MangoException {
        if (index < 0 || index >= tasks.size()) {
            throw new MangoException("invalid index");
        }

        Task task = tasks.get(index);
        if (!task.isDone()) {
            throw new MangoException("already unmarked");
        }

        task.unmark();
        completedTasks--;
        storage.save(tasks);

        System.out.println("oh dear, it seems you have not completed this task. i've unmarked it:");
        System.out.println(task);
    }
}
