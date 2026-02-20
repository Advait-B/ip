package mango.task;

import mango.storage.Storage;
import mango.parser.MangoException;

import java.util.ArrayList;

public class TaskManager {
    private static final int MAX_TASKS = 100;

    private final Storage storage;
    private final ArrayList<Task> tasks;
    private int completedTasks;

    /**
     * Creates a TaskManager that loads tasks using the given storage.
     */
    public TaskManager(Storage storage) {
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
     * Deletes a task from the task list at a specific index.
     */
    public void deleteTask(int index) throws MangoException {
        if (index < 0 || index >= tasks.size()) {
            throw new MangoException("invalid index");
        }

        Task removed = tasks.remove(index);

        if (removed.isDone()) {
            completedTasks--;
        }

        System.out.println("noted. i've removed this task:");
        System.out.println("  " + removed);
        System.out.println("now you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Prints all tasks currently stored in the task list.
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
     * @throws MangoException If the index is out of range or the task is already marked done.
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
     * Unmarks the task at the specified index and saves the updated list.
     *
     * @throws MangoException If the index is out of range or the task is already unmarked.
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
